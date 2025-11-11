package org.example.microservicioviaje.config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.microservicioviaje.entity.viaje;
import org.example.microservicioviaje.repository.viajeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ViajeDataLoader implements CommandLineRunner {

    private final viajeRepository viajeRepository;

    // Patrón ISO para parsear las fechas del CSV (YYYY-MM-DDThh:mm:ss)
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public ViajeDataLoader(viajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si la tabla está vacía
        if (viajeRepository.count() == 0) {
            System.out.println("Cargando datos de viajes desde CSV...");
            cargarViajesDesdeCSV();
        }
    }

    private void cargarViajesDesdeCSV() {
        try {
            // Obtener el archivo desde la carpeta resources
            ClassPathResource resource = new ClassPathResource("Viaje.csv");

            // Usar Reader para leer el archivo
            try (Reader reader = new FileReader(resource.getFile());
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                         .setHeader() // Usa la primera fila como encabezado de columna
                         .setSkipHeaderRecord(true) // No incluir la fila de encabezado en los registros
                         .setIgnoreHeaderCase(true)
                         .setTrim(true)
                         .build())) {

                for (CSVRecord csvRecord : csvParser) {

                    try {
                        viaje viaje = new viaje();

                        // --- 1. Campos Obligatorios

                        String monopatinId = csvRecord.get("monopatin_id");
                        if (monopatinId.isEmpty())
                            throw new IllegalArgumentException("Monopatin ID es obligatorio.");
                        viaje.setMonopatinId(Long.parseLong(monopatinId));

                        String idUsuario = csvRecord.get("id_usuario");
                        if (idUsuario.isEmpty())
                            throw new IllegalArgumentException("ID Usuario es obligatorio.");
                        viaje.setIdUsuario(Long.parseLong(idUsuario));

                        String idCuenta = csvRecord.get("id_cuenta");
                        if (idCuenta.isEmpty())
                            throw new IllegalArgumentException("ID Cuenta es obligatorio.");
                        viaje.setIdCuenta(Long.parseLong(idCuenta));

                        String paradaInicioId = csvRecord.get("parada_inicio_id");
                        if (paradaInicioId.isEmpty())
                            throw new IllegalArgumentException("Parada Inicio ID es obligatorio.");
                        viaje.setParadaInicioId(Long.parseLong(paradaInicioId));

                        String fechaInicio = csvRecord.get("fecha_hora_inicio");
                        if (fechaInicio.isEmpty())
                            throw new IllegalArgumentException("Fecha Inicio es obligatorio.");
                        viaje.setFechaInicio(LocalDateTime.parse(fechaInicio, DATE_TIME_FORMATTER));
                        // --- 2. Campos Opcionales (Validación si es null o vacío) ---


                        // Opcional: fecha_hora_fin (LocalDateTime)
                        String fechaFin = csvRecord.get("fecha_hora_fin");
                        if (!fechaFin.isEmpty()) {
                            viaje.setFechaFin(LocalDateTime.parse(fechaFin, DATE_TIME_FORMATTER));
                        }

                        // Opcional: km_recorridos (BigDecimal)
                        String kmRecorridos = csvRecord.get("km_recorridos");
                        if (!kmRecorridos.isEmpty()) {
                            viaje.setKmRecorridos(new BigDecimal(kmRecorridos));
                        }

                        // Opcional: tarifa_id (Long)
                        String tarifaId = csvRecord.get("tarifa_id");
                        if (!tarifaId.isEmpty()) {
                            viaje.setTarifaId(Long.parseLong(tarifaId));
                        }

                        // Opcional: parada_fin_id (Long)
                        String paradaFinId = csvRecord.get("parada_fin_id");
                        if (!paradaFinId.isEmpty()) {
                            viaje.setParadaFinId(Long.parseLong(paradaFinId));
                        }

                        // Opcional: tiempo_total_minutos (Integer)
                        String tiempoTotalMinutos = csvRecord.get("tiempo_total_minutos");
                        if (!tiempoTotalMinutos.isEmpty()) {
                            viaje.setTiempoTotalMinutos(Integer.parseInt(tiempoTotalMinutos));
                        }
                        // Guardar la entidad
                        viajeRepository.save(viaje);

                    } catch (Exception e) {
                        // Reporta el error y pasa al siguiente registro
                        System.err.println(" ERROR procesando la fila " + csvRecord.getRecordNumber() + " (Monopatin ID: " + csvRecord.get("monopatin_id") + "): " + e.getMessage());
                    }
                }

                System.out.println("Datos de viajes cargados exitosamente: " + viajeRepository.count() + " registros.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR AL CARGAR CSV: " + e.getMessage());
        }
    }
}
