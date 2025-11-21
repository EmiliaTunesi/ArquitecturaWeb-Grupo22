package org.example.microserviciochatia.loader;

import lombok.RequiredArgsConstructor;
import org.example.microserviciochatia.model.Monopatin;
import org.example.microserviciochatia.repository.MonopatinRepository;
import org.example.microserviciochatia.util.EstadoMonopatin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MonopatinRepository monopatinRepository;

    @Override
    public void run(String... args) throws Exception {

        if (monopatinRepository.count() > 0) {
            System.out.println("Datos ya cargados, se omite el seeding inicial.");
            return;
        }

        cargarMonopatines();

        System.out.println("Datos iniciales cargados");
    }

    private void cargarMonopatines() throws Exception {
        var resource = new ClassPathResource("data/monopatin.csv");

        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

            String line = reader.readLine(); // Header

            while ((line = reader.readLine()) != null) {

                String[] m = line.split(",");

                Monopatin monopatin = new Monopatin();

                // ID pasa a String
                monopatin.setId(m[0]);

                monopatin.setEstado(EstadoMonopatin.valueOf(m[1]));
                monopatin.setLatitudActual(Double.valueOf(m[2]));
                monopatin.setLongitudActual(Double.valueOf(m[3]));
                monopatin.setKilometrosTotales(Double.valueOf(m[4]));
                monopatin.setTiempoUsoTotal(Double.valueOf(m[5]));
                monopatin.setTiempoPausaTotal(Double.valueOf(m[6]));
                monopatin.setFechaAlta(LocalDate.parse(m[7]));

                monopatinRepository.save(monopatin);
            }
        }
    }


}