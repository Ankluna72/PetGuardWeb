/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Mascota;
import java.util.List;
import Config.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**

/**
 *
 * @author Jefferson
 */
public interface CRUDMascota {
    
    public ResultSet listar(String dni);
    public Mascota list (String dni);
    public Mascota obtenerPorId (int id);
    
    public boolean add (Mascota pet); 
    public boolean edit (Mascota pet); 
    public boolean eliminar (int pet);
    public int ultimaId();
    
    
    public Mascota obtenerMascota(int id);
    public List<Mascota> obtenerNombre(String dni);
    
}
