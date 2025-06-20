/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Config.clsConexion;
import Interfaces.CRUDMascota;
import Modelo.Mascota;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


/**
 *
 * @author Jefferson
 */
public class MascotaDAO implements CRUDMascota{
    
    clsConexion cn = new clsConexion();//para
    Connection con; //para llamar al
    PreparedStatement ps; //ejecutar
    ResultSet rs; //almacena datos de
    Mascota c = new Mascota();//objet
    Statement st;

    @Override
    public ResultSet listar(String dni) {
        
    ResultSet rs = null;
    String sql = "SELECT id, nombre, especie, raza, edad, sexo, dni_propietario FROM tbMascota WHERE dni_propietario = ? and estado = 'activo'";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, dni);  // Establecer el DNI del propietario para filtrar
        rs = ps.executeQuery();
    } catch (SQLException e) {
        System.out.println("Error al listar las mascotas: " + e.toString());
    }
    
    return rs;
                                     
    }

    @Override
    public Mascota list(String dni) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(Mascota pet) {
        
        // Construcción de la sentencia SQL
        String SQL = "INSERT INTO tbMascota(nombre, especie, raza, edad, sexo, dni_propietario, Url) "
                   + "VALUES ('" + pet.getNombre() + "', '" 
                   + pet.getEspecie() + "', '" 
                   + pet.getRaza() + "', " 
                   + pet.getEdad() + ", '" 
                   + pet.getSexo() + "', '" 
                   + pet.getDniPropietario() + "', '" 
                   + pet.getUrl() + "')";
        
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

    @Override
    public boolean edit(Mascota pet) {
        
        String sql = "UPDATE tbMascota SET nombre = ?, especie = ?, raza = ?, edad = ?, sexo = ?, dni_propietario = ?, Url = ? WHERE id = ?";
    
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pet.getNombre());
            ps.setString(2, pet.getEspecie());
            ps.setString(3, pet.getRaza());
            ps.setInt(4, pet.getEdad());
            ps.setString(5, pet.getSexo());
            ps.setString(6, pet.getDniPropietario());
            ps.setString(7, pet.getUrl());
            ps.setInt(8, pet.getId()); // ID es obligatorio para el WHERE

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar la mascota: " + e.toString());
            return false;
        }        
        
        
    }

    @Override
    public boolean eliminar(int pet) {

    String sql = "UPDATE tbMascota SET estado = 'inactivo' WHERE id = ?";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, pet);
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        System.out.println("Error al desactivar mascota: " + e.toString());
        return false;
    }        
        
        
    }

    @Override
    public int ultimaId() {
        
    
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

    @Override
    public Mascota obtenerPorId(int id) {
        
        Mascota mascota = null;
        String sql = "SELECT id, nombre, especie, raza, edad, sexo, dni_propietario, Url FROM tbMascota WHERE id = ? AND estado = 'activo'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                mascota = new Mascota();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setEdad(rs.getInt("edad"));
                mascota.setSexo(rs.getString("sexo"));
                mascota.setDniPropietario(rs.getString("dni_propietario"));
                mascota.setUrl(rs.getString("Url"));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar mascota por id: " + e.toString());
        }
        return mascota;        
        
    }

    @Override
    public Mascota obtenerMascota(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Mascota> obtenerNombre(String dni) {
        List<Mascota> mascotas = new ArrayList<>();
        // AGREGA la columna Url en el SELECT
        String sql = "SELECT id, nombre, Url FROM tbMascota WHERE dni_propietario = ? AND estado = 'activo'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            while (rs.next()) {
                Mascota m = new Mascota();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setUrl(rs.getString("Url")); // <-- AGREGA ESTA LÍNEA
                mascotas.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        } finally {
            // Cierra recursos
        }
        return mascotas;
    }
    
    
    public String obtenerFotoPorId(int id) {
    String url = null;
    String sql = "SELECT Url FROM tbMascota WHERE id = ? AND estado = 'activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            url = rs.getString("Url");
        }
        rs.close();
        ps.close();
        con.close();
    } catch (SQLException e) {
        System.out.println("Error al buscar foto de mascota por id: " + e.toString());
    }

    return url;
    
}


    
}
