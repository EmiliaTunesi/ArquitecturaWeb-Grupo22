package org.example.app.entity;

public class Factura {
    private int idFacture;
    private int idClient;

    public Factura(int idFacture, int idClient) {
        this.idFacture = idFacture;
        this.idClient = idClient;
    }

    public Factura (Cliente c){
        this.idClient=c.getIdClient();
    }

    public Factura (int id){
        this.idClient=id;
    }
    public int getIdFacture() {
        return idFacture;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Factura copy() {return new Factura(this.getIdFacture(),this.getIdClient());}

    @Override
    public String toString() {
        return "Factura" +
                ", idFactura: " + idFacture +
                ", idCliente: " + idClient;
    }
}
