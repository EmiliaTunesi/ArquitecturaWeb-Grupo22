package org.example.app.dao.interfaces;

import org.example.app.entity.Producto;

import java.util.List;

public interface ProductoDAO {
    void insertar(Producto p);
    Producto obtenerPorId(int id);
    List<Producto> obtenerTodos();
    void actualizar(Producto p);
    void eliminar(int id);
}
