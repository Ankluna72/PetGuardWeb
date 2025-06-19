<%@page import="ModeloDAO.PropietarioDAO"%>
<%@page import="Modelo.Propietario"%>
<%@page import="ModeloDAO.MascotaDAO"%>
<%@page import="Modelo.Mascota"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String registro = request.getParameter("registro");
    if ("ok".equals(registro)) {
%>
    <script>
        alert("✅ ¡Registro médico agregado correctamente!");
    </script>
<%
    } else if ("fail".equals(registro)) {
%>
    <script>
        alert("❌ Error al agregar el registro médico. Intente de nuevo.");
    </script>
<%
    }
%>

<%
    // Veterinario logueado
    String dniVeterinario = (String) session.getAttribute("dni");
    if (dniVeterinario == null) {
        response.sendRedirect("../../index.jsp?error=3");
        return;
    }

    // Buscador de propietario
    String dniPropietario = request.getParameter("dni_propietario_buscar");
    Propietario propietarioSeleccionado = null;
    if (dniPropietario != null && !dniPropietario.isEmpty()) {
        PropietarioDAO propietarioDAO = new PropietarioDAO();
        propietarioSeleccionado = propietarioDAO.obtenerPorDni(dniPropietario);
    }

    // Mascotas del propietario seleccionado
    List<Mascota> listaMascotas = null;
    if (propietarioSeleccionado != null) {
        MascotaDAO mascotaDAO = new MascotaDAO();
        listaMascotas = mascotaDAO.obtenerNombre(dniPropietario);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Historial Médico</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/agregarH.css">
    <script>
        function limpiarPropietario() {
            document.getElementById("txtdni_propietario").value = "";
            document.getElementById("nombre_propietario").value = "";
            document.getElementById("combo_mascotas").innerHTML = "<option value=''>Seleccione mascota</option>";
        }
    </script>
</head>
<body>
    <h2>Agregar Registro de Historial Médico</h2>
    <!-- Buscador de propietario -->
    <form method="get" action="agregar.jsp" style="margin-bottom: 18px;">
        <label for="dni_propietario_buscar">Buscar DNI de Propietario:</label>
        <input type="text" name="dni_propietario_buscar" id="dni_propietario_buscar" value="<%= (dniPropietario != null) ? dniPropietario : "" %>">
   
    <div class="btn-busqueda-row">
    <button type="submit" class="btn-buscar">Buscar</button>
    <button type="button" class="btn-limpiar" onclick="limpiarPropietario()">Limpiar</button>
    </div>
        
    </form>

    <form action="../../ControladorHistorialMedico" method="post">
        <input type="hidden" name="accion" value="Agregar">
        
        
        <label for="txtdni_veterinario">Veterinario:</label>
        <input type="text" name="txtdni_veterinario" id="txtdni_veterinario" value="<%= dniVeterinario %>" readonly>


        <br><br>

        <label for="txtdni_propietario">DNI del Propietario:</label>
        <input type="text" name="txtdni_propietario" id="txtdni_propietario" value="<%= (propietarioSeleccionado != null) ? propietarioSeleccionado.getDni() : "" %>" readonly>
        <br><br>

        <label for="nombre_propietario">Nombre del Propietario:</label>
        <input type="text" id="nombre_propietario" value="<%= (propietarioSeleccionado != null) ? propietarioSeleccionado.getNombre() + " " + propietarioSeleccionado.getApellido() : "" %>" readonly>
        <br><br>

        <label for="txtid_mascota">Mascota:</label>
        <select name="txtid_mascota" id="combo_mascotas" required <%= (listaMascotas == null) ? "disabled" : "" %>>
            <option value="">Seleccione mascota</option>
            <%
                if (listaMascotas != null) {
                    for (Mascota m : listaMascotas) {
            %>
                        <option value="<%= m.getId() %>"><%= m.getNombre() %></option>
            <%
                    }
                }
            %>
        </select>
        <br><br>

        <label for="txttipo_evento">Tipo de evento:</label>
        <input type="text" name="txttipo_evento" id="txttipo_evento" required>
        <br><br>

        <label for="txtfecha">Fecha:</label>
        <input type="date" name="txtfecha" id="txtfecha" required>
        <br><br>

        <label for="txtdescripcion">Descripción:</label>
        <textarea name="txtdescripcion" id="txtdescripcion" rows="3" required></textarea>
        <br><br>

        <label for="txttratamiento_aplicado">Tratamiento aplicado:</label>
        <input type="text" name="txttratamiento_aplicado" id="txttratamiento_aplicado">
        <br><br>

        <label for="txtproxima_cita">Próxima cita:</label>
        <input type="datetime-local" name="txtproxima_cita" id="txtproxima_cita">
        <br><br>

        <button type="submit" <%= (propietarioSeleccionado == null) ? "disabled" : "" %>>Agregar</button>
    </form>

    <a href="listar.jsp" class="btn-return">Volver</a>
</body>
</html>