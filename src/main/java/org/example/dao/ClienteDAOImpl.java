package org.example.dao;

import org.example.Model.Cliente;
import org.example.Util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public boolean insertar(Cliente c) {
        // id_cliente == cédula: se inserta el valor que trae el objeto
        String sql = "INSERT INTO cliente (id_cliente, nombre, correo, telefono) VALUES (?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertando Cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente buscarPorCedula(int cedula) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error buscando Cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listando Clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Cliente c) {
        String sql = "UPDATE cliente SET nombre=?, correo=?, telefono=? WHERE id_cliente=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCorreo());
            ps.setString(3, c.getTelefono());
            ps.setInt(4, c.getCedula());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando Cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int cedula) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando Cliente: " + e.getMessage());
            return false;
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id_cliente"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("telefono")
        );
    }
}