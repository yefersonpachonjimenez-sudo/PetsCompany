
package org.example.dao;
import org.example.Model.Producto;
import java.util.List;

public interface ProductoDAO {
    boolean insertar(Producto producto);
    List<Producto> listarTodos();
    Producto buscarPorId(int id);
}