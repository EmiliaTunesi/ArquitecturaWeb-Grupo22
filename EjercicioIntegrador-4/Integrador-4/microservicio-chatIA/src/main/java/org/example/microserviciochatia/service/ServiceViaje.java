package org.example.microserviciochatia.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.microserviciochatia.client.GroqClientMongoo;
import org.example.microserviciochatia.dto.RespuestaApi;
import org.example.microserviciochatia.dto.ViajeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ServiceViaje {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroqClientMongoo groqChatClient;

    private final String CONTEXTO_SQL;

    private static final Logger log = LoggerFactory.getLogger(ServiceViaje.class);

    // ========================================================================
    // [MOD - NUEVO] Reglas de extracción/seguridad para la sentencia SQL
    // ------------------------------------------------------------------------
    // Aceptamos EXACTAMENTE una sentencia que empiece por SELECT|INSERT|UPDATE|DELETE
    // y que termine en ';'. El DOTALL permite capturar saltos de línea.
    private static final Pattern SQL_ALLOWED =
            Pattern.compile("(?is)\\b(SELECT|INSERT|UPDATE|DELETE)\\b[\\s\\S]*?;");

    // Bloqueamos DDL u otras operaciones peligrosas por si el modelo "derrapa".
    private static final Pattern SQL_FORBIDDEN =
            Pattern.compile("(?i)\\b(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE)\\b");
    // ========================================================================

    public ServiceViaje() {
        this.CONTEXTO_SQL = cargarEsquemaSQL("db_viajes.sql");
    }

    private String cargarEsquemaSQL(String nombreArchivo) {
        try (InputStream inputStream = new ClassPathResource(nombreArchivo).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo SQL desde resources: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ResponseEntity<?> gestionarPrompt(String promptUsuario) {
        try {
            String promptFinal = """
                    Este es el esquema de mi base de datos PostgreSQL:
                        %s
                    
                        Basándote EXCLUSIVAMENTE en este esquema, generá una única sentencia SQL COMPLETA, VÁLIDA y adecuada para PostgreSQL.
                    
                        Reglas estrictas:
                        - Respondé SOLO con la sentencia SQL (sin explicaciones, sin texto adicional, sin aclaraciones).
                        - NO uses Markdown.
                        - NO agregues comentarios.
                        - La sentencia debe terminar con punto y coma.
                        - La sentencia debe poder ejecutarse directamente en PostgreSQL.
                    
                        Instrucción del usuario:
                        %s
                    """.formatted(CONTEXTO_SQL, promptUsuario);

            log.info("==== PROMPT ENVIADO A LA IA ====\n{}", promptFinal);

            String respuestaIa = groqChatClient.preguntar(promptFinal);
            log.info("==== RESPUESTA IA ====\n{}", respuestaIa);

            // ========================================================================
            // [MOD - CAMBIO] Usamos la nueva extracción segura (acepta DML y bloquea DDL)
            // ========================================================================
            String sql = extraerConsultaSQL(respuestaIa);
            if (sql == null || sql.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RespuestaApi<>(false,
                                "No se encontró una sentencia SQL válida en la respuesta de la IA.", null));
            }

            log.info("==== SQL EXTRAÍDA ====\n{}", sql);

            // Para JDBC/JPA normalmente NO va el ';' final
            String sqlToExecute = sql.endsWith(";") ? sql.substring(0, sql.length() - 1) : sql;

            try {
                Object data;
                // ====================================================================
                // [MOD - NUEVO] Ejecutamos SELECT con getResultList y DML con executeUpdate
                // ====================================================================
                if (sql.trim().regionMatches(true, 0, "SELECT", 0, 6)) {
                    @SuppressWarnings("unchecked")
                    List<Object[]> resultados = entityManager.createNativeQuery(sqlToExecute).getResultList();
                    List<ViajeResponseDTO> dtoLista = convertirRowsAViajeDTO(resultados);
                    return ResponseEntity.ok(new RespuestaApi<>(true, "Consulta SELECT ejecutada con éxito", dtoLista));
                } else {//aca devuelvo los datos de la consulta-----------------------------------------------------------------------------
                    int rows = entityManager.createNativeQuery(sqlToExecute).executeUpdate();
                    data = rows; // cantidad de filas afectadas
                    return ResponseEntity.ok(new RespuestaApi<>(true, "Sentencia DML ejecutada con éxito", data));
                }
            } catch (Exception e) {
                log.warn("Error al ejecutar SQL: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new RespuestaApi<>(false, "Error al ejecutar la sentencia: " + e.getMessage(), null));
            }

        } catch (Exception e) {
            log.error("Fallo al procesar prompt", e);
            return new ResponseEntity<>(
                    new RespuestaApi<>(false, "Error al procesar el prompt: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // ========================================================================
    // [MOD - REEMPLAZO] Método de extracción robusto y documentado
    //   - Acepta SOLO una sentencia que empiece con SELECT/INSERT/UPDATE/DELETE
    //   - Exige punto y coma final
    //   - Recorta todo lo que venga después del primer ';'
    //   - Bloquea DDL peligrosos (DROP/TRUNCATE/ALTER/CREATE/GRANT/REVOKE)
    // ========================================================================
    private String extraerConsultaSQL(String respuesta) {
        if (respuesta == null) return null;

        Matcher m = SQL_ALLOWED.matcher(respuesta);
        if (!m.find()) return null;

        String sql = m.group().trim();

        // Asegurar UNA sola sentencia (hasta el primer ';')
        int first = sql.indexOf(';');
        if (first > -1) {
            sql = sql.substring(0, first + 1);
        }

        // Bloquear DDL
        if (SQL_FORBIDDEN.matcher(sql).find()) {
            log.warn("Sentencia bloqueada por contener DDL prohibido: {}", sql);
            return null;
        }

        return sql;
    }
    private List<ViajeResponseDTO> convertirRowsAViajeDTO(List<Object[]> rows) {
        return rows.stream()
                .map(r -> {
                    ViajeResponseDTO dto = new ViajeResponseDTO();

                    dto.setId(((Number) r[0]).longValue());
                    dto.setIdUsuario(((Number) r[1]).longValue());
                    dto.setIdCuenta(((Number) r[2]).longValue());
                    dto.setMonopatinId(((Number) r[3]).longValue());
                    dto.setFechaInicio((LocalDateTime) r[4]);
                    dto.setFechaFin((LocalDateTime) r[5]);
                    dto.setParadaInicioId(((Number) r[6]).longValue());
                    dto.setParadaFinId(r[7] != null ? ((Number) r[7]).longValue() : null);
                    dto.setKmRecorridos(r[8] != null ? (BigDecimal) r[8] : null);
                    dto.setTarifaId(r[9] != null ? ((Number) r[9]).longValue() : null);
                    dto.setTiempoTotalMinutos(r[10] != null ? ((Number) r[10]).intValue() : null);
                    dto.setPausaTotalMinutos(r[11] != null ? ((Number) r[11]).intValue() : null);
                    dto.setCosto_total(r[8] != null ? (BigDecimal) r[8] : null);

                    return dto;
                })
                .toList();
    }
}




