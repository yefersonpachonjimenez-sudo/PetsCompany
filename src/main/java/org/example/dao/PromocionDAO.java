package org.example.dao;
import org.example.Model.Promocion;

public interface PromocionDAO {
    boolean insertar(Promocion promocion);
    Promocion buscarPorDescuento(double descuentoPct);
}