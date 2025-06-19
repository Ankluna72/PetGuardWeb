package ModeloDAO;

import Config.clsConexionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import Modelo.Observacion;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObservacionDAO {

    private final MongoCollection<Document> collection;

    public ObservacionDAO() {
        MongoDatabase db = new clsConexionMongoDB().getConnection();
        this.collection = db.getCollection("observaciones"); // cambia el nombre si tu colección es diferente
    }

    // Insertar una observación
    public void insertObservacion(Observacion obs) {
        Document doc = new Document()
                .append("id_historial", obs.getId_historial())
                .append("dni_propietario", obs.getDni_propietario())
                .append("dni_veterinario", obs.getDni_veterinario())
                .append("observacion", obs.getObservacion())
                .append("fecha", obs.getFecha())
                .append("estado", obs.getEstado());
        collection.insertOne(doc);
    }

    // Listar observaciones por id_historial
    public List<Observacion> getObservacionesByHistorial(int id_historial) {
        List<Observacion> lista = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("id_historial", id_historial)).sort(new Document("fecha", -1))) {
            Observacion o = new Observacion();
            o.setId(doc.getObjectId("_id"));
            o.setId_historial(doc.getInteger("id_historial"));
            o.setDni_propietario(doc.getString("dni_propietario"));
            o.setDni_veterinario(doc.getString("dni_veterinario"));
            o.setObservacion(doc.getString("observacion"));
            o.setFecha(doc.getDate("fecha"));
            o.setEstado(doc.getString("estado"));
            lista.add(o);
        }
        return lista;
    }

    // Actualizar una observación por _id
    public boolean updateObservacion(Observacion obs) {
        Document update = new Document("$set", new Document()
                .append("observacion", obs.getObservacion())
                .append("fecha", obs.getFecha())
                .append("estado", obs.getEstado())
                .append("dni_propietario", obs.getDni_propietario())
                .append("dni_veterinario", obs.getDni_veterinario())
        );
        return collection.updateOne(Filters.eq("_id", obs.getId()), update).getModifiedCount() > 0;
    }

    // Eliminación lógica (cambia estado a 'inactivo')
    public boolean deleteObservacion(ObjectId id) {
        Document update = new Document("$set", new Document("estado", "inactivo"));
        return collection.updateOne(Filters.eq("_id", id), update).getModifiedCount() > 0;
    }
}