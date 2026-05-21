// Implementación: ClientePromocionDAOImpl.java
package org.example.dao;

import org.example.Model.ClientePromocion;
import org.example.Util.ConexionBD;
import java.sql.*;

public class ClientePromocionDAOImpl implements ClientePromocionDAO {
    @Override
    public boolean insertar(ClientePromocion cp) {
        String sql = "INSERT INTO cliente_promocion (id_cliente, id_promocion, fecha_adquirida) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cp.getIdCliente());
            ps.setInt(2, cp.getIdPromocion());
            ps.setDate(3, cp.getFechaAdquirida());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}