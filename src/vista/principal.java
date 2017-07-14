
package vista;

import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import reporte.*;
import static vista.v_m_producto.i_producto;
import static vista.v_proveedor.i_proveedor;
import static vista.v_usuario.i_usuario;
import static vista.v_cliente.i_cliente;
import static vista.v_compra.i_compra;
import static vista.v_venta.i_venta;
import static vista.v_c_password.i_cambiarp;


public class principal extends javax.swing.JFrame {

    public principal() {
        initComponents(); 
        setSize(1367, 730);  
    }
    
    public void sesion(){
        v_sesion i = new v_sesion();
        i.show();
        this.dispose();
        i_producto=null;
        i_proveedor=null;
        i_usuario=null;
        i_cliente=null;
        i_compra=null;
        i_venta=null;
        i_cambiarp=null;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dp_principal = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        mb_principal = new javax.swing.JMenuBar();
        m_archivo = new javax.swing.JMenu();
        jm_cambiar_password = new javax.swing.JMenuItem();
        jm_cerrar_sesion = new javax.swing.JMenuItem();
        jm_salir = new javax.swing.JMenuItem();
        m_mantenimiento = new javax.swing.JMenu();
        jm_producto = new javax.swing.JMenuItem();
        jm_proveedor = new javax.swing.JMenuItem();
        jm_usuario = new javax.swing.JMenuItem();
        jm_cliente = new javax.swing.JMenuItem();
        m_movimiento = new javax.swing.JMenu();
        jm_compra = new javax.swing.JMenuItem();
        jm_venta = new javax.swing.JMenuItem();
        m_busqueda = new javax.swing.JMenu();
        jm_b_producto = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        m_reporte = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jm_stock = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jm_compra_r = new javax.swing.JMenuItem();
        jm_b_venta = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        javax.swing.GroupLayout dp_principalLayout = new javax.swing.GroupLayout(dp_principal);
        dp_principal.setLayout(dp_principalLayout);
        dp_principalLayout.setHorizontalGroup(
            dp_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 769, Short.MAX_VALUE)
        );
        dp_principalLayout.setVerticalGroup(
            dp_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 629, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("USUSARIO:");

        l_usuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        m_archivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/abrir-carpeta-de-color-naranja-icono-3995-32.png"))); // NOI18N
        m_archivo.setText("Archivo");
        m_archivo.setEnabled(false);

        jm_cambiar_password.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/contrasena.png"))); // NOI18N
        jm_cambiar_password.setText("Cambiar Contraseña");
        jm_cambiar_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_cambiar_passwordActionPerformed(evt);
            }
        });
        m_archivo.add(jm_cambiar_password);

        jm_cerrar_sesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/logout-icon.png"))); // NOI18N
        jm_cerrar_sesion.setText("Cerrar Sesión");
        jm_cerrar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_cerrar_sesionActionPerformed(evt);
            }
        });
        m_archivo.add(jm_cerrar_sesion);

        jm_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/salir-de-mi-perfil-icono-3964-32.png"))); // NOI18N
        jm_salir.setText("Salir");
        jm_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_salirActionPerformed(evt);
            }
        });
        m_archivo.add(jm_salir);

        mb_principal.add(m_archivo);

        m_mantenimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/developing-a-record-orange-icone-7931-32.png"))); // NOI18N
        m_mantenimiento.setText("Mantenimientos");
        m_mantenimiento.setEnabled(false);

        jm_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/favoritos-bebidas-texto-web20-icono-6059-32.png"))); // NOI18N
        jm_producto.setText("Articulos");
        jm_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_productoActionPerformed(evt);
            }
        });
        m_mantenimiento.add(jm_producto);

        jm_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/adelante-icono-4210-32.png"))); // NOI18N
        jm_proveedor.setText("Proveedores");
        jm_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_proveedorActionPerformed(evt);
            }
        });
        m_mantenimiento.add(jm_proveedor);

        jm_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/los-usuarios-del-sistema-icono-8916-32.png"))); // NOI18N
        jm_usuario.setText("Usuarios");
        jm_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_usuarioActionPerformed(evt);
            }
        });
        m_mantenimiento.add(jm_usuario);

        jm_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/anadir-usuario-icono-4000-32.png"))); // NOI18N
        jm_cliente.setText("Clientes");
        jm_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_clienteActionPerformed(evt);
            }
        });
        m_mantenimiento.add(jm_cliente);

        mb_principal.add(m_mantenimiento);

        m_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/los-documentos-de-la-carpeta-de-una-pluma-para-escribir-icono-5544-32.png"))); // NOI18N
        m_movimiento.setText("Movimientos");
        m_movimiento.setEnabled(false);

        jm_compra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/anadir-carrito-de-la-tienda-en-linea-de-comercio-electronico-icono-7379-32.png"))); // NOI18N
        jm_compra.setText("Compra");
        jm_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_compraActionPerformed(evt);
            }
        });
        m_movimiento.add(jm_compra);

        jm_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/la-lucha-contra-la-caja-registradora-icono-4028-32.png"))); // NOI18N
        jm_venta.setText("Venta");
        jm_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_ventaActionPerformed(evt);
            }
        });
        m_movimiento.add(jm_venta);

        mb_principal.add(m_movimiento);

        m_busqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N
        m_busqueda.setText("BUSQUEDA");
        m_busqueda.setEnabled(false);

        jm_b_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/favoritos-bebidas-texto-web20-icono-6059-32.png"))); // NOI18N
        jm_b_producto.setText("ARTICULO");
        jm_b_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_b_productoActionPerformed(evt);
            }
        });
        m_busqueda.add(jm_b_producto);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/la-lucha-contra-la-caja-registradora-icono-4028-32.png"))); // NOI18N
        jMenuItem1.setText("VENTA");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        m_busqueda.add(jMenuItem1);

        mb_principal.add(m_busqueda);

        m_reporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/SEO-icon.png"))); // NOI18N
        m_reporte.setText("Reportes");
        m_reporte.setEnabled(false);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/favoritos-bebidas-texto-web20-icono-6059-32.png"))); // NOI18N
        jMenu1.setText("PRODUCTO");

        jm_stock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/favoritos-bebidas-texto-web20-icono-6059-32.png"))); // NOI18N
        jm_stock.setText("STOCK");
        jm_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_stockActionPerformed(evt);
            }
        });
        jMenu1.add(jm_stock);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/favoritos-bebidas-texto-web20-icono-6059-32.png"))); // NOI18N
        jMenuItem2.setText("PRODUCTO");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        m_reporte.add(jMenu1);

        jm_compra_r.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/anadir-carrito-de-la-tienda-en-linea-de-comercio-electronico-icono-7379-32.png"))); // NOI18N
        jm_compra_r.setText("COMPRA");
        jm_compra_r.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_compra_rActionPerformed(evt);
            }
        });
        m_reporte.add(jm_compra_r);

        jm_b_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/la-lucha-contra-la-caja-registradora-icono-4028-32.png"))); // NOI18N
        jm_b_venta.setText("VENTA");
        jm_b_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_b_ventaActionPerformed(evt);
            }
        });
        m_reporte.add(jm_b_venta);

        mb_principal.add(m_reporte);

        setJMenuBar(mb_principal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dp_principal)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dp_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(l_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jm_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_productoActionPerformed
       String producto =v_m_producto.i_producto;
       if(producto==null){
       v_m_producto mp = new v_m_producto();
       dp_principal.add(mp);
       mp.show();
       }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_productoActionPerformed

    private void jm_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_compraActionPerformed
        String compra =v_compra.i_compra;
        if (compra==null) {
            v_compra c = new v_compra();
            dp_principal.add(c);
            c.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_compraActionPerformed

    private void jm_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_ventaActionPerformed
        String venta = v_venta.i_venta;
        if (venta==null) {
            v_venta v = new v_venta();
            dp_principal.add(v);
            v.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }   
    }//GEN-LAST:event_jm_ventaActionPerformed

    private void jm_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_salirActionPerformed
        dispose();
    }//GEN-LAST:event_jm_salirActionPerformed

    private void jm_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_usuarioActionPerformed
        String usuario = v_usuario.i_usuario;
        if (usuario==null) {
             v_usuario u = new v_usuario();
            dp_principal.add(u);
            u.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_usuarioActionPerformed

    private void jm_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_clienteActionPerformed
        String cliente = v_cliente.i_cliente;
        if (cliente==null) {
             v_cliente cl = new v_cliente();
            dp_principal.add(cl);
            cl.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_clienteActionPerformed

    private void jm_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_proveedorActionPerformed
        String proveedor = v_proveedor.i_proveedor;
        if (proveedor==null) {
             v_proveedor pr = new v_proveedor();
            dp_principal.add(pr);
            pr.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_proveedorActionPerformed

    private void jm_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_stockActionPerformed
        try {
            reporte  reportes = new reporte();
            reportes.reporte_stock();
        } catch (JRException e) {
            java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (SQLException e) {
            java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jm_stockActionPerformed

    private void jm_cerrar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_cerrar_sesionActionPerformed
      sesion();
    }//GEN-LAST:event_jm_cerrar_sesionActionPerformed

    private void jm_compra_rActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_compra_rActionPerformed
         String compra_fecha = v_r_compra.i_compra_fecha;
        if (compra_fecha==null) {
             v_r_compra cc = new v_r_compra();
            dp_principal.add(cc);
            cc.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_compra_rActionPerformed

    private void jm_b_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_b_ventaActionPerformed
         String venta_fecha = v_r_venta.i_venta_fecha;
        if (venta_fecha==null) {
             v_r_venta vv = new v_r_venta();
            dp_principal.add(vv);
            vv.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_b_ventaActionPerformed

    private void jm_b_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_b_productoActionPerformed
        String producto_b = v_b_producto.i_b_producto;
        if (producto_b==null) {
             v_b_producto vp = new v_b_producto();
            dp_principal.add(vp);
            vp.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_b_productoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void jm_cambiar_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_cambiar_passwordActionPerformed
         String cambiar = v_c_password.i_cambiarp;
        if (cambiar==null) {
             v_c_password cp = new v_c_password();
            dp_principal.add(cp);
            cp.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jm_cambiar_passwordActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String venta_b = v_b_venta.i_b_venta;
        if (venta_b==null) {
            v_b_venta vb = new v_b_venta();
            dp_principal.add(vb);
            vb.show();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
          try {
            reporte  reportes = new reporte();
            reportes.reporte_productos();
        } catch (JRException e) {
            java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (SQLException e) {
            java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane dp_principal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jm_b_producto;
    private javax.swing.JMenuItem jm_b_venta;
    private javax.swing.JMenuItem jm_cambiar_password;
    private javax.swing.JMenuItem jm_cerrar_sesion;
    public javax.swing.JMenuItem jm_cliente;
    public javax.swing.JMenuItem jm_compra;
    private javax.swing.JMenuItem jm_compra_r;
    public javax.swing.JMenuItem jm_producto;
    public javax.swing.JMenuItem jm_proveedor;
    private javax.swing.JMenuItem jm_salir;
    private javax.swing.JMenuItem jm_stock;
    public javax.swing.JMenuItem jm_usuario;
    public javax.swing.JMenuItem jm_venta;
    public static final javax.swing.JLabel l_usuario = new javax.swing.JLabel();
    public javax.swing.JMenu m_archivo;
    public javax.swing.JMenu m_busqueda;
    public javax.swing.JMenu m_mantenimiento;
    public javax.swing.JMenu m_movimiento;
    public javax.swing.JMenu m_reporte;
    private javax.swing.JMenuBar mb_principal;
    // End of variables declaration//GEN-END:variables
}
