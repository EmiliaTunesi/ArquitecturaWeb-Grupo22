package Entitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Estudiante {

    @Id
    private int dni;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private int edad;
    @Column
    private String genero;
    @Column
    private String ciudadResidencia;
    @Column
    private Integer nroLU;
    @OneToMany(mappedBy = "carrera")
    private List<EstudianteCarrera> carreras;
    public Estudiante() {

    }
    public Estudiante(int dni, String nombre, String apellido, int edad, String genero, int nroLU, String ciudadResidencia) {
        this.nroLU = nroLU;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia= ciudadResidencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNroLU() {
        return nroLU;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nroLU=" + nroLU +
                ", nombre='" + nombre +
                ", apellido='" + apellido +
                ", edad=" + edad +
                ", genero='" + genero +
                ", dni=" + dni +
                ", ciudadResidencia='" + ciudadResidencia +
                ", carreras=" + carreras +
                '}';
    }

    public void setNroLU(int nroLU) {
        this.nroLU = nroLU;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public List<EstudianteCarrera> getCarreras() {
        return new ArrayList<EstudianteCarrera>(carreras);
    }

}
