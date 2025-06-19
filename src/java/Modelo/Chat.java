/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import org.bson.types.ObjectId;

/**
 *
 * @author Jefferson
 */
public class Chat {
    
    private ObjectId id;
    private String veterinario_dni;
    private String propietario_dni;
    private String createdAt;
    private String updatedAt;

    public Chat() {
    }

    public Chat(ObjectId id, String veterinario_dni, String propietario_dni, String createdAt, String updatedAt) {
        this.id = id;
        this.veterinario_dni = veterinario_dni;
        this.propietario_dni = propietario_dni;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getVeterinario_dni() {
        return veterinario_dni;
    }

    public void setVeterinario_dni(String veterinario_dni) {
        this.veterinario_dni = veterinario_dni;
    }

    public String getPropietario_dni() {
        return propietario_dni;
    }

    public void setPropietario_dni(String propietario_dni) {
        this.propietario_dni = propietario_dni;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
    
}
