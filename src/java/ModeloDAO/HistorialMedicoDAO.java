package ModeloDAO;

import Config.clsConexion;
import Interfaces.CRUDHistorialMedico;
import Modelo.HistorialMedico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jefferson
 */
public class HistorialMedicoDAO implements CRUDHistorialMedico {

    clsConexion cn = new clsConexion(); // para establecer la conexion
    Connection con; //para llamar a la cadena conexion
    PreparedStatement ps; //ejecutar consultas query(select, insert, etc)
    ResultSet rs; // almacena datos de la consulta
    Statement st;
    HistorialMedico c = new HistorialMedico();//objeto cliente

    @Override
    public boolean add(HistorialMedico his) {
        // Se elimina el campo "veterinario" del INSERT y del VALUES
        String SQL = "INSERT INTO tbHistorialMedico("
                + "id_mascota, tipo_evento, fecha, descripcion, dni_propietario, tratamiento_aplicado, proxima_cita, dni_veterinario"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setInt(1, his.getId_mascota()); // obligatorio
            ps.setString(2, his.getTipo_evento());
            ps.setDate(3, java.sql.Date.valueOf(his.getFecha())); // LocalDate a sql.Date
            ps.setString(4, his.getDescripcion());
            ps.setString(5, his.getDni_propietario());
            ps.setString(6, his.getTratamiento_aplicado());
            if (his.getProxima_cita() != null) {
                ps.setTimestamp(7, java.sql.Timestamp.valueOf(his.getProxima_cita())); // LocalDateTime
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }
            ps.setString(8, his.getDni_veterinario()); // NUEVO campo agregado
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar historial médico: " + e.toString());
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    @Override
    public boolean edit(HistorialMedico his) {
        // Se elimina el campo "veterinario" del UPDATE
        String SQL = "UPDATE tbHistorialMedico SET "
                + "id_mascota = ?, tipo_evento = ?, fecha = ?, descripcion = ?, "
                + "dni_propietario = ?, tratamiento_aplicado = ?, proxima_cita = ?, dni_veterinario = ? "
                + "WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setInt(1, his.getId_mascota());
            ps.setString(2, his.getTipo_evento());
            ps.setDate(3, java.sql.Date.valueOf(his.getFecha())); // LocalDate
            ps.setString(4, his.getDescripcion());
            ps.setString(5, his.getDni_propietario());
            ps.setString(6, his.getTratamiento_aplicado());
            if (his.getProxima_cita() != null) {
                ps.setTimestamp(7, java.sql.Timestamp.valueOf(his.getProxima_cita())); // LocalDateTime
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }
            ps.setString(8, his.getDni_veterinario());
            ps.setInt(9, his.getId()); // El id a modificar
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("Error al editar historial médico: " + e.toString());
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    @Override
    public boolean eliminar(int id) {
        String SQL = "UPDATE tbHistorialMedico SET estado = 'inactivo' WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar historial médico: " + e.toString());
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    @Override
    public ResultSet listar(String dni) {
    ResultSet rs = null;
    // Se obtiene el nombre del propietario asociando por dni_propietario
    String SQL = "SELECT h.id, h.id_mascota, h.tipo_evento, h.fecha, h.proxima_cita, " +
                 "p.nombre AS nombre_propietario " +
                 "FROM tbHistorialMedico h " +
                 "JOIN tbPropietario p ON h.dni_propietario = p.dni " +
                 "WHERE h.dni_veterinario = ? AND h.estado = 'activo' " +
                 "ORDER BY h.fecha DESC";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(SQL);
        ps.setString(1, dni);
        rs = ps.executeQuery();
        // No cierres la conexión ni el PreparedStatement aquí porque el ResultSet se usará fuera de este método
    } catch (SQLException e) {
        System.out.println("Error al listar historial médico por veterinario: " + e.toString());
    }
    return rs; 
    }

    @Override
    public HistorialMedico obtenerHistorial(int id) {
        HistorialMedico his = null;
        // Se elimina "veterinario" del SELECT
        String SQL = "SELECT id, id_mascota, tipo_evento, fecha, descripcion, dni_propietario, tratamiento_aplicado, proxima_cita, estado, dni_veterinario "
                   + "FROM tbHistorialMedico WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                his = new HistorialMedico();
                his.setId(rs.getInt("id"));
                his.setId_mascota(rs.getInt("id_mascota"));
                his.setTipo_evento(rs.getString("tipo_evento"));
                his.setFecha(rs.getDate("fecha").toLocalDate());
                his.setDescripcion(rs.getString("descripcion"));
                // his.setVeterinario(rs.getString("veterinario")); // QUITADO
                his.setDni_propietario(rs.getString("dni_propietario"));
                his.setTratamiento_aplicado(rs.getString("tratamiento_aplicado"));
                Timestamp proximaCitaTS = rs.getTimestamp("proxima_cita");
                if (proximaCitaTS != null) {
                    his.setProxima_cita(proximaCitaTS.toLocalDateTime());
                } else {
                    his.setProxima_cita(null);
                }
                his.setEstado(rs.getString("estado"));
                his.setDni_veterinario(rs.getString("dni_veterinario"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener historial médico: " + e.toString());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return his;        
    }
    

    @Override
    public ResultSet listarp(String dni) {
        ResultSet rs = null;
        String SQL = "SELECT h.id, h.id_mascota, h.tipo_evento, h.fecha, h.proxima_cita, h.dni_veterinario " +
                     "FROM tbHistorialMedico h " +
                     "WHERE h.dni_propietario = ? AND h.estado = 'activo' " +
                     "ORDER BY h.fecha DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setString(1, dni);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error al listar historial médico por propietario: " + e.toString());
        }
        return rs; 
    }

    @Override
    public List<String> listarVeterinariosPorPropietario(String dniPropietario) {
        
        List<String> veterinarios = new ArrayList<>();
        String SQL = "SELECT DISTINCT dni_veterinario FROM tbHistorialMedico WHERE dni_propietario = ? AND estado = 'activo'";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, dniPropietario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                veterinarios.add(rs.getString("dni_veterinario"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar veterinarios únicos: " + e.toString());
        }
        return veterinarios;        
        
        
        
        
    }

    @Override
    public List<String> listarPropietariosPorVeterinario(String dniVeterinario) {
        
        List<String> propietarios = new ArrayList<>();
       String SQL = "SELECT DISTINCT dni_propietario FROM tbHistorialMedico WHERE dni_veterinario = ? AND estado = 'activo'";
       try (Connection con = cn.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL)) {
           ps.setString(1, dniVeterinario);
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               propietarios.add(rs.getString("dni_propietario"));
           }
       } catch (SQLException e) {
           System.out.println("Error al listar propietarios únicos: " + e.toString());
       }
       return propietarios;       
        
    }


}