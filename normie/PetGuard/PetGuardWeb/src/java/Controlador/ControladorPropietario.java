/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Propietario;
import ModeloDAO.PropietarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jefferson
 */
@WebServlet(name = "ControladorPropietario", urlPatterns = {"/ControladorPropietario"})
public class ControladorPropietario extends HttpServlet {
    
    String listar = "Vista/VistaPropietario/listar.jsp";
    String add = "Vista/VistaPropietario/agregar.jsp";
    String edit = "Vista/VistaPropietario/editar.jsp";
    Propietario pro = new Propietario();
    PropietarioDAO dao = new PropietarioDAO();    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorPropietario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorPropietario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        
        String acceso="";
        String action = request.getParameter("accion");
        if(action.equalsIgnoreCase("listar"))
        {
            acceso = listar;
        }
        else if (action.equalsIgnoreCase("add")) {
        acceso = add;
        } else if (action.equalsIgnoreCase("Agregar")) {
            
            String vdni = request.getParameter("txtdni");
            String vnombre = request.getParameter("txtnombre");
            String vapellido = request.getParameter("txtapellido"); // Nuevo parámetro
            String vtelefono = request.getParameter("txtcelular"); // Cambiado de vcelular a vtelefono
            String vdireccion = request.getParameter("txtdireccion");
            String vcorreo = request.getParameter("txtcorreo"); // Nuevo parámetro
            String vcontra = request.getParameter("txtcontra"); // Nuevo parámetro
            
            pro.setDni(vdni);
            pro.setNombre(vnombre);
            pro.setApellido(vapellido);
            pro.setTelefono(vtelefono);
            pro.setDireccion(vdireccion);
            pro.setCorreo(vcorreo);
            pro.setContra(vcontra);
            
            
                // En lugar de forward directo, redirigimos a listar.jsp con parámetro “accion=Agregar”
            boolean exito = dao.add(pro);
            if (exito) {
                response.sendRedirect("Vista/VistaPropietario/agregar.jsp?registro=ok");
            } else {
                response.sendRedirect("Vista/VistaPropietario/agregar.jsp?registro=fail");
            }
            return;
        }
        
        else if (action.equalsIgnoreCase("editar")) {
            request.setAttribute("dniEditar", request.getParameter("dni"));
            acceso = edit;
        }
            else if (action.equalsIgnoreCase("Actualizar")) {

            String vdni = request.getParameter("txtdni");
            String vnombre = request.getParameter("txtnombre");
            String vapellido = request.getParameter("txtapellido"); // Nuevo parámetro
            String vtelefono = request.getParameter("txtcelular"); // Cambiado de vcelular a vtelefono
            String vdireccion = request.getParameter("txtdireccion");
            String vcorreo = request.getParameter("txtcorreo"); // Nuevo parámetro
            String vcontra = request.getParameter("txtcontra"); // Nuevo parámetro
            String vfoto = request.getParameter("txtfoto");
            
            pro.setDni(vdni);
            pro.setNombre(vnombre);
            pro.setApellido(vapellido);
            pro.setTelefono(vtelefono);
            pro.setDireccion(vdireccion);
            pro.setCorreo(vcorreo);
            pro.setContra(vcontra);
            pro.setFoto(vfoto);
            
            
            
            boolean ok = dao.edit(pro);
            if (ok) {
                response.sendRedirect(request.getContextPath()
                    + "/Vista/VistaPropietario/editar.jsp?dni=" + vdni + "&msg=actualizado");
            } else {
                response.sendRedirect(request.getContextPath()
                    + "/Vista/VistaPropietario/editar.jsp?dni=" + vdni + "&msg=error");
            }
            return;

        }
       
        
        else if (action.equalsIgnoreCase("eliminar")) {
            
            String vdni = request.getParameter("dni");
            boolean ok = dao.eliminar(vdni);
            if (ok) {
                // Cierra la sesión del usuario antes de redirigir
                request.getSession().invalidate();
                // Redirige al index con mensaje de éxito
                response.sendRedirect(request.getContextPath() + "/index.jsp?msg=eliminado");
            } else {
                // Si hubo error, vuelve a editar.jsp con mensaje de error y el dni
                response.sendRedirect(request.getContextPath()
                    + "/Vista/VistaPropietario/editar.jsp?dni=" + vdni + "&msg=error");
            }
            return;

        }
        
        
        
        RequestDispatcher vista =request.getRequestDispatcher(acceso);
        
        
        vista.forward(request, response);        
        
        
        
        
        
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    doGet(request, response);
}
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
