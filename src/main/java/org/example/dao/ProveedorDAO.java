// Interface: ProveedorDAO.java
package org.example.dao;
import org.example.Model.Proveedor;
import java.util.List;

public interface ProveedorDAO {
    boolean insertar(Proveedor proveedor);
    List<Proveedor> listarTodos();
    Proveedor buscarPorId(int id);
}