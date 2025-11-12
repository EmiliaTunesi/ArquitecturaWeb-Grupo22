package org.example.microserviciomonopatin.loader;

import lombok.RequiredArgsConstructor;
import org.example.microserviciomonopatin.entity.MantenimientoEntity;
import org.example.microserviciomonopatin.entity.MonopatinEntity;
import org.example.microserviciomonopatin.repository.MantenimientoRepository;
import org.example.microserviciomonopatin.repository.MonopatinRepository;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;
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
    private final MantenimientoRepository mantenimientoRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (monopatinRepository.count() > 0 || mantenimientoRepository.count() > 0) {
            System.out.println("ℹ️ Datos ya cargados, se omite el seeding inicial.");
            return;
        }

        cargarMonopatines();
        cargarMantenimientos();

        System.out.println("Datos iniciales cargados ");
    }

    private void cargarMonopatines() throws Exception {
        var resource = new ClassPathResource("data/monopatines.csv");

        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] m = line.split(",");

                MonopatinEntity monopatin = new MonopatinEntity();
                monopatin.setId(Long.valueOf(m[0]));
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

    private void cargarMantenimientos() throws Exception {
        var resource = new ClassPathResource("data/mantenimientos.csv");

        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");

                Long monopatinId = Long.parseLong(datos[1]);

                MantenimientoEntity mantenimiento = new MantenimientoEntity();
                mantenimiento.setId(Long.valueOf(datos[0]));
                mantenimiento.setMonopatinId(monopatinRepository.getReferenceById(monopatinId));
                mantenimiento.setFechaInicio(LocalDate.parse(datos[2]));
                mantenimiento.setFechaFin(datos[3].isEmpty() ? null : LocalDate.parse(datos[3]));
                mantenimiento.setDescripcion(datos[4]);
                mantenimiento.setTipoMantenimiento(datos[5]);

                mantenimientoRepository.save(mantenimiento);
            }
        }

    }
}
