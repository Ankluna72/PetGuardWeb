/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidad;

/**
 *
 * @author Jefferson
 */

import java.time.LocalDate;

public class clsEHistorialMedico {
    
    int id;
    int id_mascota;
    String tipo_evento;
    LocalDate fecha;
    String descripcion;
    String veterinario;
    String dni_propietario;

    public clsEHistorialMedico() {
    }

    public clsEHistorialMedico(int id, int id_mascota, String tipo_evento, LocalDate fecha, String descripcion, String veterinario, String dni_propietario) {
        this.id = id;
        this.id_mascota = id_mascota;
        this.tipo_evento = tipo_evento;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.dni_propietario = dni_propietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }

    public String getTipo_evento() {
        return tipo_evento;
    }

    public void setTipo_evento(String tipo_evento) {
        this.tipo_evento = tipo_evento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getDni_propietario() {
        return dni_propietario;
    }

    public void setDni_propietario(String dni_propietario) {
        this.dni_propietario = dni_propietario;
    }
    
    

    

    
    
    
    

   
    
    
    
}
