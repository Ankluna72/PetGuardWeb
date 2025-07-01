/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 *
 * @author Jefferson
 */
public class clsChatBot {
    
    
    // Tu API Key de Gemini
    private static final String API_KEY = "AIzaSyCP8O7gVBEBiijb-vj6MTDYuVbwTku4IMM"; // Reemplaza con tu clave

    // URL de la API de Gemini
    private static final String API_URL = "https://gemini.googleapis.com/v1/chat/messages"; // Cambiar según la URL de la API de Gemini

    public static void main(String[] args) {
        try {
            // Crear el cuerpo de la solicitud en formato JSON
            String jsonRequest = "{"
                + "\"model\": \"gemini-1.5-turbo\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"¿Quién es el presidente de Estados Unidos?\"}]"
                + "}";

            // Crear una solicitud HTTP POST con HttpClient
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY) // Autenticación con la clave de API
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest, StandardCharsets.UTF_8)) // Cuerpo JSON
                    .build();

            // Crear el cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Enviar la solicitud y recibir la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta de la IA
            if (response.statusCode() == 200) {
                // Imprimir la respuesta del servidor
                System.out.println("Respuesta de Gemini: " + response.body());
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode() + " - " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        }
    }
    
    
    
}
