<%@ page import="ModeloDAO.PropietarioDAO" %>
<%@ page import="ModeloDAO.VeterinarioDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String dni = (String) session.getAttribute("dni_temp");
    String clave = (String) session.getAttribute("clave_temp");
    
    if(dni == null || clave == null) {
        response.sendRedirect("index.jsp?error=3");
        return;
    }

    session.removeAttribute("dni_temp");
    session.removeAttribute("clave_temp");

    PropietarioDAO daoProp = new PropietarioDAO();
    ModeloDAO.VeterinarioDAO daoVet = new ModeloDAO.VeterinarioDAO();

    boolean esPropietario = daoProp.validarLogin(dni, clave);
    boolean esVeterinario = daoVet.validarLogin(dni, clave);

    if(esPropietario) {
        session.setAttribute("dni", dni);
        session.setAttribute("rol", "propietario");
        response.sendRedirect("principal.jsp");
    } else if(esVeterinario) {
        session.setAttribute("dni", dni);
        session.setAttribute("rol", "veterinario");
        response.sendRedirect("principal.jsp");
    } else {
        response.sendRedirect("index.jsp?error=1");
    }
%>