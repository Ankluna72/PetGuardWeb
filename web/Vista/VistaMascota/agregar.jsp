


<%@page import="ModeloDAO.MascotaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Mostrar alerta si se agregó o no la mascota --%>
<%
    String registro = request.getParameter("registro");
    if ("ok".equals(registro)) {
%>
        <div class="message message-success">
            ¡La mascota fue registrada exitosamente!
        </div>
<%
    } else if ("fail".equals(registro)) {
%>
        <div class="message message-error">
            Hubo un error al registrar la mascota. Intenta nuevamente.
        </div>
<%
    }
%>



<%
    String dni = (String) session.getAttribute("dni");
    if (dni == null) {
        response.sendRedirect("listar.jsp?error=3");
        return;
    }
    // Obtener la siguiente id de mascota
    ModeloDAO.MascotaDAO mascotaDAO = new ModeloDAO.MascotaDAO();
    int ultimaId = mascotaDAO.ultimaId();
    int siguienteId = (ultimaId == -1) ? 1 : ultimaId + 1;
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>AGREGAR MASCOTA</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/agregarM.css">
</head>
<body>
    <div class="container">
        <h1>REGISTRAR MASCOTA</h1>
        <form action="/PetGuardWeb/ControladorMascota" method="get">
            <div class="form-group">
                <label for="txtid">ID:</label>
                <input type="number" id="txtid" name="txtid" value="<%= siguienteId %>" readonly required>
            </div>
            <div class="form-group">
                <label for="txtnombre">NOMBRE:</label>
                <input type="text" id="txtnombre" name="txtnombre" required>
            </div>
            <div class="form-group">
                <label for="txtespecie">ESPECIE:</label>
                <input type="text" id="txtespecie" name="txtespecie" required>
            </div>
            <div class="form-group">
                <label for="txtraza">RAZA:</label>
                <input type="text" id="txtraza" name="txtraza" required>
            </div>
            <div class="form-group">
                <label for="txtedad">EDAD:</label>
                <input type="number" id="txtedad" name="txtedad" min="0" max="120" required>
            </div>
            <div class="form-group">
                <label for="txtsexo">SEXO:</label>
                <select id="txtsexo" name="txtsexo" required>
                    <option value="" disabled selected>Seleccione...</option>
                    <option value="Macho">Macho</option>
                    <option value="Hembra">Hembra</option>
                </select>
            </div>
            <div class="form-group">
                <label for="txtdnipropietario">DNI PROPIETARIO:</label>
                <input type="text" id="txtdnipropietario" name="txtdnipropietario" value="<%= dni %>" readonly>
            </div>
            <div class="form-group">
                <label for="fileFoto">Selecciona foto:</label>
                <input type="file" id="fileFoto" name="fileFoto" accept="image/*">
            </div>
            <div class="form-group">
                <label for="txturl">URL FOTO:</label>
                <input type="text" id="txturl" name="txturl" placeholder="https://..." readonly>
            </div>
            <input type="submit" class="btn-submit" name="accion" value="Agregar">
        </form>
        <a href="listar.jsp" class="btn-return">Regresar a lista</a>
    </div>
            
            
            
    <script>
    document.getElementById('fileFoto').addEventListener('change', function(){
        var fileInput = this;
        if(fileInput.files.length === 0) return;
        var formData = new FormData();
        formData.append("fileFoto", fileInput.files[0]);

        // Envía la imagen al servlet que hará la subida a imgbb
        fetch('/PetGuardWeb/SubirImagenServlet', {
            method: "POST",
            body: formData
        }).then(response => response.json())
          .then(data => {
              if(data.url){
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