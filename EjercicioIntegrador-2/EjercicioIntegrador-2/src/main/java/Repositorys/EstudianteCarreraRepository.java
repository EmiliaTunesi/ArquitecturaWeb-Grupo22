package Repositorys;
import Entitys.EstudianteCarrera;
import javax.persistence.EntityManager;

public class EstudianteCarreraRepository implements Repository<EstudianteCarrera>{

        private EntityManager entitymanager;
        public EstudianteCarreraRepository(EntityManager em) {
            this.entitymanager = em;
        }
        @Override
        public void create(EstudianteCarrera object) {
            entitymanager.persist(object);
        }

        @Override
        public EstudianteCarrera findById(int id) {
            return entitymanager.find(EstudianteCarrera.class, id);
        }
}
