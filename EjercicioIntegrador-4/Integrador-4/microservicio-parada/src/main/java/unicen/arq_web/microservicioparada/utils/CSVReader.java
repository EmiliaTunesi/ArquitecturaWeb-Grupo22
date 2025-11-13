package unicen.arq_web.microservicioparada.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import unicen.arq_web.microservicioparada.entities.Parada;
import unicen.arq_web.microservicioparada.repositories.ParadaRepository;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;


@Component
public class CSVReader implements CommandLineRunner {

    private ParadaRepository pr;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si la tabla está vacía
        if (pr.count() == 0) {
            System.out.println("Cargando datos de viajes desde CSV...");
            cargarViajesDesdeCSV();
        }
    }

    private void cargarViajesDesdeCSV() {
        try {
            // Obtener el archivo desde la carpeta resources
            ClassPathResource resource = new ClassPathResource("Paradas.csv");

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
                        Parada p = new Parada();

                        String latitud = csvRecord.get("latitud");
                        if (latitud.isEmpty())
                            throw new IllegalArgumentException("Campo latitud es obligatorio.");
                        p.setLatitud(Double.parseDouble(latitud));

                        String longitud = csvRecord.get("longitud");
                        if (longitud.isEmpty())
                            throw new IllegalArgumentException("Campo longitud es obligatorio.");
                        p.setLongitud(Double.parseDouble(longitud));

                        String activa = csvRecord.get("activa");
                        if (activa.isEmpty())
                            throw new IllegalArgumentException("Campo activa es obligatorio.");
                        p.setActiva(Boolean.parseBoolean(activa));

                        String fechaAlta = csvRecord.get("fecha_alta");
                        if (!fechaAlta.isEmpty()) {
                            p.setFechaAlta(LocalDate.parse(fechaAlta));
                        }

                        pr.save(p);

                    } catch (Exception e) {
                        // Reporta el error y pasa al siguiente registro
                        System.err.println(" ERROR procesando la fila " + csvRecord.getRecordNumber());
                    }
                }
                System.out.println("Datos de viajes cargados exitosamente: " + pr.count() + " registros.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR AL CARGAR CSV: " + e.getMessage());
        }
    }
}
