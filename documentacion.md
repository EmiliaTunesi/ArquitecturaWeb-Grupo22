# Resumen

La aplicación arranca desde `Main.main()` y realiza (en ese orden):

1. crear el esquema (tablas) (`CrearEsquema.run(db)`),
2. cargar datos desde los CSV (`CargarDatos.run(db)`),
3. ejecutar la consulta del producto que más recaudó (`DevolverMaxRecaudacion.run(db)`),
4. listar clientes ordenados por cantidad de facturas (`ClienteConMasFacturas.run(db)`),
5. cerrar la conexión / apagar Derby según corresponda.
    
    El `db` (selector) determina si se usan implementaciones/SQL para **Derby** o **Postgres** (vía `DAOFactory` y `ConnectionFactory` / `PostgresSingletonConnection`).
    

---

# Flujo paso a paso

1. **Inicio** — `Main.main(String[] args)`
    - Se define `int db = DAOFactory.POSTGRES_JDBC; // o DAOFactory.DERBY_JDBC` **(desde aca se cambia la db).**
    - En el `try` se llama en secuencia a:
        - `CrearEsquema.run(db);`
        - `CargarDatos.run(db);`
        - `DevolverMaxRecaudacion.run(db);`
        - `ClienteConMasFacturas.run(db);`
    - Al final cierra la DB: si `DERBY_JDBC` intenta `DriverManager.getConnection("jdbc:derby:;shutdown=true")` (esto lanza excepción a propósito para indicarlo), si `POSTGRES_JDBC` llama `PostgresSingletonConnection.closeConnection()`.
2. **CrearEsquema.run(db)**
    - `boolean esPostgres = (db == DAOFactory.POSTGRES_JDBC);`
    - Obtiene `Connection conn = esPostgres ? PostgresSingletonConnection.getConnection() : DAOFactory.getConnectionFactory(db).getConnection();`
    - Crea un `Statement` y:
        - intenta `DROP TABLE` de `factura_producto`, `factura`, `producto`, `cliente` (ignora excepciones si no existen),
        - ejecuta los `CREATE TABLE` adecuados según `esPostgres` (hay **dos** versiones de SQL: una para Postgres — usa `SERIAL` y nombres con `_` — y otra para Derby — usa `INT` y nombres en camelCase).
    - Imprime `"Esquema de tablas creado"`.
    - Cierra `Statement` y `Connection` (cuando se usa `PostgresSingletonConnection` la conexión se mantiene por el singleton; con Derby se cierra al terminar el try-with-resources).
3. **CargarDatos.run(db)** (flujo de carga CSV → DB)
    - **`conn = PostgresSingletonConnection.getConnection()`**
    - Luego obtiene `DAOFactory daoFactory = DAOFactory.getDAOFactory(dbId);` (esto devuelve la fábrica de DAOs para Derby o Postgres).
    - Llama, en orden:
        - `leerClientesDesdeCSV(daoFactory.getClientDAO(), "clientes.csv");`
        - `leerProductosDesdeCSV(daoFactory.getProductDAO(), "productos.csv");`
        - `leerFacturasDesdeCSV(daoFactory.getFactureDAO(), "facturas.csv");`
        - `leerFacturaProductosDesdeCSV(daoFactory.getFacture_ProductDAO(), "facturas-productos.csv");`
    - Cada `leerXDesdeCSV`:
        - usa `Apache Commons CSV` (`CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));`)
        - itera filas, parsea campos (haciendo `trim()` y controles), crea la entidad (`Cliente`, `Producto`, `Factura`, `FacturaProducto`) y llama `dao.insertar(entidad);`
    - **Commit / cierre**: el código hace `conn.commit();` y en `finally` `PostgresSingletonConnection.closeConnection();` (es decir, commit y close pensado para Postgres singleton).
    - **Diferencia entre implementaciones**:
        - **Postgres DAOs** (implementaciones en `dao.implPostgres`) usan `PostgresSingletonConnection.getConnection()` internamente y *no* hacen commit en cada `insertar` — se espera un commit al final (por eso `CargarDatos` llama `conn.commit()`).
        - **Derby DAOs** (implementaciones en `dao.impl`) **reciben un `Connection` en el constructor** y en sus `insertar(...)` hacen `ps.executeUpdate(); conn.commit();` — es decir, hacen commit por cada insert.
