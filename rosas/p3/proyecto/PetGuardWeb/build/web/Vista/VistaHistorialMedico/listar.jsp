<%@page import="ModeloDAO.HistorialMedicoDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String accion = request.getParameter("accion");
    if ("Eliminar".equals(accion)) {
%>
    <script>
        alert("✅ Registro eliminado correctamente.");
    </script>
<%
    } else if ("Error".equals(accion)) {
%>
    <script>
        alert("❌ Error al eliminar el registro.");
    </script>
<%
    }
%>

<%
    String dni = (String) session.getAttribute("dni");
    if (dni == null) {
        response.sendRedirect("../../index.jsp?error=3");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Historial Médico</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/listarM.css">
</head>
<body>
    <div class="titulo-mascotas">
        <img class="icono-mascota" src="/PetGuardWeb/img/misMascotasXL.png" alt="icon" width="70" style="vertical-align:middle; margin-right:4px;">
        <span>HISTORIAL MÉDICO</span>
        <a class="btn-add-mascota" href="agregar.jsp?accion=add">
            <img src="/PetGuardWeb/img/LogoMedico.png" alt="Agregar" class="icono-boton">
            Añadir Registro Médico
        </a>
    </div>
    <div class="contenedor-tabla">
        <table class="tabla-mascotas">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ID Mascota</th>
                    <th>Tipo de Evento</th>
                    <th>Fecha</th>
                    <th>Propietario</th>
                    <th>Próxima Cita</th>
                    <th>VER</th>
                    <th>EDITAR</th>
                    <th>ELIMINAR</th>
                </tr>
            </thead>
            <tbody>
            <%
                HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();
                ResultSet rs = historialDAO.listar(dni); // <-- usa el método actualizado
                try {
                    boolean hayHistorial = false;
                    while (rs != null && rs.next()) {
                        hayHistorial = true;
            %>
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getInt("id_mascota") %></td>
                    <td><%= rs.getString("tipo_evento") %></td>
                    <td><%= rs.getDate("fecha") %></td>
                    <td><%= rs.getString("nombre_propietario") %></td>
                    <td>
                        <%= (rs.getTimestamp("proxima_cita") != null) ? rs.getTimestamp("proxima_cita").toString() : "" %>
                    </td>
                    <td>
                        <a class="btn-accion ver" title="Ver" href="ver.jsp?id=<%= rs.getInt("id") %>">
                            <img src="/PetGuardWeb/img/ver.png" alt="Ver" />
                        </a>
                    </td>
                    <td>
                        <a class="btn-accion editar" title="Editar" href="editar.jsp?id=<%= rs.getInt("id") %>">
                            <img src="/PetGuardWeb/img/editar.png" alt="Editar" />
                        </a>
                    </td>
                    <td>
                        <a class="btn-accion eliminar" title="Eliminar"
                           href="<%= request.getContextPath() %>/ControladorHistorialMedico?accion=eliminar&id=<%= rs.getInt("id") %>"
                           onclick="return confirm('¿Estás seguro de que deseas eliminar este registro médico?');">
                            <img src="/PetGuardWeb/img/borrar.png" alt="Eliminar" />
                        </a>
                    </td>
                </tr>
            <%
                    }
                    if (rs != null) rs.close();
                    if (!hayHistorial) {
            %>
                <tr>
                    <td colspan="9" class="no-data">No tienes registros médicos registrados.</td>
                </tr>
            <%
                    }
                } catch (SQLException e) {
            %>
                <tr>
                    <td colspan="9" class="error">Error al listar historial médico: <%= e.getMessage() %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <br>
        <a href="../../principal.jsp" class="btn-return">Volver al Panel Principal</a>
    </div>
</body>
</html>