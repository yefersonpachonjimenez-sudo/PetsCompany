// Implementación: ProductoPromocionDAOImpl.java
package org.example.dao;

import org.example.Model.ProductoPromocion;
import org.example.Util.ConexionBD;
import java.sql.*;

public class ProductoPromocionDAOImpl implements ProductoPromocionDAO {
    @Override
    public boolean insertar(ProductoPromocion pp) {
        String sql = "INSERT INTO producto_promocion (id_producto, id_promocion) VALUES (?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pp.getIdProducto());
            ps.setInt(2, pp.getIdPromocion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}