<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, ModeloDAO.*, Modelo.*, org.bson.types.ObjectId" %>
<%
    String dni = (String) session.getAttribute("dni");
    String rol = (String) session.getAttribute("rol");
    HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();
    PropietarioDAO propietarioDAO = new PropietarioDAO();
    MessageDAO messageDAO = new MessageDAO();
    ChatDAO chatDAO = new ChatDAO();

    // Obtener propietarios relacionados a este veterinario por historiales médicos
    List<String> propietariosDni = historialDAO.listarPropietariosPorVeterinario(dni);
    List<Propietario> propietarios = new ArrayList<>();
    for (String dniProp : propietariosDni) {
        Propietario prop = propietarioDAO.obtenerPorDni(dniProp);
        if (prop != null) propietarios.add(prop);
    }
    String propietarioSeleccionadoDni = request.getParameter("prop_dni");
    Propietario propietarioSeleccionado = null;
    if (propietarioSeleccionadoDni != null) {
        propietarioSeleccionado = propietarioDAO.obtenerPorDni(propietarioSeleccionadoDni);
    }

    // Buscar chat_id por veterinario y propietario si no viene explícito
    String chatIdStr = request.getParameter("chat_id");
    if ((chatIdStr == null || !org.bson.types.ObjectId.isValid(chatIdStr)) && propietarioSeleccionado != null) {
        for (Chat chat : chatDAO.getChatsByVeterinario(dni)) {
            if (chat.getPropietario_dni().equals(propietarioSeleccionado.getDni())) {
                chatIdStr = chat.getId().toHexString();
                break;
            }
        }
    }
    boolean chatValido = (chatIdStr != null && !chatIdStr.isEmpty() && org.bson.types.ObjectId.isValid(chatIdStr));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chat con Propietarios</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/chatP.css">
</head>
<body>
    <div class="main-chat-wrapper">
        <div class="chat-title-bar">
            <img src="../../img/burbuja.png" alt="Icono Chat" class="icono-titulo-chat">
            <h2>Chat con tus Propietarios</h2>
        </div>
        <div class="chat-container">
            <!-- Sidebar de propietarios -->
            <div class="chat-sidebar">
                <ul>
                <% for (Propietario prop : propietarios) { 
                    String fotoProp = prop.getFoto();
                    if (fotoProp == null || fotoProp.isEmpty()) {
                        fotoProp = "../../img/userXL.png";
                    }
                %>
                    <li
                        class="<%= propietarioSeleccionado != null && prop.getDni().equals(propietarioSeleccionado.getDni()) ? "selected" : "" %>"
                        onclick="window.location.href='iniciov.jsp?prop_dni=<%= prop.getDni() %><%= chatValido && propietarioSeleccionado != null && prop.getDni().equals(propietarioSeleccionado.getDni()) && chatIdStr != null ? "&chat_id=" + chatIdStr : "" %>'">
                        <img src="<%= fotoProp %>"
                             alt="Foto" class="veterinario-img" />
                        <span><%= prop.getNombre() %></span>
                    </li>
                <% } %>
                </ul>
            </div>
            <!-- Main Chat Area -->
            <div class="chat-main">
                <% if (propietarioSeleccionado != null) { 
                    String fotoSel = propietarioSeleccionado.getFoto();
                    if (fotoSel == null || fotoSel.isEmpty()) {
                        fotoSel = "../../img/userXL.png";
                    }
                %>
                    <div class="chat-header">
                        <img src="<%= fotoSel %>"
                             alt="Foto" style="width:32px;height:32px;vertical-align:middle;border-radius:50%;"/>
                        <span style="vertical-align:middle;"><%= propietarioSeleccionado.getNombre() %></span>
                        <% if (!chatValido) { %>
                        <form method="post" action="/PetGuardWeb/ControladorChatV" style="display:inline;">
                            <input type="hidden" name="accion" value="iniciar"/>
                            <input type="hidden" name="veterinario_dni" value="<%= dni %>"/>
                            <input type="hidden" name="propietario_dni" value="<%= propietarioSeleccionado.getDni() %>"/>
                            <input type="hidden" name="origen" value="iniciov.jsp"/>
                            <button type="submit" class="iniciar-btn">Iniciar Conversación</button>
                        </form>
                        <% } %>
                    </div>
                    <div class="chat-messages" id="chat-messages" >
                        <% if (chatValido) { %>
                            <jsp:include page="mensajes_chat.jsp">
                                <jsp:param name="chat_id" value="<%= chatIdStr %>"/>
                                <jsp:param name="dni" value="<%= dni %>"/>
                            </jsp:include>
                        <% } else { %>
                            <div style="color:gray;text-align:center;">Debes iniciar la conversación para enviar mensajes.</div>
                        <% } %>
                    </div>
                    <% if (chatValido) { %>
                    <form class="chat-input-area" method="post" id="form-mensaje" action="/PetGuardWeb/ControladorMessageV">
                        <input type="hidden" name="sender_dni" value="<%= dni %>"/>
                        <input type="hidden" name="sender_tipo" value="veterinario"/>
                        <input type="hidden" name="veterinario_dni" value="<%= dni %>"/>
                        <input type="hidden" name="propietario_dni" value="<%= propietarioSeleccionado.getDni() %>"/>
                        <input type="hidden" name="origen" value="iniciov.jsp"/>
                        <input type="hidden" name="chat_id" id="chat_id" value="<%= chatIdStr %>"/>
                        <input type="text" name="mensaje" placeholder="Escribe tu mensaje..." autocomplete="off" required/>
                        <input type="submit" class="btn-submit" name="accion" value="agregar"/>
                    </form>
                    <% } %>
                <% } else { %>
                    <div class="chat-header">
                        <span>Selecciona un propietario para iniciar el chat</span>
                    </div>
                    <div class="chat-messages"></div>
                    <div class="chat-input-area"></div>
                <% } %>
            </div>
        </div>
    </div>
            
<a href="../../principal.jsp" class="btn-return">Volver al Panel Principal</a>

</body>
</html>