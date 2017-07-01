package vista;

import controlador.c_producto;
import controlador.conexion;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_producto;

public class v_b_producto extends javax.swing.JInternalFrame {
    public static String i_b_producto;
    public v_b_producto() {
        initComponents();
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        i_b_producto="";
        grilla();
        ver_datos();
    }
    private void buscar(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        if (rb_codigo.isSelected()==true) {
            p.setCodigo(Integer.parseInt(t_buscar.getText()));
            List<m_producto> verCodigo = new ArrayList<m_producto>();
            verCodigo=c.listarCodigo(p);
            DefaultTableModel tbm = (DefaultTableModel)t_product.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : verCodigo){
           tbm.addRow(new String[1]);
           t_product.setValueAt(mp.getCodigo(), i, 0);
           t_product.setValueAt(mp.getProducto(), i, 1);
           t_product.setValueAt(mp.getPrecio_venta().intValue(), i, 2);
           t_product.setValueAt(mp.getStock(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);  
        }else if (rb_producto.isSelected()==true) {
            p.setProducto(t_buscar.getText().trim().toUpperCase());
            List<m_producto> verNombre = new ArrayList<m_producto>();
            verNombre=c.listarNombre(p);
            DefaultTableModel tbm = (DefaultTableModel)t_product.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : verNombre){
           tbm.addRow(new String[1]);
           t_product.setValueAt(mp.getCodigo(), i, 0);
           t_product.setValueAt(mp.getProducto(), i, 1);
           t_product.setValueAt(mp.getPrecio_venta().intValue(), i, 2);
           t_product.setValueAt(mp.getStock(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);
        }
    }
    
    private void ver_datos(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        List<m_producto> listar = new ArrayList<m_producto>();
        listar=c.listar();
        DefaultTableModel tbm = (DefaultTableModel)t_product.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : listar){
           tbm.addRow(new String[1]);
           t_product.setValueAt(mp.getCodigo(), i, 0);
           t_product.setValueAt(mp.getProducto(), i, 1);
           t_product.setValueAt(mp.getPrecio_venta().intValue(), i, 2);
           t_product.setValueAt(mp.getStock(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);  
    }
     private void grilla(){
        t_product.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_product.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_product.getColumnModel().getColumn(0).setCellRenderer(r);
        t_product.getColumnModel().getColumn(1).setCellRenderer(r); 
        t_product.getColumnModel().getColumn(2).setCellRenderer(r); 
        t_product.getColumnModel().getColumn(3).setCellRenderer(r);
        t_product.setAutoResizeMode(t_product.AUTO_RESIZE_OFF);   
        t_product.getColumnModel().getColumn(0).setPreferredWidth(70);
        t_product.getColumnModel().getColumn(1).setPreferredWidth(240);
        t_product.getColumnModel().getColumn(2).setPreferredWidth(100);
        t_product.getColumnModel().getColumn(3).setPreferredWidth(70);
        t_product.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_product = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        t_buscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rb_codigo = new javax.swing.JRadioButton();
        rb_producto = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        t_product.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        t_product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "ARTICULO", "PRECIO VENTA", "STOCK"
            }
        ));
        jScrollPane1.setViewportView(t_product);

        t_buscar.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        t_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_buscarKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_buscarKeyReleased(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        buttonGroup1.add(rb_codigo);
        rb_codigo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_codigo.setText("CODIGO");
        rb_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_codigoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_producto);
        rb_producto.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_producto.setText("ARTICULO");
        rb_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_productoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel8)
                        .addGap(29, 29, 29)
                        .addComponent(t_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(rb_codigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rb_producto))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(t_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rb_codigo)
                        .addComponent(rb_producto))
                    .addComponent(jLabel8))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscarKeyTyped
        if (rb_codigo.isSelected()==true) {
            char digito = evt.getKeyChar();
            if(!Character.isDigit(digito)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_t_buscarKeyTyped

    private void t_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscarKeyReleased
        buscar();
    }//GEN-LAST:event_t_buscarKeyReleased

    private void rb_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_codigoActionPerformed
        t_buscar.setText("");
        t_buscar.requestFocus();
    }//GEN-LAST:event_rb_codigoActionPerformed

    private void rb_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_productoActionPerformed
        t_buscar.setText("");
        t_buscar.requestFocus();
    }//GEN-LAST:event_rb_productoActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_b_producto=null;
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rb_codigo;
    private javax.swing.JRadioButton rb_producto;
    private javax.swing.JTextField t_buscar;
    private javax.swing.JTable t_product;
    // End of variables declaration//GEN-END:variables
}