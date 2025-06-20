/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

/**
 *
 * @author Jefferson
 */
import Config.clsConexionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import Modelo.Message;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private final MongoCollection<Document> collection;

    public MessageDAO() {
        MongoDatabase db = new clsConexionMongoDB().getConnection();
        this.collection = db.getCollection("messages");
    }

    // Insertar un mensaje
    public void insertMessage(Message msg) {
        Document doc = new Document()
                .append("chat_id", msg.getChat_id())
                .append("sender_dni", msg.getSender_dni())
                .append("sender_tipo", msg.getSender_tipo())
                .append("mensaje", msg.getMensaje())
                .append("timestamp", msg.getTimestamp());
        System.out.println("Insertando mensaje: " + doc.toJson());
        collection.insertOne(doc);
    }

    // Listar mensajes de un chat
    public List<Message> getMessagesByChatId(ObjectId chatId) {
        List<Message> messages = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("chat_id", chatId))) {
            Message msg = new Message();
            msg.setId(doc.getObjectId("_id"));
            msg.setChat_id(doc.getObjectId("chat_id"));
            msg.setSender_dni(doc.getString("sender_dni"));
            msg.setSender_tipo(doc.getString("sender_tipo"));
            msg.setMensaje(doc.getString("mensaje"));
            msg.setTimestamp(doc.getString("timestamp"));
            messages.add(msg);
        }
        return messages;
    }

    // Puedes agregar más métodos según necesidad (buscar por sender, eliminar mensaje, etc.)
}