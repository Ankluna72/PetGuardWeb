package Controlador;

import Modelo.Observacion;
import ModeloDAO.ObservacionDAO;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "ControladorObservacion", urlPatterns = {"/ControladorObservacion"})
public class ControladorObservacion extends HttpServlet {

    String editar = "Vista/VistaHistorialMedico/editar.jsp";
    ObservacionDAO dao = new ObservacionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null && accion.equalsIgnoreCase("eliminar")) {
            String idStr = request.getParameter("id"); // MongoDB ObjectId
            String idHistorial = request.getParameter("idHistorial");
            boolean ok = false;
            if (idStr != null && ObjectId.isValid(idStr)) {
                ObjectId oid = new ObjectId(idStr);
                ok = dao.deleteObservacion(oid);
            }
            response.sendRedirect(request.getContextPath() + "/" + editar + "?id=" + idHistorial + "&obsmsg=" + (ok ? "eliminado" : "error"));
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
            String texto = request.getParameter("observacion");
            String dniPropietario = request.getParameter("dni_propietario");
            Date fecha = new Date();
            Observacion obs = new Observacion();
            obs.setId_historial(idHistorial);
            obs.setObservacion(texto);
            obs.setFecha(fecha);
            obs.setEstado("activo");
            obs.setDni_propietario(dniPropietario);
            // Eliminar dni_veterinario
            dao.insertObservacion(obs);
            response.sendRedirect(request.getContextPath() + "/" + editar + "?id=" + idHistorial + "&obsmsg=agregado");
            return;
        } else if (accion != null && accion.equalsIgnoreCase("editar")) {
            String idStr = request.getParameter("id_observacion");
            int idHistorial = Integer.parseInt(request.getParameter("id_historial"));
            String texto = request.getParameter("observacion");
            String dniPropietario = request.getParameter("dni_propietario");
            Date fecha = new Date(); // Puedes usar la fecha actual o tomarla del formulario
            boolean ok = false;
            if (idStr != null && ObjectId.isValid(idStr)) {
                Observacion obs = new Observacion();
                obs.setId(new ObjectId(idStr));
                obs.setId_historial(idHistorial);
                obs.setObservacion(texto);
                obs.setFecha(fecha);
                obs.setEstado("activo");
                obs.setDni_propietario(dniPropietario);
                // Eliminar dni_veterinario
                ok = dao.updateObservacion(obs);
            }
            response.sendRedirect(request.getContextPath() + "/" + editar + "?id=" + idHistorial + "&obsmsg=" + (ok ? "editado" : "error"));
            return;
        }
        // Si ninguna acción coincide, reenvía por defecto
        RequestDispatcher rd = request.getRequestDispatcher(editar);
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador para agregar, editar y eliminar observaciones en el historial médico";
    }
}