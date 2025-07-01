/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocios;

import Config.clsConexion;
import Entidad.clsEPropietario;
import Config.*;
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
public class clsNMascota {
    
    clsConexion cn=new clsConexion(); // para establecer la conexion
    Connection con; //para llamar a la cadena conexion
    PreparedStatement ps; //ejecutar consultas query(select, insert, etc)
    ResultSet rs; // almacena datos de la consulta
    Statement st;
    clsEMascota c=new clsEMascota();//objeto cliente

    // Método para guardar la mascota en la base de datos
    
    public boolean MtdGuardarMascota(clsEMascota objEMascota) {
        // Construcción de la sentencia SQL
        String SQL = "INSERT INTO tbMascota(nombre, especie, raza, edad, sexo, dni_propietario, Url) "
                   + "VALUES ('" + objEMascota.getNombre() + "', '" 
                   + objEMascota.getEspecie() + "', '" 
                   + objEMascota.getRaza() + "', " 
                   + objEMascota.getEdad() + ", '" 
                   + objEMascota.getSexo() + "', '" 
                   + objEMascota.getDniPropietario() + "', '" 
                   + objEMascota.getUrl() + "')";
        
        System.out.println("Sentencia SQL: " + SQL); // Para corroborar que la sentencia está bien construida
        
        try {
            con = cn.getConnection(); // Llamar al método para obtener la conexión
            st = con.createStatement(); // Crear la declaración para ejecutar la consulta
            st.executeUpdate(SQL); // Ejecutar la sentencia SQL
            return true; // Retornar true si la operación fue exitosa
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString()); // Si ocurre un error, mostrarlo
            return false; // Retornar false en caso de error
        }
    }
    
    
    public int MtdObtenerUltimaId() {
    int ultimaId = -1;  // Valor predeterminado si no se encuentra ninguna fila
    String SQL = "SELECT MAX(id) AS ultima_id FROM tbMascota";  // Consulta para obtener el id máximo
    
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
    
    
    public ResultSet MtdListarMascotas(String dniPropietario) {
    ResultSet rs = null;
    String sql = "SELECT id, nombre, especie, raza, edad, sexo, dni_propietario FROM tbMascota WHERE dni_propietario = ? and estado = 'activo'";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, dniPropietario);  // Establecer el DNI del propietario para filtrar
        rs = ps.executeQuery();
    } catch (SQLException e) {
        System.out.println("Error al listar las mascotas: " + e.toString());
    }
    
    return rs;
    }

    public boolean MtdModificarMascota(clsEMascota objEC) {
        
        
        String sql = "UPDATE tbMascota SET nombre = ?, especie = ?, raza = ?, edad = ?, sexo = ?, dni_propietario = ?, Url = ? WHERE id = ?";
    
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, objEC.getNombre());
            ps.setString(2, objEC.getEspecie());
            ps.setString(3, objEC.getRaza());
            ps.setInt(4, objEC.getEdad());
            ps.setString(5, objEC.getSexo());
            ps.setString(6, objEC.getDniPropietario());
            ps.setString(7, objEC.getUrl());
            ps.setInt(8, objEC.getId()); // ID es obligatorio para el WHERE

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar la mascota: " + e.toString());
            return false;
        }
        
     
        
        
        
    }
    
    
    
    
    //obtener mascota
    
    
    public clsEMascota MtdObtenerMascotaPorId(int idMascota) {
    clsEMascota mascota = null;
    String sql = "SELECT id, nombre, especie, raza, edad, sexo, dni_propietario, Url FROM tbMascota WHERE id = ?";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idMascota);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            mascota = new clsEMascota();
            mascota.setId(rs.getInt("id"));
            mascota.setNombre(rs.getString("nombre"));
            mascota.setEspecie(rs.getString("especie"));
            mascota.setRaza(rs.getString("raza"));
            mascota.setEdad(rs.getInt("edad"));
            mascota.setSexo(rs.getString("sexo"));
            mascota.setDniPropietario(rs.getString("dni_propietario"));
            mascota.setUrl(rs.getString("Url"));
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener la mascota por ID: " + e.toString());
    }
    
    return mascota;
    }
    
    
    
    public boolean MtdDesactivarMascota(int idMascota) {
    String sql = "UPDATE tbMascota SET estado = 'inactivo' WHERE id = ?";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idMascota);
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        System.out.println("Error al desactivar mascota: " + e.toString());
        return false;
    }
    }
    
    
    public int MtdObtenerIdMascotaPorNombre(String nombreMascota) {
    int idMascota = -1;  // Valor por defecto si no se encuentra

    String sql = "SELECT id FROM tbMascota WHERE nombre = ? AND estado = 'activo'";

    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, nombreMascota);
        rs = ps.executeQuery();

        if (rs.next()) {
            idMascota = rs.getInt("id");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ID de la mascota por nombre: " + e.toString());
    }

    return idMascota;
    }
    
    
    
    public String MtdObtenerNombreMascotaPorId(int idMascota) {
    String nombreMascota = null;
    String sql = "SELECT nombre FROM tbMascota WHERE id = ? AND estado = 'activo'";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idMascota);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            nombreMascota = rs.getString("nombre");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener el nombre de la mascota por ID: " + e.toString());
    }
    
    return nombreMascota;
    }

    
}
