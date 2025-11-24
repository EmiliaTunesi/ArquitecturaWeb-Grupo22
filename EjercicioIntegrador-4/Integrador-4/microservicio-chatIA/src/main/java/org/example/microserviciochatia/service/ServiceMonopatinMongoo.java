package org.example.microserviciochatia.service;

import org.example.microserviciochatia.client.GroqClientMongoo;
import org.example.microserviciochatia.dto.MonopatinResponseDTO;
import org.example.microserviciochatia.model.Monopatin;
import org.example.microserviciochatia.repository.MonopatinRepository;
import org.example.microserviciochatia.util.EstadoMonopatin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.microserviciochatia.dto.RespuestaApi;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ServiceMonopatinMongoo {
    private static final Logger log = LoggerFactory.getLogger(ServiceMonopatinMongoo.class);

    @Autowired
    private GroqClientMongoo groqChatClient;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MonopatinRepository monopatinRepository;
    /* ESTO NO APLICA PORQUE USAMOS MONGO QUE NO USA SQL
    // Aceptamos EXACTAMENTE una sentencia que empiece por SELECT|INSERT|UPDATE|DELETE
    // y que termine en ';'. El DOTALL permite capturar saltos de línea.
    private static final Pattern SQL_ALLOWED =
            Pattern.compile("(?is)\\b(SELECT)\\b[\\s\\S]*?;");

    // Bloqueamos DDL u otras operaciones peligrosas por si el modelo "derrapa".
    private static final Pattern SQL_FORBIDDEN =
            Pattern.compile("(?i)\\b(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE)\\b");

    */

    // Archivo de contexto con el esquema MongoDB.
    private static final String CONTEXTO_MONGO = """
        Este es el esquema documental de mi colección 'monopatines' en MongoDB:
        {
          _id: ObjectId,
          estado: String (DISPONIBLE | EN_USO | PAUSADO | EN_MANTENIMIENTO | FUERA_DE_SERVICIO),
          latitudActual: Double,
          longitudActual: Double,
          kilometrosTotales: Double,
          tiempoUsoTotal: Double,
          tiempoPausaTotal: Double,
          fechaAlta: String (formato ISO: YYYY-MM-DD)
        }
        """;

    public ResponseEntity<?> procesarPrompt(String promptUsuario) {
        try {// aca le digo a la IA de donde sacar la info,que formato quiero que devuelva
            //que no devuelva coment,y le recuerdo el repositorio
            String promptFinal = """
                %s

                    Tu tarea es interpretar la consulta del usuario sobre la colección 'monopatines' de MongoDB.
                    
                    REGLAS ESTRICTAS:
                    1. Basate EXCLUSIVAMENTE en el esquema dado arriba.
                    2. Debés mapear la consulta del usuario a un método válido de un Spring Data MongoRepository.
                    3. La salida DEBE SER ÚNICAMENTE un método de repository válido.
                    4. No agregues texto adicional, comentarios, explicaciones ni código externo.
                    5. No inventes campos. Solo podés usar los campos reales del esquema.
                    7. El formato de salida permitido es SOLO:
                       findByEstado(EstadoMonopatin.XXXX)
                    
                    EJEMPLO DE SALIDA ESPERADA:
                    findByEstado(EstadoMonopatin.DISPONIBLE)
                    
                    Consulta del usuario:
                %s
                """.formatted(CONTEXTO_MONGO, promptUsuario);

            log.info("==== PROMPT ENVIADO A LA IA ====\n{}", promptFinal);

            String respuestaIa = groqChatClient.preguntar(promptFinal);
            log.info("==== RESPUESTA IA ====\n{}", respuestaIa);

            // ========================================================================
            //   - Acepta SOLO un método válido de repository: Ejemplo: findByEstado(EstadoMonopatin.DISPONIBLE)
            // ========================================================================

            String metodoRepository = extraerMetodoRepository(respuestaIa);
            if (metodoRepository == null || metodoRepository.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RespuestaApi<>(false,
                                "No se encontró un método de repository válido en la respuesta de la IA.", null));
            }

            log.info("==== MÉTODO EXTRAÍDO DE IA ====\n{}", metodoRepository);

            // Ejecutar el método del repository
            try {
                Object data = ejecutarMetodoRepository(metodoRepository);
                if (data instanceof List<?> lista && !lista.isEmpty() && lista.get(0) instanceof Monopatin) {
                    List<MonopatinResponseDTO> listaDto = convertirADTO((List<Monopatin>) lista);
                    return ResponseEntity.ok(new RespuestaApi<>(true, "Consulta ejecutada con éxito", listaDto));
                }
                // si agregro otra cosa lo devuelve tal cual
                return ResponseEntity.ok(new RespuestaApi<>(true, "Consulta ejecutada con éxito(datos expuestos)", data));
            } catch (Exception e) {
                log.warn("Error al ejecutar método de repository: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new RespuestaApi<>(false, "Error al ejecutar el método del repository: " + e.getMessage(), null));
            }

        } catch (Exception e) {
            log.error("Fallo al procesar prompt", e);
            return new ResponseEntity<>(
                    new RespuestaApi<>(false, "Error al procesar el prompt: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /*
     * Extrae el método repository válido de la respuesta IA.
     * Solo acepta la forma "findByEstado(EstadoMonopatin.DISPONIBLE)"
     */
    private String extraerMetodoRepository(String respuesta) {
        if (respuesta == null) return null;
        Pattern pattern = Pattern.compile("findByEstado\\s*\\(\\s*EstadoMonopatin\\.[A-Z_]+\\s*\\)");
        Matcher m = pattern.matcher(respuesta);
        if (m.find()) {
            return m.group().trim();
        }
        return null;
    }

    /*
     * Ejecuta el método extraído (solo soporta findByEstado por ahora)
     */
    private List<Monopatin> ejecutarMetodoRepository(String metodoRepository) {
        if (metodoRepository.startsWith("findByEstado(")) {
            String estadoArg = metodoRepository.substring(metodoRepository.indexOf('(') + 1, metodoRepository.indexOf(')'));
            String estadoStr = estadoArg.replace("EstadoMonopatin.", "").trim();
            EstadoMonopatin estado = EstadoMonopatin.valueOf(estadoStr);
            return monopatinRepository.findByEstado(estado);
        }
        throw new IllegalArgumentException("Método no soportado: " + metodoRepository);
    }

    private List<MonopatinResponseDTO> convertirADTO(List<Monopatin> lista) {
        return lista.stream()
                .map(m -> {
                    MonopatinResponseDTO dto = new MonopatinResponseDTO();
                    dto.setId(m.getId());
                    dto.setEstado(m.getEstado().name());
                    dto.setLatitudActual(m.getLatitudActual());
                    dto.setLongitudActual(m.getLongitudActual());
                    dto.setKilometrosTotales(m.getKilometrosTotales());
                    dto.setTiempoUsoTotal(m.getTiempoUsoTotal());
                    dto.setTiempoPausaTotal(m.getTiempoPausaTotal());
                    dto.setFechaAlta(m.getFechaAlta());
                    return dto;
                })
                .toList();
    }
}

