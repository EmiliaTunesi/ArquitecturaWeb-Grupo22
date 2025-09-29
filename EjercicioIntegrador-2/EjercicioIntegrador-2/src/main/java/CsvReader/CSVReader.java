package CsvReader;

import Entitys.Carrera;
import Entitys.Estudiante;
import Entitys.EstudianteCarrera;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import Repositorys.CarreraRepository;
import Repositorys.EstudianteRepository;
import Repositorys.EstudianteCarreraRepository;

import javax.persistence.EntityManager;
import java.io.*;

public class CSVReader {

        private EntityManager em;
        private CarreraRepository cr;
        private EstudianteRepository er;
        private EstudianteCarreraRepository ecr;

        public CSVReader(EntityManager em, CarreraRepository cr, EstudianteCarreraRepository ecr, EstudianteRepository er){
            this.em = em;
            this.cr = cr;
            this.er = er;
            this.ecr = ecr;
        }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(archivo);
        if (is == null) {
            throw new FileNotFoundException("No se encontró el archivo: " + archivo);
        }

        Reader in = new InputStreamReader(is);
        String[] header = {};
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        return csvParser.getRecords();
    }

        public void populateDB() throws Exception {
            try {
                em.getTransaction().begin();
                insertEstudiantes();
                insertCarreras();
                insertMatriculas();
                em.getTransaction().commit();
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
                            Estudiante estudiante = new Estudiante(dni, nombre, apellido, edad, genero, nroLU, ciudad);
                            er.create(estudiante);
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
                            cr.create(carrera);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                        }
                    }
                }
            }
        }
        private void insertMatriculas() throws IOException {
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
                            int id_carrera = Integer.parseInt(id_carreraString);
                            int anio_inicio = Integer.parseInt(anio_inicioString);
                            int anio_fin = Integer.parseInt(anio_finString);
                            int antiguedad = Integer.parseInt(antiguedadString);

                            Estudiante estudiante = er.findById(dni);
                            Carrera carrera = cr.findById(id_carrera);
                            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, anio_inicio, anio_fin, antiguedad);
                            ecr.create(estudianteCarrera);

                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

