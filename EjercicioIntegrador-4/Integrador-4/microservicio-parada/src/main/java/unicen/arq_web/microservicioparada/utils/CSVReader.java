package unicen.arq_web.microservicioparada.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import unicen.arq_web.microservicioparada.entities.Parada;
import unicen.arq_web.microservicioparada.repositories.ParadaRepository;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;

@Component
public class CSVReader implements CommandLineRunner {

    private final ParadaRepository pr;

    public CSVReader(ParadaRepository pr) {
        this.pr = pr;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pr.count() == 0) {
            System.out.println("Cargando datos de paradas desde CSV...");
            cargarParadasDesdeCSV();
        }
    }

    private void cargarParadasDesdeCSV() {
        try {
            ClassPathResource resource = new ClassPathResource("Paradas.csv");
            try (Reader reader = new InputStreamReader(resource.getInputStream());
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                         .setHeader()
                         .setSkipHeaderRecord(true)
                         .setIgnoreHeaderCase(true)
                         .setTrim(true)
                         .build())) {

                for (CSVRecord csvRecord : csvParser) {
                    try {
                        Parada p = new Parada();

                        String latitud = csvRecord.get("latitud");
                        if (latitud == null || latitud.isBlank())
                            throw new IllegalArgumentException("Campo latitud es obligatorio.");
                        p.setLatitud(Double.parseDouble(latitud));

                        String longitud = csvRecord.get("longitud");
                        if (longitud == null || longitud.isBlank())
                            throw new IllegalArgumentException("Campo longitud es obligatorio.");
                        p.setLongitud(Double.parseDouble(longitud));

                        String activa = csvRecord.get("activa");
                        if (activa == null || activa.isBlank())
                            throw new IllegalArgumentException("Campo activa es obligatorio.");
                        p.setActiva(Boolean.parseBoolean(activa));

                        String fechaAlta = csvRecord.get("fecha_alta");
                        if (fechaAlta != null && !fechaAlta.isBlank()) {
                            p.setFechaAlta(LocalDate.parse(fechaAlta));
                        }

                        pr.save(p);
                    } catch (Exception e) {
                        System.err.println("Error procesando fila " + csvRecord.getRecordNumber() + ": " + e.getMessage());
                    }
                }
                System.out.println("Datos de paradas cargados exitosamente: " + pr.count() + " registros.");
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CARGAR CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
