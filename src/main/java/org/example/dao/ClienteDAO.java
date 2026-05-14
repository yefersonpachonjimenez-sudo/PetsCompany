package org.example.dao;

import org.example.Model.Cliente;
import java.util.List;

public interface ClienteDAO {
    boolean insertar(Cliente c);
    Cliente buscarPorCedula(int cedula);
    List<Cliente> listarTodos();
    boolean actualizar(Cliente c);
    boolean eliminar(int cedula);
}