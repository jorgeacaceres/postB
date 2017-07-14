
package vista;

import controlador.*;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_proveedor;

public class v_proveedor extends javax.swing.JInternalFrame {
    public static String i_proveedor;
    JTextField[] textfields;
    public v_proveedor() {
        initComponents();
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        grilla_proveedor();
        i_proveedor="";
        textfields = new JTextField[]{tf_ci_ruc,tf_nombre_razon,tf_telefono};
        ver_datos();
        nuevo_registro();
        b_modificar.setEnabled(false);
        tf_codigo.setVisible(false);
    }
    
    private void agregar_registro(){
        Connection con = conexion.abrirConexion();
        m_proveedor pr = new m_proveedor();
        c_proveedor c = new c_proveedor(con);  
        pr.setCodigo(Integer.parseInt(tf_codigo.getText()));
        pr.setCi_ruc(tf_ci_ruc.getText());
        pr.setNombre_razon(tf_nombre_razon.getText().trim().toUpperCase());
        pr.setTelefono(tf_telefono.getText()); 
        c.agregar(pr);
        conexion.cerrarConexion(con);
    }
    
    private void nuevo_registro(){
        Connection con = conexion.abrirConexion();
        m_proveedor pr = new m_proveedor();
        c_proveedor c = new c_proveedor(con);  
        List<m_proveedor> maxC = new ArrayList<m_proveedor>();
        maxC=c.maxCodigo(pr);
        for(m_proveedor mu : maxC){
            this.tf_codigo.setText(String.valueOf(mu.getCodigo()));
        }        
        this.tf_ci_ruc.requestFocus();
        comunes.limpiar_txt(textfields);
        conexion.cerrarConexion(con);  
    }
    
    private void buscar() {
        Connection con = conexion.abrirConexion();
        m_proveedor pr = new m_proveedor();
        c_proveedor c = new c_proveedor(con);  
        pr.setNombre_razon(tf_busqueda.getText().trim().toUpperCase());
        pr.setCi_ruc(tf_busqueda.getText().trim().toUpperCase());
        List<m_proveedor> verProveedor = new ArrayList<m_proveedor>();
        verProveedor = c.buscarProveedor(pr);
        DefaultTableModel tbm = (DefaultTableModel) t_proveedor.getModel();
        for (int i = tbm.getRowCount() - 1; i >= 0; i--) {
            tbm.removeRow(i);
        }
        int i = 0;
        for (m_proveedor mc : verProveedor) {
            tbm.addRow(new String[1]);
            t_proveedor.setValueAt(mc.getCodigo(), i, 0);
            t_proveedor.setValueAt(mc.getCi_ruc(), i, 1);
            t_proveedor.setValueAt(mc.getNombre_razon(), i, 2);
            t_proveedor.setValueAt(mc.getTelefono(), i, 3);
            i++;
        }
        conexion.cerrarConexion(con);
    }

    private void ver_datos() {
        Connection con = conexion.abrirConexion();
        m_proveedor pr = new m_proveedor();
        c_proveedor c = new c_proveedor(con);  
        List<m_proveedor> listar = new ArrayList<m_proveedor>();
        listar = c.listar();
        DefaultTableModel tbm = (DefaultTableModel) t_proveedor.getModel();
        for (int i = tbm.getRowCount() - 1; i >= 0; i--) {
            tbm.removeRow(i);
        }
        int i = 0;
        for (m_proveedor mu : listar) {
            tbm.addRow(new String[1]);
            t_proveedor.setValueAt(mu.getCodigo(), i, 0);
            t_proveedor.setValueAt(mu.getCi_ruc(), i, 1);
            t_proveedor.setValueAt(mu.getNombre_razon(), i, 2);
            t_proveedor.setValueAt(mu.getTelefono(), i, 3);
            i++;
        }
        conexion.cerrarConexion(con);
    }
    
