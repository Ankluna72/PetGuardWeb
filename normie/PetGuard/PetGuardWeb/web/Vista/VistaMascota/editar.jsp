<%-- 
    Document   : editar
    Created on : 27 may. 2025, 16:30:08
    Author     : Jefferson Rosas
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ModeloDAO.MascotaDAO"%>
<%@page import="Modelo.Mascota"%>


<%
    String actualizado = request.getParameter("actualizado");
    if ("ok".equals(actualizado)) {
%>
    <script>
        alert("✅ ¡La mascota fue actualizada exitosamente!");
    </script>
<%
    } else if ("fail".equals(actualizado)) {
%>
    <script>
        alert("❌ Hubo un error al actualizar la mascota. Intenta nuevamente.");
    </script>
<%
    }
%>

<%
    String dni = (String) session.getAttribute("dni");
    if (dni == null) {
        response.sendRedirect("listar.jsp?error=3");
        return;
    }

    int id = Integer.parseInt(request.getParameter("id"));
    MascotaDAO mascotaDAO = new MascotaDAO();
    Mascota mascota = mascotaDAO.obtenerPorId(id);

    if (mascota == null) {
%>
        <div class="message message-error">
            No se encontró la mascota seleccionada.
        </div>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EDITAR MASCOTA</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/agregarM.css">
</head>
<body>
    <div class="container">
        <h1>EDITAR MASCOTA</h1>
        <form action="/PetGuardWeb/ControladorMascota" method="post">
            <div class="form-group">
                <label for="txtid">ID:</label>
                <input type="number" id="txtid" name="txtid" value="<%= mascota.getId() %>" readonly required>
            </div>
            <div class="form-group">
                <label for="txtnombre">NOMBRE:</label>
                <input type="text" id="txtnombre" name="txtnombre" value="<%= mascota.getNombre() %>" required>
            </div>
            <div class="form-group">
                <label for="txtespecie">ESPECIE:</label>
                <input type="text" id="txtespecie" name="txtespecie" value="<%= mascota.getEspecie() %>" required>
            </div>
            <div class="form-group">
                <label for="txtraza">RAZA:</label>
                <input type="text" id="txtraza" name="txtraza" value="<%= mascota.getRaza() %>" required>
            </div>
            <div class="form-group">
                <label for="txtedad">EDAD:</label>
                <input type="number" id="txtedad" name="txtedad" min="0" max="120" value="<%= mascota.getEdad() %>" required>
            </div>
            <div class="form-group">
                <label for="txtsexo">SEXO:</label>
                <select id="txtsexo" name="txtsexo" required>
                    <option value="Macho" <%= "Macho".equals(mascota.getSexo()) ? "selected" : "" %>>Macho</option>
                    <option value="Hembra" <%= "Hembra".equals(mascota.getSexo()) ? "selected" : "" %>>Hembra</option>
                </select>
            </div>
            <div class="form-group">
                <label for="txtdnipropietario">DNI PROPIETARIO:</label>
                <input type="text" id="txtdnipropietario" name="txtdnipropietario" value="<%= mascota.getDniPropietario() %>" readonly>
            </div>
            <div class="form-group">
                <label>FOTO ACTUAL:</label>
                <br>
                <img id="imgFoto" src="<%= mascota.getUrl() %>" alt="Foto Mascota" style="max-width: 200px; max-height: 200px; border:1px solid #ccc;">
            </div>
            <div class="form-group">
                <label for="fileFoto">Cambiar foto:</label>
                <input type="file" id="fileFoto" name="fileFoto" accept="image/*">
            </div>
            <div class="form-group">
                <label for="txturl">URL FOTO:</label>
                <input type="text" id="txturl" name="txturl" value="<%= mascota.getUrl() %>" readonly required>
            </div>
            <input type="submit" class="btn-submit" name="accion" value="Actualizar">
        </form>
        <a href="listar.jsp" class="btn-return">Regresar a lista</a>
    </div>
    <script>
    // Cuando seleccionan nueva imagen, se sube, se actualizan imagen y url
    document.getElementById('fileFoto').addEventListener('change', function(){
        var fileInput = this;
        if(fileInput.files.length === 0) return;
        var formData = new FormData();
        formData.append("fileFoto", fileInput.files[0]);
        fetch('/PetGuardWeb/SubirImagenServlet', {
            method: "POST",
            body: formData
        }).then(response => response.json())
          .then(data => {
              if(data.url){
                  document.getElementById('imgFoto').src = data.url;
                  document.getElementById('txturl').value = data.url;
                  alert("Imagen subida con éxito");
              }else{
                  alert("No se pudo subir la imagen");
              }
          });
    });
    </script>
</body>
</html>