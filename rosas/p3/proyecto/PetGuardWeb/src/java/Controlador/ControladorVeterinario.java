package Controlador;

import Modelo.Veterinario;
import ModeloDAO.VeterinarioDAO;
import java.io.IOException;
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
@WebServlet(name = "ControladorVeterinario", urlPatterns = {"/ControladorVeterinario"})
public class ControladorVeterinario extends HttpServlet {

    String listar = "Vista/VistaVeterinario/listar.jsp";
    String add = "Vista/VistaVeterinario/agregar.jsp";
    String edit = "Vista/VistaVeterinario/editar.jsp";
    Veterinario vet = new Veterinario();
    VeterinarioDAO dao = new VeterinarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acceso = "";
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        if (accion.equalsIgnoreCase("listar")) {
            acceso = listar;
        } else if (accion.equalsIgnoreCase("add")) {
            acceso = add;
        } else if (accion.equalsIgnoreCase("Agregar")) {
            String vdni = request.getParameter("txtdni");
            String vnombre = request.getParameter("txtnombre");
            String vapellidos = request.getParameter("txtapellidos");
            String vcorreo = request.getParameter("txtcorreo");
            String vtelefono = request.getParameter("txttelefono");
            String vdireccion = request.getParameter("txtdireccion");

            String vcontra = request.getParameter("txtcontra");

            vet.setDni(vdni);
            vet.setNombre(vnombre);
            vet.setApellidos(vapellidos);
            vet.setCorreo(vcorreo);
            vet.setTelefono(vtelefono);
            vet.setDireccion(vdireccion);

            vet.setContra(vcontra);

            boolean exito = dao.add(vet);
            if (exito) {
                response.sendRedirect("Vista/VistaVeterinario/agregar.jsp?registro=ok");
            } else {
                response.sendRedirect("Vista/VistaVeterinario/agregar.jsp?registro=fail");
            }
            return;
        } else if (accion.equalsIgnoreCase("editar")) {
            request.setAttribute("dniEditar", request.getParameter("dni"));
            acceso = edit;
        } else if (accion.equalsIgnoreCase("Actualizar")) {
            String vdni = request.getParameter("txtdni");
            String vnombre = request.getParameter("txtnombre");
            String vapellidos = request.getParameter("txtapellidos");
            String vcorreo = request.getParameter("txtcorreo");
            String vtelefono = request.getParameter("txttelefono");
            String vdireccion = request.getParameter("txtdireccion");
            String vfoto = request.getParameter("txtfoto");
            String vcontra = request.getParameter("txtcontra");

            vet.setDni(vdni);
            vet.setNombre(vnombre);
            vet.setApellidos(vapellidos);
            vet.setCorreo(vcorreo);
            vet.setTelefono(vtelefono);
            vet.setDireccion(vdireccion);
            vet.setFoto(vfoto);
            vet.setContra(vcontra);

            boolean ok = dao.edit(vet);
            if (ok) {
                response.sendRedirect(request.getContextPath()
                        + "/Vista/VistaVeterinario/editar.jsp?dni=" + vdni + "&msg=actualizado");
            } else {
                response.sendRedirect(request.getContextPath()
                        + "/Vista/VistaVeterinario/editar.jsp?dni=" + vdni + "&msg=error");
            }
            return;
        } else if (accion.equalsIgnoreCase("eliminar")) {
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
                        + "/Vista/VistaVeterinario/editar.jsp?dni=" + vdni + "&msg=error");
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
        return "Controlador para agregar, editar, listar y eliminar veterinarios";
    }
}