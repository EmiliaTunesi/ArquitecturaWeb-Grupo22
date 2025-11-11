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
                    viaje viaje = new viaje();

                    // 1. Campos a cargar
                    viaje.setIdUsuario(Long.parseLong(csvRecord.get("id_usuario")));
                    viaje.setIdCuenta(Long.parseLong(csvRecord.get("id_cuenta")));
                    viaje.setMonopatinId(Long.parseLong(csvRecord.get("monopatin_id")));
                    viaje.setParadaInicioId(Long.parseLong(csvRecord.get("parada_inicio_id")));


                    // Manejar el campo opcional parada_fin_id
                    String paradaFin = csvRecord.get("parada_fin_id");
                    if (!paradaFin.isEmpty()) {
                        viaje.setParadaFinId(Long.parseLong(paradaFin));
                    }

                    // 2. Campos de Fecha y Hora (LocalDateTime)
                    viaje.setFechaInicio(LocalDateTime.parse(csvRecord.get("fecha_hora_inicio"), DATE_TIME_FORMATTER));

                    // Manejar el campo opcional fecha_hora_fin
                    String fechaFin = csvRecord.get("fecha_hora_fin");
                    if (!fechaFin.isEmpty()) {
                        viaje.setFechaFin(LocalDateTime.parse(fechaFin, DATE_TIME_FORMATTER));
                    }

                    // 3. Campos Numéricos (BigDecimal / Integer)
                    viaje.setKmRecorridos(new BigDecimal(csvRecord.get("km_recorridos")));
                    viaje.setTarifaId(Long.parseLong(csvRecord.get("tarifa_id")));
                    viaje.setTiempoTotalMinutos(Integer.parseInt(csvRecord.get("tiempo_total_minutos")));
                    viaje.setPausaTotalMinutos(Integer.parseInt(csvRecord.get("pausa_total_minutos")));
                    viaje.setCostoTotal(new BigDecimal(csvRecord.get("costo_total")));

                    viajeRepository.save(viaje);
                }
                System.out.println("Datos de viajes cargados exitosamente: " + viajeRepository.count() + " registros.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR AL CARGAR CSV: " + e.getMessage());
        }
    }
}
