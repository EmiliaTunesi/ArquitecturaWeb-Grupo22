# Análisis Completo del Repositorio - ArquitecturaWeb Grupo 22

## 📋 Resumen Ejecutivo

Este repositorio pertenece al **Grupo 22** de la materia Arquitectura Web y contiene **4 trabajos prácticos integradores** que muestran la evolución de una aplicación de gestión universitaria, progresando desde arquitecturas básicas hasta microservicios modernos.

## 👥 Integrantes del Grupo

- Candela Echazú Gomez
- Joaquin Hevia
- Lautaro Acosta
- María Emilia Tunesi
- Matías Fanucchi

## 🎯 Dominio del Proyecto

El proyecto se centra en la **gestión de estudiantes universitarios y sus carreras**, permitiendo:
- Registrar estudiantes y carreras
- Gestionar inscripciones
- Realizar consultas y reportes
- Analizar estadísticas académicas

---

## 📚 Ejercicios Integradores

### 🔹 Ejercicio Integrador 1: DAO Pattern con JDBC

**Ubicación**: `/EjercicioIntegrador-1/`

**Objetivo**: Implementar el patrón DAO (Data Access Object) con JDBC puro

**Tecnologías**:
- Java 17
- Maven
- JDBC
- PostgreSQL / Apache Derby
- Patrón Factory para crear DAOs

**Características principales**:
- Arquitectura basada en el patrón DAO
- Soporte para múltiples bases de datos (PostgreSQL y Derby)
- Carga de datos desde archivos CSV
- Consultas sobre clientes, productos y facturas

**Estructura**:
```
src/main/java/org/example/app/
├── dao/
│   ├── interfaces/        # Interfaces DAO
│   ├── implPostgres/     # Implementación PostgreSQL
│   ├── impl/             # Implementación Derby
│   └── factory/          # Factory pattern para DAOs
└── Main.java
```

**Funcionalidades**:
- Crear esquema de base de datos
- Cargar datos desde CSV (clientes, productos, facturas)
- Consultar cliente con más facturas
- Calcular máxima recaudación

---

### 🔹 Ejercicio Integrador 2: JPA/Hibernate

**Ubicación**: `/EjercicioIntegrador-2/EjercicioIntegrador-2/`

**Objetivo**: Migrar de JDBC a JPA/Hibernate con gestión de relaciones many-to-many

**Tecnologías**:
- Java 17
- Maven
- JPA (Java Persistence API)
- Hibernate 5.6.15
- MySQL 8.0 con Docker
- Apache Commons CSV

**Características principales**:
- Arquitectura en capas (Entity, Repository, Service, DTO)
- Relaciones JPA bidireccionales
- Docker Compose para base de datos
- Consultas con JPQL y Criteria API

**Estructura**:
```
src/main/java/
├── Entitys/              # Entidades JPA
│   ├── Estudiante
│   ├── Carrera
│   └── EstudianteCarrera
├── Repositorys/          # Capa de acceso a datos
├── Service/              # Lógica de negocio
├── DTO/                  # Data Transfer Objects
├── CsvReader/            # Utilidad de carga CSV
└── org/example/Main.java
```

**Funcionalidades**:
1. Alta de estudiantes
2. Matricular estudiante en carrera
3. Recuperar todos los estudiantes ordenados
4. Buscar estudiante por libreta universitaria
5. Filtrar estudiantes por género
6. Listar carreras con estudiantes inscriptos
7. Filtrar estudiantes por carrera y ciudad
8. Reporte de inscriptos y graduados por año

**Base de datos**:
- MySQL 8.0 en Docker
- Usuario: user/1234
- Base de datos: TPE2db
- Puerto: 3306

---

### 🔹 Ejercicio Integrador 3: REST API con Spring Boot

**Ubicación**: `/EjercicioIntegrador-3/ejercicioIntegrador3/ejercicioIntegrador3/`

**Objetivo**: Crear una API REST completa usando Spring Boot

**Tecnologías**:
- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Web
- MySQL 8.0 con Docker
- Lombok
- Maven Wrapper
- H2 (para tests)

