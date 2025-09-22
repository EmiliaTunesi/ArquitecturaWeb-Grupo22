package org.example.app.dao.interfaces;
/*EN LAS INTERFACES AGREGAMOS OPERACIONES QUE SE HACEN EN LAS TABLAS
como por ejemplo insertar,borrar,modificar,buscar
y la implementacion va en ClienDAODerby
*/

import org.example.app.entity.Cliente;

import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente c);
    Cliente obtenerPorId(int id);
    List<Cliente> obtenerTodos();
    void actualizar(Cliente client);
    void eliminar(int id);
}
