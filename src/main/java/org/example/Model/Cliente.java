package org.example.Model;

public class Cliente {

    private int    idCliente;
    private String nombre;
    private String correo;
    private String telefono;

    public Cliente() {}

    public Cliente(int idCliente, String nombre, String correo, String telefono) {
        this.idCliente = idCliente;
        this.nombre    = nombre;
        this.correo    = correo;
        this.telefono  = telefono;
    }

    public int    getIdCliente() { return idCliente; }
    public String getNombre()    { return nombre; }
    public String getCorreo()    { return correo; }
    public String getTelefono()  { return telefono; }

    public void setIdCliente(int idCliente)   { this.idCliente = idCliente; }
    public void setNombre(String nombre)      { this.nombre    = nombre; }
    public void setCorreo(String correo)      { this.correo    = correo; }
    public void setTelefono(String telefono)  { this.telefono  = telefono; }

    @Override
    public String toString() {
        return "Cliente{id=" + idCliente + ", nombre='" + nombre + "', correo='" + correo + "'}";
    }
}