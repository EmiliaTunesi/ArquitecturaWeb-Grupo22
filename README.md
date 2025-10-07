# Arquitectura Web Grupo 22

La aplicación está desarrollada en Java utilizando Maven como gestor de dependencias y JPA/Hibernate como framework de persistencia. 
Permite gestionar información de estudiantes universitarios y sus inscripciones a carreras, conectándose a una base de datos MySQL ejecutada en Docker.

## Funcionalidad

La aplicación realiza las siguientes tareas principales:

### Creación del esquema de la base de datos

Utilizando Hibernate con configuración automática, se generan las siguientes tablas:
- **Estudiante**: Información personal de los estudiantes
- **Carrera**: Catálogo de carreras universitarias  
- **EstudianteCarrera**: Relación entre estudiantes y carreras con datos de inscripción y graduación

### Carga de datos desde archivos CSV

Se leen los archivos ubicados en `src/main/resources/`:
- `estudiantes.csv`
- `carreras.csv` 
- `estudianteCarrera.csv`

Y se insertan sus registros en la base de datos utilizando el sistema de repositorios implementado.

### Consultas y operaciones disponibles

1. **Dar de alta un estudiante**: Registrar nuevos estudiantes en el sistema
2. **Matricular estudiante en carrera**: Crear la relación estudiante-carrera
3. **Recuperar todos los estudiantes**: Listado ordenado de estudiantes
4. **Buscar estudiante por libreta universitaria**: Búsqueda específica por LU
5. **Filtrar estudiantes por género**: Consulta con criterio de género
6. **Carreras con estudiantes inscriptos**: Listado ordenado por cantidad de inscriptos
7. **Estudiantes por carrera y ciudad**: Filtrado combinado por carrera y ciudad de residencia
8. **Reporte de carreras**: Información detallada de inscriptos y graduados por año, ordenado alfabéticamente por carrera y cronológicamente por año

## Arquitectura

El proyecto sigue una arquitectura por capas:

- **Entities**: Clases de entidad JPA (Estudiante, Carrera, EstudianteCarrera)
- **DTO**: Objetos de transferencia de datos para las consultas
- **Repository**: Capa de acceso a datos con operaciones CRUD
- **Service**: Lógica de negocio y coordinación de operaciones
- **CsvReader**: Utilidad para carga masiva de datos desde archivos CSV

## Requisitos
- Java 17+
- Maven
- Docker y Docker Compose

## Configuración

### Base de datos con Docker

El proyecto incluye un archivo `docker-compose.yml` que configura automáticamente una base de datos MySQL con los siguientes parámetros:

- **Imagen**: MySQL 8.0
- **Puerto**: 3306
- **Base de datos**: TPE2db (creada automáticamente)
- **Usuario root**: root/root
- **Usuario adicional**: user/1234
- **Volumen persistente**: Los datos se mantienen entre reinicios del contenedor

Los parámetros de conexión están configurados en `src/main/resources/META-INF/persistence.xml` para conectarse al contenedor MySQL.

## Ejecución

1. Clonar el repositorio:
```bash
git clone <URL_DEL_REPOSITORIO>
cd EjercicioIntegrador-2
```

2. Levantar la base de datos MySQL con Docker:
```bash
docker-compose up -d
```
3. En `Main.java`, descomentar las operaciones que se deseen ejecutar:

4. Ejecutar la aplicación.

## Estructura del proyecto

```
src/
├── main/
│   ├── java/
│   │   ├── CsvReader/         # Utilidades para carga de CSV
│   │   ├── DTO/              # Objetos de transferencia de datos
│   │   ├── Entitys/          # Entidades JPA
│   │   ├── Repositorys/      # Capa de acceso a datos
│   │   ├── Service/          # Lógica de negocio
│   │   └── org/example/      # Clase principal
│   └── resources/
│       ├── *.csv            # Archivos de datos
│       └── META-INF/        # Configuración JPA
```
## Integrantes  
Candela Echazú Gomez  
Joaquin Hevia  
Lautaro Acosta  
María Emilia Tunesi  
Matías Fanucchi  
