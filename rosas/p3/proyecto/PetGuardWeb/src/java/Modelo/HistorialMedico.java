/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Jefferson
 */
public class HistorialMedico {
    
    private int id;
    private int id_mascota;
    private String tipo_evento;
    private LocalDate fecha;
    private String descripcion;
    private String dni_propietario;
    private String estado;
    private String tratamiento_aplicado;
    private LocalDateTime proxima_cita;
    private String dni_veterinario;

    public HistorialMedico() {
    }

    public HistorialMedico(int id, int id_mascota, String tipo_evento, LocalDate fecha, String descripcion, String dni_propietario, String estado, String tratamiento_aplicado, LocalDateTime proxima_cita, String dni_veterinario) {
        this.id = id;
        this.id_mascota = id_mascota;
        this.tipo_evento = tipo_evento;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.dni_propietario = dni_propietario;
        this.estado = estado;
        this.tratamiento_aplicado = tratamiento_aplicado;
        this.proxima_cita = proxima_cita;
        this.dni_veterinario = dni_veterinario;
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

    public String getDni_propietario() {
        return dni_propietario;
    }

    public void setDni_propietario(String dni_propietario) {
        this.dni_propietario = dni_propietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTratamiento_aplicado() {
        return tratamiento_aplicado;
    }

    public void setTratamiento_aplicado(String tratamiento_aplicado) {
        this.tratamiento_aplicado = tratamiento_aplicado;
    }

    public LocalDateTime getProxima_cita() {
        return proxima_cita;
    }

    public void setProxima_cita(LocalDateTime proxima_cita) {
        this.proxima_cita = proxima_cita;
    }

    public String getDni_veterinario() {
        return dni_veterinario;
    }

    public void setDni_veterinario(String dni_veterinario) {
        this.dni_veterinario = dni_veterinario;
    }
    
    


    
    
}
