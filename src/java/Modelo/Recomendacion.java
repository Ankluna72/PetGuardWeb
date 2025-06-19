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
public class Recomendacion {
    
    private ObjectId id; // para MongoDB
    private int id_historial;
    private String recomendacion;
    private Date fecha; // para compatibilidad MongoDB y Compass
    private String estado;
    private String dni_veterinario;

    public Recomendacion() {
    }

    public Recomendacion(ObjectId id, int id_historial, String recomendacion, Date fecha, String estado, String dni_veterinario) {
        this.id = id;
        this.id_historial = id_historial;
        this.recomendacion = recomendacion;
        this.fecha = fecha;
        this.estado = estado;
        this.dni_veterinario = dni_veterinario;
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

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
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

    public String getDni_veterinario() {
        return dni_veterinario;
    }

    public void setDni_veterinario(String dni_veterinario) {
        this.dni_veterinario = dni_veterinario;
    }


    
    
}
