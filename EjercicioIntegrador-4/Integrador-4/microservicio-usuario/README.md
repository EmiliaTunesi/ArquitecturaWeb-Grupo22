📘 Sistema de Gestión de Usuarios, Cuentas y Vinculaciones

API REST — Spring Boot

Este proyecto implementa un sistema simple y extensible para manejar usuarios, cuentas y la relación muchos a muchos entre ambos.
El resultado es una API clara, con modelos bien definidos, lógica desacoplada y documentación automática mediante Swagger.

La arquitectura busca ser limpia: cada pieza tiene su trabajo y no invade al resto. Un usuario puede tener muchas cuentas, una cuenta puede tener muchos usuarios, y nadie entra en crisis existencial por ello.

⭐ Características principales

Gestión completa de Usuarios (CRUD).

Gestión de Cuentas con campos financieros y estados operativos.

Relación N a N entre usuarios y cuentas mediante la entidad UsuarioCuenta.

Endpoints para:

vincular usuario–cuenta

listar vinculaciones

verificar si existe la vinculación

obtener usuarios asociados a una cuenta

DTOs y mapeadores para no mezclar café con té (ni entidades con requests).

Documentación automática con Swagger UI.

🔗 Explicación de la Relación N a N

La lógica del proyecto se basa en una relación muchos a muchos:

Un Usuario puede estar asociado a varias Cuentas.

Una Cuenta puede pertenecer a varios Usuarios.

En bases de datos relacionales, esto se resuelve mediante una tabla intermedia:

Usuario 1 -----\
\  
[ UsuarioCuenta ] ----- Cuenta 1
/
Usuario 2 -----/


En el código, la entidad intermedia es UsuarioCuenta.
Su existencia evita duplicar información, y permite agregar metadata futura sobre la relación (por ejemplo fecha de vinculación, permisos, rol dentro de la cuenta, etc.).

🧠 Modelos del Sistema
Usuario

Representa un cliente del sistema.

Campos clave:

nombreUsuario

nombre

apellido

email

telefono

fechaRegistro

activo

Cuenta

Representa una cuenta operativa del sistema.

Incluye:

número identificador

fecha de alta

tipo de cuenta (BÁSICA, PREMIUM, etc.)

saldo de créditos

kilómetros recorridos

activa/inactiva

fecha de renovación de cupo

integración opcional con Mercado Pago

UsuarioCuenta

Entidad que une usuario y cuenta.

Contiene solo los IDs y la relación, sin duplicar datos.

✅ Ejemplos JSON para Swagger (para usar en los “Request bodies”)
✅ Ejemplos para crear usuarios
{
"nombreUsuario": "azulito123",
"nombre": "Azul",
"apellido": "Montoya",
"email": "azul@example.com",
"telefono": "+54911223344",
"fechaRegistro": "2025-11-07T18:51:10.726Z",
"activo": true
}

{
"nombreUsuario": "marce_dev",
"nombre": "Marcelo",
"apellido": "Ruiz",
"email": "marce@example.com",
"telefono": "3512233445",
"fechaRegistro": "2025-11-07T18:51:10.726Z",
"activo": true
}

{
"nombreUsuario": "luna",
"nombre": "Luna",
"apellido": "Pereyra",
"email": "luna.pe@example.com",
"telefono": "1133221100",
"fechaRegistro": "2025-11-07T18:51:10.726Z",
"activo": false
}

✅ Ejemplos para crear cuentas
{
"numeroIdentificador": "CUENTA-001",
"fechaAlta": "2025-11-07",
"tipoCuenta": "BASICA",
"activa": true,
"fechaBaja": null,
"saldoCreditos": 50,
"cuentaMercadoPagoId": "mp_123",
"kmRecorridosMes": 10.5,
"fechaRenovacionCupo": "2025-12-01"
}

{
"numeroIdentificador": "CUENTA-002",
"fechaAlta": "2025-11-07",
"tipoCuenta": "PREMIUM",
"activa": true,
"fechaBaja": null,
"saldoCreditos": 120.75,
"cuentaMercadoPagoId": null,
"kmRecorridosMes": 0,
"fechaRenovacionCupo": null
}

{
"numeroIdentificador": "CUENTA-003",
"fechaAlta": "2025-11-07",
"tipoCuenta": "BASICA",
"activa": true,
"fechaBaja": null,
"saldoCreditos": 0,
"cuentaMercadoPagoId": null,
"kmRecorridosMes": null,
"fechaRenovacionCupo": null
}

🔌 Endpoints principales (por categoría)
📍 Usuarios

GET /usuarios

POST /usuarios

GET /usuarios/{id}

PUT /usuarios/{id}

DELETE /usuarios/{id}

📍 Cuentas

GET /cuentas

POST /cuentas

GET /cuentas/{id}

PUT /cuentas/{id}

DELETE /cuentas/{id}

📍 Vinculaciones Usuario–Cuenta

POST /usuarios-cuentas/vincular?usuarioId=&cuentaId=

GET /usuarios-cuentas

GET /usuarios-cuentas/existe?usuarioId=&cuentaId=

GET /usuarios-cuentas/cuenta/{cuentaId}/usuarios ✅ (nuevo endpoint añadido)

📚 Documentación con Swagger

El proyecto incluye Swagger / OpenAPI para documentar todos los endpoints y permitir probarlos desde el navegador.

Acceso:

http://localhost:8080/swagger-ui/index.html


Swagger muestra:

modelos de request y response

ejemplos listos para usar

documentación de cada endpoint

pruebas en tiempo real

Si un humano tuviera que leer tu código, Swagger sería el mapa; si el humano no lee, al menos podrá hacer clics.

🏗️ Arquitectura interna

El proyecto sigue una estructura clásica y limpia:

Controller → recibe requests

Service → maneja lógica de negocio

Repository → habla con la base de datos

DTO / Mapper → evita fuga de entidades al exterior

Entidad UsuarioCuenta → resuelve la relación N-a-N

Nada está de más y nada falta. Elegancia sin floritura.

✅ Cómo correr el proyecto

Clonar el repo

Importar en IntelliJ / Eclipse como proyecto Maven

Configurar datasource en application.properties

Ejecutar la aplicación

Abrir Swagger y jugar