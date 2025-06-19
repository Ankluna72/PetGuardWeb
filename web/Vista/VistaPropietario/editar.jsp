<%@page import="Modelo.Propietario"%>
<%@page import="ModeloDAO.PropietarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String msg = request.getParameter("msg");
    if ("actualizado".equals(msg)) {
%>
    <script>alert("✅ ¡Tus datos fueron actualizados!");</script>
<%
    } else if ("error".equals(msg)) {
%>
    <script>alert("❌ Error al actualizar los datos. Intenta de nuevo.");</script>
<%
    } else if ("eliminar_error".equals(msg)) {
%>
    <script>alert("❌ Error al eliminar la cuenta. Intenta de nuevo.");</script>
<%
    }

    String dni = (String) session.getAttribute("dni");
    if (dni == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp?error=3");
        return;
    }

    PropietarioDAO propietarioDAO = new PropietarioDAO();
    Propietario propietario = propietarioDAO.obtenerPorDni(dni);
    if (propietario == null) {
%>
        <div style="color: red; text-align: center; margin-top: 30px;">
            No se encontró la información del propietario.
        </div>
        <%
        return;
    }
    String fotoActual = (propietario.getFoto() != null && !propietario.getFoto().trim().isEmpty())
        ? propietario.getFoto()
        : "/PetGuardWeb/img/userXL.png";
    String urlFotoTexto = (propietario.getFoto() != null) ? propietario.getFoto() : "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Propietario</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/editarP.css">
</head>
<body>
    <div class="contenedor-editar">
        <h2>Editar Mis Datos</h2>
        <form action="<%=request.getContextPath()%>/ControladorPropietario" method="post" class="form-editar" autocomplete="off">
            <input type="hidden" name="accion" value="Actualizar">

            <div class="form-group">
                <label>DNI:</label>
                <input type="text" name="txtdni" value="<%=propietario.getDni()%>" readonly>
            </div>
            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" name="txtnombre" value="<%=propietario.getNombre()%>" required>
            </div>
            <div class="form-group">
                <label>Apellido:</label>
                <input type="text" name="txtapellido" value="<%=propietario.getApellido()%>" required>
            </div>
            <div class="form-group">
                <label>Celular:</label>
                <input type="text" name="txtcelular" value="<%=propietario.getTelefono()%>" required>
            </div>
            <div class="form-group">
                <label>Dirección:</label>
                <input type="text" name="txtdireccion" value="<%=propietario.getDireccion()%>" required>
            </div>
            <div class="form-group">
                <label>Correo:</label>
                <input type="email" name="txtcorreo" value="<%=propietario.getCorreo()%>" required>
            </div>
            <div class="form-group">
                <label>Contraseña:</label>
                <input type="password" name="txtcontra" value="<%=propietario.getContra()%>" required>
            </div>
            
            <!-- SECCIÓN FOTO AL FINAL -->
            <div class="form-group form-group-foto">
                <label for="fileFoto">Foto de perfil:</label>
                <div class="foto-actions">
                    <img id="previewFoto" class="img-foto-perfil"
                         src="<%= fotoActual %>"
                         alt="Foto de perfil"
                         style="width:120px;height:120px;object-fit:cover;object-position:center;border-radius:18px;border:2px solid #d0e0f0;background:#f7fbff;margin-bottom:10px;">
                    <input type="file" id="fileFoto" accept="image/*" style="width:180px;">
                    <button type="button" id="btnBorrarFoto">Borrar foto</button>
                </div>
                <input type="text" id="txtfoto" name="txtfoto"
                       value="<%= urlFotoTexto %>"
                       readonly placeholder="URL de imagen de perfil">
            </div>
            <div class="form-group">
                <input type="submit" class="btn-submit" name="accion" value="Actualizar">
            </div>
        </form>
        <!-- Botón para eliminar cuenta, fuera del formulario -->
        <form action="<%=request.getContextPath()%>/ControladorPropietario" method="post" style="margin-top:30px;text-align:center;">
            <input type="hidden" name="accion" value="eliminar">
            <input type="hidden" name="dni" value="<%=propietario.getDni()%>">
            <button type="submit" class="btn-eliminar" onclick="return confirm('¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.');">
                Eliminar Cuenta
            </button>
        </form>
        <form method="post" style="margin-top:30px;text-align:left;">
            <a href="../../principal.jsp" class="btn-return">Volver al Panel Principal</a>
        </form>
    </div>
    <script>
    // Subida AJAX de imagen a tu API igual que en veterinario
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
                  document.getElementById('txtfoto').value = data.url;
                  document.getElementById('previewFoto').src = data.url;
                  alert("Imagen subida con éxito");
              }else{
                  alert("No se pudo subir la imagen");
              }
          });
    });

    // Borrar imagen
    document.getElementById('btnBorrarFoto').addEventListener('click', function(e){
        e.preventDefault();
        document.getElementById('txtfoto').value = "";
        document.getElementById('previewFoto').src = "/PetGuardWeb/img/userXL.png";
        document.getElementById('fileFoto').value = "";
    });
    </script>
</body>
</html>