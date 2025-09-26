package Service;

import javax.persistence.EntityManagerFactory;

public class CarreraServicio {

}
public class DireccionServ {
    private DireccionDAO direccionDAO;

    public DireccionServ(EntityManagerFactory emf) {
        this.direccionDAO = new DireccionDAO(emf);
    }

    public void insert(Direccion d) {
        direccionDAO.insert(d);
    }
