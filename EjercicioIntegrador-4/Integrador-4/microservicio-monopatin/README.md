Sistema de Gestión de Monopatines y Mantenimientos

=======================================================

API REST — Spring Boot

Este microservicio implementa la gestión de monopatines eléctricos y sus mantenimientos preventivos y correctivos, dentro de una arquitectura basada en microservicios.
Provee endpoints REST claros y desacoplados para registrar, consultar y administrar monopatines, así como para gestionar los mantenimientos asociados a cada unidad.

Incluye documentación automática con Swagger / OpenAPI.

Características principales

Gestión completa de Monopatines (altas, bajas, estados, reportes).

Gestión de Mantenimientos asociados a monopatines.

Control de estado de servicio (en servicio, fuera de servicio, disponible, etc.).

Generación de reportes de uso y consultas filtradas.

Arquitectura limpia con capas bien separadas (Controller, Service, Repository).

Modelos del Sistema
🛴 Monopatín

Representa una unidad operativa del sistema de movilidad.

Atributos típicos:

id

latitud / longitud actual

estado (DISPONIBLE, EN_USO, FUERA_SERVICIO, MANTENIMIENTO)

kilómetros recorridos

fecha de alta

fecha de última revisión

activo/inactivo

🧰 Mantenimiento

Representa una tarea de mantenimiento aplicada a un monopatín.

id

monopatinId (referencia al monopatín asociado)

tipo (PREVENTIVO o CORRECTIVO)

fechaInicio

fechaFin

descripción

técnico asignado

estado (EN_PROCESO, FINALIZADO)

Endpoints disponibles
🚲 Monopatines

Base path: /api/monopatines

Método	Endpoint	Descripción
POST	/api/monopatines	Crea un nuevo monopatín.
PUT	/api/monopatines/{id}/fuera-servicio	Marca un monopatín como fuera de servicio.
GET	/api/monopatines/{id}/disponible	Marca o consulta la disponibilidad de un monopatín.
PUT	/api/monopatines/{id}/finalizar	Finaliza un viaje o proceso asociado al monopatín.
GET	/api/monopatines/{id}	Obtiene los datos de un monopatín específico.
GET	/api/monopatines	Lista todos los monopatines.
GET	/api/monopatines/reporte-uso	Devuelve un reporte agregado del uso de los monopatines.
🔧 Mantenimientos

Base path: /api/mantenimientos

Método	Endpoint	Descripción
POST	/api/mantenimientos	Registra un nuevo mantenimiento.
PUT	/api/mantenimientos/{id}/finalizar	Marca un mantenimiento como finalizado.
GET	/api/mantenimientos/{id}	Obtiene los datos de un mantenimiento específico.
GET	/api/mantenimientos	Lista todos los mantenimientos.
GET	/api/mantenimientos/monopatin/{monopatinId}	Lista los mantenimientos de un monopatín determinado.
Documentación con Swagger

El proyecto incluye Swagger / OpenAPI para documentar y probar los endpoints directamente desde el navegador.
