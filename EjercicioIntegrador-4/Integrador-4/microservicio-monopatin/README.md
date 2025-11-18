<<<<<<< HEAD
Sistema de Gestión de Monopatines y su Mantenimiento
API REST — Spring Boot
Este proyecto implementa un sistema simple y extensible para manejar mantenimientos y su mantenimientocuentas. El resultado es una API clara, con modelos bien definidos, lógica desacoplada y documentación automática mediante Swagger.

Características principales
Gestión completa de Monopatines (CRUD).


Explicación de la Relación N a N
La lógica del proyecto se basa en una relación muchos a muchos:

Un Usuario puede estar asociado a varias Cuentas.

Una Cuenta puede pertenecer a varios Usuarios.

En el código, la entidad intermedia entre ambos es UsuarioCuenta. Su existencia evita duplicar información, y permite agregar metadata futura sobre la relación (por ejemplo fecha de vinculación, permisos, rol dentro de la cuenta, etc.).

Modelos del Sistema
Usuario
Representa un cliente del sistema.

nombreUsuario

nombre

apellido

email

telefono

fechaRegistro

activo

Cuenta
Representa una cuenta operativa del sistema.

número id
=======
Sistema de Gestión de Monopatines y Mantenimientos
==================================================

API REST — Spring Boot
----------------------

Este microservicio forma parte del ecosistema de gestión de monopatines y se encarga de administrarlos y sus respectivos **mantenimientos**.

Brinda una API REST clara, con modelos bien definidos, endpoints organizados y soporte para documentación automática mediante Swagger / OpenAPI.

---

Características principales
---------------------------

- Gestión completa de **Monopatines** (alta, consulta, actualización de estado, reportes de uso).  
- Gestión de **Mantenimientos** asociados a los monopatines.  
- Lógica de negocio desacoplada mediante servicios y repositorios.  
- Integración con Swagger para documentación automática.  

---

Modelos del Sistema
-------------------

### 🛴 Monopatín

**Representa un vehículo eléctrico disponible para uso dentro del sistema.**

Campos principales:
- id  
- fechaAlta  
- estado (DISPONIBLE, FUERA_DE_SERVICIO, EN_USO)  
- kilómetrosRecorridos  
- tiempoUsoTotal
- tiempoPausaTotal  
- fechaÚltimoMantenimiento  

**Lógica clave:**
- Puede estar disponible, en mantenimiento, en uso o fuera de servicio.
- Registra su historial de uso y kilómetros recorridos.
- Permite generar reportes de uso agregados.

---

### 🔧 Mantenimiento
>>>>>>> 61621827197cf873a5ac1663911b1acaa7115355

**Registra tareas de mantenimiento asociadas a un monopatín.**

<<<<<<< HEAD
tipo de cuenta (BÁSICA, PREMIUM)

saldo de créditos

kilómetros recorridos

activa/inactiva

fecha de renovación de cupo

integración con Mercado Pago (hardcodeado)

UsuarioCuenta
Entidad que une usuario y cuenta.

Contiene solo los IDs y la relación.

Endpoints
Usuarios
GET /usuarios

POST /usuarios

GET /usuarios/{id}

PUT /usuarios/{id}

DELETE /usuarios/{id}

Cuentas
GET /cuentas

POST /cuentas

GET /cuentas/{id}

PUT /cuentas/{id}

DELETE /cuentas/{id}

PATCH /cuentas/{id}/anular
Inhabilita la cuenta (soft delete).
Marca la cuenta como inactiva y setea fechaBaja si corresponde.

PATCH /cuentas/{id}/reactivar
Vuelve a activar una cuenta previamente anulada.

Vinculaciones Usuario–Cuenta
POST /usuarios-cuentas/vincular?usuarioId=&cuentaId=

GET /usuarios-cuentas

GET /usuarios-cuentas/existe?usuarioId=&cuentaId=

GET /usuarios-cuentas/cuenta/{cuentaId}/usuarios

Documentación con Swagger
El proyecto incluye Swagger / OpenAPI para documentar todos los endpoints y permitir probarlos desde el navegador.

Acceso:

http://localhost:8085/swagger-ui/index.html

Arquitectura interna
Controller → recibe requests

Service → maneja lógica de negocio

Repository → habla con la base de datos

DTO / Mapper → evita fuga de entidades al exterior

Entidad UsuarioCuenta → resuelve la relación N-a-N

Cómo correr el proyecto
Clonar el repo

Importar en IntelliJ / Eclipse como proyecto Maven

Configurar datasource en application.properties (esta aplicación esta configurada para usar postgress)

Ejecutar la aplicación

Abrir Swagger y probar
=======
Campos principales:
- id  
- monopatinId (referencia al vehículo mantenido)  
- fechaInicio  
- fechaFin  
- tipoMantenimiento  
- descripción
- estadoMnatenimiento    

**Lógica clave:**
- Cada mantenimiento se asocia a un monopatín.  
- Puede marcarse como finalizado.  
- Permite consultar mantenimientos por monopatín.  

---

Endpoints
---------

### 🛴 Monopatines

Base URL: `/api/monopatines`

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| **POST** | `/api/monopatines` | Crea un nuevo monopatín. |
| **PUT** | `/api/monopatines/{id}/fuera-servicio` | Marca el monopatín como fuera de servicio. |
| **GET** | `/api/monopatines/{id}/disponible` | Verifica si un monopatín está disponible. |
| **PUT** | `/api/monopatines/{id}/finalizar` | Marca el fin de un viaje o uso activo. |
| **GET** | `/api/monopatines/{id}` | Obtiene un monopatín por su ID. |
| **GET** | `/api/monopatines` | Lista todos los monopatines registrados. |
| **GET** | `/api/monopatines/reporte-uso` | Genera un reporte de uso (tiempo, distancia, disponibilidad). |

---

### 🔧 Mantenimientos

Base URL: `/api/mantenimientos`

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| **POST** | `/api/mantenimientos` | Registra un nuevo mantenimiento. |
| **PUT** | `/api/mantenimientos/{id}/finalizar` | Marca un mantenimiento como finalizado. |
| **GET** | `/api/mantenimientos/{id}` | Obtiene un mantenimiento por ID. |
| **GET** | `/api/mantenimientos` | Lista todos los mantenimientos. |
| **GET** | `/api/mantenimientos/monopatin/{monopatinId}` | Lista los mantenimientos asociados a un monopatín específico. |

---

Documentación con Swagger
-------------------------

El proyecto incluye Swagger / OpenAPI para documentar y probar los endpoints directamente desde el navegador.

Acceso local:
http://localhost:8085/swagger-ui/index.html
>>>>>>> 61621827197cf873a5ac1663911b1acaa7115355
