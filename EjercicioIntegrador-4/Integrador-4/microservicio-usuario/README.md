 Sistema de Gestión de Usuarios, Cuentas y Vinculaciones
 =======================================================

API REST — Spring Boot
----------------------

Este proyecto implementa un sistema simple y extensible para manejar usuarios, cuentas y la relación muchos a muchos entre ambos.
El resultado es una API clara, con modelos bien definidos, lógica desacoplada y documentación automática mediante Swagger.

Características principales
---------------------------

Gestión completa de Usuarios (CRUD).

Gestión de Cuentas con campos financieros y estados operativos.

Relación N a N entre usuarios y cuentas mediante la entidad UsuarioCuenta.

Explicación de la Relación N a N
---------------------------------

La lógica del proyecto se basa en una relación muchos a muchos:

Un Usuario puede estar asociado a varias Cuentas.

Una Cuenta puede pertenecer a varios Usuarios.

En el código, la entidad intermedia entre ambos es UsuarioCuenta.
Su existencia evita duplicar información, y permite agregar metadata futura sobre la relación (por ejemplo fecha de vinculación, permisos, rol dentro de la cuenta, etc.).

 Modelos del Sistema
 -------------------
 
#### Usuario

**Representa un cliente del sistema.**

- nombreUsuario

- nombre

- apellido

- email

- telefono

- fechaRegistro

- activo

#### Cuenta

**Representa una cuenta operativa del sistema.**

- número id

- fecha de alta

- tipo de cuenta (BÁSICA, PREMIUM)

- saldo de créditos

- kilómetros recorridos

- activa/inactiva

- fecha de renovación de cupo

- integración con Mercado Pago (hardcodeado)

#### UsuarioCuenta 

**Entidad que une usuario y cuenta.**

Contiene solo los IDs y la relación.


Endpoints
---------

#### Usuarios 

GET /usuarios

POST /usuarios

GET /usuarios/{id}

PUT /usuarios/{id}

DELETE /usuarios/{id}

#### Cuentas

GET /cuentas

POST /cuentas

GET /cuentas/{id}

PUT /cuentas/{id}

DELETE /cuentas/{id}

PATCH /cuentas/{id}/anular  <br>
Inhabilita la cuenta (soft delete).  <br>
Marca la cuenta como inactiva y setea fechaBaja si corresponde.  <br>

PATCH /cuentas/{id}/reactivar  <br>
Vuelve a activar una cuenta previamente anulada.

#### Vinculaciones Usuario–Cuenta

POST /usuarios-cuentas/vincular?usuarioId=&cuentaId=

GET /usuarios-cuentas

GET /usuarios-cuentas/existe?usuarioId=&cuentaId=

GET /usuarios-cuentas/cuenta/{cuentaId}/usuarios 


Documentación con Swagger
-------------------------

El proyecto incluye Swagger / OpenAPI para documentar todos los endpoints y permitir probarlos desde el navegador.

Acceso:

http://localhost:8080/swagger-ui/index.html


Arquitectura interna
--------------------


Controller → recibe requests

Service → maneja lógica de negocio

Repository → habla con la base de datos

DTO / Mapper → evita fuga de entidades al exterior

Entidad UsuarioCuenta → resuelve la relación N-a-N



##### Cómo correr el proyecto

Clonar el repo

Importar en IntelliJ / Eclipse como proyecto Maven

Configurar datasource en application.properties (esta aplicación esta configurada para usar postgress)

Ejecutar la aplicación

Abrir Swagger y probar
