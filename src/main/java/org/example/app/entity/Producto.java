package org.example.app.entity;

public class Producto {
    private int idProduct;
    private String nombre;
    private float price;

    public Producto(String name, float price) {
        this.nombre = name;
        this.price = price;
    }
    public Producto(int idProduct,String name, float price) {
        this.nombre = name;
        this.price = price;
        this.idProduct=idProduct;
    }

    public Producto copy(){return new Producto(this.idProduct,this.nombre,this.price);}

    public int getIdProduct() {
        return idProduct;
    }

    public String getNombre() {
        return nombre;
    }

    public float getValue() {
        return price;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Producto " +
                ", id: " + idProduct +
                ", nombre: " + nombre + '\'' +
                ", precio: " + price;
    }
}
