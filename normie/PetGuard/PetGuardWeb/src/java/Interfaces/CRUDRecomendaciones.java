/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Observacion;
import Modelo.Recomendacion;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Jefferson
 */
public interface CRUDRecomendaciones {
    
    
    public boolean add (Recomendacion rec); 
    public boolean eliminar (int id);
    public List<Recomendacion> listar(int idhistorial);
    public boolean editar (Recomendacion rec); 

    
}
