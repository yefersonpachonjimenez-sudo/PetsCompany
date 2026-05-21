package org.example.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3314/tienda";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection conexion = null;

    private ConexionBD() {}


    public static Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("-> Conexión establecida con éxito.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de SQL al intentar conectar con la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("-> Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("Error al intentar cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}