4. **DevolverMaxRecaudacion.run(dbId)**
    - `boolean esPostgres = (dbId == DAOFactory.POSTGRES_JDBC);`
    - `ConnectionFactory factory = DAOFactory.getConnectionFactory(dbId);`
    - SQL (tal como está):
        
        ```sql
        SELECT p.id_producto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion
        FROM producto p
        JOIN factura_producto fp ON p.id_producto = fp.id_producto
        GROUP BY p.id_producto, p.nombre
        ORDER BY recaudacion DESC
        FETCH FIRST ROW ONLY
        
        ```
        
    - Abre conexión: si `esPostgres` usa `PostgresSingletonConnection.getConnection()`; si no, `factory.getConnection()`. Ejecuta `PreparedStatement`, lee `ResultSet` y muestra `id`, `nombre`, `recaudacion`.
5. **ClienteConMasFacturas.run(dbId)**
    - Similar a DevolverMaxRecaudacion: arma SQL para contar facturas por cliente:
        
        ```sql
        SELECT c.id_client, c.nombre, c.email, COUNT(f.id_client) AS cantidad_facturas
        FROM cliente c
        LEFT JOIN factura f ON c.id_client = f.id_client
        GROUP BY c.id_client, c.nombre, c.email
        ORDER BY cantidad_facturas DESC
        
        ```
        
    - Ejecuta la consulta usando `PostgresSingletonConnection` o `factory.getConnection()` según `dbId`, imprime lista ordenada.
6. **Cierre** — `Main` (al final) hace:
    - si `db == DAOFactory.DERBY_JDBC` intenta `DriverManager.getConnection("jdbc:derby:;shutdown=true")` dentro de un `try/catch` y cuando recibe la excepción imprime `"Conexión Derby cerrada"`;
    - si `db == DAOFactory.POSTGRES_JDBC` llama `PostgresSingletonConnection.closeConnection()`.

---

# Componentes y responsabilidades

- `Main` — orquesta la ejecución y decide `db` (Derby / Postgres).
- `CrearEsquema` — crea (o recrea) las tablas con SQL distinto según DB.
- `CargarDatos` — lee CSVs y llama a los DAOs para insertar.
- `DevolverMaxRecaudacion` — consulta agregada por producto.
- `ClienteConMasFacturas` — consulta de clientes ordenados por facturas.
- `entity.*` (`Cliente`, `Producto`, `Factura`, `FacturaProducto`) — modelos simples.
- `dao.interfaces.*` — interfaces DAO.
- `dao.implPostgres.*` — implementaciones Postgres (usan `PostgresSingletonConnection`).
- `dao.impl.*` — implementaciones Derby (reciben `Connection` y hacen commit por insert).
- `dao.factory.*` (`DAOFactory`, `PostgresDAOFactory`, `DerbyDAOFactory`) — el patrón Factory que devuelve DAOs/ConnectionFactory según `db`.
- `utils.*` — `ConnectionFactory`, `DerbyConnectionFactory`, `PostgresConnectionFactory`, `PostgresSingletonConnection` (manejan la conexión real).

---

# Cómo se cambia entre Derby y Postgres (pasos prácticos)

1. Abrir `Main.java`. Cambiá la línea:
    
    ```java
    int db = DAOFactory.POSTGRES_JDBC; // o DAOFactory.DERBY_JDBC
    
    ```
    a la db que se quiera usar
    
2. **configurar la conexión**:
    - Para **Postgres**: editá `PostgresConnectionFactory` (host, puerto, nombre de DB) y que el servidor esté corriendo.
    - Para **Derby (embebido)**: `DerbyConnectionFactory`  (no necesita servidor).
3. Ejecutá la app desde el **directorio del proyecto** (o definí Working Directory en IntelliJ como la raíz) — los `FileReader("clientes.csv")` y demás usan rutas relativas y deben encontrarse (los CSV están en `src/main/resources` — si ejecutás desde IDE conviene usar la ruta relativa correcta o cargar como recurso).
4. Run `Main.`