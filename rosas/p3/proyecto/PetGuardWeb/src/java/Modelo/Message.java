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
public class Message {
    
    private ObjectId id;
    private ObjectId chat_id;
    private String sender_dni;
    private String sender_tipo;
    private String mensaje;
    private String timestamp;

    public Message() {
    }

    public Message(ObjectId id, ObjectId chat_id, String sender_dni, String sender_tipo, String mensaje, String timestamp) {
        this.id = id;
        this.chat_id = chat_id;
        this.sender_dni = sender_dni;
        this.sender_tipo = sender_tipo;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getChat_id() {
        return chat_id;
    }

    public void setChat_id(ObjectId chat_id) {
        this.chat_id = chat_id;
    }

    public String getSender_dni() {
        return sender_dni;
    }

    public void setSender_dni(String sender_dni) {
        this.sender_dni = sender_dni;
    }

    public String getSender_tipo() {
        return sender_tipo;
    }

    public void setSender_tipo(String sender_tipo) {
        this.sender_tipo = sender_tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
