
<%-- 
    Document   : validarCaptcha
    Created on : 22 may. 2025, 16:14:32
    Author     : Jefferson Rosas
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Validar CAPTCHA primero
    String captchaInput = request.getParameter("captchaInput");
    String captchaGenerated = request.getParameter("captchaGenerated");
    
    if(!captchaInput.equalsIgnoreCase(captchaGenerated)) {
        response.sendRedirect("index.jsp?error=2");
        return;
    }

    // Guardar credenciales temporalmente en sesión
    session.setAttribute("dni_temp", request.getParameter("dni"));
    session.setAttribute("clave_temp", request.getParameter("clave"));
    
    // Redirigir a validarLogin.jsp
    response.sendRedirect("validarLogin.jsp");
%>