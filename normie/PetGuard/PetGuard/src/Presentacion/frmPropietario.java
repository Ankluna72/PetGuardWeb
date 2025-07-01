/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;
import Config.clsImagesAPI;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.io.File;
import Config.clsImagesAPI;
import Entidad.clsEMascota;
import Negocios.clsNMascota;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.awt.dnd.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import Config.*;
import Entidad.clsEPropietario;
import Negocios.clsNPropietario;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

import Config.*;
import Entidad.clsEHistorialMedico;
import Negocios.clsNHistorialMedico;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

//Estilos


import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.UIManager;




/**
 *
 * @author Jefferson
 */
public class frmPropietario extends javax.swing.JFrame {
    
    
    
    clsConexion cn=new clsConexion(); // para establecer la conexion
    Connection con; //para llamar a la cadena conexion
    PreparedStatement ps; //ejecutar consultas query(select, insert, etc)
    ResultSet rs; // almacena datos de la consulta
    Statement st;
    clsEMascota c=new clsEMascota();//objeto cliente
    
    String urlFoto;
    //Tablas 
    
    //Mascotas
    DefaultTableModel modelo = new DefaultTableModel();
    
    //Historial medico
    DefaultTableModel modelo1 = new DefaultTableModel();

    //Bandera Accion mascota
    int accionMascota = 0;
    
    //Bandera accion historial
    
    int accionHistorial = 0;


    /**
     * Creates new form frmPropietario
     */
    public frmPropietario() {
        initComponents();
        
        
        // Supongamos que tu JTextArea se llama lblDescripcion:
        lblDescripcion.setLineWrap(true);        // Activa el salto automático de línea
        lblDescripcion.setWrapStyleWord(true);   // Rompe en palabras, no a mitad de palabra
        lblDescripcion.setEditable(false);       // Si sólo quieres mostrar texto (opcional)
        // Opcional: para que empiece siempre arriba al cargar el texto
        lblDescripcion.setCaretPosition(0);
        
        txtDescripcionA.setLineWrap(true);        // Activa el salto automático de línea
        txtDescripcionA.setWrapStyleWord(true);   // Rompe en palabras, no a mitad de palabra
        txtDescripcionA.setEditable(true);        // Puedes hacerlo editable si quieres permitir la edición
        txtDescripcionA.setCaretPosition(0);  
        
        
        
        
        //Personalizar combobox
        
        personalizarComboBox(cmbSexo);
        personalizarComboBox(cmbMascota);
                
                
        
        
        
        //configurar tabbed pane
        
        btnMascotas.setBackground(new Color(43,121,194,255)); // RGB personalizado
        
        
        //boton agregar mascota
                

        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/Files/nuevaMascota.png"));
    
        int lado = btnAgregarMascota.getHeight(); // Usamos solo el alto para mantener forma cuadrada
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(lado, lado, Image.SCALE_SMOOTH);
        btnAgregarMascota.setIcon(new ImageIcon(imagenEscalada));
        
        
        //tabbed config
        
        jtbHistorial.setUI(null);
        jbpPrincipal.setUI(null);        
        jtbMascotas.setUI(null);
        
        
        jbpPrincipal.setSelectedIndex(0);
        
        
        //Listar mascotas

        clsNMascota objNC=new clsNMascota();//negocios

        txtDNI.setText(frmLogin.proD);
        txtDniP.setText(frmLogin.proD);
        txtID.setText(String.valueOf(objNC.MtdObtenerUltimaId()+1));
        
        
        obtenerDatosPorDni();
        
      
        lblUsuario.setText(frmLogin.proN);

        
        
        
        //Inicializacion de la tabla mascota: 
       
        
        tblMascotas.setRowHeight(50); // Puedes ajustar el valor si quieres más alto
        
        modelo.addColumn ("ID");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("ESPECIE");
        modelo.addColumn("RAZA");
        modelo.addColumn("EDAD");
        modelo.addColumn("SEXO");
        modelo.addColumn("PROPIETARIO");
        modelo.addColumn("EDITAR");
        modelo.addColumn("ELIMINAR");
        tblMascotas.setModel(modelo);
               
        //Listar Mascotas
        
        mtdListarMascotas();
        
        
        //Inicializacion de la tabla historial medico: 
        
        
        tblHistorial.setRowHeight(50); // Puedes ajustar el valor si quieres más alto
        
        modelo1.addColumn ("ID");
        modelo1.addColumn("ID MASCOTA");
        modelo1.addColumn("EVENTO");
        modelo1.addColumn("FECHA");
        modelo1.addColumn("VETERINARIO");
        modelo1.addColumn("VER");
        modelo1.addColumn("EDITAR");
        modelo1.addColumn("ELIMINAR");
        tblHistorial.setModel(modelo1);
        
        mtdListarHistorialMedico();

        
        
        
        
        //configuracion de diseño de la tabla mascotas ------------------------------------------------------------
        
        // Estilo moderno para la tabla
        tblMascotas.setRowHeight(35); // Espacio entre filas
        tblMascotas.setGridColor(Color.BLACK); // Color de líneas
        tblMascotas.setShowGrid(true);
        tblMascotas.setIntercellSpacing(new Dimension(1, 1));

        // Cambiar color de fondo y texto
        tblMascotas.setBackground(Color.WHITE);
        tblMascotas.setForeground(Color.DARK_GRAY);
        tblMascotas.setSelectionBackground(new Color(60, 120, 180)); // Fondo al seleccionar
        tblMascotas.setSelectionForeground(Color.WHITE); // Letra blanca al seleccionar

        // Fuente moderna
        tblMascotas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Personalizar la cabecera del JTable
        JTableHeader header = tblMascotas.getTableHeader();
        header.setBackground(Color.BLACK);               // Fondo negro
        header.setForeground(Color.WHITE);               // Letras blancas
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(header.getWidth(), 35)); // Altura del header
        
