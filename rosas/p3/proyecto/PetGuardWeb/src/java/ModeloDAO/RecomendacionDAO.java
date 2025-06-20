package ModeloDAO;

import Config.clsConexionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import Modelo.Recomendacion;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.conversions.Bson;

public class RecomendacionDAO {

    private final MongoCollection<Document> collection;

    public RecomendacionDAO() {
        MongoDatabase db = new clsConexionMongoDB().getConnection();
        this.collection = db.getCollection("recomendaciones"); // Cambia si tu colección se llama diferente
    }

    // Insertar una recomendación
    public void insertRecomendacion(Recomendacion rec) {
        Document doc = new Document()
                .append("id_historial", rec.getId_historial())
                .append("dni_veterinario", rec.getDni_veterinario())
                .append("recomendacion", rec.getRecomendacion())
                .append("fecha", rec.getFecha()) // Debe ser tipo java.util.Date
                .append("estado", rec.getEstado());
        collection.insertOne(doc);
    }

    // Listar recomendaciones por id_historial y estado "activo"
    public List<Recomendacion> getRecomendacionesByHistorial(int id_historial) {
        List<Recomendacion> lista = new ArrayList<>();
        // Ahora el filtro exige que el estado sea "activo"
        Bson filtro = Filters.and(
            Filters.eq("id_historial", id_historial),
            Filters.eq("estado", "activo")
        );
        for (Document doc : collection.find(filtro)) {
            Recomendacion rec = new Recomendacion();
            rec.setId(doc.getObjectId("_id"));
            rec.setId_historial(doc.getInteger("id_historial"));
            rec.setDni_veterinario(doc.getString("dni_veterinario"));
            rec.setRecomendacion(doc.getString("recomendacion"));
            rec.setFecha(doc.getDate("fecha"));
            rec.setEstado(doc.getString("estado"));
            lista.add(rec);
        }
        return lista;
    }

    // Actualizar una recomendación (por _id)
    public boolean updateRecomendacion(Recomendacion rec) {
        Document update = new Document("$set", new Document()
                .append("recomendacion", rec.getRecomendacion())
                .append("fecha", rec.getFecha())
                .append("estado", rec.getEstado())
                .append("dni_veterinario", rec.getDni_veterinario())
        );
        return collection.updateOne(Filters.eq("_id", rec.getId()), update).getModifiedCount() > 0;
    }

    // Eliminar (lógico: cambia estado) una recomendación
    public boolean deleteRecomendacion(ObjectId id) {
        Document update = new Document("$set", new Document("estado", "inactivo"));
        return collection.updateOne(Filters.eq("_id", id), update).getModifiedCount() > 0;
    }

    // Puedes agregar métodos según necesites (buscar por _id, etc.)
}