<%@page import="ModeloDAO.MascotaDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    String accion = request.getParameter("accion");
    if ("Eliminar".equals(accion)) {
%>
    <script>
        alert("🗑 ¡Mascota eliminada correctamente!");
    </script>
<%
    } else if ("Error".equals(accion)) {
%>
    <script>
        alert("❌ Error al eliminar la mascota. Intenta nuevamente.");
    </script>
<%
    }
%>

<%
    String dni = (String) session.getAttribute("dni");
    if (dni == null) {
        response.sendRedirect("index.jsp?error=3");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Mis Mascotas</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/listarM.css">
</head>
<body>
    <div class="titulo-mascotas">
        <img class="icono-mascota" src="/PetGuardWeb/img/misMascotasXL.png" alt="icon" width="70" style="vertical-align:middle; margin-right:4px;">
        <span>MIS MASCOTAS</span>

        <a class="btn-add-mascota" href="agregar.jsp?accion=add">
            <img src="/PetGuardWeb/img/nuevaMascota.png" alt="Agregar" class="icono-boton">
            Agregar Mascota
        </a>        
        
    </div>
    <div class="contenedor-tabla">
        <table class="tabla-mascotas">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NOMBRE</th>
                    <th>ESPECIE</th>
                    <th>RAZA</th>
                    <th>EDAD</th>
                    <th>SEXO</th>
                    <th>PROPIETARIO</th>
                    <th>EDITAR</th>
                    <th>ELIMINAR</th>
                </tr>
            </thead>
            <tbody>
            <%
                MascotaDAO mascotaDAO = new MascotaDAO();
                ResultSet rs = mascotaDAO.listar(dni);
                try {
                    boolean hayMascotas = false;
                    while (rs != null && rs.next()) {
                        hayMascotas = true;
            %>
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getString("nombre") %></td>
                    <td><%= rs.getString("especie") %></td>
                    <td><%= rs.getString("raza") %></td>
                    <td><%= rs.getInt("edad") %></td>
                    <td><%= rs.getString("sexo") %></td>
                    <td><%= rs.getString("dni_propietario") %></td>
                    <td>
                        <a class="btn-accion editar" title="Editar" href="editar.jsp?id=<%= rs.getInt("id") %>">
                            <img src="/PetGuardWeb/img/editar.png" alt="Editar" />
                        </a>
                    </td>
                <td>
                <a class="btn-accion eliminar" title="Eliminar"
                   href="<%= request.getContextPath() %>/ControladorMascota?accion=eliminar&id=<%= rs.getInt("id") %>"
                   onclick="return confirm('¿Estás seguro de que deseas eliminar esta mascota?');">
                    <img src="/PetGuardWeb/img/borrar.png" alt="Eliminar" />
                </a>
                </td>
                </tr>
            <%
                    }
                    if (rs != null) rs.close();
                    if (!hayMascotas) {
            %>
                <tr>
                    <td colspan="9" class="no-data">No tienes mascotas registradas.</td>
                </tr>
            <%
                    }
                } catch (SQLException e) {
            %>
                <tr>
                    <td colspan="9" class="error">Error al listar mascotas: <%= e.getMessage() %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
            
            <p><p>
                <p><p>
            
                    <a href="../../principal.jsp" class="btn-return">Volver al Panel Principal</a>    
    </div>
            

            
</body>
</html>