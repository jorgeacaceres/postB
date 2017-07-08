
package vista;

import controlador.c_r_compra;
import controlador.c_r_venta;
import controlador.conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_r_compra;
import modelo.m_r_venta;
import net.sf.jasperreports.engine.JRException;
import reporte.reporte;

public class v_r_compra extends javax.swing.JInternalFrame {
    JTable tabla;
    ResultSet rs;
    public static String i_compra_fecha;
    DefaultTableModel tbm;
    public v_r_compra() {
        initComponents();
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        i_compra_fecha="";
        nuevo();
    }
    private void nuevo(){
        Date fa = new Date();
        jdt1.setDate(fa);
        jdt2.setDate(fa);
    }
     public void agregar_fecha(){
        Connection con = conexion.abrirConexion();
        m_r_compra rc = new m_r_compra();
        c_r_compra c = new c_r_compra(con);   
        String mes="",año="",fecha="";
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha1,fecha2;
                    String f1,f2;
                    fecha1 =jdt1.getDate();
                    fecha2 =jdt2.getDate();
                    f1= df.format(fecha1);
                    f2= df.format(fecha2);
                     rc.setF_inicio(f1);
                     rc.setF_fin(f2);
                     c.agregar_fecha(rc);           
    }

   /* public void buscar(){
         try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date f,f1,f2;
            Double s=0d;
            f1 =jdt1.getDate();
            f2 =jdt2.getDate();
            String fecha1,fecha2;
            fecha1= df.format(f1);
            fecha2= df.format(f2);
            
            String fecha="",producto="",cantidad="",sub="";
             Connection con = conexion.abrirConexion();
             Statement st=con.createStatement(); 
                    String sql=("select v.f,p.nombre,sum(vd.cantidad)as cantidad, sum(subtotal)as sub " +
                                "from venta_detalle as vd " +
                                "inner join producto as p " +
                                "on vd.codigo_producto=p.codigo " +
                                "inner join venta as v " +
                                "on vd.id=v.id " +
                                "where v.f between '"+fecha1+"' and '"+fecha2+"' " +
                                "group by v.f,p.nombre " +
                                "order by v.f desc;");
            ResultSet rs= st.executeQuery(sql);
            while(rs.next()){
                DateFormat ff = new SimpleDateFormat("dd-MMM-yyyy");
                f=rs.getDate(1);
                producto=rs.getString(2);
                cantidad=rs.getString(3);
                fecha=ff.format(f);
                s=rs.getDouble(4);
                sub=String.valueOf(s);
                tbm=(DefaultTableModel) t_buscar.getModel();
                String fila[]={fecha,producto,cantidad,sub};
                tbm.addRow(fila);
            }
                
            } catch (Exception e) {
            }
           //return rs;
    }
    public void verDatos(){
         try {
             Double s=0d;
             String fecha,producto="",cantidad="",sub="";
             Date f;
             Connection con = conexion.abrirConexion();
             Statement st=con.createStatement(); 
                    String sql=("select v.f,p.nombre,sum(vd.cantidad)as cantidad, sum(subtotal)as sub " +
                                "from venta_detalle as vd " +
                                "inner join producto as p " +
                                "on vd.codigo_producto=p.codigo " +
                                "inner join venta as v " +
                                "on vd.id=v.id " +
                                "group by v.f,p.nombre " +
                                "order by v.f desc;");
            ResultSet rs= st.executeQuery(sql);
            while(rs.next()){
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                f=rs.getDate(1);
                producto=rs.getString(2);
                cantidad=rs.getString(3);
                fecha=df.format(f);
                s=rs.getDouble(4);
                sub=String.valueOf(s);
                tbm=(DefaultTableModel) t_buscar.getModel();
                String fila[]={fecha,producto,cantidad,sub};
                tbm.addRow(fila);
            }
                
            } catch (Exception e) {
            }
           //return rs;
    }
    
    private void limpiaTabla(){
        try{
            DefaultTableModel tbm = (DefaultTableModel) t_buscar.getModel();
            for(int i=0; i<t_buscar.getRowCount(); i++){
                tbm.removeRow(i); 
                 i-=1;
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void grilla(){
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_buscar.getColumnModel().getColumn(0).setCellRenderer(r);
        t_buscar.getColumnModel().getColumn(1).setCellRenderer(r); 
        t_buscar.getColumnModel().getColumn(2).setCellRenderer(r); 
        t_buscar.getColumnModel().getColumn(3).setCellRenderer(r); 
        t_buscar.setAutoResizeMode(t_buscar.AUTO_RESIZE_OFF);   
        t_buscar.getColumnModel().getColumn(0).setPreferredWidth(150);
        t_buscar.getColumnModel().getColumn(1).setPreferredWidth(250);
        t_buscar.getColumnModel().getColumn(2).setPreferredWidth(100);
        t_buscar.getColumnModel().getColumn(3).setPreferredWidth(100);
        t_buscar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }*/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        b_buscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jdt1 = new com.toedter.calendar.JDateChooser();
        jdt2 = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setTitle("COMPRA");
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        b_buscar.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        b_buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-16.png"))); // NOI18N
        b_buscar.setText("REPORTE");
        b_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_buscarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 19)); // NOI18N
        jLabel1.setText("FECHA INICIO");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 19)); // NOI18N
        jLabel2.setText("FECHA FIN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(68, 68, 68))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jdt1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(b_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jdt2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b_buscar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_compra_fecha=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void b_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_buscarActionPerformed
       if(jdt1.getDate()== null && jdt2.getDate()==null){
            JOptionPane.showMessageDialog(this, "SELECCIONE FECHAS","ATENCION",JOptionPane.WARNING_MESSAGE);
       }else if ((jdt1.getDate()== null) && !(jdt2.getDate()==null)){
            JOptionPane.showMessageDialog(this, "SELECCIONE FECHA INICIO","ATENCION",JOptionPane.WARNING_MESSAGE);
       }else if (!(jdt1.getDate()== null) && (jdt2.getDate()==null)){
            JOptionPane.showMessageDialog(this, "SELECCIONE FECHA FIN","ATENCION",JOptionPane.WARNING_MESSAGE);
       }else
        {
            if(jdt1.getDate().before(jdt2.getDate()) ){
                agregar_fecha();
               try {
                    reporte  reportes = new reporte();
                    reportes.reporte_compra();
                } catch (JRException e) {
                    java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);

                }
                catch (SQLException e) {
                    java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);

                }
                //limpiaTabla(); 
                //buscar();
            }else if(jdt2.getDate().before(jdt1.getDate())){
                JOptionPane.showMessageDialog(this, "FECHA FIN DEBE SER MAYOR","ATENCION",JOptionPane.WARNING_MESSAGE);
            }else{
                agregar_fecha();
                if(jdt1.getDate().before(jdt2.getDate()) ){
                agregar_fecha();
               try {
                    reporte  reportes = new reporte();
                    reportes.reporte_compra();
                } catch (JRException e) {
                    java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);

                }
                catch (SQLException e) {
                    java.util.logging.Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, e);

                }
                }
                //limpiaTabla(); 
                //buscar();   
            }
        }
        
    }//GEN-LAST:event_b_buscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_buscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdt1;
    private com.toedter.calendar.JDateChooser jdt2;
    // End of variables declaration//GEN-END:variables
}
