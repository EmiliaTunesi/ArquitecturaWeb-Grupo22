# Arquitectura Web - Grupo 22

La aplicación está desarrollada en **Java** utilizando **Maven** como gestor de dependencias, y permite trabajar con datos de clientes, productos y facturas, conectándose a diferentes motores de base de datos.

## Funcionalidad

La aplicación realiza las siguientes tareas principales:

- **Creación del esquema de base de datos**
    
    Según el motor configurado (PostgreSQL o Derby), se ejecutan los scripts necesarios para generar las tablas.
    
- **Carga de datos desde archivos CSV**
    
    Se leen los archivos:
    
    - `clientes.csv`
    - `productos.csv`
    - `facturas.csv`
    - `facturas-productos.csv`
    
    Y se insertan sus registros en la base de datos.
    
- **Consultas y reportes**
    - Identificar el **cliente con más facturas**.
    - Determinar la **máxima recaudación** obtenida.

## Requisitos

- **Java 17+**
- **Maven**
- Motor de base de datos (según se desee usar):
    - PostgreSQL
    - Apache DERBY

## Ejecución

1. Clonar el repositorio y moverse a la rama master:
    
    ```bash
    git clone <https://github.com/EmiliaTunesi/ArquitecturaWeb-Grupo22.git>
    cd ArquitecturaWeb-Grupo22
    git checkout master
    ```
    
2. En el código (`Main.java`) definir con qué base de datos trabajar:

```java
int db = DAOFactory.POSTGRES_JDBC; // o DAOFactory.DERBY_JDBC

```

## Integrantes
Candela Echazú Gomez  
Joaquin Hevia  
Lautaro Acosta  
María Emilia Tunesi  
Matías Falucchi  
