package org.example.app.entity;

public class Cliente {
    private int idClient;
    private String nombre;
    private String email;

    public Cliente(String name, String email) {
        this.nombre = name;
        this.email = email;
    }

    public Cliente(int idClient,String name, String email){
        this.idClient=idClient;
        this.nombre=name;
        this.email=email;
    }
    public Cliente(){}

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdClient() {
        return idClient;
    }


    public String getNombre() {
        return nombre;
    }


    public String getEmail() {
        return email;
    }


    public Cliente getClient(){return new Cliente(this.idClient,this.nombre,this.email);}

    @Override
    public String toString() {

        return  "Cliente: " +
                "id: " + idClient +
                ", nombre: " + nombre + '\'' +
                ", email: " + email;
    }
}
