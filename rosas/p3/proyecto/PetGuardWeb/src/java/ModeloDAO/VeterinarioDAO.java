package ModeloDAO;

import Config.clsConexion;
import Interfaces.CRUDVeterinario;
import Modelo.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jefferson
 */
public class VeterinarioDAO implements CRUDVeterinario {

    clsConexion cn = new clsConexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Veterinario c = new Veterinario();

    @Override
    public boolean validarLogin(String dni, String clave) {
        boolean acceso = false;
        String sql = "SELECT * FROM tbVeterinario WHERE dni = ? AND contra = ? AND estado = 'activo'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if (rs.next()) {
                acceso = true;
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return acceso;
    }

    @Override
    public boolean add(Veterinario vet) {
        boolean exito = false;
        try {
            con = cn.getConnection();
            String sql = "INSERT INTO tbVeterinario (dni, nombre, apellidos, correo, telefono, direccion, contra) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, vet.getDni());
            ps.setString(2, vet.getNombre());
            ps.setString(3, vet.getApellidos());
            ps.setString(4, vet.getCorreo());
            ps.setString(5, vet.getTelefono());
            ps.setString(6, vet.getDireccion());
            ps.setString(7, vet.getContra());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar veterinario: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return exito;
    }

    @Override
    public boolean edit(Veterinario vet) {
        boolean exito = false;
        try {
            con = cn.getConnection();
            String sql = "UPDATE tbVeterinario SET nombre=?, apellidos=?, correo=?, telefono=?, direccion=?, foto=?, contra=? WHERE dni=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, vet.getNombre());
            ps.setString(2, vet.getApellidos());
            ps.setString(3, vet.getCorreo());
            ps.setString(4, vet.getTelefono());
            ps.setString(5, vet.getDireccion());
            ps.setString(6, vet.getFoto());
            ps.setString(7, vet.getContra());
            ps.setString(8, vet.getDni());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar los datos del veterinario: " + e.getMessage());
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
            // No se elimina físicamente, solo se marca como inactivo
            String sql = "UPDATE tbVeterinario SET estado = 'inactivo' WHERE dni = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al desactivar veterinario: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return exito;
    }

    @Override
    public Veterinario obtenerPorDni(String dni) {
        Veterinario veterinario = null;
        String sql = "SELECT dni, nombre, apellidos, correo, telefono, direccion, foto, contra, estado FROM tbVeterinario WHERE dni = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                veterinario = new Veterinario();
                veterinario.setDni(rs.getString("dni"));
                veterinario.setNombre(rs.getString("nombre"));
                veterinario.setApellidos(rs.getString("apellidos"));
                veterinario.setCorreo(rs.getString("correo"));
                veterinario.setTelefono(rs.getString("telefono"));
                veterinario.setDireccion(rs.getString("direccion"));
                veterinario.setFoto(rs.getString("foto"));
                veterinario.setContra(rs.getString("contra"));
                veterinario.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener veterinario por DNI: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return veterinario;
    }

    @Override
    public String obtenerFotoPorDni(String dni) {
        
        
            String foto = null;
    String sql = "SELECT foto FROM tbVeterinario WHERE dni = ?";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        rs = ps.executeQuery();
        if (rs.next()) {
            foto = rs.getString("foto");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener foto del veterinario por DNI: " + e.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (ps != null) ps.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }
    return foto;
        
    }

    
}