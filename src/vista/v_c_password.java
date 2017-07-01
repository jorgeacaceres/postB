/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.c_usuario;
import controlador.conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelo.m_usuario;
import static vista.v_c_password.tf_usu;

/**
 *
 * @author jck
 */
public class v_c_password extends javax.swing.JInternalFrame {
    public static String i_cambiarp;
    public v_c_password() {
        initComponents();
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        i_cambiarp="";
    }

    public void cambiar(){
     Connection con = conexion.abrirConexion();
     m_usuario v = new m_usuario();
     c_usuario c = new c_usuario(con); 
     String usuario="",nuevo="",confir="";
     Integer u=0;
     nuevo=new String(tp_nueva.getPassword());
     confir=new String(tp_confir.getPassword());
     try { 
                    usuario= tf_usu.getText();
                    Statement sentencia = null;
                    ResultSet resultado = null;
                    sentencia = con.createStatement();
                    resultado = sentencia.executeQuery("SELECT id FROM usuario WHERE usuario = '"+usuario+"';");
                    while (resultado.next()){
                        u=resultado.getInt(1);
                    }  
        } catch (Exception e) {
        }
        if (nuevo.equals(confir)){
            v.setContraseña(nuevo);
            v.setId(u);
            c.modificar(v);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Contraseñas Incorrectas");
            this.tp_confir.requestFocus();
        }
     conexion.cerrarConexion(con);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tp_nueva = new javax.swing.JPasswordField();
        tp_confir = new javax.swing.JPasswordField();
        bt_cambiar = new javax.swing.JButton();
        bt_confir = new javax.swing.JButton();

        setTitle("CAMBIAR CONTRASEÑA");
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Usuario");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Contraseña Nueva");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Confirmar Contraseña");

        tf_usu.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tf_usu.setEnabled(false);

        tp_nueva.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        tp_confir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        bt_cambiar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        bt_cambiar.setText("Cambiar");
        bt_cambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cambiarActionPerformed(evt);
            }
        });

        bt_confir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        bt_confir.setText("Salir");
        bt_confir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_confirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(8, 8, 8))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tp_nueva, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(tp_confir)
                    .addComponent(tf_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bt_cambiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_confir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tf_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tp_nueva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tp_confir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(bt_cambiar)
                        .addGap(15, 15, 15)
                        .addComponent(bt_confir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_cambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cambiarActionPerformed
        cambiar();
        i_cambiarp=null; 
        this.dispose();
    }//GEN-LAST:event_bt_cambiarActionPerformed

    private void bt_confirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_confirActionPerformed
        i_cambiarp=null;
        this.dispose();
    }//GEN-LAST:event_bt_confirActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_cambiarp=null;
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cambiar;
    private javax.swing.JButton bt_confir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public static final javax.swing.JTextField tf_usu = new javax.swing.JTextField();
    private javax.swing.JPasswordField tp_confir;
    private javax.swing.JPasswordField tp_nueva;
    // End of variables declaration//GEN-END:variables
}
