
package vista;

import controlador.c_cliente;
import controlador.comunes;
import controlador.conexion;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_cliente;
import static vista.v_usuario.i_usuario;

public class v_cliente extends javax.swing.JInternalFrame {
    public static String i_cliente;
    JTextField[] textfields;
    public v_cliente() {
        initComponents();
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        grilla_cliente();
        i_cliente="";
        textfields = new JTextField[]{tf_ci_ruc,tf_nombre_razon,tf_busqueda,tf_telefono};
        ver_datos();
        nuevo_registro();
        b_modificar.setEnabled(false);
        tf_codigo.setVisible(false);
    }
    
    private void agregar_registro(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con);  
        cl.setCodigo(Integer.parseInt(tf_codigo.getText()));
        cl.setCi_ruc(tf_ci_ruc.getText());
        cl.setNombre_razon(tf_nombre_razon.getText().trim().toUpperCase());
        cl.setTelefono(tf_telefono.getText().trim().toUpperCase());
        c.agregar(cl);
        conexion.cerrarConexion(con);
    }
    
    private void nuevo_registro(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con);  
        List<m_cliente> maxC = new ArrayList<m_cliente>();
        maxC=c.maxCodigo(cl);
        for(m_cliente mu : maxC){
            this.tf_codigo.setText(String.valueOf(mu.getCodigo()));
        }        
        this.tf_ci_ruc.requestFocus();
        comunes.limpiar_txt(textfields);
        conexion.cerrarConexion(con);  
    }
    
    private void buscar(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con); 
            cl.setCi_ruc(tf_busqueda.getText());
            cl.setNombre_razon(tf_busqueda.getText().trim().toUpperCase());
            List<m_cliente> verCliente = new ArrayList<m_cliente>();
            verCliente=c.buscarCliente(cl);
            DefaultTableModel tbm = (DefaultTableModel)t_cliente.getModel();
            
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_cliente mc : verCliente){
           tbm.addRow(new String[1]);
           t_cliente.setValueAt(mc.getCodigo(), i, 0);
           t_cliente.setValueAt(mc.getCi_ruc(), i, 1);
           t_cliente.setValueAt(mc.getNombre_razon(), i, 2);
           t_cliente.setValueAt(mc.getTelefono(), i, 3);
           i++;
           }
        
            conexion.cerrarConexion(con);  
    }
    
    private void ver_datos(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con); 
        List<m_cliente> listar = new ArrayList<m_cliente>();
        listar=c.listar();
        DefaultTableModel tbm = (DefaultTableModel)t_cliente.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_cliente mc : listar){
           tbm.addRow(new String[1]);
           t_cliente.setValueAt(mc.getCodigo(), i, 0);
           t_cliente.setValueAt(mc.getCi_ruc(), i, 1);
           t_cliente.setValueAt(mc.getNombre_razon(), i, 2);
           t_cliente.setValueAt(mc.getTelefono(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);  
    }
    
    private void grilla_cliente(){
        t_cliente.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_cliente.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_cliente.getColumnModel().getColumn(0).setCellRenderer(r);
        t_cliente.getColumnModel().getColumn(1).setCellRenderer(r);    
        t_cliente.getColumnModel().getColumn(2).setCellRenderer(r);
        t_cliente.getColumnModel().getColumn(3).setCellRenderer(r);
        t_cliente.setAutoResizeMode(t_cliente.AUTO_RESIZE_OFF); 
        t_cliente.getColumnModel().getColumn(0).setMaxWidth(0);
        t_cliente.getColumnModel().getColumn(0).setMinWidth(0);
        t_cliente.getColumnModel().getColumn(0).setPreferredWidth(0);
        t_cliente.getColumnModel().getColumn(1).setPreferredWidth(120);
        t_cliente.getColumnModel().getColumn(2).setPreferredWidth(247);
        t_cliente.getColumnModel().getColumn(3).setPreferredWidth(130);
        t_cliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private void modificar_registro(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con); 
        cl.setCi_ruc(tf_ci_ruc.getText());
        cl.setNombre_razon(tf_nombre_razon.getText().trim().toUpperCase());
        cl.setTelefono(tf_telefono.getText().trim().toUpperCase());
        cl.setCodigo(Integer.parseInt(tf_codigo.getText()));
        c.modificar(cl);
        conexion.cerrarConexion(con);
    }
    
    private void setear(){
     //JOptionPane.showMessageDialog(null, "doble click");
          DefaultTableModel tbm;  
          int index = t_cliente.getSelectedRow();
          try {
              String codigo, nombre, ci,telefono;
              if(index==-1){
                    JOptionPane.showMessageDialog(this, "SELECCIONE UN PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                }else{
                  tbm= (DefaultTableModel) t_cliente.getModel();
                  codigo=t_cliente.getValueAt(index, 0).toString();
                  ci=t_cliente.getValueAt(index, 1).toString();
                  nombre=t_cliente.getValueAt(index, 2).toString(); 
                  telefono=t_cliente.getValueAt(index,3).toString();
                  tf_codigo.setText(codigo);
                  tf_ci_ruc.setText(ci);
                  tf_nombre_razon.setText(nombre);
                  tf_telefono.setText(telefono);
              }
          } catch (Exception e) {
          }
          this.t_cliente.requestFocus();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        tf_nombre_razon = new javax.swing.JTextField();
        tf_ci_ruc = new javax.swing.JTextField();
        tf_codigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        b_nuevo = new javax.swing.JButton();
        b_agregar = new javax.swing.JButton();
        b_modificar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        tf_telefono = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        tf_busqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_cliente = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("CLIENTE");
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

        tf_nombre_razon.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        tf_ci_ruc.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        tf_codigo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tf_codigo.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("RAZON");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("CI/RUC");

        b_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/bg-nueva-pestana-icono-8859-32.png"))); // NOI18N
        b_nuevo.setText("NUEVO");
        b_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_nuevoActionPerformed(evt);
            }
        });

        b_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/agregar-editar-icono-6607-32.png"))); // NOI18N
        b_agregar.setText("AGREGAR");
        b_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarActionPerformed(evt);
            }
        });

        b_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/cambio-de-registro-de-documentos-una-pluma-para-escribir-texto-icono-5178-32.png"))); // NOI18N
        b_modificar.setText("MODIFICAR");
        b_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_modificarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Telefono");

        tf_telefono.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tf_ci_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tf_nombre_razon, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(b_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addComponent(b_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(b_modificar))
                .addContainerGap(23, Short.MAX_VALUE))
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_nombre_razon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tf_ci_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        tf_busqueda.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_busquedaKeyReleased(evt);
            }
        });

        t_cliente.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "CI/RUC", "NOMBRE/RAZON", "TELEFONO"
            }
        ));
        t_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_clienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_cliente);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("BUSCAR");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(tf_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
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

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        if(tf_ci_ruc.getText().equals("")&&tf_nombre_razon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_ci_ruc.requestFocus();
        }else if(tf_ci_ruc.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE CI/RUC","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
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

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_cliente=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void tf_busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_busquedaKeyReleased
        buscar();
    }//GEN-LAST:event_tf_busquedaKeyReleased

    private void b_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_modificarActionPerformed
         if(tf_ci_ruc.getText().equals("")&&tf_nombre_razon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            tf_ci_ruc.requestFocus();
        }else if(tf_ci_ruc.getText().equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE CI/RUC","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
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

    private void t_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_clienteMouseClicked
      t_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
      if(e.getClickCount()==2){
          setear();
          b_modificar.setEnabled(true);
          b_agregar.setEnabled(false);
      }}
     });
    }//GEN-LAST:event_t_clienteMouseClicked

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        nuevo_registro();
    }//GEN-LAST:event_formInternalFrameActivated

    private void b_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevoActionPerformed
        nuevo_registro();
        b_agregar.setEnabled(true);
        b_modificar.setEnabled(false);
    }//GEN-LAST:event_b_nuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_modificar;
    private javax.swing.JButton b_nuevo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable t_cliente;
    private javax.swing.JTextField tf_busqueda;
    private javax.swing.JTextField tf_ci_ruc;
    private javax.swing.JTextField tf_codigo;
    private javax.swing.JTextField tf_nombre_razon;
    private javax.swing.JTextField tf_telefono;
    // End of variables declaration//GEN-END:variables
}
