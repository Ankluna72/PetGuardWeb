package Controlador;

import Modelo.Message;
import ModeloDAO.MessageDAO;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "ControladorMessageV", urlPatterns = {"/ControladorMessageV"})
public class ControladorMessageV extends HttpServlet {

    MessageDAO dao = new MessageDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // DEBUG: Loguea todos los parámetros recibidos
        System.out.println("---- PARAMETROS RECIBIDOS ----");
        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            System.out.println(name + " = " + request.getParameter(name));
        }
        System.out.println("------------------------------");

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Falta el parámetro 'accion'");
            return;
        }

        if (accion.equalsIgnoreCase("agregar")) {
            String chatIdStr = request.getParameter("chat_id");
            String senderDni = request.getParameter("sender_dni");
            String senderTipo = request.getParameter("sender_tipo"); // debería ser "veterinario"
            String mensaje = request.getParameter("mensaje");
            String origen = request.getParameter("origen"); // para saber a dónde volver
            String propietarioDni = request.getParameter("propietario_dni"); // importante para la URL

            if (chatIdStr != null && org.bson.types.ObjectId.isValid(chatIdStr)
                    && mensaje != null && !mensaje.trim().isEmpty()) {
                try {
                    ObjectId chatId = new ObjectId(chatIdStr);
                    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    Message msg = new Message();
                    msg.setChat_id(chatId);
                    msg.setSender_dni(senderDni);
                    msg.setSender_tipo(senderTipo);
                    msg.setMensaje(mensaje);
                    msg.setTimestamp(timestamp);

                    dao.insertMessage(msg);

                    // REDIRECT A INICIOV.JSP CON LOS PARÁMETROS DEL CHAT ABIERTO
                    String redirectUrl = request.getContextPath() + "/Vista/VistaChat/iniciov.jsp"
                        + "?prop_dni=" + propietarioDni
                        + "&chat_id=" + chatIdStr;
                    response.sendRedirect(redirectUrl);
                    return;

                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Error al agregar mensaje: " + ex.getMessage());
                }
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Error: Datos inválidos");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: Acción no soportada");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Este servlet NO maneja GET, solo POST (AJAX o submit)
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.getWriter().write("Método no permitido");
    }

    @Override
    public String getServletInfo() {
        return "Controlador para enviar mensajes de chat (vista veterinario), solo POST y solo iniciov.jsp";
    }
}