        //Borde redondeado
        JScrollPane scroll = (JScrollPane) tblMascotas.getParent().getParent();
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // Forzar color negro en header y letras blancas
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.BLACK);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Aplicar el renderer a todas las columnas del header
        for (int i = 0; i < tblMascotas.getColumnModel().getColumnCount(); i++) {
            tblMascotas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        
        
        //----------------------------------------------------------------------------------        
        
        
        
        // Configuración de diseño de la tabla historial ------------------------------------------------------------

        tblHistorial.setRowHeight(35); // Espacio entre filas
        tblHistorial.setGridColor(Color.BLACK); // Color de líneas
        tblHistorial.setShowGrid(true);
        tblHistorial.setIntercellSpacing(new Dimension(1, 1));

        // Cambiar color de fondo y texto
        tblHistorial.setBackground(Color.WHITE);
        tblHistorial.setForeground(Color.DARK_GRAY);
        tblHistorial.setSelectionBackground(new Color(60, 120, 180)); // Fondo al seleccionar
        tblHistorial.setSelectionForeground(Color.WHITE); // Letra blanca al seleccionar

        // Fuente moderna
        tblHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Personalizar la cabecera del JTable
        JTableHeader headerHistorial = tblHistorial.getTableHeader();
        headerHistorial.setBackground(Color.BLACK);               // Fondo negro
        headerHistorial.setForeground(Color.WHITE);               // Letras blancas
        headerHistorial.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerHistorial.setOpaque(true);
        headerHistorial.setPreferredSize(new Dimension(headerHistorial.getWidth(), 35)); // Altura del header

        // Borde redondeado del scroll
        JScrollPane scrollHistorial = (JScrollPane) tblHistorial.getParent().getParent();
        scrollHistorial.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Forzar color negro en header y letras blancas centradas
        DefaultTableCellRenderer headerRendererHistorial = new DefaultTableCellRenderer();
        headerRendererHistorial.setBackground(Color.BLACK);
        headerRendererHistorial.setForeground(Color.WHITE);
        headerRendererHistorial.setHorizontalAlignment(JLabel.CENTER);
        headerRendererHistorial.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Aplicar el renderer a todas las columnas del header de historial
        for (int i = 0; i < tblHistorial.getColumnModel().getColumnCount(); i++) {
            tblHistorial.getColumnModel().getColumn(i).setHeaderRenderer(headerRendererHistorial);
        }

        //----------------------------------------------------------------------------------
        
        
        
        
        
        
        //Evento mouse listener para la tabla ------------------------------------------------------------------------------------------------
        
        
        tblMascotas.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = tblMascotas.rowAtPoint(e.getPoint());
            int col = tblMascotas.columnAtPoint(e.getPoint());

            if (col == 7) { // Columna EDITAR
                String idMascota = tblMascotas.getValueAt(row, 0).toString();
                // Aquí va tu lógica para modificar la mascota
                
                jtbMascotas.setSelectedIndex(1);
                
                lblMascota.setText("Modificar Mascota");
                
                btnMascotaAccion.setText("Modificar");
                
                accionMascota=1;
                
                clsNMascota negocioMascota = new clsNMascota();
                clsEMascota miMascota = negocioMascota.MtdObtenerMascotaPorId(Integer.parseInt(idMascota)); // ejemplo con ID = 5

                txtID.setText(String.valueOf(miMascota.getId()));
                txtNombre.setText(miMascota.getNombre());
                txtEspecie.setText(miMascota.getEspecie());
                txtRaza.setText(miMascota.getRaza());
                txtEdad.setText(String.valueOf(miMascota.getEdad()));
                cmbSexo.setSelectedItem(miMascota.getSexo());
                CargarImagen(miMascota.getUrl());
                
                urlFoto = miMascota.getUrl();
                
                btnMascotaAccion.setIcon(new ImageIcon(getClass().getResource("/Files/editarB.png")));
                
                                
                
                
                
                
                
                
                
                
                System.out.println("Editar mascota con ID: " + idMascota);
                
                
                
            } else if (col == 8) { // Columna ELIMINAR
                String idMascota = tblMascotas.getValueAt(row, 0).toString();               
                // Aquí va tu lógica para eliminar la mascota
                
                clsNMascota objMascota = new clsNMascota();
                
                int respuesta = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro que deseas eliminar esta mascota?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

                if (respuesta == JOptionPane.YES_OPTION) {
                    boolean exito = objMascota.MtdDesactivarMascota(Integer.parseInt(idMascota)); // ← AQUÍ pones tu ID
                    
                    mtdListarMascotas();
                }
                
                
                
                System.out.println("Eliminar mascota con ID: " + idMascota);
            }
        }
        });
        
        //----------------------------------------------------------------------------------------------------------------------------------------
        
        
        tblMascotas.addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            int column = tblMascotas.columnAtPoint(e.getPoint());
            if (column == 7 || column == 8) { // columnas de editar y eliminar
                tblMascotas.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                tblMascotas.setCursor(Cursor.getDefaultCursor());
            }
        }
        });
        
        
        // Evento mouse listener para la tabla tblHistorial -------------------------------------------------------
        tblHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblHistorial.rowAtPoint(e.getPoint());
                int col = tblHistorial.columnAtPoint(e.getPoint());

                if (col == 5) { // Columna VER
                    String idHistorial = tblHistorial.getValueAt(row, 0).toString();
                    System.out.println("Ver historial con ID: " + idHistorial);
                    
                    
                    jtbHistorial.setSelectedIndex(2);
                    
                    
                    String dni = frmLogin.proD;
                    
                    
                    clsNHistorialMedico clsNM = new clsNHistorialMedico();
                    
                    clsNMascota clsNA = new clsNMascota();

                    
                    
                    
                    clsEHistorialMedico miHistorial = clsNM.MtdObtenerHistorialPorId(Integer.parseInt(idHistorial)); // ejemplo con ID = 5

                    lblAsunto.setText(miHistorial.getTipo_evento());
                    lblVeterinario.setText(miHistorial.getVeterinario());
                    lblDescripcion.setText(miHistorial.getDescripcion());
                    lblFecha.setText(miHistorial.getFecha().toString());
                    
                    String mascota = clsNA.MtdObtenerNombreMascotaPorId(miHistorial.getId_mascota());
                    
                    lblNMascota.setText(mascota);
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                } else if (col == 6) { // Columna EDITAR
                    String idHistorial = tblHistorial.getValueAt(row, 0).toString();
                    System.out.println("Editar historial con ID: " + idHistorial);
              
                    // TODO add your handling code here:

                    clsNHistorialMedico clsNM = new clsNHistorialMedico();
                    clsNMascota clsNA = new clsNMascota();

                    
                    
                    accionHistorial = 1;
                    
                    String dni = frmLogin.proD;

                    // Llamar al método que obtiene las mascotas
                    MtdObtenerMascotasPorDni(dni);
                    

                    




                    lblHistorial.setText("Modificar Registro");

                    btnAccionHistorial.setText("Modificar");
                    
                    
                    //accion de cargar textbox:
                    
                    clsEHistorialMedico miHistorial = clsNM.MtdObtenerHistorialPorId(Integer.parseInt(idHistorial)); // ejemplo con ID = 5

                    txtIDH.setText(String.valueOf(miHistorial.getId()));
                    txtEvento.setText(miHistorial.getTipo_evento());
                    txtVeterinario.setText(miHistorial.getVeterinario());
                    txtDescripcionA.setText(miHistorial.getDescripcion());
                    txtFecha.setText(miHistorial.getFecha().toString());
                    
                    
                    int idM = miHistorial.getId_mascota();
                    
                    System.out.println(idHistorial);

                    
                    String mascota = clsNA.MtdObtenerNombreMascotaPorId(idM);
                    
                    System.out.println(mascota);
                    
                    cmbMascota.setSelectedItem(mascota);
                    
                    
                    

                    
                    
                    
                    
                    
                    
                    
                    


                    btnAccionHistorial.setIcon(new ImageIcon(getClass().getResource("/Files/editarB.png")));

                    txtIDH.setText(idHistorial);
                    
                    jtbHistorial.setSelectedIndex(1);
                    
                    
                    
                    
                    
                    
                    
                } else if (col == 7) { // Columna ELIMINAR
                    String idHistorial = tblHistorial.getValueAt(row, 0).toString();
                    System.out.println("Eliminar historial con ID: " + idHistorial);
                    
                    
                    
                    clsNHistorialMedico objHistorial = new clsNHistorialMedico();
                
                    int respuesta = JOptionPane.showConfirmDialog(null, 
                    "¿Estás seguro que deseas eliminar este registro?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        boolean exito = objHistorial.MtdDesactivarHistorial(Integer.parseInt(idHistorial)); // ← AQUÍ pones tu ID

                        mtdListarHistorialMedico();
                    }


                
                    
                }
            }
        });
        
        //Activar el cursor hand
        
        tblHistorial.addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            int column = tblHistorial.columnAtPoint(e.getPoint());
            if (column == 5 || column == 6 || column == 7) { // columnas de ver, editar y eliminar
                tblHistorial.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                tblHistorial.setCursor(Cursor.getDefaultCursor());
            }
        }
        });
        
        
        
        
        //----------------------------------------------------------------------------------------------
    

        
        
    }
    //-------------------------------------------- FIN DEL CONSTRUCTOR ------------------------------------------------------------------------------------
    
    
    
    
    
    
        // Método para obtener los datos del propietario y llenar los TextBox
    public void obtenerDatosPorDni() {
        String dni = txtDniP.getText(); // Suponiendo que txtDni es el campo donde se ingresa el DNI
        clsNPropietario objNPropietario = new clsNPropietario();

        // Obtener el objeto propietario
        clsEPropietario propietario = objNPropietario.MtdObtenerDatosPorDni(dni);

        if (propietario != null) {
            // Llenar los TextBox con los datos del propietario
            txtNombreP.setText(propietario.getNombre());
            txtApellidoP.setText(propietario.getApellido());
            txtTelefonoP.setText(propietario.getTelefono());
            txtDireccionP.setText(propietario.getDireccion());
            txtCorreoP.setText(propietario.getCorreo());
            txtContraP.setText(propietario.getContra());
            System.out.println("Datos cargados correctamente.");
        } else {
            System.out.println("No se encontró el propietario con ese DNI.");
        }
    }
    

    
    
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    
    
    public void mtdListarHistorialMedico() {
    clsNHistorialMedico objNH = new clsNHistorialMedico();
    modelo1.getDataVector().removeAllElements();  // Limpiar la tabla antes de agregar los nuevos datos
    modelo1.fireTableDataChanged();  // Notificar que los datos han cambiado
    String dniPropietario = txtDNI.getText();  // Obtener el DNI del propietario
    ResultSet rs = objNH.MtdListarHistorialPorDniPropietario(dniPropietario);  // Obtener historiales médicos por DNI
    try {
        while (rs.next()) {
            String[] dato = new String[8];  // Columnas: id, id_mascota, tipo_evento, fecha, veterinario, Ver, Editar, Eliminar
            dato[0] = rs.getString("id");
            dato[1] = rs.getString("id_mascota");
            dato[2] = rs.getString("tipo_evento");
            dato[3] = rs.getString("fecha");
            dato[4] = rs.getString("veterinario");
            dato[5] = "Ver";
            dato[6] = "Editar";
            dato[7] = "Eliminar";
            modelo1.addRow(dato);  // ✅ Ahora sí, usas el modelo correcto
        }
    } catch (SQLException e) {
        System.out.println("Error al mostrar historial médico: " + e.toString());
    }
    tblHistorial.setModel(modelo1);  // ✅ Asegúrate de que la tabla use el modelo correcto

    // Asignar render y editor para botones
    tblHistorial.getColumn("VER").setCellRenderer(new ButtonRenderer("/Files/ver.png"));
    tblHistorial.getColumn("EDITAR").setCellRenderer(new ButtonRenderer("/Files/editar.png"));
    tblHistorial.getColumn("ELIMINAR").setCellRenderer(new ButtonRenderer("/Files/borrar.png"));
    tblHistorial.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), 5, tblHistorial, "/Files/ver.png"));
    tblHistorial.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), 6, tblHistorial, "/Files/editar.png"));
    tblHistorial.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), 7, tblHistorial, "/Files/borrar.png"));
    }
    
    
    

    
    
    
    
    private void LimpiarCamposMascota() {
    //txtID.setText("");
    txtNombre.setText("");
    txtEspecie.setText("");
    txtRaza.setText("");
    txtEdad.setText("");
    jblFoto.setIcon(new ImageIcon(getClass().getResource("/Files/mascotalogoS.png")));
    urlFoto=("");
    
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new java.awt.Button();
        bg = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMascotas = new javax.swing.JLabel();
        btnHistorial = new javax.swing.JLabel();
        btnPerfil = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jbpPrincipal = new javax.swing.JTabbedPane();
        jpnMascotas = new javax.swing.JPanel();
        jtbMascotas = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        btnAgregarMascota = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMascotas = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEspecie = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRaza = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtIdMascota = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtEdad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jblFoto = new javax.swing.JLabel();
        btnVolver = new javax.swing.JLabel();
        btnMascotaAccion = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblMascota = new javax.swing.JLabel();
        btnSubirImagen = new javax.swing.JLabel();
        cmbSexo = new javax.swing.JComboBox<>();
        jpnlPerfil = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNombreP = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtApellidoP = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTelefonoP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtIdMascota1 = new javax.swing.JLabel();
        txtDniP = new javax.swing.JTextField();
        txtDireccionP = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCorreoP = new javax.swing.JTextField();
        txtContraP = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnGuardarPerfil = new javax.swing.JLabel();
        jpnlHistorial = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jtbHistorial = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        btnAgregarRegistro = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtEvento = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtIdMascota2 = new javax.swing.JLabel();
        txtIDH = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtVeterinario = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        cmbMascota = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        lblHistorial = new javax.swing.JLabel();
        btnAccionHistorial = new javax.swing.JLabel();
        btnAtras = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcionA = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        lblNMascota = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lblDescripcion = new javax.swing.JTextArea();
        lblAsunto = new javax.swing.JLabel();
        lblVeterinario = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblVeterinario2 = new javax.swing.JLabel();
        lblVeterinario3 = new javax.swing.JLabel();
        btnAtrasR = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        button1.setLabel("button1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setMinimumSize(new java.awt.Dimension(930, 700));
        bg.setPreferredSize(new java.awt.Dimension(930, 700));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(53, 65, 77));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMascotas.setBackground(new java.awt.Color(53, 65, 77));
        btnMascotas.setFont(new java.awt.Font("Roboto Medium", 0, 15)); // NOI18N
        btnMascotas.setForeground(new java.awt.Color(255, 255, 255));
        btnMascotas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMascotas.setText("Gestionar Mascotas");
        btnMascotas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMascotas.setOpaque(true);
        btnMascotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMascotasMouseClicked(evt);
            }
        });
        jPanel2.add(btnMascotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 70));

        btnHistorial.setBackground(new java.awt.Color(53, 65, 77));
        btnHistorial.setFont(new java.awt.Font("Roboto Medium", 0, 15)); // NOI18N
        btnHistorial.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHistorial.setText("Historial Medico");
        btnHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorial.setOpaque(true);
        btnHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHistorialMouseClicked(evt);
            }
        });
        jPanel2.add(btnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 240, 70));

        btnPerfil.setBackground(new java.awt.Color(53, 65, 77));
        btnPerfil.setFont(new java.awt.Font("Roboto Medium", 0, 15)); // NOI18N
        btnPerfil.setForeground(new java.awt.Color(255, 255, 255));
        btnPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPerfil.setText("Gestionar Perfil");
        btnPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPerfil.setOpaque(true);
        btnPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPerfilMouseClicked(evt);
            }
        });
        jPanel2.add(btnPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, 70));

        bg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 240, 590));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbpPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jbpPrincipal.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jbpPrincipal.setMinimumSize(new java.awt.Dimension(930, 650));

        jpnMascotas.setBackground(new java.awt.Color(255, 255, 255));
        jpnMascotas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtbMascotas.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarMascota.setBackground(new java.awt.Color(102, 153, 255));
        btnAgregarMascota.setFont(new java.awt.Font("Roboto Medium", 0, 16)); // NOI18N
        btnAgregarMascota.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarMascota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAgregarMascota.setText("Agregar Mascota");
        btnAgregarMascota.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnAgregarMascota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarMascota.setOpaque(true);
        btnAgregarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMascotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarMascotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarMascotaMouseExited(evt);
            }
        });
        jPanel5.add(btnAgregarMascota, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 190, 40));

        tblMascotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblMascotas);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 720, 470));

        jLabel22.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/misMascotas.png"))); // NOI18N
        jLabel22.setText("MIS MASCOTAS");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 30));

        jtbMascotas.addTab("tab1", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(53, 65, 77), 2));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("Nombre:");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel6.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 210, -1));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setText("Especie:");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        txtEspecie.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel6.add(txtEspecie, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 210, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setText("Raza:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        txtRaza.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazaActionPerformed(evt);
            }
        });
        jPanel6.add(txtRaza, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 160, -1));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("Edad:");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 40, -1));

        txtDNI.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDNI.setEnabled(false);
        txtDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIActionPerformed(evt);
            }
        });
        jPanel6.add(txtDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 220, -1));

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel9.setText("Sexo:");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 40, -1));

        txtIdMascota.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIdMascota.setText("ID:");
        jPanel6.add(txtIdMascota, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        txtID.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtID.setEnabled(false);
        jPanel6.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 130, -1));

        txtEdad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEdadActionPerformed(evt);
            }
        });
        jPanel6.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 140, -1));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel10.setText("DNI Propietario:");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 120, -1));

        jblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/mascotalogoS.png"))); // NOI18N
        jblFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel6.add(jblFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 320, 130, 110));

        btnVolver.setBackground(new java.awt.Color(102, 153, 255));
        btnVolver.setFont(new java.awt.Font("Roboto Condensed", 1, 16)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/volver.png"))); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.setOpaque(true);
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVolverMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolverMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolverMouseExited(evt);
            }
        });
        jPanel6.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 470, 100, 40));

        btnMascotaAccion.setBackground(new java.awt.Color(102, 153, 255));
        btnMascotaAccion.setFont(new java.awt.Font("Roboto Condensed", 1, 16)); // NOI18N
        btnMascotaAccion.setForeground(new java.awt.Color(255, 255, 255));
        btnMascotaAccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMascotaAccion.setText("Agregar");
        btnMascotaAccion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnMascotaAccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMascotaAccion.setOpaque(true);
        btnMascotaAccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMascotaAccionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMascotaAccionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMascotaAccionMouseExited(evt);
            }
        });
        jPanel6.add(btnMascotaAccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 100, 40));

        jPanel7.setBackground(new java.awt.Color(53, 65, 77));

        lblMascota.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        lblMascota.setForeground(new java.awt.Color(255, 255, 255));
        lblMascota.setText("AGREGAR MASCOTA");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblMascota)
                .addContainerGap(544, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblMascota)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 60));

        btnSubirImagen.setBackground(new java.awt.Color(255, 102, 0));
        btnSubirImagen.setFont(new java.awt.Font("Roboto Condensed", 1, 16)); // NOI18N
        btnSubirImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnSubirImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSubirImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/subir_imagen.png"))); // NOI18N
        btnSubirImagen.setText("Subir Imagen");
        btnSubirImagen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnSubirImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubirImagen.setOpaque(true);
        btnSubirImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubirImagenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubirImagenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubirImagenMouseExited(evt);
            }
        });
        jPanel6.add(btnSubirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 160, 40));

        cmbSexo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Macho", "Hembra" }));
        cmbSexo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(cmbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 150, -1));

        jtbMascotas.addTab("tab2", jPanel6);

        jpnMascotas.add(jtbMascotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 570));

        jbpPrincipal.addTab("Gestionar mascotas", jpnMascotas);

        jpnlPerfil.setBackground(new java.awt.Color(255, 255, 255));
        jpnlPerfil.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jpnlPerfil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel11.setText("Nombre:");
        jpnlPerfil.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        txtNombreP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jpnlPerfil.add(txtNombreP, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 160, -1));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel12.setText("Apellidos:");
        jpnlPerfil.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        txtApellidoP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jpnlPerfil.add(txtApellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 210, -1));

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel13.setText("Telefono:");
        jpnlPerfil.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        txtTelefonoP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTelefonoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoPActionPerformed(evt);
            }
        });
        jpnlPerfil.add(txtTelefonoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 160, -1));

        jLabel14.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel14.setText("Direccion:");
        jpnlPerfil.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, -1, -1));

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel15.setText("Contraseña:");
        jpnlPerfil.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, -1, -1));

        txtIdMascota1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIdMascota1.setText("DNI:");
        jpnlPerfil.add(txtIdMascota1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        txtDniP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDniP.setEnabled(false);
        jpnlPerfil.add(txtDniP, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 140, -1));

        txtDireccionP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDireccionP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionPActionPerformed(evt);
            }
        });
        jpnlPerfil.add(txtDireccionP, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 240, -1));

        jLabel17.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel17.setText("Correo:");
        jpnlPerfil.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, -1, -1));

        txtCorreoP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jpnlPerfil.add(txtCorreoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 230, -1));

        txtContraP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jpnlPerfil.add(txtContraP, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 160, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jpnlPerfil.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 550, 140));

        jPanel8.setBackground(new java.awt.Color(53, 65, 77));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/editarUsuario.png"))); // NOI18N
        jLabel16.setText("GESTIONAR PERFIL");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, 40));

        jpnlPerfil.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 60));

        btnGuardarPerfil.setBackground(new java.awt.Color(68, 182, 120));
        btnGuardarPerfil.setFont(new java.awt.Font("Roboto Medium", 0, 16)); // NOI18N
        btnGuardarPerfil.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardarPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/guardar.png"))); // NOI18N
        btnGuardarPerfil.setText("Guardar Cambios");
        btnGuardarPerfil.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnGuardarPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarPerfil.setOpaque(true);
        btnGuardarPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarPerfilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarPerfilMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarPerfilMouseExited(evt);
            }
        });
        jpnlPerfil.add(btnGuardarPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 180, 40));

        jbpPrincipal.addTab("Gestionar Perfil", jpnlPerfil);

        jpnlHistorial.setBackground(new java.awt.Color(255, 255, 255));
        jpnlHistorial.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jpnlHistorial.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblHistorial);

        jPanel10.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 720, 430));

        jLabel28.setFont(new java.awt.Font("Roboto Black", 1, 20)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/saludMascotaM.png"))); // NOI18N
        jLabel28.setText("HISTORIAL MEDICO");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 50));

        btnAgregarRegistro.setBackground(new java.awt.Color(246, 167, 0));
        btnAgregarRegistro.setFont(new java.awt.Font("Roboto Medium", 0, 16)); // NOI18N
        btnAgregarRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarRegistro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAgregarRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/subirRegistro.png"))); // NOI18N
        btnAgregarRegistro.setText("Agregar Registro");
        btnAgregarRegistro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnAgregarRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarRegistro.setOpaque(true);
        btnAgregarRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarRegistroMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarRegistroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarRegistroMouseExited(evt);
            }
        });
        jPanel10.add(btnAgregarRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 190, 40));

        jtbHistorial.addTab("tab2", jPanel10);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel18.setText("Tipo de Evento:");
        jPanel9.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        txtEvento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel9.add(txtEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 170, -1));

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel19.setText("Descripcion:");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel21.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel21.setText("Fecha:");
        jPanel9.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 50, -1));

        txtIdMascota2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIdMascota2.setText("ID:");
        jPanel9.add(txtIdMascota2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 30, -1));

        txtIDH.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIDH.setEnabled(false);
        jPanel9.add(txtIDH, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 120, -1));

        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        jPanel9.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, 130, -1));

        jLabel24.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel24.setText("Veterinario:");
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 260, -1, -1));

        txtVeterinario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel9.add(txtVeterinario, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 210, -1));

        jLabel27.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel27.setText("Mascota:");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        cmbMascota.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel9.add(cmbMascota, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 150, -1));

        jPanel11.setBackground(new java.awt.Color(53, 65, 77));

        lblHistorial.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        lblHistorial.setForeground(new java.awt.Color(255, 255, 255));
        lblHistorial.setText("AGREGAR REGISTRO");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblHistorial)
                .addContainerGap(545, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblHistorial)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 60));

        btnAccionHistorial.setBackground(new java.awt.Color(102, 153, 255));
        btnAccionHistorial.setFont(new java.awt.Font("Roboto Condensed", 1, 16)); // NOI18N
        btnAccionHistorial.setForeground(new java.awt.Color(255, 255, 255));
        btnAccionHistorial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAccionHistorial.setText("Agregar");
        btnAccionHistorial.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnAccionHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccionHistorial.setOpaque(true);
        btnAccionHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAccionHistorialMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAccionHistorialMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAccionHistorialMouseExited(evt);
            }
        });
        jPanel9.add(btnAccionHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 100, 40));

        btnAtras.setBackground(new java.awt.Color(102, 153, 255));
        btnAtras.setFont(new java.awt.Font("Roboto Condensed", 1, 16)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/volver.png"))); // NOI18N
        btnAtras.setText("Volver");
        btnAtras.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnAtras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtras.setOpaque(true);
        btnAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasMouseExited(evt);
            }
        });
        jPanel9.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 470, 100, 40));

        txtDescripcionA.setColumns(20);
        txtDescripcionA.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcionA.setRows(5);
        jScrollPane3.setViewportView(txtDescripcionA);

        jPanel9.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 350, 140));

        jtbHistorial.addTab("tab1", jPanel9);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(53, 65, 77));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/carta.png"))); // NOI18N
        jLabel20.setText("REGISTRO MEDICO");
        jPanel13.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 200, 40));

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 60));

        lblNMascota.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        lblNMascota.setText("lblMascota");
        jPanel12.add(lblNMascota, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 80, 20));

        lblDescripcion.setColumns(20);
        lblDescripcion.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        lblDescripcion.setRows(5);
        jScrollPane4.setViewportView(lblDescripcion);

        jPanel12.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 680, 230));

        lblAsunto.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblAsunto.setText("lblAsunto");
        jPanel12.add(lblAsunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 330, 40));

        lblVeterinario.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        lblVeterinario.setText("lblVeterinario");
        jPanel12.add(lblVeterinario, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 500, 110, 20));

        lblFecha.setFont(new java.awt.Font("Roboto", 0, 17)); // NOI18N
        lblFecha.setText("lblFecha");
        jPanel12.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 130, 40));

        lblVeterinario2.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        lblVeterinario2.setText("Veterinario:");
        jPanel12.add(lblVeterinario2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 500, 80, 20));

        lblVeterinario3.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        lblVeterinario3.setText("Mascota:");
        jPanel12.add(lblVeterinario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 70, 20));

        btnAtrasR.setBackground(new java.awt.Color(102, 153, 255));
        btnAtrasR.setFont(new java.awt.Font("Roboto Condensed", 1, 16)); // NOI18N
        btnAtrasR.setForeground(new java.awt.Color(255, 255, 255));
        btnAtrasR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAtrasR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/volver.png"))); // NOI18N
        btnAtrasR.setText("Volver");
        btnAtrasR.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnAtrasR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtrasR.setOpaque(true);
        btnAtrasR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasRMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasRMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasRMouseExited(evt);
            }
        });
        jPanel12.add(btnAtrasR, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 100, 40));

        jtbHistorial.addTab("tab3", jPanel12);

        jpnlHistorial.add(jtbHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 570));

        jbpPrincipal.addTab("Historial Medico", jpnlHistorial);

        jPanel3.add(jbpPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 740, 610));

        jLabel2.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        jLabel2.setText("PANEL DE CONTROL");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/userS.png"))); // NOI18N
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, -1, -1));

        lblUsuario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("Usuario");
        jPanel3.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 150, 20));

        bg.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 760, 700));

        jPanel4.setBackground(new java.awt.Color(71, 85, 98));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/HHHS.png"))); // NOI18N
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 220, 70));

        bg.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 110));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void CargarImagen(String url)
    
    {
        try {
        String imageUrl = url;

        URL imageURL = new URL(imageUrl);  // La URL es válida
        ImageIcon imageIcon = new ImageIcon(imageURL);

        int labelWidth = jblFoto.getWidth();
        int labelHeight = jblFoto.getHeight();

        Image img = imageIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        jblFoto.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage());
        }
   
    }
    
    private void txtEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEdadActionPerformed

    private void txtDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDNIActionPerformed

    private void txtRazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazaActionPerformed

    //Personalizar combobox
    
    
    public void personalizarComboBox(JComboBox comboBox) {
    //comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Borde negro cuadrado
    comboBox.setBackground(Color.WHITE);
    comboBox.setForeground(Color.BLACK);
    comboBox.setFocusable(false); // Quita el resaltado al hacer clic
    }

    


    
    
    public void mtdListarMascotas()
    
    {
        clsNMascota objNC = new clsNMascota(); // Clase de negocios
        modelo.getDataVector().removeAllElements();  // Limpiar la tabla antes de agregar los nuevos datos
        modelo.fireTableDataChanged();  // Notificar que los datos han cambiado

        String dniPropietario = txtDNI.getText();  // Obtener el DNI del propietario que inició sesión

        ResultSet rs = objNC.MtdListarMascotas(dniPropietario);  // Obtener las mascotas filtradas por el DNI del propietario

        try {
            while (rs.next()) {
                String[] dato = new String[9];  // Ajuste de tamaño para las columnas de datos
                dato[0] = rs.getString("id");  // ID de la mascota
                dato[1] = rs.getString("nombre");  // Nombre de la mascota
                dato[2] = rs.getString("especie");  // Especie de la mascota
                dato[3] = rs.getString("raza");  // Raza de la mascota
                dato[4] = rs.getString("edad");  // Edad de la mascota
                dato[5] = rs.getString("sexo");  // Sexo de la mascota
                dato[6] = rs.getString("dni_propietario");  // DNI del propietario (aunque esto no lo necesitas mostrar)
                dato[7] = "Editar";    // Placeholder para botón Editar
                dato[8] = "Eliminar";  // Placeholder para botón Eliminar

                modelo.addRow(dato);  // Agregar los datos a la tabla
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar los datos: " + e.toString());
        }

        tblMascotas.setModel(modelo);  // Asignar el modelo a la tabla
        
        // Columnas con iconos
        tblMascotas.getColumn("EDITAR").setCellRenderer(new ButtonRenderer("/Files/editar.png"));
        tblMascotas.getColumn("ELIMINAR").setCellRenderer(new ButtonRenderer("/Files/borrar.png"));

        // Detectar clics en esas columnas
        tblMascotas.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), 7, tblMascotas, "/Files/editar.png"));
        tblMascotas.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), 8, tblMascotas, "/Files/borrar.png"));
        
        


        
    
    }
    
    
    
    
    //Clase auxiliar para configurar los iconos en las tablas
    
    class ButtonRenderer extends DefaultTableCellRenderer {
    private final ImageIcon icon;

    public ButtonRenderer(String iconPath) {
        this.icon = new ImageIcon(getClass().getResource(iconPath));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true); // MUY IMPORTANTE para que se vea el fondo personalizado
        // Para evitar el efecto blanco al hacer clic
        if (isSelected) {
            label.setBackground(table.getBackground()); // o el color de fondo que uses normalmente
        } else {
            label.setBackground(Color.WHITE); // fondo normal cuando no está seleccionado
        }

        return label;
    }
    }

    
    
    //Clase para detectar los iconos de mascotas
    
    class ButtonEditor extends DefaultCellEditor {
    private final JLabel label = new JLabel();
    private boolean clicked;
    private final int columnIndex;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, int columnIndex, JTable table, String iconPath) {
        super(checkBox);
        this.columnIndex = columnIndex;
        this.table = table;
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true); // Para que se vea bien el fondo
        label.setIcon(new ImageIcon(getClass().getResource(iconPath)));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        clicked = true;

        // Para mantener el fondo blanco (o el que uses normalmente)
        label.setBackground(table.getBackground());
        return label;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            String idMascota = table.getValueAt(table.getSelectedRow(), 0).toString();
            if (columnIndex == 7) {
                System.out.println("Editar mascota con ID: " + idMascota);
            } else if (columnIndex == 8) {
                System.out.println("Eliminar mascota con ID: " + idMascota);
            }
        }
        clicked = false;
        return "";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
    }
    
    
    
    //Clase para detectar los iconos de historial medico
    
    class ButtonEditorHistorial extends DefaultCellEditor {
    private final JLabel label = new JLabel();
    private boolean clicked;
    private final int columnIndex;
    private JTable table;

    public ButtonEditorHistorial(JCheckBox checkBox, int columnIndex, JTable table, String iconPath) {
        super(checkBox);
        this.columnIndex = columnIndex;
        this.table = table;
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true); // Para que se vea bien el fondo
        label.setIcon(new ImageIcon(getClass().getResource(iconPath)));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        clicked = true;
        label.setBackground(table.getBackground()); // Mantener fondo original
        return label;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            String idHistorial = table.getValueAt(table.getSelectedRow(), 0).toString();
            if (columnIndex == 5) {
                System.out.println("VER historial con ID: " + idHistorial);
                // Aquí podrías abrir un formulario o mostrar más info
            } else if (columnIndex == 6) {
                System.out.println("EDITAR historial con ID: " + idHistorial);
                // Acción para editar
            } else if (columnIndex == 7) {
                System.out.println("ELIMINAR historial con ID: " + idHistorial);
                // Acción para eliminar
            }
        }
        clicked = false;
        return "";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
    }
    
    
 
    
    
 
    
    
    
    
    
    
    
    
    
    
    
    public void MtdObtenerMascotasPorDni(String dni) {
    try {
        con = cn.getConnection(); // Obtener conexión
        // SQL para obtener todos los nombres de las mascotas asociadas al DNI del propietario
        String sql = "SELECT nombre FROM tbMascota WHERE dni_propietario = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, dni); // Establecer el DNI del propietario

        rs = ps.executeQuery();

        // Verifica cuántos resultados devuelve la consulta
        if (!rs.isBeforeFirst()) {
            System.out.println("No se encontraron mascotas para este DNI.");
        }

        // Limpiar el ComboBox antes de agregar los elementos
        cmbMascota.removeAllItems();

        // Iterar sobre el ResultSet y agregar los nombres de las mascotas al ComboBox
        while (rs.next()) {
            String nombreMascota = rs.getString("nombre");
            cmbMascota.addItem(nombreMascota);
            System.out.println("Agregando mascota: " + nombreMascota); // Depuración
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener las mascotas por DNI: " + e.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (ps != null) ps.close(); } catch (SQLException e) {}
        try { if (con != null) con.close(); } catch (SQLException e) {}
    }
    }
    
    
    
    
    private void txtTelefonoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoPActionPerformed

    private void txtDireccionPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionPActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void btnMascotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMascotasMouseClicked
        // TODO add your handling code here:
        
        btnMascotas.setBackground(new Color(43,121,194,255)); // RGB personalizado
        
        btnHistorial.setBackground(new Color(53,65,77)); // RGB personalizado
        btnPerfil.setBackground(new Color(53,65,77)); // RGB personalizado
        
        jbpPrincipal.setSelectedIndex(0);

        
        
    }//GEN-LAST:event_btnMascotasMouseClicked

    private void btnPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerfilMouseClicked
        // TODO add your handling code here:
        
        
        btnMascotas.setBackground(new Color(53,65,77)); // RGB personalizado
        
        btnHistorial.setBackground(new Color(53,65,77)); // RGB personalizado
        btnPerfil.setBackground(new Color(43,121,194,255)); // RGB personalizado
        
        jbpPrincipal.setSelectedIndex(1);
        
        
    }//GEN-LAST:event_btnPerfilMouseClicked

    private void btnHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistorialMouseClicked
        // TODO add your handling code here:
        
        btnMascotas.setBackground(new Color(53,65,77)); // RGB personalizado
        
        btnHistorial.setBackground(new Color(43,121,194,255)); // RGB personalizado
        btnPerfil.setBackground(new Color(53,65,77)); // RGB personalizado
        
        jbpPrincipal.setSelectedIndex(2);
        
        
    }//GEN-LAST:event_btnHistorialMouseClicked

    private void btnVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVolverMouseClicked
        // TODO add your handling code here:
        
        
        jtbMascotas.setSelectedIndex(0);
        
        LimpiarCamposMascota();
        
        mtdListarMascotas();
        
        
    }//GEN-LAST:event_btnVolverMouseClicked

    private void btnVolverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVolverMouseEntered
        // TODO add your handling code here:
        
        
        btnVolver.setBackground(new Color(132,173,255));
        
        
    }//GEN-LAST:event_btnVolverMouseEntered

    private void btnVolverMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVolverMouseExited
        // TODO add your handling code here:
        
        btnVolver.setBackground(new Color(102,153,255));
        
        
    }//GEN-LAST:event_btnVolverMouseExited

    private void btnMascotaAccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMascotaAccionMouseClicked
        // TODO add your handling code here:


        if(accionMascota == 0)

        {

            clsEMascota objEC=new clsEMascota();//entidad
            clsNMascota objNC=new clsNMascota();//negocios

            objEC.setDniPropietario(txtDNI.getText());  // Asumiendo que txtDniPropietario es el campo para el DNI del propietario
            objEC.setNombre(txtNombre.getText());                   // Asumiendo que txtNombre es el campo para el nombre de la mascota
            objEC.setEspecie(txtEspecie.getText());                 // Asumiendo que txtEspecie es el campo para la especie de la mascota
            objEC.setRaza(txtRaza.getText());                       // Asumiendo que txtRaza es el campo para la raza de la mascota
            objEC.setEdad(Integer.parseInt(txtEdad.getText()));    // Asumiendo que txtEdad es el campo para la edad de la mascota y convertimos el texto a entero
            
            objEC.setSexo(cmbSexo.getSelectedItem().toString());
            objEC.setUrl(urlFoto);

            if(objNC.MtdGuardarMascota(objEC)){
                JOptionPane.showMessageDialog(null, "DATOS GUARDADOS");
                
                LimpiarCamposMascota();
                
                txtID.setText(String.valueOf(objNC.MtdObtenerUltimaId()+1));
                
                mtdListarMascotas();
                
                
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "ERROR");
            }

        }

        else

        {

            clsEMascota objEC = new clsEMascota(); // entidad
            clsNMascota objNC = new clsNMascota(); // negocios

            // Muy importante: necesitas pasar el ID de la mascota que quieres modificar
            objEC.setId(Integer.parseInt(txtID.getText())); // Asegúrate de tener el ID cargado

            objEC.setDniPropietario(txtDNI.getText());
            objEC.setNombre(txtNombre.getText());
            objEC.setEspecie(txtEspecie.getText());
            objEC.setRaza(txtRaza.getText());
            objEC.setEdad(Integer.parseInt(txtEdad.getText()));
            objEC.setSexo(cmbSexo.getSelectedItem().toString());
            objEC.setUrl(urlFoto); // si estás usando una URL para foto

            if (objNC.MtdModificarMascota(objEC)) {
                JOptionPane.showMessageDialog(null, "DATOS MODIFICADOS");
                
                LimpiarCamposMascota();
                mtdListarMascotas();
                
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }

        }        
        
        
        
    }//GEN-LAST:event_btnMascotaAccionMouseClicked

    private void btnMascotaAccionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMascotaAccionMouseEntered
        // TODO add your handling code here:
        
        btnMascotaAccion.setBackground(new Color(132,173,255));
        
    }//GEN-LAST:event_btnMascotaAccionMouseEntered

    private void btnMascotaAccionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMascotaAccionMouseExited
        // TODO add your handling code here:
        
        btnMascotaAccion.setBackground(new Color(102,153,255));
        
    }//GEN-LAST:event_btnMascotaAccionMouseExited

    private void btnAgregarMascotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMascotaMouseEntered
        // TODO add your handling code here:
        
        btnAgregarMascota.setBackground(new Color(132,173,255));
        
    }//GEN-LAST:event_btnAgregarMascotaMouseEntered

    private void btnAgregarMascotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMascotaMouseExited
        // TODO add your handling code here:
        
        btnAgregarMascota.setBackground(new Color(102,153,255));
        
    }//GEN-LAST:event_btnAgregarMascotaMouseExited

    private void btnAgregarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMascotaMouseClicked


        // TODO add your handling code here:
        
        clsNMascota clsNM = new clsNMascota();
        
        jtbMascotas.setSelectedIndex(1);
                
        lblMascota.setText("Agregar Mascota");
                
        btnMascotaAccion.setText("Agregar");
                
        accionMascota=0;
        
        btnMascotaAccion.setIcon(new ImageIcon(getClass().getResource("/Files/nuevo.png")));
        
        txtID.setText(String.valueOf(clsNM.MtdObtenerUltimaId()+1));
        
        
    }//GEN-LAST:event_btnAgregarMascotaMouseClicked

    private void btnSubirImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirImagenMouseClicked
        // TODO add your handling code here:

        // Crear un filtro para solo seleccionar imágenes (por ejemplo, jpg, png)
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg", "bmp");
        fileChooser.setFileFilter(filter);

        // Abrir el cuadro de diálogo para seleccionar un archivo
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Si el usuario seleccionó un archivo
            File selectedFile = fileChooser.getSelectedFile();
            // Obtener la ruta del archivo seleccionado
            String imagePath = selectedFile.getAbsolutePath();

            // Crear una instancia de clsImagesAPI y subir la imagen
            clsImagesAPI imagesAPI = new clsImagesAPI();
            String apiKey = "6472de56c9cc1b31238adc4a33c541e0"; // Asegúrate de poner tu clave API aquí

            String imageUrl = imagesAPI.uploadImage(imagePath, apiKey);

            if (imageUrl != null) {
                // Si la URL se devuelve correctamente
                JOptionPane.showMessageDialog(this, "Imagen subida correctamente.");

                urlFoto = imageUrl;

                // Cargar la imagen desde la URL
                try {
                    // Convertir el String a un objeto URL (usando un nombre diferente para evitar conflictos)
                    URL imageURL = new URL(imageUrl);  // Crear un objeto URL con la URL de la imagen
                    ImageIcon imageIcon = new ImageIcon(imageURL);  // Crear un ImageIcon con la imagen desde la URL

                    // Obtener el tamaño del JLabel
                    int labelWidth = jblFoto.getWidth();
                    int labelHeight = jblFoto.getHeight();

                    // Escalar la imagen para que se ajuste al tamaño del JLabel
                    Image img = imageIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

                    // Establecer la imagen escalada como icono del JLabel
                    jblFoto.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage());
                }
            } else {
                // Si hubo un error (por ejemplo, la subida falló)
                JOptionPane.showMessageDialog(this, "Error al subir la imagen.");
            }

        } else {
            // Si el usuario no seleccionó ningún archivo o canceló
            JOptionPane.showMessageDialog(this, "No se seleccionó ninguna imagen.");
        }        
        
        
    }//GEN-LAST:event_btnSubirImagenMouseClicked

    private void btnSubirImagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirImagenMouseEntered
        // TODO add your handling code here:
        btnSubirImagen.setBackground(new Color(255, 132, 27, 255));
        
        
    }//GEN-LAST:event_btnSubirImagenMouseEntered

    private void btnSubirImagenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirImagenMouseExited
        // TODO add your handling code here:
        
        btnSubirImagen.setBackground(new Color(255,102,0));
        
    }//GEN-LAST:event_btnSubirImagenMouseExited

    private void btnGuardarPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPerfilMouseClicked
        // TODO add your handling code here:
        
        
        clsEPropietario objEC=new clsEPropietario();//entidad
        clsNPropietario objNC=new clsNPropietario();//negocios
        //ingresar los valores para mi objeto
        objEC.setDni(txtDniP.getText());
        objEC.setNombre(txtNombreP.getText());
        objEC.setApellido(txtApellidoP.getText());
        objEC.setDireccion(txtDireccionP.getText());
        objEC.setTelefono(txtTelefonoP.getText());
        objEC.setCorreo(txtCorreoP.getText());
        String contra = new String(txtContraP.getPassword()); // Convertimos directamente el contenido del JPasswordField a String
    
        objEC.setContra(contra);
        


        if(objNC.MtdModificarPropietario(objEC)){
            JOptionPane.showMessageDialog(null, "DATOS GUARDADOS");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
                
        
        
    }//GEN-LAST:event_btnGuardarPerfilMouseClicked

    private void btnGuardarPerfilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPerfilMouseEntered
        // TODO add your handling code here:
        
        
        btnGuardarPerfil.setBackground(new Color(93, 204, 144));
        
    }//GEN-LAST:event_btnGuardarPerfilMouseEntered

    private void btnGuardarPerfilMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPerfilMouseExited
        // TODO add your handling code here:
        
        btnGuardarPerfil.setBackground(new Color(68,182,120));
        
    }//GEN-LAST:event_btnGuardarPerfilMouseExited

    private void btnAgregarRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRegistroMouseClicked

        // TODO add your handling code here:
        
        //clsNMascota clsNM = new clsNMascota();
        
        clsNHistorialMedico clsNH = new clsNHistorialMedico();
        
        jtbHistorial.setSelectedIndex(1);
        
        String dni = frmLogin.proD;

        // Llamar al método que obtiene las mascotas
        MtdObtenerMascotasPorDni(dni);
            
            
        lblHistorial.setText("Agregar Registro");
        
        btnAccionHistorial.setText("Agregar");
        
        
        btnAccionHistorial.setIcon(new ImageIcon(getClass().getResource("/Files/nuevo.png")));
        
        txtIDH.setText(String.valueOf(clsNH.MtdObtenerUltimaId()+1));
        
        
        
    }//GEN-LAST:event_btnAgregarRegistroMouseClicked

    private void btnAgregarRegistroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRegistroMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarRegistroMouseEntered

    private void btnAgregarRegistroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRegistroMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarRegistroMouseExited

    public void LimpiarCamposHistorial(){
        
        
        txtEvento.setText("");
        txtFecha.setText("");
        txtVeterinario.setText("");
        txtDescripcionA.setText("");
    
    
    }
    
    
    
    private void btnAccionHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccionHistorialMouseClicked
        // TODO add your handling code here:
        
        if(accionHistorial == 0)

        {

            clsEHistorialMedico objEC=new clsEHistorialMedico();//entidad
            clsNHistorialMedico objNC=new clsNHistorialMedico();//negocios
            
            
            clsNMascota objNM = new clsNMascota();
                                                  

            objEC.setDni_propietario(frmLogin.proD);  // Asumiendo que txtDniPropietario es el campo para el DNI del propietario
            objEC.setId(Integer.parseInt(txtIDH.getText()));                   // Asumiendo que txtNombre es el campo para el nombre de la mascota
            objEC.setId_mascota(objNM.MtdObtenerIdMascotaPorNombre(cmbMascota.getSelectedItem().toString()));                // Asumiendo que txtEspecie es el campo para la especie de la mascota
            objEC.setTipo_evento(txtEvento.getText());                       // Asumiendo que txtRaza es el campo para la raza de la mascota
            objEC.setFecha(LocalDate.parse(txtFecha.getText()));    // Asumiendo que txtEdad es el campo para la edad de la mascota y convertimos el texto a entero
            objEC.setDescripcion(txtDescripcionA.getText());
            objEC.setVeterinario(txtVeterinario.getText());
            
            

            if(objNC.MtdGuardarHistorialMedico(objEC)){
                JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
                
                LimpiarCamposHistorial();
                
                txtID.setText(String.valueOf(objNC.MtdObtenerUltimaId()+1));
                
                mtdListarHistorialMedico();
                
                
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "ERROR");
            }

        }

        else

        {

            clsEHistorialMedico objEC=new clsEHistorialMedico();//entidad
            clsNHistorialMedico objNC=new clsNHistorialMedico();//negocios

            clsNMascota objNM = new clsNMascota();
                                                  

            objEC.setDni_propietario(frmLogin.proD);  // Asumiendo que txtDniPropietario es el campo para el DNI del propietario
            objEC.setId(Integer.parseInt(txtIDH.getText()));                   // Asumiendo que txtNombre es el campo para el nombre de la mascota
            objEC.setId_mascota(objNM.MtdObtenerIdMascotaPorNombre(cmbMascota.getSelectedItem().toString()));                // Asumiendo que txtEspecie es el campo para la especie de la mascota
            objEC.setTipo_evento(txtEvento.getText());                       // Asumiendo que txtRaza es el campo para la raza de la mascota
            objEC.setFecha(LocalDate.parse(txtFecha.getText()));    // Asumiendo que txtEdad es el campo para la edad de la mascota y convertimos el texto a entero
            objEC.setDescripcion(txtDescripcionA.getText());
            objEC.setVeterinario(txtVeterinario.getText());
            
            

            if (objNC.MtdModificarHistorialMedico(objEC)) {
                JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
                
                LimpiarCamposHistorial();
                mtdListarHistorialMedico();
                
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }

        }  
        

        
        
        
        
    }//GEN-LAST:event_btnAccionHistorialMouseClicked

    private void btnAccionHistorialMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccionHistorialMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAccionHistorialMouseEntered

    private void btnAccionHistorialMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAccionHistorialMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAccionHistorialMouseExited

    private void btnAtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasMouseClicked
        // TODO add your handling code here:
        
        jtbHistorial.setSelectedIndex(0);
        LimpiarCamposHistorial();
        mtdListarHistorialMedico();        
        
        
    }//GEN-LAST:event_btnAtrasMouseClicked

    private void btnAtrasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasMouseEntered

    private void btnAtrasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasMouseExited

    private void btnAtrasRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasRMouseClicked


        // TODO add your handling code here:
        
        
        jtbHistorial.setSelectedIndex(0);
        
    }//GEN-LAST:event_btnAtrasRMouseClicked

    private void btnAtrasRMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasRMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasRMouseEntered

    private void btnAtrasRMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasRMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasRMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPropietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JLabel btnAccionHistorial;
    private javax.swing.JLabel btnAgregarMascota;
    private javax.swing.JLabel btnAgregarRegistro;
    private javax.swing.JLabel btnAtras;
    private javax.swing.JLabel btnAtrasR;
    private javax.swing.JLabel btnGuardarPerfil;
    private javax.swing.JLabel btnHistorial;
    private javax.swing.JLabel btnMascotaAccion;
    private javax.swing.JLabel btnMascotas;
    private javax.swing.JLabel btnPerfil;
    private javax.swing.JLabel btnSubirImagen;
    private javax.swing.JLabel btnVolver;
    private java.awt.Button button1;
    private javax.swing.JComboBox<String> cmbMascota;
    private javax.swing.JComboBox<String> cmbSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jblFoto;
    private javax.swing.JTabbedPane jbpPrincipal;
    private javax.swing.JPanel jpnMascotas;
    private javax.swing.JPanel jpnlHistorial;
    private javax.swing.JPanel jpnlPerfil;
    private javax.swing.JTabbedPane jtbHistorial;
    private javax.swing.JTabbedPane jtbMascotas;
    private javax.swing.JLabel lblAsunto;
    private javax.swing.JTextArea lblDescripcion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHistorial;
    private javax.swing.JLabel lblMascota;
    private javax.swing.JLabel lblNMascota;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVeterinario;
    private javax.swing.JLabel lblVeterinario2;
    private javax.swing.JLabel lblVeterinario3;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTable tblMascotas;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JPasswordField txtContraP;
    private javax.swing.JTextField txtCorreoP;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextArea txtDescripcionA;
    private javax.swing.JTextField txtDireccionP;
    private javax.swing.JTextField txtDniP;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtEspecie;
    private javax.swing.JTextField txtEvento;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDH;
    private javax.swing.JLabel txtIdMascota;
    private javax.swing.JLabel txtIdMascota1;
    private javax.swing.JLabel txtIdMascota2;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextField txtRaza;
    private javax.swing.JTextField txtTelefonoP;
    private javax.swing.JTextField txtVeterinario;
    // End of variables declaration//GEN-END:variables
}
