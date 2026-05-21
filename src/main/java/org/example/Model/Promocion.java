package org.example.Model;
import java.sql.Date;

public class Promocion {
    private int idPromocion;
    private String nombre;
    private double descuentoPct;
    private Date fechaInicio;
    private Date fechaFin;

    public Promocion() {
    }

    public Promocion(int idPromocion, String nombre, double descuentoPct, Date fechaInicio, Date fechaFin) {
        this.idPromocion = idPromocion;
        this.nombre = nombre;
        this.descuentoPct = descuentoPct;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDescuentoPct() {
        return descuentoPct;
    }

    public void setDescuentoPct(double descuentoPct) {
        this.descuentoPct = descuentoPct;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Promocion{" +
                "idPromocion=" + idPromocion +
                ", nombre='" + nombre + '\'' +
                ", descuentoPct=" + descuentoPct +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
