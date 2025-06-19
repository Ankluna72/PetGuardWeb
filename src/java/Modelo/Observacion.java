/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import java.util.Date;
/**
 *
 * @author Jefferson
 */
public class Observacion {
    
    private ObjectId id;          // MongoDB _id
    private int id_historial;
    private String dni_propietario;
    private String dni_veterinario;
    private String observacion;
    private Date fecha;           // MongoDB date type
    private String estado;

    public Observacion() {
    }

    public Observacion(ObjectId id, int id_historial, String dni_propietario, String dni_veterinario, String observacion, Date fecha, String estado) {
        this.id = id;
        this.id_historial = id_historial;
        this.dni_propietario = dni_propietario;
        this.dni_veterinario = dni_veterinario;
        this.observacion = observacion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public String getDni_propietario() {
        return dni_propietario;
    }

    public void setDni_propietario(String dni_propietario) {
        this.dni_propietario = dni_propietario;
    }

    public String getDni_veterinario() {
        return dni_veterinario;
    }

    public void setDni_veterinario(String dni_veterinario) {
        this.dni_veterinario = dni_veterinario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    
    
}
