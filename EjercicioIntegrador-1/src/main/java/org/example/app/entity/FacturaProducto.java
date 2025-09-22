package org.example.app.entity;

public class FacturaProducto {
    private int idFacture;
    private int idProduct;
    private int cantidad;

    public FacturaProducto(int idFacture, int idProduct, int cantidad) {
        this.idFacture = idFacture;
        this.idProduct = idProduct;
        this.cantidad = cantidad;
    }

    public int getIdFacture() {return idFacture;}

    public int getIdProduct() {return idProduct;}

    public int getCantidad() {return cantidad;}

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public FacturaProducto copy() {return new FacturaProducto(this.getIdFacture(),this.getIdProduct(),this.getCantidad());}

    @Override
    public String toString() {
        return "FacturaProducto: " +
                ", idFactura: " + idFacture +
                ", idProducto: " + idProduct +
                ", cantidad: " + cantidad;
    }
}
