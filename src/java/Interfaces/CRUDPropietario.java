/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Propietario;

/**
 *
 * @author Jefferson
 */
public interface CRUDPropietario {
    
    public boolean validarLogin(String dni, String clave);
    public boolean edit (Propietario pro); 
    public boolean add (Propietario pro); 
    public boolean eliminar (String dni);
    public Propietario obtenerPorDni (String dni);

    
    
    
}
