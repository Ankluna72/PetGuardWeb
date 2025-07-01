/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocios;

import Config.clsConexion;

import Config.clsConexion;
import Entidad.clsEPropietario;
import Config.*;
import Entidad.clsEHistorialMedico;
import Entidad.clsEMascota;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Jefferson
 */
public class clsNHistorialMedico {
    
    clsConexion cn=new clsConexion(); // para establecer la conexion
    Connection con; //para llamar a la cadena conexion
    PreparedStatement ps; //ejecutar consultas query(select, insert, etc)
    ResultSet rs; // almacena datos de la consulta
    Statement st;
    clsEHistorialMedico c=new clsEHistorialMedico();//objeto cliente

    // Método para guardar la mascota en la base de datos
    public boolean MtdGuardarHistorialMedico(clsEHistorialMedico obj) {
    String SQL = "INSERT INTO tbHistorialMedico(id_mascota, tipo_evento, fecha, descripcion, veterinario, dni_propietario) "
               + "VALUES (?, ?, ?, ?, ?, ?)";
    
    try {
        con = cn.getConnection();  // Obtener conexión
        ps = con.prepareStatement(SQL);  // Preparar la sentencia SQL
        ps.setInt(1, obj.getId_mascota());
        ps.setString(2, obj.getTipo_evento());
        ps.setDate(3, java.sql.Date.valueOf(obj.getFecha())); // Convertir LocalDate a java.sql.Date
        ps.setString(4, obj.getDescripcion());
        ps.setString(5, obj.getVeterinario());
        ps.setString(6, obj.getDni_propietario()); // Nuevo parámetro añadido
        ps.executeUpdate();  // Ejecutar inserción
        return true;
    } catch (SQLException e) {
        System.out.println("Error al guardar historial médico: " + e.toString());
        return false;
    }
    }

    
    
    public ResultSet MtdListarHistorialPorDniPropietario(String dniPropietario) {
    ResultSet rs = null;
    // Si quieres excluir la descripción, simplemente no la pongas en el SELECT
    String sql = "SELECT id, id_mascota, tipo_evento, fecha, veterinario "
               + "FROM tbHistorialMedico "
               + "WHERE dni_propietario = ? and estado = 'activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, dniPropietario);  // Filtrar por el DNI del propietario
        rs = ps.executeQuery();
    } catch (SQLException e) {
        System.out.println("Error al listar el historial médico: " + e.toString());
    }
    return rs;
    }

    
    
    
    
    
    
    
    
    
    
    public int MtdObtenerUltimaId() {
    int ultimaId = -1;  // Valor predeterminado si no se encuentra ninguna fila
    String SQL = "SELECT MAX(id) AS ultima_id FROM tbHistorialMedico";  // Consulta para obtener el id máximo
    
    try {
        con = cn.getConnection();  // Establece la conexión
        st = con.createStatement();  // Prepara la sentencia SQL
        rs = st.executeQuery(SQL);  // Ejecuta la consulta
        
        if (rs.next()) {
            ultimaId = rs.getInt("ultima_id");  // Obtiene el valor de la última id
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener la última id: " + e.toString());
    }
    
    return ultimaId;  // Devuelve el valor de la última id (o -1 si no se encuentra ninguna fila)
    }
    
    
    
    
    public boolean MtdModificarHistorialMedico(clsEHistorialMedico obj) {
    String SQL = "UPDATE tbHistorialMedico SET id_mascota = ?, tipo_evento = ?, fecha = ?, descripcion = ?, veterinario = ?, dni_propietario = ? "
               + "WHERE id = ?";

    try {
        con = cn.getConnection();  // Obtener conexión
        ps = con.prepareStatement(SQL);  // Preparar la sentencia SQL
        ps.setInt(1, obj.getId_mascota());
        ps.setString(2, obj.getTipo_evento());
        ps.setDate(3, java.sql.Date.valueOf(obj.getFecha()));
        ps.setString(4, obj.getDescripcion());
        ps.setString(5, obj.getVeterinario());
        ps.setString(6, obj.getDni_propietario());
        ps.setInt(7, obj.getId());  // El ID del historial que se va a modificar
        ps.executeUpdate();  // Ejecutar actualización
        return true;
    } catch (SQLException e) {
        System.out.println("Error al modificar historial médico: " + e.toString());
        return false;
    }
    }
    
    
    public clsEHistorialMedico MtdObtenerHistorialPorId(int idHistorial) {
    clsEHistorialMedico historial = null;
    String SQL = "SELECT * FROM tbHistorialMedico WHERE id = ? AND estado = 'activo'";

    try {
        con = cn.getConnection();  // Establecer conexión
        ps = con.prepareStatement(SQL);
        ps.setInt(1, idHistorial);  // Pasar la ID del historial
        rs = ps.executeQuery();

        if (rs.next()) {
            historial = new clsEHistorialMedico();  // Crear objeto
            historial.setId(rs.getInt("id"));
            historial.setId_mascota(rs.getInt("id_mascota"));
            historial.setTipo_evento(rs.getString("tipo_evento"));
            historial.setFecha(rs.getDate("fecha").toLocalDate());
            historial.setDescripcion(rs.getString("descripcion"));
            historial.setVeterinario(rs.getString("veterinario"));
            historial.setDni_propietario(rs.getString("dni_propietario"));
            // Puedes agregar más campos si agregas nuevas columnas
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener historial médico por ID: " + e.toString());
    }

    return historial;  // Devuelve null si no encontró o el objeto lleno si lo encontró
    }
    
    
    
    public boolean MtdDesactivarHistorial(int idHistorial) {
    String sql = "UPDATE tbHistorialMedico SET estado = 'inactivo' WHERE id = ?";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idHistorial);
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        System.out.println("Error al desactivar historial: " + e.toString());
        return false;
    }
    }
    
    
    
}
