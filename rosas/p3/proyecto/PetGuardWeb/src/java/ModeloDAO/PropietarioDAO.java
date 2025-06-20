/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Config.clsConexion;
import Interfaces.CRUDPropietario;
import Modelo.Propietario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Jefferson
 */
public class PropietarioDAO implements CRUDPropietario {
    
        
    clsConexion cn = new clsConexion();//para
    Connection con; //para llamar al
    PreparedStatement ps; //ejecutar
    ResultSet rs; //almacena datos de
    Propietario c = new Propietario();//objet

    @Override
    public boolean validarLogin(String dni, String clave) {
        
    boolean acceso = false;
    String sql = "SELECT * FROM tbPropietario WHERE dni = ? AND contra = ? AND estado = 'activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ps.setString(2, clave);
        rs = ps.executeQuery();
        if (rs.next()) {
            acceso = true; // Login correcto
        }
    } catch (SQLException e) {
        System.out.println("Error en login: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
    return acceso;
        
        
    }

    @Override
    
    public boolean edit(Propietario pro) {
    boolean exito = false;
    try {
        // Obtener la conexión
        con = cn.getConnection();

        // Consulta SQL para actualizar los datos del propietario, ahora incluye la columna foto
        String sql = "UPDATE tbPropietario SET nombre = ?, apellido = ?, telefono = ?, direccion = ?, correo = ?, contra = ?, foto = ? WHERE dni = ?";

        // Preparar la sentencia SQL
        ps = con.prepareStatement(sql);

        // Establecer los valores en la sentencia SQL
        ps.setString(1, pro.getNombre());
        ps.setString(2, pro.getApellido());
        ps.setString(3, pro.getTelefono());
        ps.setString(4, pro.getDireccion());
        ps.setString(5, pro.getCorreo());
        ps.setString(6, pro.getContra());
        ps.setString(7, pro.getFoto()); // Nuevo campo: foto
        ps.setString(8, pro.getDni());

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
    
    

    @Override
    public boolean add(Propietario pro) {
        
        boolean exito = false;
        try {
            con = cn.getConnection();

            String sql = "INSERT INTO tbPropietario (dni, nombre, apellido, telefono, direccion, correo, contra) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getDni());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getApellido());
            ps.setString(4, pro.getTelefono());
            ps.setString(5, pro.getDireccion());
            ps.setString(6, pro.getCorreo());
            ps.setString(7, pro.getContra());

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

    @Override
    public boolean eliminar(String dni) {
        
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

    @Override
    public Propietario obtenerPorDni(String dni) {
        
        Propietario propietario = null;
       String sql = "SELECT dni, nombre, apellido, telefono, direccion, correo, contra, estado, foto FROM tbPropietario WHERE dni = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setString(1, dni);
           rs = ps.executeQuery();
           if (rs.next()) {
               propietario = new Propietario();
               propietario.setDni(rs.getString("dni"));
               propietario.setNombre(rs.getString("nombre"));
               propietario.setApellido(rs.getString("apellido"));
               propietario.setTelefono(rs.getString("telefono"));
               propietario.setDireccion(rs.getString("direccion"));
               propietario.setCorreo(rs.getString("correo"));
               propietario.setContra(rs.getString("contra"));
               propietario.setEstado(rs.getString("estado"));
               propietario.setFoto(rs.getString("foto"));
           }
       } catch (SQLException e) {
           System.out.println("Error al obtener propietario por DNI: " + e.getMessage());
       } finally {
           try { if (rs != null) rs.close(); } catch (SQLException e) {}
           try { if (ps != null) ps.close(); } catch (SQLException e) {}
           try { if (con != null) con.close(); } catch (SQLException e) {}
       }
       return propietario;      
        
    }
    
}
