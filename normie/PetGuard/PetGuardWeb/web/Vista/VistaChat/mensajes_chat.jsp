<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, ModeloDAO.*, Modelo.*, org.bson.types.ObjectId" %>
<%
    String chatIdStr = request.getParameter("chat_id");
    String dni = request.getParameter("dni"); // del propietario logueado
    MessageDAO messageDAO = new MessageDAO();
    ChatDAO chatDAO = new ChatDAO();
    PropietarioDAO propietarioDAO = new PropietarioDAO();
    List<Message> mensajes = null;
    Propietario propietario = null;
    String fotoProp = "../../img/userXL.png";

    if (chatIdStr != null && org.bson.types.ObjectId.isValid(chatIdStr)) {
        mensajes = messageDAO.getMessagesByChatId(new ObjectId(chatIdStr));
        // Obtener propietario relacionado al chat
        Chat chat = chatDAO.getChatById(new ObjectId(chatIdStr));
        if (chat != null) {
            propietario = propietarioDAO.obtenerPorDni(chat.getPropietario_dni());
            if (propietario != null && propietario.getFoto() != null && !propietario.getFoto().isEmpty()) {
                fotoProp = propietario.getFoto();
            }
        }
    }
%>
<% if (mensajes != null && !mensajes.isEmpty()) {
    for (Message msg : mensajes) {
        boolean propio = msg.getSender_dni().equals(dni);
%>
    <div class="chat-bubble <%= propio ? "right" : "left" %>">
        <% if (!propio) { %>
            <div class="bubble-img-container">
                <img src="<%= fotoProp %>" class="bubble-user-img" alt="Foto propietario"/>
            </div>
        <% } %>
        <div class="bubble-content">
            <span><%= msg.getMensaje() %></span>
            <span class="bubble-time"><%= msg.getTimestamp() %></span>
        </div>
    </div>
<%
    }
} else {
%>
    <div style="color:gray;text-align:center;">No hay mensajes aún.</div>
<% } %>