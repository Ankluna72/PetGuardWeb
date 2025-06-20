<%-- 
    Document   : principalv
    Created on : 14 jun. 2025, 16:23:38
    Author     : Jefferson
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <%String rol = (String) session.getAttribute("rol");%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel Principal</title>
    <link rel="stylesheet" href="css/principal.css">
</head>
<body>
    <main>
        <div class="container">
            <h1>Panel Principal PetGuard</h1>
            <div class="grid">
                

                <% if ("propietario".equals(rol)) { %>
                    <a href="Vista/VistaMascota/listar.jsp?accion=editar" class="module">
                        <img src="img/mascota.png" alt="Mascota">
                        <span>Mascotas</span>
                    </a>
                <% } %>
                
                <a href="<%= "propietario".equals(rol) ? "Vista/VistaPropietario/editar.jsp?accion=listar" : "Vista/VistaVeterinario/editar.jsp?accion=listar" %>" class="module">
                    <img src="img/usuario.png" alt="Perfil">
                    <span>Perfil</span>
                </a>
                
                
                <a href="<%= "propietario".equals(rol) ? "Vista/VistaHistorialMedico/listarp.jsp?accion=listar" : "Vista/VistaHistorialMedico/listar.jsp?accion=listar" %>" class="module">
                    <img src="img/historial.png" alt="Historial">
                    <span>Historial Medico</span>
                </a>
                
                <a href="Vista/VistaPetGuardBot/PetGuardBot.jsp" class="module">
                    <img src="img/petguardbot.png" alt="PetGuardBot">
                    <span>PetGuard Bot</span>
                </a>
                    
                <a href="<%= "propietario".equals(rol) ? "Vista/VistaChat/iniciop.jsp" : "Vista/VistaChat/iniciov.jsp" %>" class="module">
                    <img src="img/chat.png" alt="Chat">
                    <span>Chat</span>
                </a>

            </div>
        </div>
    </main>

    <!-- Botón flotante para cerrar sesión en la parte inferior izquierda -->
    <button class="logout-btn" onclick="window.location.href='index.jsp'">
        <img src="img/logout.png" class="logout-logo" alt="Cerrar Sesión">
        Cerrar Sesión
    </button>

    <footer>
        <p>2025 Jefferson Rosas. Todos los derechos reservados.</p>
    </footer>
</body>
</html>