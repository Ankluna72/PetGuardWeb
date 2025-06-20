<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="ModeloDAO.RecomendacionDAO"%>
<%@page import="Modelo.Recomendacion"%>
<%@page import="ModeloDAO.ObservacionDAO"%>
<%@page import="Modelo.Observacion"%>
<%@page import="java.util.List"%>
<%@page import="ModeloDAO.HistorialMedicoDAO"%>
<%@page import="Modelo.HistorialMedico"%>
<%@page import="ModeloDAO.MascotaDAO"%>
<%@page import="Modelo.Mascota"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ModeloDAO.VeterinarioDAO"%>
<%@page import="Modelo.Veterinario"%>
<%
    
        String rol = (String) session.getAttribute("rol");


    
    
    String dni = (String) session.getAttribute("dni");
    if (dni == null) {
        response.sendRedirect("../../index.jsp?error=3");
        return;
    }
    String idParam = request.getParameter("id");
    int idHis = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : -1;
    HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();
    HistorialMedico his = historialDAO.obtenerHistorial(idHis);

    ObservacionDAO obsDao = new ObservacionDAO();
    List<Observacion> observaciones = obsDao.getObservacionesByHistorial(idHis);

    RecomendacionDAO recDao = new RecomendacionDAO();
    List<Recomendacion> recomendaciones = recDao.getRecomendacionesByHistorial(idHis);

    MascotaDAO mascotaDAO = new MascotaDAO();
    Mascota mascota = mascotaDAO.obtenerPorId(his.getId_mascota());
    String urlMascota = (mascota != null && mascota.getUrl() != null) ? mascota.getUrl() : "";

    if (his == null) {
        response.sendRedirect("listar.jsp?error=notfound");
        return;
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    

    VeterinarioDAO vetDao = new VeterinarioDAO();
    Veterinario vet = vetDao.obtenerPorDni(his.getDni_veterinario());
    String nombreVeterinario = (vet != null) ? (vet.getNombre() + " " + vet.getApellidos()) : his.getDni_veterinario();

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informe de Historial Médico</title>
    <link rel="stylesheet" href="/PetGuardWeb/css/verH.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
    <style>
        html, body {
            padding: 0 !important;
            margin: 0 !important;
            background: #fff !important;
            height: 100%;
        }
        body {
            min-height: 0 !important;
        }
        .documento-historial {
            margin: 0 auto 24px auto !important;
        }
        @media print {
            html, body { background: #fff !important; padding: 0 !important; margin: 0 !important; }
            .documento-historial { margin-top: 0 !important; box-shadow: none !important; }
        }
        .foto-mascota {
            display: flex;
            justify-content: center;
            margin-bottom: 22px;
        }
        .foto-mascota img {
            max-width: 150px;
            max-height: 150px;
            border-radius: 12px;
            border: 2px solid #d0e0f0;
            object-fit: cover;
            background: #f7fbff;
        }
        .observador-propietario {
            color: #f39c12;
            font-weight: 500;
        }
        .observador-veterinario {
            color: #27ae60;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="documento-historial" id="docHistorial" style="margin-top:0;">
        <!-- CABECERA DE INFORME -->
        <div class="cabecera-informe"
             style="display: flex; align-items: center; border-bottom: 2px solid #e3e8f0; margin-bottom: 22px; padding-bottom: 16px;">
            <img src="/PetGuardWeb/img/logoL.png" alt="Logo"
                 style="width: 258px; height: 80px; background: #fff; border-radius: 12px; margin-right: 18px;">
            <div>
                <h1 style="margin: 0; color: #184d8a; font-size: 2.1rem; letter-spacing: 1px;">INFORME DE HISTORIAL MÉDICO</h1>
                <span style="color: #888; font-size: 1.09rem;">Emitido: <%= java.time.LocalDate.now().format(dateFormatter) %></span>
            </div>
        </div>
        <!-- FOTO DE LA MASCOTA -->
        <div class="foto-mascota">
            <% if(urlMascota != null && !urlMascota.isEmpty()) { %>
                <img src="<%= urlMascota %>" alt="Foto de la Mascota">
            <% } else { %>
                <span style="color:#aaa;">Sin foto de mascota</span>
            <% } %>
        </div>
        <!-- DATOS DEL HISTORIAL -->
        <div class="datos-historial"
             style="background: #f7fbff; border-radius: 10px; padding: 22px 28px 15px 28px; border: 1.5px solid #e6eefb; margin-bottom: 24px; font-size: 1.08rem;">
            <table style="width:100%; border-collapse:collapse;">
                <tr>
                    <td><b>ID Historial:</b></td>
                    <td><%= his.getId() %></td>
                    <td><b>ID Mascota:</b></td>
                    <td><%= his.getId_mascota() %></td>
                </tr>
                <tr>
                    <td><b>Tipo de Evento:</b></td>
                    <td><%= his.getTipo_evento() %></td>
                    <td><b>Estado:</b></td>
                    <td><%= his.getEstado() %></td>
                </tr>
                <tr>
                    <td><b>Fecha:</b></td>
                    <td><%= (his.getFecha() != null ? his.getFecha().format(dateFormatter) : "") %></td>
                    <td><b>Veterinario:</b></td>
                    <td><%= nombreVeterinario %></td>

                </tr>
                <tr>
                    <td><b>Próxima Cita:</b></td>
                    <td colspan="3"><%= (his.getProxima_cita() != null ? his.getProxima_cita().format(dateTimeFormatter) : "No registrada") %></td>
                </tr>
                <tr>
                    <td><b>Tratamiento Aplicado:</b></td>
                    <td colspan="3"><%= his.getTratamiento_aplicado() != null ? his.getTratamiento_aplicado() : "No registrado" %></td>
                </tr>
                <tr>
                    <td style="vertical-align: top;"><b>Descripción:</b></td>
                    <td colspan="3"><%= his.getDescripcion() %></td>
                </tr>
            </table>
        </div>

        <!-- SECCION OBSERVACIONES -->
        <div class="seccion-doc">
            <h3>Observaciones</h3>
            <% if (observaciones != null && !observaciones.isEmpty()) { %>
                <ol style="padding-left: 18px;">
                    <% for (Observacion o : observaciones) { 
                        boolean esVeterinario = o.getDni_veterinario() != null && !o.getDni_veterinario().isEmpty();
                        boolean esPropietario = o.getDni_propietario() != null && !o.getDni_propietario().isEmpty();
                        String autor = esVeterinario ? "Veterinario" : "Propietario";
                        String autorClase = esVeterinario ? "observador-veterinario" : "observador-propietario";
                        String icono = esVeterinario ? "🩺" : "🐾";
                    %>
                        <li style="margin-bottom: 12px;">
                            <div style="font-size: 1.04rem; color: #374151;">
                                <span class="<%= autorClase %>"><%= icono %> <%= autor %>:</span> <%= o.getObservacion() %>
                            </div>
                            <div style="color: #6a7ea8; font-size: 0.97rem;">
                                <%= o.getFecha() != null ? o.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).format(dateTimeFormatter) : "" %>
                            </div>
                        </li>
                    <% } %>
                </ol>
            <% } else { %>
                <p><i>No hay observaciones registradas.</i></p>
            <% } %>
        </div>
 
        
        
<!-- SECCION RECOMENDACIONES -->
        <div class="seccion-doc">
            <h3>Recomendaciones</h3>
            <% if (recomendaciones != null && !recomendaciones.isEmpty()) { %>
                <ol style="padding-left: 18px;">
                    <% for (Recomendacion r : recomendaciones) { %>
                        <li style="margin-bottom: 12px;">
                            <div style="font-size: 1.04rem; color: #374151;">
                                <span class="observador-veterinario">🩺 Veterinario:</span> <%= r.getRecomendacion() %>
                            </div>
                            <div style="color: #6a7ea8; font-size: 0.97rem;">
                                <%= r.getFecha() != null ? r.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).format(dateTimeFormatter) : "" %>
                            </div>
                        </li>
                    <% } %>
                </ol>
            <% } else { %>
                <p><i>No hay recomendaciones registradas.</i></p>
            <% } %>
        </div>        

        <!-- FIRMADO 
        
        <div style="margin-top:40px; text-align:right;">
            <div style="font-size:1rem; color:#888;">_______________________</div>
            <div style="font-weight:600; color:#184d8a;">Firma del Veterinario</div>
        </div>
        
        -->
        <!-- BOTONES -->
        <div style="display: flex; justify-content:center; align-items:center; margin-top:36px;">
            
            <button class="btn-pdf" onclick="descargarPDF()">
                <img src="/PetGuardWeb/img/pdf.png" alt="PDF" style="width:22px; height:22px;vertical-align:middle; margin-right:7px;">
                Descargar PDF
            </button>
        </div>
    </div>
    <script>
    function descargarPDF() {
        var element = document.getElementById('docHistorial');
        // Calcula la altura del contenido en milímetros (1px = 0.264583 mm aprox)
        var pxHeight = element.offsetHeight;
        var mmHeight = pxHeight * 0.264583 + 20; // 20mm extra por si acaso
        var opt = {
            margin:       [0, 0, 0, 0],
            filename:     'Informe_HistorialMedico_<%= his.getId() %>.pdf',
            image:        { type: 'jpeg', quality: 0.98 },
            html2canvas:  { scale: 2, useCORS: true, scrollY: 0 },
            jsPDF:        { unit: 'mm', format: [210, mmHeight], orientation: 'portrait' }
        };
        window.scrollTo(0, 0);
        html2pdf().set(opt).from(element).save();
    }
    </script>
    
    

<a href="<%= "propietario".equals(rol) ? "listarp.jsp" : "listar.jsp" %>" class="btn-return-grey">Volver</a>


</body>
</html>