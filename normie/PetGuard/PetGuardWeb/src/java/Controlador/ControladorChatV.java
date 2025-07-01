package Controlador;

import Modelo.Chat;
import ModeloDAO.ChatDAO;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "ControladorChatV", urlPatterns = {"/ControladorChatV"})
public class ControladorChatV extends HttpServlet {

    ChatDAO chatDAO = new ChatDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("iniciar".equalsIgnoreCase(accion)) {
            String propietario_dni = request.getParameter("propietario_dni");
            String veterinario_dni = request.getParameter("veterinario_dni");
            String origen = request.getParameter("origen");
            if (origen == null || (!origen.equals("iniciop.jsp") && !origen.equals("iniciov.jsp"))) {
                origen = "iniciov.jsp";
            }

            // Buscar si ya existe un chat entre este veterinario y propietario
            ObjectId chatId = buscarChatIdVeterinario(chatDAO, propietario_dni, veterinario_dni);

            if (chatId == null) {
                // Crear nuevo chat
                String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Chat chat = new Chat();
                chat.setPropietario_dni(propietario_dni);
                chat.setVeterinario_dni(veterinario_dni);
                chat.setCreatedAt(now);
                chat.setUpdatedAt(now);
                chatDAO.insertChat(chat);

                // Volver a buscar para obtener el nuevo _id
                chatId = buscarChatIdVeterinario(chatDAO, propietario_dni, veterinario_dni);
            }

            // Redirigir al chat con el nuevo o existente chat_id
            response.sendRedirect(request.getContextPath() +
                "/Vista/VistaChat/" + origen +
                "?prop_dni=" + propietario_dni +
                "&chat_id=" + (chatId != null ? chatId.toHexString() : ""));
            return;
        }
        // Si no es acción iniciar, vuelve al chat de veterinario
        response.sendRedirect(request.getContextPath() + "/Vista/VistaChat/iniciov.jsp");
    }

    // Busca _id de chat existente entre veterinario y propietario
    private ObjectId buscarChatIdVeterinario(ChatDAO chatDAO, String propietario_dni, String veterinario_dni) {
        for (Chat chat : chatDAO.getChatsByVeterinario(veterinario_dni)) {
            if (chat.getPropietario_dni().equals(propietario_dni)) {
                return chat.getId();
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador para crear y obtener chats (Vista Veterinario)";
    }
}