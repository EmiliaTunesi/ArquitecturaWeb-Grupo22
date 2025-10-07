package Repositorys;
import javax.persistence.EntityManager;

public interface Repository <T>{
        void create(T object);
        T findById(int id);
}
