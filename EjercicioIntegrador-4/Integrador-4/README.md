# Documentación de Endpoints – Trabajo Integrador 4 (Primera Parte)

## 🔗 Swagger UI – Documentación por Microservicio

Podés acceder a la documentación interactiva de cada microservicio a través de los siguientes enlaces:

- **Usuarios Service** → [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)  
- **Viajes Service** → [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)  
- **Monopatines Service** → [http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html)  
- **Paradas Service** → [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)  
- **Tarifas Service** → [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)

---


📘 **Notas generales**  
- Todos los endpoints están accesibles a través del API Gateway (`localhost:8082`).  
- Los campos de tipo fecha deben seguir el formato `yyyy-mm-dd`.
- La base de datos utilizada es Postgres, crearlas con los siguientes nombres antes de levantar el proyecto: micro_viaje, base_paradas, microservicio_monopatin_db, base-microservicio, usuarios_db.


## a. Reporte de uso de monopatines
**Objetivo:** Permite generar un reporte de uso por kilómetros, configurable para incluir o no los tiempos de pausa.  
**Endpoint:**  
GET http://localhost:8082/api/monopatines/reporte-uso?incluirPausas=true  
**Parámetros:**  
- incluirPausas *(booleano, opcional)*: true o false. Determina si el reporte considera los tiempos de pausa.  
**Microservicio:** monopatines-service

---

## b. Anular cuenta de usuario (desde el csv se cargan 5 usuarios y 5 cuentas, es decir ids disponibles para pruebas: 1,2,3,4,5)
**Objetivo:** Permite al administrador anular (inhabilitar temporalmente) una cuenta de usuario.  
**Endpoint:**  
PUT http://localhost:8082/api/cuentas/{id}/anular  
**Parámetros de ruta:**  
- {id}: ID del usuario a anular.  
**Microservicio:** cuentas-service

---

## c. Monopatines con más de X viajes en un año
**Objetivo:** Consulta los monopatines que superan una cantidad de viajes durante un año específico.  
**Endpoint:**  
GET http://localhost:8082/viajes/reporte/monopatines?anio=&minViajes=  
**Parámetros:**  
- anio *(int, obligatorio)*: Año a consultar.  
- minViajes *(int, obligatorio)*: Número mínimo de viajes.  
**Microservicio:** viajes-service

---

## d. Total facturado en un rango de meses
**Objetivo:** Consulta el total facturado dentro de un rango de meses en un año determinado.  
**Endpoint:**  
GET http://localhost:8082/viajes/facturacion?anio=&mesInicio=&mesFin=  
**Parámetros:**  
- anio *(int, obligatorio)*  
- mesInicio *(int, obligatorio)*  
- mesFin *(int, obligatorio)*  
**Microservicio:** viajes-service

---

## e. Usuarios que más usan los monopatines
**Objetivo:** Obtiene los usuarios con mayor uso de monopatines, filtrados por período y tipo de usuario.  
**Endpoint:**  
GET http://localhost:8082/api/usuarios/top-usuarios?fechaInicio=&fechaFin=&tipoUsuario=  
**Parámetros:**  
- fechaInicio *(string, formato yyyy-mm-dd, obligatorio)*  
- fechaFin *(string, formato yyyy-mm-dd, obligatorio)*  
- tipoUsuario *(string, opcional)*: Por ejemplo "premium" o "standard".  
**Microservicio:** usuarios-service

---

## f. Ajuste de precios
**Objetivo:** Permite registrar un nuevo ajuste de precios, que entrará en vigencia a partir de una fecha determinada.  
**Endpoint:**  
POST http://localhost:8082/tarifa/nueva
**Microservicio:** tarifas-service

---

## g. Monopatines cercanos a mi ubicación
**Objetivo:** Permite a un usuario buscar monopatines cercanos a su zona.  
**Endpoint:**  
GET http://localhost:8082/paradas/cercanas?lat=&long=  
**Parámetros:**  
- lat *(float, obligatorio)*: Latitud del usuario.  
- long *(float, obligatorio)*: Longitud del usuario.  
**Microservicio:** paradas-service

---

## h. Uso personal de monopatines
**Objetivo:** Permite al usuario consultar cuánto ha usado los monopatines en un período, con la opción de incluir uso de usuarios relacionados.  
**Endpoint:**  
GET http://localhost:8082/api/usuarios/uso?fechaInicio=&fechaFin
**Parámetros:**  
- fechaInicio *(string, formato yyyy-mm-dd, obligatorio)*  
- fechaFin *(string, formato yyyy-mm-dd, obligatorio)*   
**Microservicio:** usuarios-service

---
