# Documentación de Endpoints – Trabajo Integrador 4 (Segunda Parte)

## 🔗 Swagger UI – Documentación por Microservicio

Podés acceder a la documentación interactiva de cada microservicio a través de los siguientes enlaces:

- **Usuarios Service** → [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)  
- **Viajes Service** → [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)  
- **Monopatines Service** → [http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html)  
- **Paradas Service** → [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)  
- **Tarifas Service** → [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)
- **CHATIA Service** → [http://localhost:8089](http://localhost:8089)

---

## 🔐 Seguridad y Autenticación (JWT)

Todos los endpoints (excepto `/api/authenticate` y el registro público) están protegidos mediante **JWT (JSON Web Tokens)**. 

### Configuración inicial

1. **Base de datos:** Crear las siguientes bases de datos en PostgreSQL antes de levantar el proyecto:
   - `micro_viaje`
   - `base_paradas`
   - `microservicio_monopatin_db` (Esta db es de mongo, así que tenés que levantar el server de mongo primero tirando el comando mongod)
   - `base-microservicio`
   - `usuarios_db`

2. **API Key de Groq (para el Chat IA):**
   - Obtené una API Key gratuita en: [https://console.groq.com](https://console.groq.com)
   - Configurá la variable de entorno antes de levantar el microservicio de Chat:
     ```bash
     GROQ_API_KEY=tu_clave_aqui mvn spring-boot:run
     ```
   - O agregá la key en el archivo `application.yml` del microservicio Chat IA:
     ```yaml
     groq:
       api:
         key: tu_clave_aqui
     ```


## 📦 Colección de Postman con AUTH (usuarios listos para correr y obtener el token)

Podés importar esta colección para probar todos los endpoints:

**[Descargar Colección de Postman](https://www.postman.co/workspace/My-Workspace~5a88da99-16de-4825-87fb-f256d7305a9c/collection/22344577-269f1c13-4b5d-47bd-bdd8-de79799e4b54?action=share&creator=22344577)**

La colección incluye:
- ✅ Login como Admin
- ✅ Login como Usuario Normal (cuenta básica)
- ✅ Login como Usuario Premium
- ✅ Ejemplos de chat ia

---
---

## 🤖 Chat IA (Solo usuarios con cuenta Premium)

**Objetivo:** Permite a usuarios con cuenta premium consultar información sobre viajes y monopatines mediante lenguaje natural.

**Endpoint:**  
`POST [http://localhost:8082/api/ia/viaje/prompt](http://localhost:8082/api/ia/prompt)`

**Seguridad con JWT:**
- `Authorization: Bearer {token_de_usuario_premium}`

**Body (JSON String):**
```json
"¿Cuántos viajes he realizado este año?"
```

---

## 📘 Notas generales  
- Todos los endpoints están accesibles a través del API Gateway (`localhost:8082`)
- Los campos de tipo fecha deben seguir el formato `yyyy-mm-dd`
- El token JWT tiene una validez de 24 horas
- Las rutas públicas (sin autenticación requerida):
  - `POST /api/authenticate` (login)
  - `POST /api/usuarios` (registro)

---

## a. Reporte de uso de monopatines
**Objetivo:** Permite generar un reporte de uso por kilómetros, configurable para incluir o no los tiempos de pausa.  
**Endpoint:**  
`GET http://localhost:8082/api/monopatines/reporte-uso?incluirPausas=true`  
**Parámetros:**  
- `incluirPausas` *(booleano, opcional)*: true o false. Determina si el reporte considera los tiempos de pausa.  
**Requiere:** Token JWT con rol ROLE_ADMIN  
**Microservicio:** monopatines-service

---

## b. Anular cuenta de usuario
**Objetivo:** Permite al administrador anular (inhabilitar temporalmente) una cuenta de usuario.  
**Endpoint:**  
`PATCH http://localhost:8082/api/cuentas/{id}/anular`  
**Parámetros de ruta:**  
- `{id}`: ID de la cuenta a anular (IDs disponibles: 1, 2, 3, 4, 5)  
**Requiere:** Token JWT con rol ROLE_ADMIN  
**Microservicio:** usuarios-service

---

## c. Monopatines con más de X viajes en un año
**Objetivo:** Consulta los monopatines que superan una cantidad de viajes durante un año específico.  
**Endpoint:**  
`GET http://localhost:8082/viajes/reporte/monopatines?anio=2024&minViajes=10`  
**Parámetros:**  
- `anio` *(int, obligatorio)*: Año a consultar  
- `minViajes` *(int, obligatorio)*: Número mínimo de viajes  
**Requiere:** Token JWT con rol ROLE_ADMIN  
**Microservicio:** viajes-service

---

## d. Total facturado en un rango de meses
**Objetivo:** Consulta el total facturado dentro de un rango de meses en un año determinado.  
**Endpoint:**  
`GET http://localhost:8082/viajes/facturacion?anio=2024&mesInicio=1&mesFin=6`  
**Parámetros:**  
- `anio` *(int, obligatorio)*  
- `mesInicio` *(int, obligatorio)*: Entre 1 y 12  
- `mesFin` *(int, obligatorio)*: Entre 1 y 12  
**Requiere:** Token JWT con rol ROLE_ADMIN  
**Microservicio:** viajes-service

---

## e. Usuarios que más usan los monopatines
**Objetivo:** Obtiene los usuarios con mayor uso de monopatines, filtrados por año y estado activo.  
**Endpoint:**  
`GET http://localhost:8082/api/usuarios/top-usuarios?anio=2024&activo=true`  
**Parámetros:**  
- `anio` *(int, obligatorio)*  
- `activo` *(boolean, opcional)*: Filtrar por usuarios activos/inactivos  
**Requiere:** Token JWT con rol ROLE_ADMIN  
**Microservicio:** usuarios-service

---

## f. Ajuste de precios
**Objetivo:** Permite registrar un nuevo ajuste de precios, que entrará en vigencia a partir de una fecha determinada.  
**Endpoint:**  
`POST http://localhost:8082/tarifa/nueva`  
**Body (JSON):**
```json
{
  "tarifaBase": 50.0,
  "tarifaPorPausaExtendida": 20.0,
  "fechaVigencia": "2025-01-01"
}
```
**Requiere:** Token JWT con rol ROLE_ADMIN  
**Microservicio:** tarifas-service

---

## g. Monopatines cercanos a mi ubicación
**Objetivo:** Permite a un usuario buscar monopatines cercanos a su zona.  
**Endpoint:**  
`GET http://localhost:8082/paradas/cercanas?lat=-37.3217&long=-59.1348`  
**Parámetros:**  
- `lat` *(float, obligatorio)*: Latitud del usuario  
- `long` *(float, obligatorio)*: Longitud del usuario  
**Requiere:** Token JWT (cualquier usuario autenticado)  
**Microservicio:** paradas-service

---

## h. Uso personal de monopatines
**Objetivo:** Permite al usuario consultar cuánto ha usado los monopatines en un período.  
**Endpoint:**  
`GET http://localhost:8082/api/usuarios/uso?idUsuario=2&idCuenta=1&desde=2024-01-01&hasta=2024-12-31`  
**Parámetros:**  
- `idUsuario` *(long, obligatorio)*  
- `idCuenta` *(long, obligatorio)*  
- `desde` *(string, formato yyyy-mm-dd, obligatorio)*  
- `hasta` *(string, formato yyyy-mm-dd, obligatorio)*  
**Requiere:** Token JWT (el usuario solo puede consultar su propio uso)  
**Microservicio:** usuarios-service

---

