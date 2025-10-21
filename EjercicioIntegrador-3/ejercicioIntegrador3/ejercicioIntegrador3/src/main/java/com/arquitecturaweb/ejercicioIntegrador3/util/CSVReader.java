package com.arquitecturaweb.ejercicioIntegrador3.util;


import com.arquitecturaweb.ejercicioIntegrador3.entity.Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante_Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.repository.CarreraRepository;
import com.arquitecturaweb.ejercicioIntegrador3.repository.EstudianteRepository;
import com.arquitecturaweb.ejercicioIntegrador3.repository.Estudiante_CarreraRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

@Service
public final class CSVReader {

    @Autowired
    private EstudianteRepository er;
    @Autowired
    private CarreraRepository cr;
    @Autowired
    private Estudiante_CarreraRepository ecr;

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src/main/resources/"+archivo;
        Reader in = new FileReader(path);
        String[] header = {};
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }

    public void populateDB() throws Exception {
        try {
            insertEstudiantes();
            insertCarreras();
            insertMatriculas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void insertEstudiantes() throws IOException {
        for(CSVRecord row : getData("estudiantes.csv")) {
            if(row.size() >= 7) {
                String dniString = row.get(0);
                String nombre = row.get(1);
                String apellido = row.get(2);
                String edadString = row.get(3);
                String genero = row.get(4);
                String ciudad = row.get(5);
                String nroLUString = row.get(6);
                if(!dniString.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !edadString.isEmpty() && !genero.isEmpty() && !nroLUString.isEmpty() && !ciudad.isEmpty()) {
                    try {
                        int nroLU = Integer.parseInt(nroLUString);
                        int edad = Integer.parseInt(edadString);
                        int dni = Integer.parseInt(dniString);
                        Estudiante estudiante = new Estudiante(nombre, apellido, dni, genero, edad, ciudad, nroLU);

                        er.save(estudiante);
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                    }
                }
            }
        }
    }
    private void insertCarreras() throws IOException {
        for(CSVRecord row : getData("carreras.csv")) {
            if(row.size() >= 3) {
                String id_carreraString = row.get(0);
                String nombre = row.get(1);
                String duracionString = row.get(2);
                if(!id_carreraString.isEmpty() && !nombre.isEmpty() && !duracionString.isEmpty()) {
                    try {
                        int id_carrera = Integer.parseInt(id_carreraString);
                        int duracion = Integer.parseInt(duracionString);
                        Carrera carrera = new Carrera(id_carrera, nombre, duracion);
                        cr.save(carrera);
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                    }
                }
            }
        }
    }
    private void insertMatriculas() throws IOException {
        int registrosExitosos = 0;
        int registrosOmitidos = 0;

        for(CSVRecord row : getData("estudianteCarrera.csv")) {
            if(row.size() >= 6) {
                String idString = row.get(0);
                String dniString = row.get(1);
                String id_carreraString = row.get(2);
                String anio_inicioString = row.get(3);
                String anio_finString = row.get(4);
                String antiguedadString = row.get(5);

                if(!dniString.isEmpty() && !id_carreraString.isEmpty() && !anio_inicioString.isEmpty() && !anio_finString.isEmpty()) {
                    try {
                        int dni = Integer.parseInt(dniString);
                        long id_carrera = Long.parseLong(id_carreraString);
                        int anio_inicio = Integer.parseInt(anio_inicioString);
                        int anio_fin = Integer.parseInt(anio_finString);
                        int antiguedad = Integer.parseInt(antiguedadString);

                        Optional<Estudiante> optEstudiante = er.findByDni(dni);
                        Optional<Carrera> optCarrera = cr.findById(id_carrera);

                        if(optEstudiante.isPresent() && optCarrera.isPresent()) {
                            Estudiante estudiante = optEstudiante.get();
                            Carrera carrera = optCarrera.get();
                            Estudiante_Carrera estudianteCarrera = new Estudiante_Carrera(estudiante, carrera, anio_inicio, anio_fin, antiguedad);
                            ecr.save(estudianteCarrera);
                            registrosExitosos++;
                        } else {
                            System.err.println("⚠️ Registro omitido - No se encontró: " +
                                    (optEstudiante.isEmpty() ? "Estudiante DNI " + dni : "") +
                                    (optCarrera.isEmpty() ? " Carrera ID " + id_carrera : ""));
                            registrosOmitidos++;
                        }

                    } catch (NumberFormatException e) {
                        System.err.println(" Error de formato en datos: " + e.getMessage());
                        registrosOmitidos++;
                    }
                }
            }
        }


    }
    }
