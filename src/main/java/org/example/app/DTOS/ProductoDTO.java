package org.example.app.DTOS;

public class ProductoDTO {
    private String nombre;
    private double totalRecaudado;

    public ProductoDTO(String nombre, double totalRecaudado) {
         this.nombre = nombre;
        this.totalRecaudado = totalRecaudado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(double totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }
    public String toString() {
        return "Producto: " + nombre + " - Total recaudado: $" + totalRecaudado;
    }
}
