<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, ModeloDAO.*, Modelo.*, org.bson.types.ObjectId" %>
<%
    String dni = (String) session.getAttribute("dni");
    String rol = (String) session.getAttribute("rol");
    HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();
    VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    MessageDAO messageDAO = new MessageDAO();
    ChatDAO chatDAO = new ChatDAO();

    List<String> veterinariosDni = historialDAO.listarVeterinariosPorPropietario(dni);
    List<Veterinario> veterinarios = new ArrayList<>();
    for (String dniVet : veterinariosDni) {
        Veterinario vet = veterinarioDAO.obtenerPorDni(dniVet);
        if (vet != null) veterinarios.add(vet);
    }
    String veterinarioSeleccionadoDni = request.getParameter("vet_dni");
    Veterinario veterinarioSeleccionado = null;
    if (veterinarioSeleccionadoDni != null) {
        veterinarioSeleccionado = veterinarioDAO.obtenerPorDni(veterinarioSeleccionadoDni);
    }

    // BUSCAR chat_id por propietario y veterinario si no viene explícito
    String chatIdStr = request.getParameter("chat_id");
    if ((chatIdStr == null || !org.bson.types.ObjectId.isValid(chatIdStr)) && veterinarioSeleccionado != null) {
        for (Chat chat : chatDAO.getChatsByPropietario(dni)) {
            if (chat.getVeterinario_dni().equals(veterinarioSeleccionado.getDni())) {
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
    <title>Chat con Veterinarios</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/chatP.css">
</head>
<body>
    <div class="main-chat-wrapper">
        <div class="chat-title-bar">
            <img src="/PetGuardWeb/img/burbuja.png" alt="Icono Chat" class="icono-titulo-chat">
            <h2>Chat con tus Veterinarios</h2>
        </div>
        <div class="chat-container">
            <!-- Sidebar de veterinarios -->
            <div class="chat-sidebar">
                <ul>
                <% for (Veterinario vet : veterinarios) { 
                    String fotoVet = vet.getFoto();
                    if (fotoVet == null || fotoVet.isEmpty()) {
                        fotoVet = "/PetGuardWeb/img/userXL.png";
                    }
                %>
                    <li
                        class="<%= veterinarioSeleccionado != null && vet.getDni().equals(veterinarioSeleccionado.getDni()) ? "selected" : "" %>"
                        onclick="window.location.href='iniciop.jsp?vet_dni=<%= vet.getDni() %><%= chatValido && veterinarioSeleccionado != null && vet.getDni().equals(veterinarioSeleccionado.getDni()) && chatIdStr != null ? "&chat_id=" + chatIdStr : "" %>'">
                        <img src="<%= fotoVet %>"
                             alt="Foto" class="veterinario-img" />
                        <span><%= vet.getNombre() %></span>
                    </li>
                <% } %>
                </ul>
            </div>
            <!-- Main Chat Area -->
            <div class="chat-main">
                <% if (veterinarioSeleccionado != null) { 
                    String fotoSel = veterinarioSeleccionado.getFoto();
                    if (fotoSel == null || fotoSel.isEmpty()) {
                        fotoSel = "/PetGuardWeb/img/userXL.png";
                    }
                %>
                    <div class="chat-header">
                        <img src="<%= fotoSel %>"
                             alt="Foto" />
                        <span><%= veterinarioSeleccionado.getNombre() %></span>
                        <% if (!chatValido) { %>
                        <form method="post" action="/PetGuardWeb/ControladorChat" style="display:inline;">
                            <input type="hidden" name="accion" value="iniciar"/>
                            <input type="hidden" name="veterinario_dni" value="<%= veterinarioSeleccionado.getDni() %>"/>
                            <input type="hidden" name="propietario_dni" value="<%= dni %>"/>
                            <input type="hidden" name="origen" value="iniciop.jsp"/>
                            <button type="submit" class="iniciar-btn">Iniciar Conversación</button>
                        </form>
                        <% } %>
                    </div>
                    <div class="chat-messages" id="chat-messages">
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
                    <form class="chat-input-area" method="post" id="form-mensaje" action="/PetGuardWeb/ControladorMessage">
                        <input type="hidden" name="sender_dni" value="<%= dni %>"/>
                        <input type="hidden" name="sender_tipo" value="propietario"/>
                        <input type="hidden" name="veterinario_dni" value="<%= veterinarioSeleccionado.getDni() %>"/>
                        <input type="hidden" name="propietario_dni" value="<%= dni %>"/>
                        <input type="hidden" name="origen" value="iniciop.jsp"/>
                        <input type="hidden" name="chat_id" id="chat_id" value="<%= chatIdStr %>"/>
                        <input type="text" name="mensaje" placeholder="Escribe tu mensaje..." autocomplete="off" required/>
                        <input type="submit" class="btn-add-mensaje" name="accion" value="agregar"/>
                    </form>
                    <% } %>
                <% } else { %>
                    <div class="chat-header">
                        <span>Selecciona un veterinario para iniciar el chat</span>
                    </div>
                    <div class="chat-messages"></div>
                    <div class="chat-input-area"></div>
                <% } %>
            </div>
        </div>
    </div>
            
                    <a href="/PetGuardWeb/principal.jsp" class="btn-return">Volver al Panel Principal</a>

</body>
</html>