package vista;

import controlador.*;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_producto;
import modelo.m_usuario;
import static vista.v_c_password.i_cambiarp;
import static vista.v_compra.i_compra;
import static vista.v_m_producto.i_producto;
import static vista.v_venta.i_venta;

public class v_usuario extends javax.swing.JInternalFrame {

    public static String i_usuario;
    JTextField[] textfields;
    JRadioButton[] jRadioButtons;

    public v_usuario() {
        initComponents();
        grilla_cliente();
        int a = principal.dp_principal.getWidth() - this.getWidth();
        int b = principal.dp_principal.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        i_usuario = "";
        textfields = new JTextField[]{tf_usu, tf_contraseña,tf_busqueda};
        jRadioButtons = new JRadioButton[]{rb_admin, rb_invitado, rb_si, rb_no};
        tf_codigo.setVisible(false);
        nuevo_registro();
        ver_datos();
    }

    private void agregar_registro() {
        String tipo = "", activo_ = "";
        Connection con = conexion.abrirConexion();
        m_usuario u = new m_usuario();
        c_usuario c = new c_usuario(con);
        if (rb_si.isSelected() == true) {
            activo_ = "SI";
        } else if (rb_no.isSelected() == true) {
            activo_ = "NO";
        }
        if (rb_admin.isSelected() == true) {
            tipo = "ADMINISTRADOR";
        } else if (rb_invitado.isSelected() == true) {
            tipo = "INVITADO";
        }
        u.setId(Integer.parseInt(tf_codigo.getText()));
        u.setUsuario(tf_usu.getText());
        u.setContraseña(tf_contraseña.getText());
        u.setActivo(activo_);
        u.setTipo(tipo);
        c.agregar(u);
        conexion.cerrarConexion(con);
    }

    private void nuevo_registro() {
        Connection con = conexion.abrirConexion();
        m_usuario u = new m_usuario();
        c_usuario c = new c_usuario(con);
        List<m_usuario> maxC = new ArrayList<m_usuario>();
        maxC = c.maxCodigo(u);
        for (m_usuario mu : maxC) {
            this.tf_codigo.setText(String.valueOf(mu.getId()));
        }
        this.tf_usu.requestFocus();
        comunes.limpiar_txt(textfields);
        bg_activo.clearSelection();
        bg_tipo.clearSelection();
        conexion.cerrarConexion(con);
    }

    private void buscar() {
        Connection con = conexion.abrirConexion();
        m_usuario u = new m_usuario();
        c_usuario c = new c_usuario(con);
        u.setUsuario(tf_busqueda.getText().trim());
        List<m_usuario> verUsuario = new ArrayList<m_usuario>();
        verUsuario = c.buscarCliente(u);
        DefaultTableModel tbm = (DefaultTableModel) t_usuario.getModel();
        for (int i = tbm.getRowCount() - 1; i >= 0; i--) {
            tbm.removeRow(i);
        }
        int i = 0;
        for (m_usuario mc : verUsuario) {
            tbm.addRow(new String[1]);
            t_usuario.setValueAt(mc.getUsuario(), i, 0);
            t_usuario.setValueAt(mc.getContraseña(), i, 1);
            t_usuario.setValueAt(mc.getActivo(), i, 2);
            t_usuario.setValueAt(mc.getTipo(), i, 3);
            i++;
        }

        conexion.cerrarConexion(con);
    }

    private void ver_datos() {
        Connection con = conexion.abrirConexion();
        m_usuario u = new m_usuario();
        c_usuario c = new c_usuario(con);
        List<m_usuario> listar = new ArrayList<m_usuario>();
        listar = c.listar();
        DefaultTableModel tbm = (DefaultTableModel) t_usuario.getModel();
        for (int i = tbm.getRowCount() - 1; i >= 0; i--) {
            tbm.removeRow(i);
        }
        int i = 0;
        for (m_usuario mu : listar) {
            tbm.addRow(new String[1]);
            t_usuario.setValueAt(mu.getUsuario(), i, 0);
            t_usuario.setValueAt(mu.getContraseña(), i, 1);
            t_usuario.setValueAt(mu.getActivo(), i, 2);
            t_usuario.setValueAt(mu.getTipo(), i, 3);
            i++;
        }
        conexion.cerrarConexion(con);
    }
    
