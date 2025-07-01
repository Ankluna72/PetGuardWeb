/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;


/**
 *
 * @author Jefferson
 */
public class clsImagesAPI {
    
     // URL del servicio de API de imgbb
    private static final String API_URL = "https://api.imgbb.com/1/upload";
    
        // Método para subir una imagen a imgbb
    public String uploadImage(String imagePath, String apiKey) {
        // Verificar si la API key está vacía o nula
        if (apiKey == null || apiKey.isEmpty()) {
            return null; // Devuelve null si la API key es inválida
        }

        // Ruta de la imagen
        File imageFile = new File(imagePath);

        // Si el archivo no existe, devuelve null
        if (!imageFile.exists()) {
            return null;
        }

        // Crear una entidad multipart para la petición POST
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("image", new FileBody(imageFile)); // Añadir la imagen como parte del formulario
        builder.addTextBody("key", apiKey); // Añadir el API Key en el cuerpo de la solicitud
        
        // Crear una solicitud HTTP POST
        HttpPost httpPost = new HttpPost(API_URL);
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        // Realizar la solicitud y obtener la respuesta
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            org.apache.http.HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            
            // Procesar la respuesta JSON
            JSONObject jsonResponse = new JSONObject(result);
            boolean success = jsonResponse.getBoolean("success");
            
            if (success) {
                // Obtener la URL de la imagen subida
                return jsonResponse.getJSONObject("data").getString("url");
            }
        } catch (IOException e) {
            e.printStackTrace();  // En caso de error en la conexión
        }
        
        return null;  // Devuelve null si algo sale mal
    }


    
    
}

