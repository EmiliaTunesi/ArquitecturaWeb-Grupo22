package org.example.app.dao.interfaces;

import org.example.app.entity.Factura;

import java.util.List;

public interface FacturaDAO {
    void insertar(Factura fac);
    Factura obtenerPorId(int id);
    List<Factura> obtenerTodos();
    void actualizar(Factura fac);
    void eliminar(int id);
}