    private void grilla_cliente() {
        t_usuario.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_usuario.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_usuario.getColumnModel().getColumn(0).setCellRenderer(r);
        t_usuario.getColumnModel().getColumn(2).setCellRenderer(r);
        t_usuario.getColumnModel().getColumn(3).setCellRenderer(r);       
        t_usuario.setAutoResizeMode(t_usuario.AUTO_RESIZE_OFF);
        t_usuario.getColumnModel().getColumn(0).setPreferredWidth(180);
        t_usuario.getColumnModel().getColumn(1).setMaxWidth(0);
        t_usuario.getColumnModel().getColumn(1).setMinWidth(0);
        t_usuario.getColumnModel().getColumn(1).setPreferredWidth(0);
        t_usuario.getColumnModel().getColumn(2).setPreferredWidth(100);
        t_usuario.getColumnModel().getColumn(3).setPreferredWidth(200);
        t_usuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void acceder(String usuario, String pass)
    {
       Connection con = conexion.abrirConexion();
       String tipo="";
       String sql="SELECT * FROM usuario WHERE usuario='"+usuario+"' AND contra='"+pass+"'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                tipo=rs.getString("tipo");
            }
            if(tipo.equals("ADMINISTRADOR"))
            {
                agregar_registro();
                txt_usu.setText("");
                txt_pass.setText("");
                validacion.dispose();
                nuevo_registro();
                ver_datos();
            }
            if(tipo.equals("INVITADO"))
            {
               JOptionPane.showMessageDialog(null, "PERMISO DENEGADO", "ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
               
            }
            if((!tipo.equals("ADMINISTRADOR"))&& (!tipo.equals("INVITADO")))
            {
                JOptionPane.showMessageDialog(null, "USUARIO O CONTRASEÑA INCORRECTA ","ATENCION",JOptionPane.ERROR_MESSAGE); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(v_sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.cerrarConexion(con);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_tipo = new javax.swing.ButtonGroup();
        bg_activo = new javax.swing.ButtonGroup();
        validacion = new javax.swing.JDialog();
        txt_usu = new javax.swing.JTextField();
        txt_pass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_cancelar = new javax.swing.JButton();
        btn_aceptar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_usu = new javax.swing.JTextField();
        tf_codigo = new javax.swing.JTextField();
        rb_no = new javax.swing.JRadioButton();
        rb_si = new javax.swing.JRadioButton();
        rb_admin = new javax.swing.JRadioButton();
        rb_invitado = new javax.swing.JRadioButton();
        b_agregar = new javax.swing.JButton();
        b_nuevo = new javax.swing.JButton();
        tf_contraseña = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_usuario = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        jLabel6 = new javax.swing.JLabel();
        tf_busqueda = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jLabel1.setText("Usuario");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jLabel8.setText("Contraseña");

        btn_cancelar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_cancelar.setText("Cancelar");

        btn_aceptar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_aceptar.setText("Aceptar");
        btn_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout validacionLayout = new javax.swing.GroupLayout(validacion.getContentPane());
        validacion.getContentPane().setLayout(validacionLayout);
        validacionLayout.setHorizontalGroup(
            validacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(validacionLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(validacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(validacionLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(validacionLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(validacionLayout.createSequentialGroup()
                        .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_aceptar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        validacionLayout.setVerticalGroup(
            validacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(validacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(validacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(validacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(validacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_aceptar))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("USUARIO");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REGISTRO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("TIPO");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("CONTRASEÑA");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("USUARIO");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("ACTIVO");

        tf_usu.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        tf_codigo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tf_codigo.setEnabled(false);

        bg_activo.add(rb_no);
        rb_no.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        rb_no.setText("NO");

        bg_activo.add(rb_si);
        rb_si.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        rb_si.setText("SI");

        bg_tipo.add(rb_admin);
        rb_admin.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        rb_admin.setText("Administrador");

        bg_tipo.add(rb_invitado);
        rb_invitado.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        rb_invitado.setText("Invitado");

        b_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/agregar-editar-icono-6607-32.png"))); // NOI18N
        b_agregar.setText("AGREGAR");
        b_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarActionPerformed(evt);
            }
        });

        b_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/bg-nueva-pestana-icono-8859-32.png"))); // NOI18N
        b_nuevo.setText("NUEVO");
        b_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_nuevoActionPerformed(evt);
            }
        });

        tf_contraseña.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rb_si)
                        .addGap(18, 18, 18)
                        .addComponent(rb_no)
                        .addGap(26, 26, 26)
                        .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rb_admin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_invitado))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tf_contraseña, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tf_usu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(b_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tf_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(rb_si)
                                .addComponent(rb_no))
                            .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_admin)
                                .addComponent(rb_invitado))
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b_nuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_agregar)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        t_usuario.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_usuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "USUARIO", "CONTRASEÑA", "ACTIVO", "TIPO"
            }
        ));
        t_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_usuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_usuario);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("BUSCAR");

        tf_busqueda.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_busquedaKeyReleased(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(tf_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_usuario = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed

        String usu,contra;
        usu=tf_usu.getText();
        contra=tf_contraseña.getText();
        if (usu.equals("")&&contra.equals("")&&rb_si.isSelected()==false&&rb_no.isSelected()==false&&rb_admin.isSelected()==false&&rb_invitado.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "INGRESE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (usu.equals("")) {
            JOptionPane.showMessageDialog(this, "INGRESE USUARIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (contra.equals("")) {
            JOptionPane.showMessageDialog(this, "INGRESE CONTRASEÑA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (rb_si.isSelected()==false&&rb_no.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "SELECCIONE ACTIVIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (rb_admin.isSelected()==false&&rb_invitado.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "SELECCIONE TIPO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            validacion.setSize(230,165);
            validacion.setLocationRelativeTo(null);
            validacion.setModal(true);
            validacion.setVisible(true);
            validacion.pack(); 
        }
    }//GEN-LAST:event_b_agregarActionPerformed

    private void b_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevoActionPerformed
        nuevo_registro();
        b_agregar.setEnabled(true);
    }//GEN-LAST:event_b_nuevoActionPerformed

    private void tf_busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_busquedaKeyReleased
        buscar();
    }//GEN-LAST:event_tf_busquedaKeyReleased

    private void t_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_usuarioMouseClicked
           
    }//GEN-LAST:event_t_usuarioMouseClicked

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        nuevo_registro();
    }//GEN-LAST:event_formInternalFrameActivated

    private void btn_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aceptarActionPerformed
        String usu=txt_usu.getText().trim();
        String pas=new String(txt_pass.getPassword()).trim();
        if (usu.equals("")==true&&pas.equals("")==true) {
            JOptionPane.showMessageDialog(this, "COMPLETE LOS DATOS","ATENCION",JOptionPane.WARNING_MESSAGE);
        }else if (usu.equals("")==true) {
            JOptionPane.showMessageDialog(this, "INGRESE USUARIO","ATENCION",JOptionPane.WARNING_MESSAGE);;
        }else if (pas.equals("")==true) {
            JOptionPane.showMessageDialog(this, "INGRESE CONTRASEÑA","ATENCION",JOptionPane.WARNING_MESSAGE);;
        }else{
            acceder(usu, pas);
        }
    }//GEN-LAST:event_btn_aceptarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_nuevo;
    private javax.swing.ButtonGroup bg_activo;
    private javax.swing.ButtonGroup bg_tipo;
    private javax.swing.JButton btn_aceptar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rb_admin;
    private javax.swing.JRadioButton rb_invitado;
    private javax.swing.JRadioButton rb_no;
    private javax.swing.JRadioButton rb_si;
    private javax.swing.JTable t_usuario;
    private javax.swing.JTextField tf_busqueda;
    private javax.swing.JTextField tf_codigo;
    private javax.swing.JPasswordField tf_contraseña;
    private javax.swing.JTextField tf_usu;
    private javax.swing.JPasswordField txt_pass;
    private javax.swing.JTextField txt_usu;
    private javax.swing.JDialog validacion;
    // End of variables declaration//GEN-END:variables
}
