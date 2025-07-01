/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocios;

import Entidad.clsEPropietario;
import Config.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jefferson
 */
public class clsNPropietario {
    
    
    clsConexion cn=new clsConexion(); // para establecer la conexion
    Connection con; //para llamar a la cadena conexion
    PreparedStatement ps; //ejecutar consultas query(select, insert, etc)
    ResultSet rs; // almacena datos de la consulta
    Statement st;
    clsEPropietario c=new clsEPropietario();//objeto cliente
    
    
    public String MtdObtenerNombrePorDni(String dni) {
    String nombreCompleto = null;
    
    try {
        con = cn.getConnection(); // Obtener conexión
        String sql = "SELECT nombre, apellido FROM tbPropietario WHERE dni = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            nombreCompleto = nombre + " " + apellido;
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener nombre por DNI: " + e.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (ps != null) ps.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }
    
    return nombreCompleto;
    }
    
    
    // Método para obtener un objeto clsEPropietario con todos los datos
    public clsEPropietario MtdObtenerDatosPorDni(String dni) {
        clsEPropietario propietario = null;
        try {
            con = cn.getConnection(); // Obtener conexión
            String sql = "SELECT nombre, apellido, telefono, direccion, correo, contra FROM tbPropietario WHERE dni = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Crear el objeto propietario y asignar los valores
                propietario = new clsEPropietario(
                    dni,
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("correo"),
                    rs.getString("contra")
                );
            } else {
                // Si no se encuentra el propietario, puedes devolver null o manejarlo como prefieras
                System.out.println("No se encontró el propietario con el DNI proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos por DNI: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return propietario;
    }

    public boolean MtdModificarPropietario(clsEPropietario objEC) {
        
        boolean exito = false;
    try {
        // Obtener la conexión
        con = cn.getConnection();
        
        // Consulta SQL para actualizar los datos del propietario
        String sql = "UPDATE tbPropietario SET nombre = ?, apellido = ?, telefono = ?, direccion = ?, correo = ?, contra = ? WHERE dni = ?";
        
        // Preparar la sentencia SQL
        ps = con.prepareStatement(sql);
        
        // Establecer los valores en la sentencia SQL
        ps.setString(1, objEC.getNombre());
        ps.setString(2, objEC.getApellido());
        ps.setString(3, objEC.getTelefono());
        ps.setString(4, objEC.getDireccion());
        ps.setString(5, objEC.getCorreo());
        ps.setString(6, objEC.getContra());
        ps.setString(7, objEC.getDni());
        
        // Ejecutar la actualización
        int filasAfectadas = ps.executeUpdate();
        
        // Verificar si se afectaron filas (lo que significa que se actualizó correctamente)
        if (filasAfectadas > 0) {
            exito = true;
        }
    } catch (SQLException e) {
        System.out.println("Error al modificar los datos del propietario: " + e.getMessage());
    } finally {
        // Cerrar recursos
        try { if (ps != null) ps.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }
    return exito;
        
        
    }
    

    public boolean MtdRegistrarPropietario(clsEPropietario objEC) {
    boolean exito = false;
    try {
        con = cn.getConnection();

        String sql = "INSERT INTO tbPropietario (dni, nombre, apellido, telefono, direccion, correo, contra) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        ps = con.prepareStatement(sql);
        ps.setString(1, objEC.getDni());
        ps.setString(2, objEC.getNombre());
        ps.setString(3, objEC.getApellido());
        ps.setString(4, objEC.getTelefono());
        ps.setString(5, objEC.getDireccion());
        ps.setString(6, objEC.getCorreo());
        ps.setString(7, objEC.getContra());

        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            exito = true;
        }

    } catch (SQLException e) {
        System.out.println("Error al registrar propietario: " + e.getMessage());
    } finally {
        try { if (ps != null) ps.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }
    return exito;
    }
    
    
    
    
    public boolean MtdEliminarPropietario(String dni) {
        boolean exito = false;
        try {
            con = cn.getConnection();

            // Consulta SQL para actualizar el estado a inactivo
            String sql = "UPDATE tbPropietario SET estado = 'inactivo' WHERE dni = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, dni);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al desactivar propietario: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return exito;
    }
    
    
    
}
