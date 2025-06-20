/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.HistorialMedico;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Jefferson
 */
public interface CRUDHistorialMedico {
    
    
    public boolean add (HistorialMedico his); 
    public boolean edit (HistorialMedico his); 
    public boolean eliminar (int id);
    public ResultSet listar (String dni);
    public ResultSet listarp (String dni);
    public HistorialMedico obtenerHistorial (int id);
    public List<String> listarVeterinariosPorPropietario(String dniPropietario);
    public List<String> listarPropietariosPorVeterinario(String dniVeterinario);

    
}