    private void grilla_proveedor() {
        t_proveedor.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_proveedor.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_proveedor.getColumnModel().getColumn(0).setCellRenderer(r);
        t_proveedor.getColumnModel().getColumn(1).setCellRenderer(r);
        t_proveedor.getColumnModel().getColumn(2).setCellRenderer(r);
        t_proveedor.getColumnModel().getColumn(3).setCellRenderer(r);
        t_proveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t_proveedor.getColumnModel().getColumn(0).setMaxWidth(0);
        t_proveedor.getColumnModel().getColumn(0).setMinWidth(0);
        t_proveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
        t_proveedor.getColumnModel().getColumn(1).setPreferredWidth(140);
        t_proveedor.getColumnModel().getColumn(2).setPreferredWidth(280);
        t_proveedor.getColumnModel().getColumn(3).setPreferredWidth(127);
        t_proveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void modificar_registro() {
        Connection con = conexion.abrirConexion();
        m_proveedor pr = new m_proveedor();
        c_proveedor c = new c_proveedor(con);  
        pr.setCi_ruc(tf_ci_ruc.getText());
        pr.setNombre_razon(tf_nombre_razon.getText().trim().toUpperCase());
        pr.setTelefono(tf_telefono.getText());
        pr.setCodigo(Integer.parseInt(tf_codigo.getText()));
        c.modificar(pr);
        conexion.cerrarConexion(con);
    }

    private void setear() {
        DefaultTableModel tbm;
        int index = t_proveedor.getSelectedRow();
        try {
            String codigo, ci_ruc, nombre_razon, telefono;
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "SELECCIONE UN PRODUCTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                tbm = (DefaultTableModel) t_proveedor.getModel();
                codigo = t_proveedor.getValueAt(index, 0).toString();
                ci_ruc = t_proveedor.getValueAt(index, 1).toString();
                nombre_razon = t_proveedor.getValueAt(index, 2).toString();
                telefono = t_proveedor.getValueAt(index, 3).toString();
                tf_codigo.setText(codigo);
                tf_ci_ruc.setText(ci_ruc);
                tf_nombre_razon.setText(nombre_razon);
                tf_telefono.setText(telefono);
            }
        } catch (HeadlessException e) {
        }
        this.tf_ci_ruc.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_tipo = new javax.swing.ButtonGroup();
        bg_activo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_telefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tf_codigo = new javax.swing.JTextField();
        tf_ci_ruc = new javax.swing.JTextField();
        tf_nombre_razon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        b_modificar = new javax.swing.JButton();
        b_agregar = new javax.swing.JButton();
        b_nuevo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_proveedor = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        jLabel6 = new javax.swing.JLabel();
        tf_busqueda = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("PROVEEDOR");
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

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("CI/RUC");

        tf_telefono.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("TELEFONO");

        tf_codigo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tf_codigo.setEnabled(false);

        tf_ci_ruc.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        tf_nombre_razon.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("NOMBRE/RAZON");

        b_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/cambio-de-registro-de-documentos-una-pluma-para-escribir-texto-icono-5178-32.png"))); // NOI18N
        b_modificar.setText("MODIFICAR");
        b_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_modificarActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel2)))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_nombre_razon, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_ci_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tf_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b_nuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_agregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_modificar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tf_ci_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tf_nombre_razon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        t_proveedor.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_proveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "  COD", " CI/RUC", " NOMBRE/RAZON", " TELEFONO"
            }
        ));
        t_proveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_proveedorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_proveedor);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("BUSCAR");

        tf_busqueda.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_busquedaActionPerformed(evt);
            }
        });
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(tf_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_proveedor=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        if(tf_ci_ruc.getText().equals("")&&tf_nombre_razon.getText().equals("")&&tf_telefono.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_ci_ruc.requestFocus();
        }else if(tf_ci_ruc.getText().equals("")){
            JOptionPane.showMessageDialog(this, "IINGRESE CI/RUC","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_ci_ruc.requestFocus();
        }else if(tf_nombre_razon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE NOMBRE/RAZON","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_nombre_razon.requestFocus();
        }else if(tf_telefono.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE TELEFONO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_telefono.requestFocus();
        }else{    
            agregar_registro();
            nuevo_registro();
            ver_datos();
            b_modificar.setEnabled(false);
        }
    }//GEN-LAST:event_b_agregarActionPerformed

    private void tf_busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_busquedaKeyReleased
        buscar();
    }//GEN-LAST:event_tf_busquedaKeyReleased

    private void b_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevoActionPerformed
        nuevo_registro();
        b_agregar.setEnabled(true);
        b_modificar.setEnabled(false);
    }//GEN-LAST:event_b_nuevoActionPerformed

    private void b_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_modificarActionPerformed
      if(tf_ci_ruc.getText().equals("")&&tf_nombre_razon.getText().equals("")&&tf_telefono.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_ci_ruc.requestFocus();
        }else if(tf_ci_ruc.getText().equals("")){
            JOptionPane.showMessageDialog(this, "IINGRESE CI/RUC","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_ci_ruc.requestFocus();
        }else if(tf_nombre_razon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE NOMBRE/RAZON","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_nombre_razon.requestFocus();
        }else if(tf_telefono.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE TELEFONO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_telefono.requestFocus();
        }else{    
            modificar_registro();
            nuevo_registro();
            ver_datos();
            b_agregar.setEnabled(true);
            b_modificar.setEnabled(false);
        }
    }//GEN-LAST:event_b_modificarActionPerformed

    private void t_proveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_proveedorMouseClicked
        t_proveedor.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
        if(e.getClickCount()==2){
            setear();
            b_modificar.setEnabled(true);
            b_agregar.setEnabled(false);
         }}
        });
    }//GEN-LAST:event_t_proveedorMouseClicked

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        nuevo_registro();
    }//GEN-LAST:event_formInternalFrameActivated

    private void tf_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_busquedaActionPerformed
    }//GEN-LAST:event_tf_busquedaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_modificar;
    private javax.swing.JButton b_nuevo;
    private javax.swing.ButtonGroup bg_activo;
    private javax.swing.ButtonGroup bg_tipo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable t_proveedor;
    private javax.swing.JTextField tf_busqueda;
    private javax.swing.JTextField tf_ci_ruc;
    private javax.swing.JTextField tf_codigo;
    private javax.swing.JTextField tf_nombre_razon;
    private javax.swing.JTextField tf_telefono;
    // End of variables declaration//GEN-END:variables
}
