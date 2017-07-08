
package vista;

import controlador.c_usuario;
import controlador.comunes;
import controlador.conexion;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.m_usuario;
import static vista.v_m_producto.i_producto;
import static vista.v_venta.i_venta;
import static vista.v_compra.i_compra;
import static vista.v_c_password.i_cambiarp;

public class v_sesion extends javax.swing.JFrame {
    JTextField[] textfields;
    public v_sesion() {
        initComponents();
        this.setLocationRelativeTo(null);
        textfields = new JTextField[]{tf_usu,pf_pass};
    }
    private Connection conn;
    
    public v_sesion (Connection conn){
        this.conn=conn;
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
                   this.setVisible(false);
                   principal p = new principal();
                   
                   v_c_password cp = new v_c_password();
                   i_cambiarp=null;
                   cp.tf_usu.setText(usuario);
                   
                   v_venta v = new v_venta();
                   i_venta=null;
                   v.tf_usuario.setText(usuario);
                   
                   v_compra c = new v_compra();
                   i_compra=null;
                   c.tf_usuario.setText(usuario);
                   
                   p.show();
                   p.l_usuario.setText(usuario);
                   p.m_archivo.setEnabled(true);
                   p.m_mantenimiento.setEnabled(true);
                   p.m_movimiento.setEnabled(true);
                   p.m_reporte.setEnabled(true); 
                   p.m_busqueda.setEnabled(true);
                   
                   v_m_producto pr = new v_m_producto();
                   i_producto=null;
                   pr.tf_usuario.setText(usuario);
            }
            if(tipo.equals("INVITADO"))
            {
                   this.setVisible(false);
                   principal p = new principal();
                   v_venta v = new v_venta();
                   i_venta=null;
                   v.tf_usuario.setText(usuario);
                   
                   v_compra c = new v_compra();
                   i_compra=null;
                   c.tf_usuario.setText(usuario);
                   
                   p.show();
                   p.l_usuario.setText(usuario);
                   p.m_archivo.setEnabled(true);
                   p.m_mantenimiento.setEnabled(true);
                   p.m_movimiento.setEnabled(true);
                   p.m_reporte.setEnabled(true);
                   p.m_busqueda.setEnabled(true);
                   p.jm_usuario.setVisible(false);
                   v_m_producto pr = new v_m_producto();
                   i_producto=null;
                   pr.tf_usuario.setText(usuario);
                   pr.t_stock.setEnabled(false);
            }
            if((!tipo.equals("ADMINISTRADOR"))&& (!tipo.equals("INVITADO")))
            {
                JOptionPane.showMessageDialog(this, "USUARIO O CONTRASEÑA INCORRECTA ","ATENCION",JOptionPane.ERROR_MESSAGE);
                comunes.limpiar_txt(textfields);
                this.tf_usu.requestFocus();  
            }
        } catch (SQLException ex) {
            Logger.getLogger(v_sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.cerrarConexion(con);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tf_usu = new javax.swing.JTextField();
        pf_pass = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/usuarios-icono-8209-96.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel1.setText("USUARIO");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel2.setText("CONTRASEÑA");

        tf_usu.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        tf_usu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_usuKeyTyped(evt);
            }
        });

        pf_pass.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        pf_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pf_passKeyTyped(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/prohibe-icono-5792-32.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/aceptar-cheque-verde-ok-si-icono-6092-32.png"))); // NOI18N
        jButton1.setText("INGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(pf_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(55, 55, 55)
                                .addComponent(tf_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(27, 27, 27)
                        .addComponent(jButton2)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tf_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(pf_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String usu=tf_usu.getText().trim();
        String pas=new String(pf_pass.getPassword()).trim();
        if (usu.equals("")==true&&pas.equals("")==true) {
            JOptionPane.showMessageDialog(this, "COMPLETE LOS DATOS","ATENCION",JOptionPane.WARNING_MESSAGE);
        }else if (usu.equals("")==true) {
            JOptionPane.showMessageDialog(this, "INGRESE USUARIO","ATENCION",JOptionPane.WARNING_MESSAGE);;
        }else if (pas.equals("")==true) {
            JOptionPane.showMessageDialog(this, "INGRESE CONTRASEÑA","ATENCION",JOptionPane.WARNING_MESSAGE);;
        }else{
            acceder(usu, pas);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_usuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_usuKeyTyped
        String usu=tf_usu.getText().trim();
        String pas=new String(pf_pass.getPassword()).trim();
        char tecla=evt.getKeyChar();
        if(tecla==KeyEvent.VK_ENTER){
            acceder(usu,pas);
        }else if(tecla==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_tf_usuKeyTyped

    private void pf_passKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pf_passKeyTyped
        String usu=tf_usu.getText().trim();
        String pas=new String(pf_pass.getPassword()).trim();
        char tecla=evt.getKeyChar();
        if(tecla==KeyEvent.VK_ENTER){
            acceder(usu,pas);
        }else if(tecla==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_pf_passKeyTyped

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
            java.util.logging.Logger.getLogger(v_sesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(v_sesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(v_sesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(v_sesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new v_sesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField pf_pass;
    private javax.swing.JTextField tf_usu;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
