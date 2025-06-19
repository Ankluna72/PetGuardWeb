<%@page import="ModeloDAO.ObservacionDAO"%>
<%@page import="Modelo.Observacion"%>
<%@page import="ModeloDAO.RecomendacionDAO"%>
<%@page import="Modelo.Recomendacion"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="java.util.List"%>
<%@page import="ModeloDAO.HistorialMedicoDAO"%>
<%@page import="Modelo.HistorialMedico"%>
<%@page import="ModeloDAO.MascotaDAO"%>
<%@page import="Modelo.Mascota"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%
    DateTimeFormatter fechaLatam = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<%
    String dni = (String) session.getAttribute("dni");
    String rol = (String) session.getAttribute("rol");

    if (dni == null) {
        response.sendRedirect("../../index.jsp?error=3");
        return;
    }
    String idParam = request.getParameter("id");
    int idHis = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : -1;
    HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();
    HistorialMedico his = historialDAO.obtenerHistorial(idHis);
    MascotaDAO mascotaDAO = new MascotaDAO();
    Mascota mascota = mascotaDAO.obtenerPorId(his.getId_mascota());

    // Observaciones desde MongoDB
    ObservacionDAO obsDao = new ObservacionDAO();
    List<Observacion> observaciones = obsDao.getObservacionesByHistorial(idHis);

    // Recomendaciones desde MongoDB
    RecomendacionDAO recDao = new RecomendacionDAO();
    List<Recomendacion> recomendaciones = recDao.getRecomendacionesByHistorial(idHis);

    String obsmsg = request.getParameter("obsmsg");
    String obsEditId = request.getParameter("obsEditId");

    String recmsg = request.getParameter("recmsg");
    String recEditId = request.getParameter("recEditId");

    if (his == null) {
        response.sendRedirect("listar.jsp?error=notfound");
        return;
    }
%>

