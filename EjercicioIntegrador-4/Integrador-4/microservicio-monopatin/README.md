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

**Registra tareas de mantenimiento asociadas a un monopatín.**

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
