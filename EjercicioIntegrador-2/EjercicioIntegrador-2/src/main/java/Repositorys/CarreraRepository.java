package Repositorys;
import DTO.CarreraDTO;
import DTO.CarreraReporteDTO;
import DTO.ReporteAnioDTO;
import Entitys.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

        //Genera reporte anual de carreras con la cantidad de estudiantes que se graduaron ese año y la cantidad de estudiantes que ingresaron ese año
        public List<ReporteAnioDTO> generarReporte() {
            List<Carrera> carreras = entitymanager.createQuery("SELECT c FROM Carrera c ORDER BY c.nombre", Carrera.class).getResultList();
            List<ReporteAnioDTO> reportes = new ArrayList<>();

            for(Carrera carrera : carreras){
                ReporteAnioDTO reporte = new ReporteAnioDTO(carrera.getNombre());

                String jpql = "SELECT ec.anio_inicio, COUNT(ec) " +
                        "FROM EstudianteCarrera ec " +
                        "WHERE ec.carrera.id_carrera = :idCarrera " +
                        "GROUP BY ec.anio_inicio " +
                        "ORDER BY ec.anio_inicio";

                String jpql2 = "SELECT ec.anio_fin, COUNT(ec) " +
                        "FROM EstudianteCarrera ec " +
                        "WHERE ec.carrera.id_carrera = :idCarrera AND ec.anio_fin <> 0" +
                        "GROUP BY ec.anio_fin " +
                        "ORDER BY ec.anio_fin";

                List<Object[]> inscriptosList = entitymanager.createQuery(jpql).setParameter("idCarrera", carrera.getId_carrera()).getResultList();
                List<Object[]> esgresadosList = entitymanager.createQuery(jpql2).setParameter("idCarrera", carrera.getId_carrera()).getResultList();
                for (Object[] resultado : inscriptosList){
                    int anio = (Integer) resultado[0];
                    int inscriptos = ((Number) resultado[1]).intValue();
                    reporte.getInfoPorAnio().put(anio, new CarreraReporteDTO(inscriptos));
                }
                for (Object[] resultado : esgresadosList){
                    int anio = (Integer) resultado[0];
                    int egresados = ((Number) resultado[1]).intValue();
                    CarreraReporteDTO c = reporte.getInfoPorAnio().get(anio);
                    if (c == null){
                        c = new CarreraReporteDTO(0);
                    }
                    c.setEgresados(egresados);
                    reporte.getInfoPorAnio().put(anio, c);
                }
                reportes.add(reporte);
            }
            return reportes;
        }

}
