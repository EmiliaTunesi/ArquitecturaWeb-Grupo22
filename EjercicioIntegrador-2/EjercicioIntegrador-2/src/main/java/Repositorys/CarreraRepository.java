package Repositorys;
import DTO.CarreraDTO;
import DTO.CarreraReporteDTO;
import DTO.ReporteAnioDTO;
import Entitys.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarreraRepository implements Repository<Carrera>{
        private EntityManager entitymanager;
        public CarreraRepository(EntityManager em) {
            this.entitymanager = em;
        }

        @Override
        public void create(Carrera object) {
            entitymanager.persist(object);
        }

        @Override
        public Carrera findById(int id) {
            return entitymanager.find(Carrera.class, id);
        }
        //busca carreras con estudiantes inscriptos y ordena los resultados de mayor a menor por cantidad de estudiantes
        public List<CarreraDTO> EncontrarCarreraConInscriptos() {
            String jpql = "SELECT c, c.estudiantes.size FROM Carrera c WHERE c.estudiantes.size > 0 ORDER BY c.estudiantes.size DESC ";
            Query query = entitymanager.createQuery(jpql);
            List<Object[]> resultados = query.getResultList();
            List<CarreraDTO> carreras = new ArrayList<>();
            for(Object[] r : resultados){
                CarreraDTO carrera = new CarreraDTO((Carrera) r[0], (Integer)r[1]);
                carreras.add(carrera);
            }
            return carreras;
        }

        /*
        3) Generar un reporte de las carreras, que para cada carrera incluya información de los
        inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
        los años de manera cronológica
         */
    public List<ReporteAnioDTO> generarReporte() {
        List<ReporteAnioDTO> reportes = new ArrayList<>();

        String sql = """
        SELECT 
            c.id_carrera,
            c.nombre,
            c.duracion,
            ec.anio_inicio,
            COUNT(*) AS inscriptos,
            SUM(CASE WHEN ec.anio_fin != 0 THEN 1 ELSE 0 END) AS graduados
            FROM Carrera c
            JOIN EstudianteCarrera ec ON c.id_carrera = ec.carrera_id_carrera
            GROUP BY c.id_carrera, c.nombre, c.duracion, ec.anio_inicio
            ORDER BY c.nombre ASC, ec.anio_inicio ASC
        """;

        Query query = entitymanager.createNativeQuery(sql);
        List<Object[]> resultados = query.getResultList();

        // Map auxiliar para evitar duplicados por carrera
        Map<String, ReporteAnioDTO> mapaCarreras = new LinkedHashMap<>();

        for (Object[] r : resultados) {
            // Campos en el orden del SELECT
            String nombreCarrera = (String) r[1];
            Integer anioInicio = ((Number) r[3]).intValue();
            Integer inscriptos = ((Number) r[4]).intValue();
            Integer graduados = ((Number) r[5]).intValue();

            // Si la carrera no esta en el map, la agregamos
            mapaCarreras.putIfAbsent(nombreCarrera, new ReporteAnioDTO(nombreCarrera));

            // Obtenemos el DTO de la carrera
            ReporteAnioDTO reporte = mapaCarreras.get(nombreCarrera);

            // Creamos el reporte del año con los valores correspondientes
            CarreraReporteDTO infoAnio = new CarreraReporteDTO(inscriptos);
            infoAnio.setEgresados(graduados);

            // Lo guardamos en el map interno por año
            reporte.getInfoPorAnio().put(anioInicio, infoAnio);
        }
        // Pasamos los valores del map a la lista final
        reportes.addAll(mapaCarreras.values());
        return reportes;
    }

        /*EstudianteCarrera
anio_fin           | int  | YES  |     | NULL    |       |
| anio_inicio        | int  | YES  |     | NULL    |       |
| antiguedad         | int  | YES  |     | NULL    |       |
| estudiante_dni     | int  | NO   | PRI | NULL    |       |
| carrera_id_carrera */


}
