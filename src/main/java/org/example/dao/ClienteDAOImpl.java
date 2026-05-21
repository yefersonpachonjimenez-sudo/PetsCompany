// Implementación: ClienteDAOImpl.java
package org.example.dao;

import org.example.Model.Cliente;
import org.example.Util.ConexionBD;
import java.sql.*;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO cliente (nombre, correo, telefono) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCorreo());
            ps.setString(3, c.getTelefono());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) c.setIdCliente(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}