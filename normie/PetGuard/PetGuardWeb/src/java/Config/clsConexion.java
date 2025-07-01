/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.sql.*;
    
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Jefferson
 */
public class clsConexion {
    
    Connection con;

    public Connection getConnection() {
        
        try {
            // Establece la conexión a la base de datos MySQL en la nube
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection(
                "jdbc:mysql://sql.freedb.tech:3306/freedb_petguardbd", 
                "freedb_Rofferson21", 
                "F67##yVuPm4CT&&"
            );
        
        } catch (SQLException | ClassNotFoundException e) {
            // En caso de error, muestra el mensaje
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        return con;
    }
    
}
