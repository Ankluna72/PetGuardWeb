package Controlador;

import Modelo.Recomendacion;
import ModeloDAO.RecomendacionDAO;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "ControladorRecomendacion", urlPatterns = {"/ControladorRecomendacion"})
public class ControladorRecomendacion extends HttpServlet {

    String editar = "Vista/VistaHistorialMedico/editar.jsp";
    RecomendacionDAO dao = new RecomendacionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null && accion.equalsIgnoreCase("eliminar")) {
            String idStr = request.getParameter("id");
            String idHistorial = request.getParameter("idHistorial");
            boolean ok = false;
            if (idStr != null && ObjectId.isValid(idStr)) {
                ObjectId oid = new ObjectId(idStr);
                ok = dao.deleteRecomendacion(oid);
            }
            response.sendRedirect(request.getContextPath() + "/" + editar + "?id=" + idHistorial + "&recmsg=" + (ok ? "eliminado" : "error"));
            return;
        }
        // Si no hay acción, solo reenviar a editar.jsp
        RequestDispatcher rd = request.getRequestDispatcher(editar);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null && accion.equalsIgnoreCase("agregar")) {
            int idHistorial = Integer.parseInt(request.getParameter("id_historial"));
            String texto = request.getParameter("recomendacion");
            String dniVeterinario = request.getParameter("dni_veterinario");
            Date fecha = new Date();

            Recomendacion rec = new Recomendacion();
            rec.setId_historial(idHistorial);
            rec.setRecomendacion(texto);
            rec.setFecha(fecha);
            rec.setEstado("activo");
            rec.setDni_veterinario(dniVeterinario);

            dao.insertRecomendacion(rec);
            response.sendRedirect(request.getContextPath() + "/" + editar + "?id=" + idHistorial + "&recmsg=agregado");
            return;
        } else if (accion != null && accion.equalsIgnoreCase("editar")) {
            String idStr = request.getParameter("id_recomendacion");
            int idHistorial = Integer.parseInt(request.getParameter("id_historial"));
            String texto = request.getParameter("recomendacion");
            String dniVeterinario = request.getParameter("dni_veterinario");
            Date fecha = new Date();
            boolean ok = false;
            if (idStr != null && ObjectId.isValid(idStr)) {
                Recomendacion rec = new Recomendacion();
                rec.setId(new ObjectId(idStr));
                rec.setId_historial(idHistorial);
                rec.setRecomendacion(texto);
                rec.setFecha(fecha);
                rec.setEstado("activo");
                rec.setDni_veterinario(dniVeterinario);
                ok = dao.updateRecomendacion(rec);
            }
            response.sendRedirect(request.getContextPath() + "/" + editar + "?id=" + idHistorial + "&recmsg=" + (ok ? "editado" : "error"));
            return;
        }
        // Si ninguna acción coincide, reenvía por defecto
        RequestDispatcher rd = request.getRequestDispatcher(editar);
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador para agregar, editar y eliminar recomendaciones en el historial médico";
    }
}