<%
    String actualizado = request.getParameter("actualizado");
    if ("ok".equals(actualizado)) {
%>
    <script>
        alert("✅ ¡El registro médico fue actualizado exitosamente!");
    </script>
<%
    } else if ("fail".equals(actualizado)) {
%>
    <script>
        alert("❌ Hubo un error al actualizar el registro médico. Intenta nuevamente.");
    </script>
<%
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Historial Médico</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/editarH.css">
</head>
<body>
<div class="historial-layout">
    <!-- Panel Observaciones -->
    <aside class="side-panel observaciones">
        <h3>Observaciones</h3>
        <% if (obsmsg != null) { %>
            <div class="msg"><%= obsmsg %></div>
        <% } %>
        <% if (observaciones != null && !observaciones.isEmpty()) {
            for(Observacion o : observaciones) { 
                // Determinar autor y estilos
                boolean esVeterinario = o.getDni_veterinario() != null && !o.getDni_veterinario().isEmpty();
                boolean esPropietario = o.getDni_propietario() != null && !o.getDni_propietario().isEmpty();
                String autor = esVeterinario ? "Veterinario" : "Propietario";
                String autorClase = esVeterinario ? "autor-veterinario" : "autor-propietario";
                String icono = esVeterinario ? "🩺" : "🐾";
        %>
            <div class="comentario <%= autorClase %>">
                <% if (obsEditId != null && obsEditId.equals(o.getId().toString()) && (
                        (esVeterinario && dni.equals(o.getDni_veterinario())) ||
                        (esPropietario && dni.equals(o.getDni_propietario()))
                    )) { %>
                    <!-- Formulario de edición de observación SOLO SI la hizo este usuario -->
                    <form class="add-comentario-form" action="/PetGuardWeb/ControladorObservacion" method="post">
                        <input type="hidden" name="accion" value="editar"/>
                        <input type="hidden" name="id_observacion" value="<%= o.getId().toString() %>"/>
                        <input type="hidden" name="id_historial" value="<%= idHis %>"/>
                        <input type="hidden" name="dni_propietario" value="<%= esPropietario ? dni : "" %>"/>
                        <input type="hidden" name="dni_veterinario" value="<%= esVeterinario ? dni : "" %>"/>
                        <textarea name="observacion" maxlength="250" required><%= o.getObservacion() %></textarea>
                        <button type="submit">Guardar</button>
                        <a href="editar.jsp?id=<%= idHis %>" class="btn-return" style="margin-left:8px;">Cancelar</a>
                    </form>
                <% } else { %>
                    <span class="autor <%= autorClase %>"><%= icono %> <%= autor %></span>
                    <%= o.getObservacion() %>
                <span class="fecha">
                    <%
                        if (o.getFecha() != null) {
                            java.time.LocalDateTime ldt = o.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                            out.print(ldt.format(fechaLatam));
                        }
                    %>
                </span>
                    <div class="comentario-btns">
                        <% if (
                                (esVeterinario && dni.equals(o.getDni_veterinario())) ||
                                (esPropietario && dni.equals(o.getDni_propietario()))
                            ) { %>
                            <form action="/PetGuardWeb/ControladorObservacion" method="get" style="display:inline;">
                                <input type="hidden" name="accion" value="eliminar"/>
                                <input type="hidden" name="id" value="<%= o.getId().toString() %>"/>
                                <input type="hidden" name="idHistorial" value="<%= idHis %>"/>
                                <button type="submit" class="eliminar" onclick="return confirm('¿Eliminar esta observación?')">Eliminar</button>
                            </form>
                            <form action="editar.jsp" method="get" style="display:inline;">
                                <input type="hidden" name="id" value="<%= idHis %>"/>
                                <input type="hidden" name="obsEditId" value="<%= o.getId().toString() %>"/>
                                <button type="submit" class="editar-btn" title="Editar">Editar</button>
                            </form>
                        <% } %>
                    </div>
                <% } %>
            </div>
        <%  } 
        } else { %>
            <div class="comentario">No hay observaciones.</div>
        <% } %>
        <form class="add-comentario-form" action="/PetGuardWeb/ControladorObservacion" method="post" style="margin-top:12px;">
            <input type="hidden" name="accion" value="agregar"/>
            <input type="hidden" name="id_historial" value="<%= idHis %>"/>
            <input type="hidden" name="dni_propietario" value="<%= "propietario".equals(rol) ? dni : "" %>"/>
            <input type="hidden" name="dni_veterinario" value="<%= "veterinario".equals(rol) ? dni : "" %>"/>
            <textarea name="observacion" maxlength="250" required placeholder="Agregar observación..."></textarea>
            <button type="submit">Agregar</button>
        </form>
    </aside>

    <!-- Panel Central Formulario -->
    <main class="form-panel">
        <h2>Editar Registro de Historial Médico</h2>
        <form action="/PetGuardWeb/ControladorHistorialMedico" method="post">
            <input type="hidden" name="accion" value="Actualizar">
            <input type="hidden" name="txtid" value="<%= his.getId() %>">

            <label for="txtid_mascota">ID de Mascota:</label>
            <input type="text" name="txtid_mascota" id="txtid_mascota" value="<%= his.getId_mascota() %>" readonly>
            <br><br>

            <label for="nombre_mascota">Nombre de Mascota:</label>
            <input type="text" id="nombre_mascota" value="<%= mascota != null ? mascota.getNombre() : "" %>" readonly>
            <br><br>
            
            <label for="txttipo_evento">Tipo de evento:</label>
            <input type="text" name="txttipo_evento" id="txttipo_evento" value="<%= his.getTipo_evento() %>" required >
            <br><br>

            <label for="txtfecha">Fecha:</label>
            <input type="date" name="txtfecha" id="txtfecha" value="<%= his.getFecha() %>" required >
            <br><br>

            <label for="txtdescripcion">Descripción:</label>
            <textarea name="txtdescripcion" id="txtdescripcion" rows="5" required ><%= his.getDescripcion() %></textarea>
            <br><br>

            <label for="txtdni_veterinario">Veterinario:</label>
            <input type="text" name="txtdni_veterinario" id="txtdni_veterinario" value="<%= his.getDni_veterinario() %>" readonly>
            <br><br>

            <label for="txtdni_propietario">DNI del Propietario:</label>
            <input type="text" name="txtdni_propietario" id="txtdni_propietario" value="<%= his.getDni_propietario() %>" readonly>
            <br><br>

            <label for="txttratamiento_aplicado">Tratamiento aplicado:</label>
            <input type="text" name="txttratamiento_aplicado" id="txttratamiento_aplicado" value="<%= his.getTratamiento_aplicado() != null ? his.getTratamiento_aplicado() : "" %>" >
            <br><br>

            <label for="txtproxima_cita">Próxima cita:</label>
            <input type="datetime-local" name="txtproxima_cita" id="txtproxima_cita"
                value="<%= his.getProxima_cita() != null ? his.getProxima_cita().toString().replace(' ', 'T') : "" %>" >
            <br><br>
            
            <% if(!"propietario".equals(rol)) { %>
                <button type="submit" class="btn-submit">Actualizar</button>
            <% } %>
        </form>
    </main>

    <!-- Panel de Recomendaciones -->
    <aside class="side-panel recomendaciones">
        <h3>Recomendaciones</h3>
        <% if (recmsg != null) { %>
            <div class="msg"><%= recmsg %></div>
        <% } %>
        <% if (recomendaciones != null && !recomendaciones.isEmpty()) {
            for(Recomendacion r : recomendaciones) { %>
                <div class="comentario autor-veterinario">
                    <% if (recEditId != null && recEditId.equals(r.getId().toString()) && dni.equals(his.getDni_veterinario())) { %>
                        <form class="add-comentario-form" action="/PetGuardWeb/ControladorRecomendacion" method="post">
                            <input type="hidden" name="accion" value="editar"/>
                            <input type="hidden" name="id_recomendacion" value="<%= r.getId().toString() %>"/>
                            <input type="hidden" name="id_historial" value="<%= idHis %>"/>
                            <input type="hidden" name="dni_veterinario" value="<%= his.getDni_veterinario() %>"/>
                            <textarea name="recomendacion" maxlength="250" required><%= r.getRecomendacion() %></textarea>
                            <button type="submit">Guardar</button>
                            <a href="editar.jsp?id=<%= idHis %>" class="btn-return" style="margin-left:8px;">Cancelar</a>
                        </form>
                    <% } else { %>
                        <span class="autor autor-veterinario">🩺 Veterinario</span>
                        <%= r.getRecomendacion() %>
                    <span class="fecha">
                        <%
                            if (r.getFecha() != null) {
                                java.time.LocalDateTime ldt = r.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                                out.print(ldt.format(fechaLatam));
                            }
                        %>
                    </span>
                        <div class="comentario-btns">
                            <% if (dni.equals(his.getDni_veterinario())) { %>
                                <form action="/PetGuardWeb/ControladorRecomendacion" method="get" style="display:inline;">
                                    <input type="hidden" name="accion" value="eliminar"/>
                                    <input type="hidden" name="id" value="<%= r.getId().toString() %>"/>
                                    <input type="hidden" name="idHistorial" value="<%= idHis %>"/>
                                    <button type="submit" class="eliminar" onclick="return confirm('¿Eliminar esta recomendación?')">Eliminar</button>
                                </form>
                                <form action="editar.jsp" method="get" style="display:inline;">
                                    <input type="hidden" name="id" value="<%= idHis %>"/>
                                    <input type="hidden" name="recEditId" value="<%= r.getId().toString() %>"/>
                                    <button type="submit" class="editar-btn" title="Editar">Editar</button>
                                </form>
                            <% } %>
                        </div>
                    <% } %>
                </div>
        <%  } 
        } else { %>
            <div class="comentario">No hay recomendaciones.</div>
        <% } %>
        <% if (dni.equals(his.getDni_veterinario())) { %>
        <form class="add-comentario-form" action="/PetGuardWeb/ControladorRecomendacion" method="post" style="margin-top:12px;">
            <input type="hidden" name="accion" value="agregar"/>
            <input type="hidden" name="id_historial" value="<%= idHis %>"/>
            <input type="hidden" name="dni_veterinario" value="<%= his.getDni_veterinario() %>"/>
            <textarea name="recomendacion" maxlength="250" required placeholder="Agregar recomendación..."></textarea>
            <button type="submit">Agregar</button>
        </form>
        <% } %>
    </aside>        
</div>

<a href="<%= "propietario".equals(rol) ? "listarp.jsp" : "listar.jsp" %>" class="btn-return">Volver</a>
    
</body>
</html>