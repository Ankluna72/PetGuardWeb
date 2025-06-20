/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Veterinario;

/**
 *
 * @author Jefferson
 */
public interface CRUDVeterinario {
    
    public boolean validarLogin(String dni, String clave);
    public boolean edit (Veterinario pro); 
    public boolean add (Veterinario pro); 
    public boolean eliminar (String dni);
    public Veterinario obtenerPorDni (String dni);
    public String obtenerFotoPorDni(String dni);
    
}
