/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;



import Config.clsConexionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import Modelo.Chat;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO {
    private final MongoCollection<Document> collection;

    public ChatDAO() {
        MongoDatabase db = new clsConexionMongoDB().getConnection();
        this.collection = db.getCollection("chats");
    }

    // Insertar un chat
    public void insertChat(Chat chat) {
        Document doc = new Document()
                .append("veterinario_dni", chat.getVeterinario_dni())
                .append("propietario_dni", chat.getPropietario_dni())
                .append("createdAt", chat.getCreatedAt())
                .append("updatedAt", chat.getUpdatedAt());
        collection.insertOne(doc);
    }

    // Buscar un chat por ID
    public Chat getChatById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) return null;
        Chat chat = new Chat();
        chat.setId(doc.getObjectId("_id"));
        chat.setVeterinario_dni(doc.getString("veterinario_dni"));
        chat.setPropietario_dni(doc.getString("propietario_dni"));
        chat.setCreatedAt(doc.getString("createdAt"));
        chat.setUpdatedAt(doc.getString("updatedAt"));
        return chat;
    }

    // Listar todos los chats de un propietario
    public List<Chat> getChatsByPropietario(String propietario_dni) {
        List<Chat> chats = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("propietario_dni", propietario_dni))) {
            Chat chat = new Chat();
            chat.setId(doc.getObjectId("_id"));
            chat.setVeterinario_dni(doc.getString("veterinario_dni"));
            chat.setPropietario_dni(doc.getString("propietario_dni"));
            chat.setCreatedAt(doc.getString("createdAt"));
            chat.setUpdatedAt(doc.getString("updatedAt"));
            chats.add(chat);
        }
        return chats;
    }
    
    // Listar todos los chats de un veterinario
    public List<Chat> getChatsByVeterinario(String veterinario_dni) {
        List<Chat> chats = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("veterinario_dni", veterinario_dni))) {
            Chat chat = new Chat();
            chat.setId(doc.getObjectId("_id"));
            chat.setVeterinario_dni(doc.getString("veterinario_dni"));
            chat.setPropietario_dni(doc.getString("propietario_dni"));
            chat.setCreatedAt(doc.getString("createdAt"));
            chat.setUpdatedAt(doc.getString("updatedAt"));
            chats.add(chat);
        }
        return chats;
    }    
    

    // Puedes agregar más métodos según necesidad (actualizar, eliminar, etc.)
}