<%-- 
    Document   : Agregar
    Created on : 27 may 2025, 9:41:25
    Author     : Jefferson
--%>

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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>REGISTRAR PROPIETARIO</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/agregarP.css">
</head>

<body>
    <div class="container">
        <h1>REGISTRAR PROPIETARIO</h1>
        <form action="/PetGuardWeb/ControladorPropietario">
            <div class="form-group">
                <label for="txtdni">DNI:</label>
                <input type="text" id="txtdni" name="txtdni" required>
            </div>
            
            <div class="form-group">
                <label for="txtnombre">NOMBRE:</label>
                <input type="text" id="txtnombre" name="txtnombre" required>
            </div>
            
            <div class="form-group">
                <label for="txtapellido">APELLIDO:</label>
                <input type="text" id="txtapellido" name="txtapellido" required>
            </div>
            
            <div class="form-group">
                <label for="txtdireccion">DIRECCIÓN:</label>
                <input type="text" id="txtdireccion" name="txtdireccion" required>
            </div>
            
            <div class="form-group">
                <label for="txtcelular">CELULAR:</label>
                <input type="text" id="txtcelular" name="txtcelular" required>
            </div>
            
            <div class="form-group">
                <label for="txtcorreo">CORREO:</label>
                <input type="email" id="txtcorreo" name="txtcorreo" required>
            </div>
            
            <div class="form-group">
                <label for="txtcontra">CONTRASEÑA:</label>
                <input type="password" id="txtcontra" name="txtcontra" required>
            </div>
                       
            <input type="submit" class="btn-submit" name="accion" value="Agregar">
        </form>
        <a href="/PetGuardWeb/index.jsp" class="btn-return">Regresar al Login</a>
    </div>
</body>

</html>