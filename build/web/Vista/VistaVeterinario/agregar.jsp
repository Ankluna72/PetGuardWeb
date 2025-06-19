<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- Mostrar mensajes de éxito/error --%>
<%
    String registro = request.getParameter("registro");
    if ("ok".equals(registro)) {
%>
    <script>alert('✅ ¡Registro exitoso!');</script>
<%
    } else if ("fail".equals(registro)) {
%>
    <script>alert('❌ Error al registrar, intenta nuevamente.');</script>
<%
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>REGISTRAR VETERINARIO</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/agregarV.css">
</head>
<body>
    <div class="container">
        <h1>REGISTRAR VETERINARIO</h1>
        <form action="/PetGuardWeb/ControladorVeterinario" method="post">
            <div class="form-group">
                <label for="dni">DNI:</label>
                <input type="text" id="dni" name="txtdni" required>
            </div>
            <div class="form-group">
                <label for="nombre">NOMBRE:</label>
                <input type="text" id="nombre" name="txtnombre" required>
            </div>
            <div class="form-group">
                <label for="apellidos">APELLIDOS:</label>
                <input type="text" id="apellidos" name="txtapellidos" required>
            </div>
            <div class="form-group">
                <label for="correo">CORREO:</label>
                <input type="email" id="correo" name="txtcorreo" required>
            </div>
            <div class="form-group">
                <label for="telefono">TELÉFONO:</label>
                <input type="text" id="telefono" name="txttelefono" required>
            </div>
            <div class="form-group">
                <label for="direccion">DIRECCIÓN:</label>
                <input type="text" id="direccion" name="txtdireccion" required>
            </div>
            <div class="form-group">
                <label for="fileFoto">Selecciona foto:</label>
                <input type="file" id="fileFoto" name="fileFoto" accept="image/*">
            </div>
            <div class="form-group">
                <label for="foto">URL FOTO:</label>
                <input type="text" id="foto" name="txtfoto" placeholder="https://..." readonly required>
            </div>
            <div class="form-group">
                <label for="contra">CONTRASEÑA:</label>
                <input type="password" id="contra" name="txtcontra" required>
            </div>

            <input type="submit" class="btn-submit" name="accion" value="Agregar">
        </form>
        <a href="/PetGuardWeb/index.jsp" class="btn-return">Regresar al Login</a>
    </div>

    <script>
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
                  document.getElementById('foto').value = data.url;
                  alert("Imagen subida con éxito");
              }else{
                  alert("No se pudo subir la imagen");
              }
          });
    });
    </script>
</body>
</html>