**Características principales**:
- Arquitectura REST con controladores
- DTOs separados para request y response
- Validación de datos
- DevTools para desarrollo
- Colección Postman incluida

**Estructura**:
```
src/main/java/com/arquitecturaweb/ejercicioIntegrador3/
├── entity/               # Entidades JPA
├── dto/
│   ├── request/         # DTOs de entrada
│   └── response/        # DTOs de salida
├── service/             # Servicios de negocio
├── repository/          # Repositorios Spring Data
└── controller/          # Controladores REST
```

**Endpoints REST**:
Los endpoints exponen operaciones CRUD y consultas sobre:
- Estudiantes
- Carreras
- Inscripciones (Estudiante_Carrera)

**Características Spring**:
- Spring Data JPA para repositorios
- Validación con Bean Validation
- Configuración en `application.properties`
- Colección Postman en `/ej3-postman/`

---

### 🔹 Ejercicio Integrador 4: Microservicios

**Ubicación**: `/EjercicioIntegrador-4/Integrador-4/`

**Objetivo**: Arquitectura de microservicios con Spring Boot

**Tecnologías**:
- Java 21
- Spring Boot 3.5.7
- Arquitectura de Microservicios
- Maven

**Características principales**:
- Arquitectura distribuida
- Múltiples microservicios independientes
- Proyecto padre con módulos

**Estructura**:
```
Integrador-4/
├── pom.xml                          # POM padre
├── src/                             # Aplicación principal
└── microservicio-administrador/     # Microservicio
    └── src/
```

**Microservicios identificados**:
1. **Microservicio Administrador**: Gestión administrativa
2. **Aplicación Principal**: Coordinador/Gateway

**Estado**: Estructura inicial de proyecto Spring Boot (en desarrollo)

---

## 🔄 Evolución Tecnológica

El repositorio muestra una clara progresión:

1. **Ejercicio 1**: JDBC puro → Fundamentos de acceso a datos
2. **Ejercicio 2**: JPA/Hibernate → ORM y gestión automática
3. **Ejercicio 3**: Spring Boot REST → APIs modernas
4. **Ejercicio 4**: Microservicios → Arquitectura distribuida

## 📊 Tecnologías Utilizadas

### Lenguajes y Frameworks
- **Java**: 17 (ejercicios 1-3) y 21 (ejercicio 4)
- **Spring Boot**: 3.5.6 y 3.5.7
- **Hibernate**: 5.6.15

### Bases de Datos
- **MySQL 8.0**: Producción (con Docker)
- **Apache Derby**: Testing/desarrollo
- **H2**: Testing en memoria

### Herramientas de Construcción
- **Maven**: Gestión de dependencias
- **Maven Wrapper**: Incluido en ejercicios 3 y 4

### DevOps y Contenedores
- **Docker Compose**: Gestión de MySQL
- **Git**: Control de versiones

### Librerías Adicionales
- **Apache Commons CSV**: Carga de datos
- **Lombok**: Reducción de boilerplate
- **Spring DevTools**: Hot reload

## 🏗️ Patrones Arquitectónicos Aplicados

1. **DAO Pattern** (Ejercicio 1)
2. **Factory Pattern** (Ejercicio 1)
3. **Repository Pattern** (Ejercicio 2, 3, 4)
4. **DTO Pattern** (Ejercicio 2, 3, 4)
5. **Service Layer** (Ejercicio 2, 3, 4)
6. **MVC** (Ejercicio 3, 4)
7. **REST** (Ejercicio 3)
8. **Microservices** (Ejercicio 4)

## 📁 Estructura General del Repositorio

