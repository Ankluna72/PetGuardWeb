/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Mascota;
import ModeloDAO.MascotaDAO;
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
@WebServlet(name = "ControladorMascota", urlPatterns = {"/ControladorMascota"})
public class ControladorMascota extends HttpServlet {
    
    String listar = "Vista/VistaMascota/listar.jsp";
    String add = "Vista/VistaMascota/agregar.jsp";
    String edit = "Vista/VistaMascota/editar.jsp";
    Mascota pet = new Mascota();
    MascotaDAO dao = new MascotaDAO();

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
            out.println("<title>Servlet ControladorMascota</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorMascota at " + request.getContextPath() + "</h1>");
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
        
            String acceso = "";
            String action = request.getParameter("accion");
            if (action.equalsIgnoreCase("listar")) {
                acceso = listar;
            } else if (action.equalsIgnoreCase("add")) {
                acceso = add;
            }        
        
        
            else if (action.equalsIgnoreCase("Agregar")) {
            
            int vid = Integer.parseInt(request.getParameter("txtid"));
            String vnombre = request.getParameter("txtnombre");
            String vespecie = request.getParameter("txtespecie");
            String vraza = request.getParameter("txtraza");
            int vedad = Integer.parseInt(request.getParameter("txtedad"));
            String vsexo = request.getParameter("txtsexo");
            String vdnipropietario = request.getParameter("txtdnipropietario");
            String vurl = request.getParameter("txturl");
            
            pet.setId(vid);
            pet.setNombre(vnombre);
            pet.setEspecie(vespecie);
            pet.setRaza(vraza);
            pet.setEdad(vedad);
            pet.setSexo(vsexo);
            pet.setDniPropietario(vdnipropietario);
            pet.setUrl(vurl);

            // En lugar de forward directo, redirigimos a listar.jsp con parámetro “accion=Agregar”
            boolean exito = dao.add(pet);
            if (exito) {
                response.sendRedirect("Vista/VistaMascota/agregar.jsp?registro=ok");
            } else {
                response.sendRedirect("Vista/VistaMascota/agregar.jsp?registro=fail");
            }
            return;
            
        }
        
        else if (action.equalsIgnoreCase("editar")) {
            request.setAttribute("idEditar", request.getParameter("id"));
            acceso = edit;
        }
            else if (action.equalsIgnoreCase("Actualizar")) {
                
                
            int vid                = Integer.parseInt(request.getParameter("txtid"));        
            String vnombre           = request.getParameter("txtnombre");
            String vespecie          = request.getParameter("txtespecie");
            String vraza             = request.getParameter("txtraza");
            int vedad                = Integer.parseInt(request.getParameter("txtedad"));
            String vsexo             = request.getParameter("txtsexo");
            String vdnipropietario   = request.getParameter("txtdnipropietario");
            String vurl              = request.getParameter("txturl");


            pet.setNombre(vnombre);
            pet.setEspecie(vespecie);
            pet.setRaza(vraza);
            pet.setEdad(vedad);
            pet.setSexo(vsexo);
            pet.setDniPropietario(vdnipropietario);
            pet.setUrl(vurl);
            pet.setId(vid);
                

            boolean ok = dao.edit(pet);
            String id = request.getParameter("txtid");
            if (ok) {
                response.sendRedirect(request.getContextPath() + "/Vista/VistaMascota/editar.jsp?id=" + id + "&actualizado=ok");
            } else {
                response.sendRedirect(request.getContextPath() + "/Vista/VistaMascota/editar.jsp?id=" + id + "&actualizado=fail");
            }
            return;
        }
       
        
        else if (action.equalsIgnoreCase("eliminar")) {
            
        int vid = Integer.parseInt(request.getParameter("id"));
        boolean ok = dao.eliminar(vid);
        if (ok) {
            // Redirigimos a listar.jsp con ?accion=Eliminar
            response.sendRedirect(request.getContextPath()
                + "/Vista/VistaMascota/listar.jsp?accion=Eliminar");
        } else {
            response.sendRedirect(request.getContextPath()
                + "/Vista/VistaMascota/listar.jsp?accion=Error");
        }
        return;

        }
        
        
        
        RequestDispatcher vista = request.getRequestDispatcher(acceso);
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
