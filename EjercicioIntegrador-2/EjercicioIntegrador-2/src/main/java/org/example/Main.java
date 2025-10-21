package org.example;

import Entitys.Estudiante;
import Service.Servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Arqui");
        EntityManager em = emf.createEntityManager();

        Servicios serv = new Servicios(em);
        serv.inicializarDB();

        //a) dar de alta un estudiante
       // Estudiante estudiante = new Estudiante(1310101008, "Soledad", "Rodriguez", 29, "Femenino", 1290811, "Balcarce");
        //serv.agregarEstudiante(estudiante);

        //b) matricular un estudiante en una carrera //mejora enviar estudiante
        //serv.matricularEstudiante(1310101008, 1);

        //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
       //System.out.println(serv.obtenerEstudiantes());

        //d) recuperar un estudiante, en base a su número de libreta universitaria.
        //System.out.println(serv.obtenerEstudiantePorLU(95419));

        //e) recuperar todos los estudiantes, en base a su género.
       //System.out.println(serv.obtenerEstudiantesPorGenero("Masculino"));

        //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        //System.out.println(serv.obteberCarrerasConInscriptos());

        //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        //System.out.println(serv.obtenerEstudiantesPorCarreraCiudad(1, "Balcarce"));

        /*
        3) Generar un reporte de las carreras, que para cada carrera incluya
        información de los inscriptos y egresados por año. Se deben ordenar las
        carreras alfabéticamente, y presentar los años de manera cronológica.
         */
        //System.out.println(serv.generarReporte());

        em.close();
        emf.close();
    }
}
