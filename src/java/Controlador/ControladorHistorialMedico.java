package Controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Modelo.HistorialMedico;
import ModeloDAO.HistorialMedicoDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Jefferson
 */
@WebServlet(name = "ControladorHistorialMedico", urlPatterns = {"/ControladorHistorialMedico"})
public class ControladorHistorialMedico extends HttpServlet {
    
    String listar = "Vista/HistorialMedico/listar.jsp";
    String add = "Vista/HistorialMedico/agregar.jsp";
    String edit = "Vista/HistorialMedico/editar.jsp";
    HistorialMedicoDAO dao = new HistorialMedicoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acceso = "";
        String action = request.getParameter("accion");
        if (action == null) action = "listar";

        if (action.equalsIgnoreCase("listar")) {
            acceso = listar;
        } else if (action.equalsIgnoreCase("add")) {
            acceso = add;
        } else if (action.equalsIgnoreCase("Agregar")) {
            // Recoger parámetros del formulario (usando "txt" como prefijo)
            int idMascota = Integer.parseInt(request.getParameter("txtid_mascota"));
            String tipoEvento = request.getParameter("txttipo_evento");
            String fecha = request.getParameter("txtfecha");
            String descripcion = request.getParameter("txtdescripcion");
            String dniPropietario = request.getParameter("txtdni_propietario");
            String tratamientoAplicado = request.getParameter("txttratamiento_aplicado");
            String proximaCitaStr = request.getParameter("txtproxima_cita");
            String dniVeterinario = request.getParameter("txtdni_veterinario"); // nuevo campo

            // Setear en el modelo
            HistorialMedico his = new HistorialMedico();
            his.setId_mascota(idMascota);
            his.setTipo_evento(tipoEvento);
            his.setFecha(LocalDate.parse(fecha));
            his.setDescripcion(descripcion);
            his.setDni_propietario(dniPropietario);
            his.setTratamiento_aplicado(tratamientoAplicado);
            if (proximaCitaStr != null && !proximaCitaStr.isEmpty()) {
                his.setProxima_cita(LocalDateTime.parse(proximaCitaStr.replace(" ", "T")));
            } else {
                his.setProxima_cita(null);
            }
            his.setDni_veterinario(dniVeterinario);

            // Insertar en la base de datos
            boolean exito = dao.add(his);
            if (exito) {
                response.sendRedirect("Vista/VistaHistorialMedico/agregar.jsp?registro=ok");
            } else {
                response.sendRedirect("Vista/VistaHistorialMedico/agregar.jsp?registro=fail");
            }
            return;          
        } else if (action.equalsIgnoreCase("editar")) {
            acceso = edit;
        } else if (action.equalsIgnoreCase("Actualizar")) {
            // Recoger parámetros del formulario
            int id = Integer.parseInt(request.getParameter("txtid"));
            int idMascota = Integer.parseInt(request.getParameter("txtid_mascota"));
            String tipoEvento = request.getParameter("txttipo_evento");
            String fecha = request.getParameter("txtfecha");
            String descripcion = request.getParameter("txtdescripcion");
            String dniPropietario = request.getParameter("txtdni_propietario");
            String tratamientoAplicado = request.getParameter("txttratamiento_aplicado");
            String proximaCitaStr = request.getParameter("txtproxima_cita");
            String dniVeterinario = request.getParameter("txtdni_veterinario"); // nuevo campo

            // Setear en el modelo
            HistorialMedico his = new HistorialMedico();
            his.setId(id);
            his.setId_mascota(idMascota);
            his.setTipo_evento(tipoEvento);
            his.setFecha(LocalDate.parse(fecha));
            his.setDescripcion(descripcion);
            his.setDni_propietario(dniPropietario);
            his.setTratamiento_aplicado(tratamientoAplicado);
            if (proximaCitaStr != null && !proximaCitaStr.isEmpty()) {
                his.setProxima_cita(LocalDateTime.parse(proximaCitaStr.replace(" ", "T")));
            } else {
                his.setProxima_cita(null);
            }
            his.setDni_veterinario(dniVeterinario);

            boolean actualizado = dao.edit(his);
            if (actualizado) {
                response.sendRedirect("Vista/VistaHistorialMedico/editar.jsp?id=" + id + "&actualizado=ok");
            } else {
                response.sendRedirect("Vista/VistaHistorialMedico/editar.jsp?id=" + id + "&actualizado=fail");
            }
            return;
        } else if (action.equalsIgnoreCase("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean eliminado = dao.eliminar(id);
            if (eliminado) {
                response.sendRedirect("Vista/VistaHistorialMedico/listar.jsp?accion=Eliminar");
            } else {
                response.sendRedirect("Vista/VistaHistorialMedico/listar.jsp?accion=Error");
            }
            return;
        }
        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador para agregar, editar y eliminar historial médico";
    }
}