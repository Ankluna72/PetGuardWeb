/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Config.clsImagesAPI;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;

@MultipartConfig
@WebServlet(name = "SubirImagenServlet", urlPatterns = {"/SubirImagenServlet"})
public class SubirImagenServlet extends HttpServlet {

    private static final String API_KEY = "cb04c1b05bcaf77e13430d83eaa6230e"; // tu API key

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recibe el archivo del input
        Part filePart = request.getPart("fileFoto");
        String fileName = filePart.getSubmittedFileName();
        File tempFile = File.createTempFile("img_", "_" + fileName);

        // Guarda el archivo temporalmente
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Sube la imagen a imgbb usando tu clase
        clsImagesAPI imagesAPI = new clsImagesAPI();
        String imageUrl = imagesAPI.uploadImage(tempFile.getAbsolutePath(), API_KEY);

        // Borra el archivo temporal
        tempFile.delete();

        // Devuelve la respuesta en JSON
        response.setContentType("application/json");
        JSONObject json = new JSONObject();
        if (imageUrl != null) {
            json.put("url", imageUrl);
        } else {
            json.put("url", "");
        }
        response.getWriter().write(json.toString());
    }

    @Override
    public String getServletInfo() {
        return "Sube una imagen a imgbb y retorna la URL";
    }
}