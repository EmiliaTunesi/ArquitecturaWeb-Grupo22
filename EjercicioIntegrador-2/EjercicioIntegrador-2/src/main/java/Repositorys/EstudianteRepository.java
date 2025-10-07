package Repositorys;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import Entitys.Estudiante;

public class EstudianteRepository implements Repository<Estudiante>{

        private EntityManager entityManager;
        public EstudianteRepository(EntityManager em) {
            this.entityManager = em;
        }
        @Override
        public void create(Estudiante object) {
            entityManager.persist(object);
        }

        @Override
        public Estudiante findById(int id) {
            return entityManager.find(Estudiante.class, id);
        }
        public Estudiante findByLU(int LU){
            String jpql = "SELECT e FROM Estudiante e WHERE nroLU = :LU";

            return  entityManager.createQuery(jpql, Estudiante.class).setParameter("LU", LU).getSingleResult();
        }

        public List<Estudiante> findAll(){
            String jpql = "SELECT e FROM Estudiante e ORDER BY e.apellido";
            return  entityManager.createQuery(jpql, Estudiante.class).getResultList();
        }

        public List<Estudiante> findByGenero(String genero){
            String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero";
            Query q = entityManager.createQuery(jpql, Estudiante.class);
            q.setParameter("genero", genero);
            return q.getResultList();
        }

        public List<Estudiante> findByCarreraAndCiudad(int id_carrera,String ciudad) {
            String jpql = "SELECT e FROM Estudiante e JOIN EstudianteCarrera ec ON ec.estudiante.dni = e.dni WHERE ec.carrera.id_carrera = :carrera AND e.ciudadResidencia = :ciudad";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("carrera", id_carrera);
            query.setParameter("ciudad", ciudad);
            return query.getResultList();
        }


}
