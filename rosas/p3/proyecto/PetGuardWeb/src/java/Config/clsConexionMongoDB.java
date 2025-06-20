/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

/**
 *
 * @author HP
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Jefferson
 */

public class clsConexionMongoDB {

    MongoClient mongoClient;
    MongoDatabase database;
    
    // Cambia "petguard" por el nombre real de tu base de datos en MongoDB Atlas si es diferente
    private final String dbName = "PetGuard";
    private final String connectionString = "mongodb+srv://dbpetguard:MbPrnbppZGRxd4Hw@cluster0.ncukzne.mongodb.net/";

    public MongoDatabase getConnection() {
        try {
            // Establece la conexión a la base de datos MongoDB Atlas en la nube
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(dbName);
            System.out.println("Conexión exitosa a MongoDB Atlas: " + dbName);
        } catch (Exception e) {
            // En caso de error, muestra el mensaje
            System.out.println("Error en la conexión MongoDB: " + e.getMessage());
        }
        return database;
    }
    
    // Método para cerrar la conexión (opcional)
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión MongoDB cerrada.");
        }
    }
}