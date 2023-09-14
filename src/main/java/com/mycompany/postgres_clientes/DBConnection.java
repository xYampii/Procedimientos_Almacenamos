package com.mycompany.postgres_clientes;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class DBConnection { 
    Connection cn = null;

    public Connection establecerConexion() {
        try {
            Properties propiedades = new Properties();
            String rutaArchivo = "C:\\Users\\suyan\\OneDrive\\Documentos\\Progra Files\\Universidad\\Netbeans Projects\\Postgres_clientes - copia\\src\\main\\java\\com\\mycompany\\postgres_clientes\\config.properties";

            try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
                propiedades.load(fis);

                String dbUser = propiedades.getProperty("dbUser");
                String dbPassword = propiedades.getProperty("dbPassword");
                String dbUrl = propiedades.getProperty("dbUrl");

                Class.forName("org.postgresql.Driver");
                cn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                JOptionPane.showMessageDialog(null, "Conexión establecida");
            } catch (IOException | ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
        return cn;
    }
}
