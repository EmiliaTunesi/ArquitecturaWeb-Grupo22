package Service;

import DTO.CarreraDTO;
import DTO.EstudianteDTO;
import DTO.ReporteAnioDTO;
import Entitys.Carrera;
import Entitys.Estudiante;
import Entitys.EstudianteCarrera;
import CsvReader.CSVReader;
import Repositorys.CarreraRepository;
import Repositorys.EstudianteRepository;
import Repositorys.EstudianteCarreraRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Servicios {
    private EntityManager em;
    private CarreraRepository cr;
    private EstudianteRepository er;
    private EstudianteCarreraRepository ecr;

    public Servicios(EntityManager em){
        this.em = em;
        this.cr = new CarreraRepository(em);
        this.er = new EstudianteRepository(em);
        this.ecr = new EstudianteCarreraRepository(em);
    }

    private List<EstudianteDTO> convertirEstudiantes(List<Estudiante> estudiantes){
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        for (Estudiante estudiante : estudiantes){
            EstudianteDTO estudianteDTO = new EstudianteDTO(estudiante);
            estudiantesDTO.add(estudianteDTO);
        }
        return estudiantesDTO;
    }

    public void inicializarDB() throws Exception {
        CSVReader reader = new CSVReader(em, cr, ecr, er);
        reader.populateDB();
    }
    //a
    public void agregarEstudiante(Estudiante estudiante){
        em.getTransaction().begin();
        er.create(estudiante);
        em.getTransaction().commit();
    }
    //b
    public void matricularEstudiante(int dni, int carrera_id){
        Carrera carrera = cr.findById(carrera_id);
        Estudiante estudiante = er.findById(dni);
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, 2025, 0, 0);
        em.getTransaction().begin();
        ecr.create(estudianteCarrera);
        em.getTransaction().commit();
    }
    //c
    public List<EstudianteDTO> obtenerEstudiantes(){
        return convertirEstudiantes(er.findAll());
    }
    //d ggggg
    public EstudianteDTO obtenerEstudiantePorLU(int LU){
        return new EstudianteDTO(er.findByLU(LU));
    }
    //e
    public List<EstudianteDTO> obtenerEstudiantesPorGenero(String genero){
        return convertirEstudiantes(er.findByGenero(genero));
    }
    //f
    public List<CarreraDTO> obteberCarrerasConInscriptos(){
        return cr.EncontrarCarreraConInscriptos();
    }
    //g
    public List<EstudianteDTO> obtenerEstudiantesPorCarreraCiudad(int id_carrera, String ciudad){
        return convertirEstudiantes(er.findByCarreraAndCiudad(id_carrera, ciudad));
    }
    //3
    public List<ReporteAnioDTO> generarReporte(){
        return cr.generarReporte();
    }
}
