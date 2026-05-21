package org.example.Model;

import java.sql.Date;

public class ClientePromocion {

    private int  idCliente;
    private int  idPromocion;
    private Date fechaAdquirida;

    public ClientePromocion() {}

    public ClientePromocion(int idCliente, int idPromocion, Date fechaAdquirida) {
        this.idCliente      = idCliente;
        this.idPromocion    = idPromocion;
        this.fechaAdquirida = fechaAdquirida;
    }

    public int  getIdCliente()      { return idCliente; }
    public int  getIdPromocion()    { return idPromocion; }
    public Date getFechaAdquirida() { return fechaAdquirida; }

    public void setIdCliente(int idCliente)           { this.idCliente      = idCliente; }
    public void setIdPromocion(int idPromocion)       { this.idPromocion    = idPromocion; }
    public void setFechaAdquirida(Date fechaAdquirida){ this.fechaAdquirida = fechaAdquirida; }

    @Override
    public String toString() {
        return "ClientePromocion{idCliente=" + idCliente +
                ", idPromocion=" + idPromocion +
                ", fechaAdquirida=" + fechaAdquirida + "}";
    }
}