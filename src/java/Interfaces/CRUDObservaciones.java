/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Observacion;
import java.sql.ResultSet;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jefferson
 */
public interface CRUDObservaciones {
    
    public boolean add (Observacion obs); 
    public boolean editar (Observacion obs); 
    public boolean eliminar (int id);
    public List<Observacion> listar(int idhistorial);
    
    
    
}