```
ArquitecturaWeb-Grupo22/
├── README.md                           # Documentación principal (Ejercicio 2)
├── ANALISIS_REPOSITORIO.md            # Este archivo
├── EjercicioIntegrador-1/              # JDBC y DAO Pattern
│   ├── pom.xml
│   ├── Readme.md
│   └── src/
├── EjercicioIntegrador-2/              # JPA/Hibernate
│   └── EjercicioIntegrador-2/
│       ├── pom.xml
│       ├── docker-compose.yml
│       └── src/
├── EjercicioIntegrador-3/              # Spring Boot REST API
│   └── ejercicioIntegrador3/
│       └── ejercicioIntegrador3/
│           ├── pom.xml
│           ├── docker-compose.yml
│           ├── mvnw
│           ├── ej3-postman/
│           └── src/
├── EjercicioIntegrador-4/              # Microservicios
│   └── Integrador-4/
│       ├── pom.xml
│       ├── mvnw
│       ├── microservicio-administrador/
│       └── src/
└── integradorDB/                       # Base de datos Derby local
```

## 🚀 Cómo Ejecutar

### Ejercicio 1 - JDBC
```bash
cd EjercicioIntegrador-1
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.app.Main"
```

### Ejercicio 2 - JPA/Hibernate
```bash
cd EjercicioIntegrador-2/EjercicioIntegrador-2
docker-compose up -d
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### Ejercicio 3 - Spring Boot REST
```bash
cd EjercicioIntegrador-3/ejercicioIntegrador3/ejercicioIntegrador3
docker-compose up -d
./mvnw spring-boot:run
# Importar colección Postman desde ej3-postman/
```

### Ejercicio 4 - Microservicios
```bash
cd EjercicioIntegrador-4/Integrador-4
./mvnw spring-boot:run
```

## 📝 Datos de Prueba

Los ejercicios incluyen archivos CSV para carga inicial:
- `estudiantes.csv`
- `carreras.csv`
- `estudianteCarrera.csv`
- `clientes.csv`
- `productos.csv`
- `facturas.csv`
- `facturas-productos.csv`

## 🔍 Consultas y Operaciones Disponibles

### Operaciones Comunes (Ejercicios 2 y 3)
- ✅ CRUD de Estudiantes
- ✅ CRUD de Carreras
- ✅ Matriculación de estudiantes
- ✅ Búsqueda por libreta universitaria
- ✅ Filtrado por género
- ✅ Filtrado por ciudad y carrera
- ✅ Reportes de inscriptos/graduados por año
- ✅ Carreras ordenadas por cantidad de inscriptos

## 🎓 Conceptos de Arquitectura Web Aplicados

1. **Separación de Responsabilidades**: Capas bien definidas
2. **Inversión de Dependencias**: Uso de interfaces
3. **Inyección de Dependencias**: Spring Framework
4. **ORM**: Hibernate/JPA
5. **REST**: Arquitectura de servicios web
6. **Persistencia**: Múltiples estrategias
7. **Contenedorización**: Docker
8. **Escalabilidad**: Microservicios

## 📚 Propósito Académico

Este repositorio demuestra:
- Evolución de arquitecturas de software
- Migración progresiva de tecnologías
- Buenas prácticas de desarrollo
- Patrones de diseño enterprise
- Trabajo en equipo

## 🔗 Tecnologías Clave por Ejercicio

| Ejercicio | Arquitectura | Persistencia | Framework |
|-----------|-------------|--------------|-----------|
| 1 | DAO + JDBC | JDBC puro | Java SE |
| 2 | Repository | JPA/Hibernate | Java SE + JPA |
| 3 | REST MVC | Spring Data JPA | Spring Boot |
| 4 | Microservicios | Spring Data JPA | Spring Boot |

---

## 💡 Conclusión

Este repositorio representa un **portafolio completo** de aprendizaje en Arquitectura Web, mostrando:

- ✅ Dominio de Java y sus ecosistemas
- ✅ Comprensión de patrones arquitectónicos
- ✅ Evolución de monolito a microservicios
- ✅ Uso de herramientas modernas (Docker, Spring Boot)
- ✅ Aplicación de buenas prácticas
- ✅ Trabajo colaborativo en equipo

El proyecto demuestra una sólida comprensión de cómo construir aplicaciones empresariales escalables y mantenibles utilizando diferentes aproximaciones arquitectónicas.
