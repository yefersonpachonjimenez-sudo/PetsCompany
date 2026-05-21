// Implementación: PromocionDAOImpl.java
package org.example.dao;

import org.example.Model.Promocion;
import org.example.Util.ConexionBD;
import java.sql.*;

public class PromocionDAOImpl implements PromocionDAO {

    @Override
    public boolean insertar(Promocion p) {
        String sql = "INSERT INTO promocion (nombre, descuento_pct, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getDescuentoPct());
            ps.setDate(3, p.getFechaInicio());
            ps.setDate(4, p.getFechaFin());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) p.setIdPromocion(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public Promocion buscarPorDescuento(double descuentoPct) {
        // Busca la última promoción registrada que coincida con el porcentaje
        String sql = "SELECT * FROM promocion WHERE descuento_pct = ? ORDER BY id_promocion DESC LIMIT 1";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, descuentoPct);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Promocion p = new Promocion();
                    p.setIdPromocion(rs.getInt("id_promocion"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescuentoPct(rs.getDouble("descuento_pct"));
                    p.setFechaInicio(rs.getDate("fecha_inicio"));
                    p.setFechaFin(rs.getDate("fecha_fin"));
                    return p;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}