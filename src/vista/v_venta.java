package vista;
import controlador.c_cliente;
import controlador.c_producto;
import controlador.c_venta;
import controlador.c_venta_detalle;
import controlador.comunes;
import controlador.conexion;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_cliente;
import modelo.m_producto;
import modelo.m_venta;
import modelo.m_venta_detalle;
import static vista.principal.dp_principal;

public class v_venta extends javax.swing.JInternalFrame {
    DefaultTableModel tbm;
    JTextField[] textfields;
    Double t_exentas=0d,t_iva_5=0d,t_iva_10=0d,total=0d;
    Double l_iva_5=0d,l_iva_10=0d,l_total=0d;
    DecimalFormat fd = new DecimalFormat("###0");
    DecimalFormat fd_total = new DecimalFormat("Gs #,##0");
    Icon grabar;
    Icon eliminar;
    public static String i_venta;
    int b=0;

    public v_venta() {
        initComponents();
        lbl_total_pagar.setText(fd_total.format(total));
        grabar= new ImageIcon("src/graficos/guardar-archivo-icono-6713-32.png");
        eliminar= new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        int _width = principal.dp_principal.getWidth()-this.getWidth();
        int _height = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(_width/2, _height/2);
        i_venta="";
        textfields = new JTextField[]{tf_exentas,tf_iva_5,tf_iva_10,tf_total,
                                      tf_iva5,tf_iva10,tf_iva_total,tf_c_nr,
                                      tf_c_cr};
        nuevo_registro();
        grilla_cliente();
        grilla_filtro();
        grilla_venta();
        ver_datos_producto();
        ver_datos_cliente();
    }
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    private void nuevo_registro(){
        Connection con = conexion.abrirConexion();
        m_venta v = new m_venta();
        c_venta c = new c_venta(con);
        List<m_venta> maxC = new ArrayList<m_venta>();
        maxC=c.maxCodigo(v);
        for(m_venta mp : maxC){
            this.t_nro_factura.setText(String.valueOf(mp.getId()));
        }
        Date fa = new Date();
        jdt.setDate(fa);
        comunes.limpiar_txt(textfields);
        conexion.cerrarConexion(con);  
    }
    
    private void agregar_cabecera(){
        Connection con = conexion.abrirConexion();
        m_venta v = new m_venta();
        c_venta c = new c_venta(con);   
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Integer t=0,u=0,cli=0;
        Date f;
        String p,usuario="",cliente="";
        f =jdt.getDate();
        p= df.format(f);
        try { 
                    usuario=tf_usuario.getText();
                    Statement s1 = null;
                    ResultSet r1 = null;
                    s1 = con.createStatement();
                    r1 = s1.executeQuery("SELECT id FROM usuario WHERE usuario = '"+usuario+"';");
                    while (r1.next()){
                        u=r1.getInt(1);
                    }  
                    cliente=tf_c_cr.getText();
                    Statement s2 = null;
                    ResultSet r2 = null;
                    s2 = con.createStatement();
                    r2 = s2.executeQuery("SELECT id FROM cliente WHERE ci_ruc = '"+cliente+"';");
                    while (r2.next()){
                        cli=r2.getInt(1);
                    }  
        } catch (SQLException e) {}
            if( !(tf_c_cr.getText().equals("")) ){
                v.setId(Integer.parseInt(t_nro_factura.getText()));
                v.setFecha(p);
                v.setTotal(t);
                v.setUsuario(u);
                v.setCliente(cli);
                c.agregar_f(v);
                conexion.cerrarConexion(con);
            }else{
                v.setId(Integer.parseInt(t_nro_factura.getText()));
                v.setFecha(p);
                v.setTotal(t);
                v.setUsuario(u);
                c.agregar_t(v);
                conexion.cerrarConexion(con);
            }           
    }
    
    private void agregar_detalle(){
         Connection con = conexion.abrirConexion();
         String id,codigo, precio_venta,cantidad,codi;
         Integer c=0,stock=0,calc=0;
            String sql = "INSERT INTO venta_detalle(id,cantidad,codigo_producto,precio_venta) " 
                        +"VALUES (?, ?, ?, ?);";           
            if (t_venta.getRowCount()>0) {       
                for(int i=0;i<t_venta.getRowCount();i++){
                try {
                    id = t_venta.getValueAt(i, 0).toString();
                    cantidad= t_venta.getValueAt(i, 1).toString();
                    codigo= t_venta.getValueAt(i, 2).toString();
                    precio_venta= t_venta.getValueAt(i, 4).toString();
                    //codi=t_venta.getValueAt(i, 8).toString();
                    Statement sentencia = null;
                    ResultSet resultado = null;
                    sentencia = con.createStatement();
                    resultado = sentencia.executeQuery("SELECT codigo, stock FROM producto WHERE codigo = '"+codigo+"';");
                    while (resultado.next()){
                        c=resultado.getInt(1);
                        stock =resultado.getInt(2);
                    }  
                    calc=stock-Integer.parseInt(cantidad);
                    if (calc<0) {
                        JOptionPane.showMessageDialog(this, "VERIFIQUE STOCK","STOCK",JOptionPane.WARNING_MESSAGE);
                    }else{
                        Integer id_=Integer.parseInt(id);
                        Integer cant=Integer.parseInt(cantidad);
                        Integer cod=Integer.parseInt(codigo);
                        Double precio_v=Double.parseDouble(precio_venta);
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setInt(1, id_);
                        ps.setInt(2, cant);
                        ps.setInt(3, cod);
                        ps.setDouble(4, precio_v); 
                        ps.executeUpdate();
                    }
                }catch (SQLException ex) {
                   Logger.getLogger(v_compra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JOptionPane.showMessageDialog(this, "REGISTRADO","REGISTRADO",JOptionPane.WARNING_MESSAGE,grabar);
        }else{
            JOptionPane.showMessageDialog(this, "ERROR","ERROR",JOptionPane.ERROR);
        }
         conexion.cerrarConexion(con);
    }
    
     private void buscar_cliente(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con); 
            cl.setCi_ruc(tf_busqueda_cliente.getText());
            cl.setNombre_razon(tf_busqueda_cliente.getText().trim().toUpperCase());
            List<m_cliente> verCliente = new ArrayList<m_cliente>();
            verCliente=c.buscarCliente(cl);
            DefaultTableModel tbm_b_cliente = (DefaultTableModel)t_cliente.getModel();
        for(int i = tbm_b_cliente.getRowCount()-1; i >= 0; i--){
            tbm_b_cliente.removeRow(i);
            }
        int i = 0;
        for(m_cliente mc : verCliente){
           tbm_b_cliente.addRow(new String[1]);
           t_cliente.setValueAt(mc.getCodigo(), i, 0);
           t_cliente.setValueAt(mc.getCi_ruc(), i, 1);
           t_cliente.setValueAt(mc.getNombre_razon(), i, 2);
           i++;
           }
            conexion.cerrarConexion(con);  
    }
    private void buscar_producto(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
            p.setCod_barra(t_buscar_producto.getText().trim());
            p.setProducto(t_buscar_producto.getText().trim().toUpperCase());
            List<m_producto> verNombre = new ArrayList<m_producto>();
            verNombre=c.buscar(p);
            DefaultTableModel tbm_b_producto = (DefaultTableModel)t_filtro.getModel();
        for(int i = tbm_b_producto.getRowCount()-1; i >= 0; i--){
            tbm_b_producto.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : verNombre){
           tbm_b_producto.addRow(new String[1]);
           t_filtro.setValueAt(mp.getCodigo(), i, 0);
           t_filtro.setValueAt(mp.getCod_barra(), i, 1);
           t_filtro.setValueAt(mp.getProducto(), i, 2);
           t_filtro.setValueAt(mp.getStock(), i, 3);
           t_filtro.setValueAt(mp.getPrecio_venta().intValue(), i,4);
           t_filtro.setValueAt(mp.getIva(), i, 5);
           i++;
           }
    }
    private void ver_datos_cliente(){
        Connection con = conexion.abrirConexion();
        m_cliente cl = new m_cliente();
        c_cliente c = new c_cliente(con); 
        List<m_cliente> listar = new ArrayList<m_cliente>();
        listar=c.listar();
        DefaultTableModel tbm_d_cliente = (DefaultTableModel)t_cliente.getModel();
        for(int i = tbm_d_cliente.getRowCount()-1; i >= 0; i--){
            tbm_d_cliente.removeRow(i);
            }
        int i = 0;
        for(m_cliente mc : listar){
           tbm_d_cliente.addRow(new String[1]);
           t_cliente.setValueAt(mc.getCodigo(), i, 0);
           t_cliente.setValueAt(mc.getCi_ruc(), i, 1);
           t_cliente.setValueAt(mc.getNombre_razon(), i, 2);
           i++;
           }
            tf_busqueda_cliente.setText("");
            this.tf_busqueda_cliente.requestFocus();
            conexion.cerrarConexion(con);  
    }
    private void ver_datos_producto(){   
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        List<m_producto> listar = new ArrayList<m_producto>();
        listar=c.listar();
        DefaultTableModel tbm_d_producto = (DefaultTableModel)t_filtro.getModel();
        for(int i = tbm_d_producto.getRowCount()-1; i >= 0; i--){
            tbm_d_producto.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : listar){
           tbm_d_producto.addRow(new String[1]);
           t_filtro.setValueAt(mp.getCodigo(), i, 0);
           t_filtro.setValueAt(mp.getCod_barra(), i, 1);
           t_filtro.setValueAt(mp.getProducto(), i, 2);
           t_filtro.setValueAt(mp.getStock(), i, 3);
           t_filtro.setValueAt(mp.getPrecio_venta().intValue(), i, 4);
           t_filtro.setValueAt(mp.getIva(), i, 5);
           i++;
           }
            conexion.cerrarConexion(con);  
    }
    
    private void grilla_filtro(){
        t_filtro.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_filtro.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_filtro.getColumnModel().getColumn(0).setCellRenderer(r);
        t_filtro.getColumnModel().getColumn(1).setCellRenderer(r); 
        t_filtro.getColumnModel().getColumn(2).setCellRenderer(r);    
        t_filtro.getColumnModel().getColumn(3).setCellRenderer(r);    
        t_filtro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t_filtro.getColumnModel().getColumn(0).setMaxWidth(0);
        t_filtro.getColumnModel().getColumn(0).setMinWidth(0);
        t_filtro.getColumnModel().getColumn(0).setPreferredWidth(0);
        t_filtro.getColumnModel().getColumn(1).setPreferredWidth(133);
        t_filtro.getColumnModel().getColumn(2).setPreferredWidth(260);
        t_filtro.getColumnModel().getColumn(3).setPreferredWidth(57);
        t_filtro.getColumnModel().getColumn(4).setPreferredWidth(100);
        t_filtro.getColumnModel().getColumn(5).setMaxWidth(0);
        t_filtro.getColumnModel().getColumn(5).setMinWidth(0);
        t_filtro.getColumnModel().getColumn(5).setPreferredWidth(0);
        t_filtro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);      
    }
    
    private void grilla_cliente(){
        t_cliente.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_cliente.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_cliente.getColumnModel().getColumn(1).setCellRenderer(r); 
        t_cliente.getColumnModel().getColumn(2).setCellRenderer(r);       
        t_cliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t_cliente.getColumnModel().getColumn(0).setMaxWidth(0);
        t_cliente.getColumnModel().getColumn(0).setMinWidth(0);
        t_cliente.getColumnModel().getColumn(0).setPreferredWidth(0);
        t_cliente.getColumnModel().getColumn(1).setPreferredWidth(130);
        t_cliente.getColumnModel().getColumn(2).setPreferredWidth(300);
        t_cliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);      
    }
    
       private void grilla_venta(){
        t_venta.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_venta.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        //centrar grilla   
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_venta.getColumnModel().getColumn(1).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(3).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(4).setCellRenderer(r);    
        t_venta.getColumnModel().getColumn(5).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(6).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(7).setCellRenderer(r);
        //ajustar tamaño grilla
        t_venta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //ocultar columna
        t_venta.getColumnModel().getColumn(0).setMaxWidth(0);
        t_venta.getColumnModel().getColumn(0).setMinWidth(0);
        t_venta.getColumnModel().getColumn(0).setPreferredWidth(0);
        t_venta.getColumnModel().getColumn(1).setPreferredWidth(90);
        t_venta.getColumnModel().getColumn(2).setMaxWidth(0);
        t_venta.getColumnModel().getColumn(2).setMinWidth(0);
        t_venta.getColumnModel().getColumn(2).setPreferredWidth(0);
        t_venta.getColumnModel().getColumn(3).setPreferredWidth(232);
        t_venta.getColumnModel().getColumn(4).setPreferredWidth(120);
        t_venta.getColumnModel().getColumn(5).setPreferredWidth(100);
        t_venta.getColumnModel().getColumn(6).setPreferredWidth(100);
        t_venta.getColumnModel().getColumn(7).setPreferredWidth(100);      
        t_venta.getColumnModel().getColumn(8).setMaxWidth(0);
        t_venta.getColumnModel().getColumn(8).setMinWidth(0);
        t_venta.getColumnModel().getColumn(8).setPreferredWidth(0);
        //seleccion simple una sola fila
        t_venta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
   private void agregar_cliente(){
              DefaultTableModel tbm_a_cliente;  
              int index = t_cliente.getSelectedRow();
              String  nombre="", ci="",n,c;
              n=tf_c_cr.getText();
              c=tf_c_nr.getText();
              if (n.isEmpty()&&c.isEmpty()){
                tbm_a_cliente= (DefaultTableModel) t_cliente.getModel();
                ci=t_cliente.getValueAt(index, 1).toString();
                nombre=t_cliente.getValueAt(index, 2).toString();  
                tf_c_cr.setText(ci);
                tf_c_nr.setText(nombre);
              }else{
                tf_c_cr.setText("");
                tf_c_nr.setText("");
                tbm_a_cliente= (DefaultTableModel) t_cliente.getModel();
                ci=t_cliente.getValueAt(index, 1).toString();
                nombre=t_cliente.getValueAt(index, 2).toString();  
                tf_c_cr.setText(ci);
                tf_c_nr.setText(nombre);
              }
    }   
       
    private void agregar_producto(){  
        Connection con = conexion.abrirConexion();
        m_venta_detalle vd = new m_venta_detalle();
        c_venta_detalle c = new c_venta_detalle(con);
        Integer s=0,calc=0;
        int fselect = t_filtro.getSelectedRow();
        int fcount = t_filtro.getRowCount();
         String id="",codigo="", producto="", precio_venta="",cantidad="",
                    iva="",exentas="",iva_5="",iva_10="",valor_string,cod="";
            Integer valor_int=0,stock=0;
        try {            
            if(fcount==1){
                tbm=(DefaultTableModel) t_filtro.getModel();
                id=t_nro_factura.getText();
                cantidad=(t_cantidad.getText());
                codigo=t_filtro.getValueAt(0, 0).toString();
                producto=t_filtro.getValueAt(0, 2).toString();
                stock=Integer.parseInt(t_filtro.getValueAt(0, 3).toString());
                precio_venta=t_filtro.getValueAt(0, 4).toString();
                iva=t_filtro.getValueAt(0, 5).toString(); 
            }else if (fcount>1){    
                tbm=(DefaultTableModel) t_filtro.getModel();
                id=t_nro_factura.getText();
                cantidad=(t_cantidad.getText());
                codigo=t_filtro.getValueAt(fselect, 0).toString();
                producto=t_filtro.getValueAt(fselect, 2).toString();
                stock=Integer.parseInt(t_filtro.getValueAt(fselect, 3).toString());
                precio_venta=t_filtro.getValueAt(fselect, 4).toString();
                iva=t_filtro.getValueAt(fselect, 5).toString(); 
            }
                valor_int=((Integer.parseInt(cantidad))*(Integer.parseInt(precio_venta)));     
                valor_string=String.valueOf(valor_int);
                List<m_venta_detalle> maxC = new ArrayList<m_venta_detalle>();
                maxC=c.maxCodigo(vd);
                for(m_venta_detalle mvd : maxC){
                cod=String.valueOf(mvd.getCod()+t_venta.getRowCount());
                }
            if(stock>=1){
                Statement sentencia = null;
                ResultSet resultado = null;           
                sentencia = con.createStatement();
                resultado = sentencia.executeQuery("SELECT stock FROM producto WHERE codigo = '"+codigo+"';");                          
                while (resultado.next()){
                    stock =resultado.getInt(1);
                }  
                calc=stock-Integer.parseInt(cantidad);
                if (calc<0) {
                    JOptionPane.showMessageDialog(this, "VERIFIQUE STOCK","STOCK",JOptionPane.WARNING_MESSAGE);
                }else{ 
                    switch (iva) {
                        case "0":
                            exentas=valor_string;
                            iva_5="0";
                            iva_10="0";
                            break;
                        case "5":
                            exentas="0";
                            iva_5=valor_string;
                            iva_10="0";
                            break;
                        case "10":
                            exentas="0";
                            iva_5="0";
                            iva_10=valor_string;
                            break;
                    }                    
                    if (t_venta.getRowCount()>=1){
                        String valor="";
                        int identificador=0;
                        for (int i = 0; i<t_venta.getRowCount(); i++) {
                            if(codigo.equals(t_venta.getValueAt(i, 2).toString().trim())){
                                valor = t_venta.getValueAt(i, 2).toString().trim();
                                identificador= i;
                            } 
                        }
                        if(codigo.equals(valor)){
                            tbm=(DefaultTableModel) t_venta.getModel();
                            String cant_aux=tbm.getValueAt(identificador, 1).toString();            
                            String aux_cantidad = String.valueOf(Integer.parseInt(cant_aux)+Integer.parseInt(cantidad));
                            tbm.setValueAt(aux_cantidad,identificador, 1);                            
                            String sub = String.valueOf(Integer.parseInt(precio_venta)*Integer.parseInt(aux_cantidad));
                            switch (iva) {
                                case "0":
                                    tbm.setValueAt(sub, identificador, 5);    
                                    break;
                                case "5":
                                    tbm.setValueAt(sub, identificador, 6);
                                    break;
                                case "10":
                                    tbm.setValueAt(sub, identificador, 7);
                                    break;
                            }
                        }else{            
                            tbm=(DefaultTableModel) t_venta.getModel();
                            String filaelemento[] = {id,cantidad,codigo,producto,precio_venta,exentas,iva_5,iva_10,cod};
                            tbm.addRow(filaelemento);
                        }                    
                    }else{
                        tbm=(DefaultTableModel) t_venta.getModel();
                        String filaelemento[] = {id,cantidad,codigo,producto,precio_venta,exentas,iva_5,iva_10,cod};
                        tbm.addRow(filaelemento);
                    }
                        int t_exe=0,t_iva5=0,t_iva10=0;
                        String f_e,f_5,f_10;
                        for (int i = 0; i<t_venta.getRowCount(); i++) {
                            f_e=tbm.getValueAt(i, 5).toString();
                            t_exe=t_exe+Integer.parseInt(f_e);
                            f_5=tbm.getValueAt(i, 6).toString();
                            t_iva5=t_iva5+Integer.parseInt(f_5);                       
                            f_10=tbm.getValueAt(i, 7).toString();
                            t_iva10=t_iva10+Integer.parseInt(f_10);
                        }
                        tf_exentas.setText(String.valueOf(t_exe));
                        tf_iva_5.setText(String.valueOf(t_iva5));
                        tf_iva_10.setText(String.valueOf(t_iva10));       
                        total=Double.parseDouble(tf_exentas.getText())+Double.parseDouble(tf_iva_5.getText())+Double.parseDouble(tf_iva_10.getText());
                        tf_total.setText(fd.format(total));
                        lbl_total_pagar.setText(fd_total.format(total));
                        l_iva_5=Double.parseDouble(tf_iva_5.getText())/21d;
                        tf_iva5.setText(fd.format(l_iva_5));
                        l_iva_10=Double.parseDouble(tf_iva_10.getText())/11d;
                        tf_iva10.setText(fd.format(l_iva_10));
                        l_total=Double.parseDouble(tf_iva5.getText())+Double.parseDouble(tf_iva10.getText());
                        tf_iva_total.setText(fd.format(l_total));
                    }
            }else{
                JOptionPane.showMessageDialog(this, "STOCK NO DISPONIBLE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
        }
        conexion.cerrarConexion(con); 
    }
     
    private void limpiaTabla(){
        try{
            DefaultTableModel tbm = (DefaultTableModel) t_venta.getModel();
            for(int i=0; i<t_venta.getRowCount(); i++){
                tbm.removeRow(i); 
                 i-=1;
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void eliminar_fila(){
        int fsel,resp,f;
        int exentas,iva_5,iva_10;
            fsel=t_venta.getSelectedRow();           
            if (fsel==-1) {
                JOptionPane.showMessageDialog(this, "SELECCIONE PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                resp=JOptionPane.showConfirmDialog(null, "ESTA SEGURO","ADVERTENCIA",JOptionPane.YES_NO_OPTION);
                if (resp==JOptionPane.YES_OPTION) {
                      exentas=Integer.parseInt(t_venta.getValueAt(fsel, 5).toString());
                      iva_5=Integer.parseInt(t_venta.getValueAt(fsel, 6).toString());
                      iva_10=Integer.parseInt(t_venta.getValueAt(fsel, 7).toString());
                      
                      t_exentas=Double.parseDouble(tf_exentas.getText());
                      t_iva_5=Double.parseDouble(tf_iva_5.getText());
                      t_iva_10=Double.parseDouble(tf_iva_10.getText());
                      if (exentas!=0) {
                            t_exentas=t_exentas-exentas;
                            tf_exentas.setText(fd.format(t_exentas));
                      }else if (iva_5!=0) {
                            t_iva_5=t_iva_5-iva_5;
                            tf_iva_5.setText(fd.format(t_iva_5));
                            l_iva_5=t_iva_5/21d;
                            tf_iva5.setText(fd.format(l_iva_5));
                      }else if (iva_10!=0) {
                            t_iva_10=t_iva_10-iva_10;
                            tf_iva_10.setText(fd.format(t_iva_10));
                            l_iva_10=t_iva_10/11d;
                            tf_iva10.setText(fd.format(l_iva_10));
                      }
                      total=t_exentas+t_iva_5+t_iva_10;
                      tf_total.setText(fd.format(total));
                      lbl_total_pagar.setText(fd_total.format(total));
                      l_total=Double.parseDouble(tf_iva5.getText())+Double.parseDouble(tf_iva10.getText());
                      tf_iva_total.setText(fd.format(l_total));
                      tbm.removeRow(fsel);
                      f=t_venta.getRowCount();
                      if(f<=0){
                      comunes.limpiar_txt(textfields);
                      }
                }
            }
    }
    
    public void cancelar_venta(){
        switch(b){
            case 1:
            Object[] opcsi = { "Si", "No" };
            int i = JOptionPane.showOptionDialog(null,"DESEA CANCELAR FACTURA "
            +""+this.t_nro_factura.getText()+" ?", "BORRADO",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
            opcsi, opcsi[0]);
            if (i == JOptionPane.YES_OPTION) {
                limpiaTabla();
                nuevo_registro();
            }
            break;
            case 2:
                Object[] opcsy = { "Si", "No" };
                int y = JOptionPane.showOptionDialog(null,"DESEA CANCELAR FACTURA "
                +""+this.t_nro_factura.getText()+" ?", "BORRADO",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                opcsy, opcsy[0]);
                if (y == JOptionPane.YES_OPTION) {
                    this.dispose();
                }else if(y == JOptionPane.NO_OPTION){
                   this.setVisible(true);
                   this.show();
                }
            break;
            case 3:
                this.dispose();
            break;
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        d_agregar_producto = new javax.swing.JDialog();
        p_busqueda = new javax.swing.JPanel();
        t_buscar_producto = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_filtro = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        b_agregar = new javax.swing.JButton();
        b_salir = new javax.swing.JButton();
        t_cantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        bg_busqueda = new javax.swing.ButtonGroup();
        d_agregar_cliente = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        tf_busqueda_cliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_cliente = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        b_registrar = new javax.swing.JButton();
        p_cabecera = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        p_detalle = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        tf_exentas = new javax.swing.JTextField();
        tf_iva_5 = new javax.swing.JTextField();
        tf_iva_10 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tf_total = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        tf_iva5 = new javax.swing.JTextField();
        tf_iva10 = new javax.swing.JTextField();
        tf_iva_total = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        t_venta = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        jLabel5 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tf_c_nr = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tf_c_cr = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jdt = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        t_nro_factura = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        b_add_producto = new javax.swing.JButton();
        b_eliminar = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        b_factura = new javax.swing.JButton();
        bt_cancelar = new javax.swing.JButton();
        b_close = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbl_total_pagar = new javax.swing.JLabel();

        d_agregar_producto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        d_agregar_producto.setTitle("PRODUCTOS");
        d_agregar_producto.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                d_agregar_productoWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                d_agregar_productoWindowOpened(evt);
            }
        });

        p_busqueda.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSQUEDA"));

        t_buscar_producto.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_buscar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_buscar_productoActionPerformed(evt);
            }
        });
        t_buscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_buscar_productoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_buscar_productoKeyTyped(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        javax.swing.GroupLayout p_busquedaLayout = new javax.swing.GroupLayout(p_busqueda);
        p_busqueda.setLayout(p_busquedaLayout);
        p_busquedaLayout.setHorizontalGroup(
            p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_busquedaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_busquedaLayout.setVerticalGroup(
            p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_busquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(t_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        t_filtro.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_filtro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "COD BARRA", "PRODUCTO", "STOCK", "PRE. VENTA", "iva"
            }
        ));
        t_filtro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_filtroMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(t_filtro);

        b_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/agregar-editar-icono-6607-32.png"))); // NOI18N
        b_agregar.setText("AGREGAR");
        b_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarActionPerformed(evt);
            }
        });

        b_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/prohibe-icono-5792-32.png"))); // NOI18N
        b_salir.setText("SALIR");
        b_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_salirActionPerformed(evt);
            }
        });

        t_cantidad.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_cantidadKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel3.setText("CANTIDAD");

        javax.swing.GroupLayout d_agregar_productoLayout = new javax.swing.GroupLayout(d_agregar_producto.getContentPane());
        d_agregar_producto.getContentPane().setLayout(d_agregar_productoLayout);
        d_agregar_productoLayout.setHorizontalGroup(
            d_agregar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregar_productoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(b_agregar)
                .addGap(18, 18, 18)
                .addComponent(b_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, d_agregar_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(d_agregar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(p_busqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        d_agregar_productoLayout.setVerticalGroup(
            d_agregar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregar_productoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(p_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(d_agregar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(d_agregar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(b_salir)
                        .addComponent(b_agregar)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        d_agregar_cliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        d_agregar_cliente.setTitle("CLIENTES");
        d_agregar_cliente.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                d_agregar_clienteWindowClosed(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(485, 475));

        tf_busqueda_cliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tf_busqueda_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_busqueda_clienteKeyReleased(evt);
            }
        });

        t_cliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        t_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "           CI/RUC", "NOMBRE/RAZON"
            }
        ));
        t_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_clienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_cliente);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setText("BUSCAR");

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        b_registrar.setText("REGISTRAR");
        b_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_registrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(tf_busqueda_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(b_registrar)
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_busqueda_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24)))
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addComponent(b_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout d_agregar_clienteLayout = new javax.swing.GroupLayout(d_agregar_cliente.getContentPane());
        d_agregar_cliente.getContentPane().setLayout(d_agregar_clienteLayout);
        d_agregar_clienteLayout.setHorizontalGroup(
            d_agregar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregar_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );
        d_agregar_clienteLayout.setVerticalGroup(
            d_agregar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregar_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        setIconifiable(true);
        setTitle("VENTA");
        setToolTipText("");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        p_cabecera.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setText("FECHA");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 22)); // NOI18N
        jLabel4.setText("BODEGA G");

        p_detalle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tf_exentas.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_exentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_exentas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_exentas.setEnabled(false);

        tf_iva_5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_iva_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_iva_5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_iva_5.setEnabled(false);
        tf_iva_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_iva_5ActionPerformed(evt);
            }
        });

        tf_iva_10.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_iva_10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_iva_10.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_iva_10.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setText("SUB-TOTALES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tf_exentas, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_iva_5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(tf_iva_10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(tf_exentas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tf_iva_5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tf_iva_10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tf_total.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        tf_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_total.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_total.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel7.setText("TOTAL");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tf_iva5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_iva5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_iva5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_iva5.setEnabled(false);

        tf_iva10.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_iva10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_iva10.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_iva10.setEnabled(false);

        tf_iva_total.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_iva_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_iva_total.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_iva_total.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel8.setText("LIQUIDACION DEL IVA: (5%)");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setText("(10%)");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel10.setText("TOTAL IVA:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(tf_iva5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(tf_iva10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tf_iva_total)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(tf_iva5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tf_iva10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tf_iva_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel8)
                .addComponent(jLabel9)
                .addComponent(jLabel10))
        );

        t_venta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        t_venta.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CANTIDAD", "COD. PROD.", "ARTICULO", "PRE. UNITARIO", "EXENTAS", "5%", "10%", "COD"
            }
        ));
        jScrollPane4.setViewportView(t_venta);

        javax.swing.GroupLayout p_detalleLayout = new javax.swing.GroupLayout(p_detalle);
        p_detalle.setLayout(p_detalleLayout);
        p_detalleLayout.setHorizontalGroup(
            p_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_detalleLayout.setVerticalGroup(
            p_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_detalleLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel5.setText("BEBIDAS EN GENERAL - CIGARRILLOS - GOLOSINAS ");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel21.setText("NOMBRE/RAZON");

        tf_c_nr.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_c_nr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_c_nr.setEnabled(false);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel22.setText("CI/RUC");

        tf_c_cr.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_c_cr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_c_cr.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-16.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel23.setText("USUARIO");

        tf_usuario.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tf_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_usuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_usuario.setEnabled(false);

        jdt.setBackground(new java.awt.Color(255, 255, 255));
        jdt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setText("NRO FACTURA");

        t_nro_factura.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_nro_factura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_nro_factura.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        t_nro_factura.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t_nro_factura)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(t_nro_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_cabeceraLayout = new javax.swing.GroupLayout(p_cabecera);
        p_cabecera.setLayout(p_cabeceraLayout);
        p_cabeceraLayout.setHorizontalGroup(
            p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(p_cabeceraLayout.createSequentialGroup()
                .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21))
                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                        .addComponent(tf_c_nr)
                        .addGap(4, 4, 4)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))
                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                        .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdt, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_c_cr, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(p_cabeceraLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(tf_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(p_cabeceraLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jLabel23)))
                        .addGap(94, 94, 94)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
            .addGroup(p_cabeceraLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_cabeceraLayout.setVerticalGroup(
            p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_cabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(tf_c_nr, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(p_cabeceraLayout.createSequentialGroup()
                                .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(tf_c_cr, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel22)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(p_cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jdt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(p_cabeceraLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(p_cabeceraLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(p_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("ARTICULO"));

        b_add_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/agregar-editar-icono-6607-32.png"))); // NOI18N
        b_add_producto.setText("ARTICULO");
        b_add_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_add_productoActionPerformed(evt);
            }
        });

        b_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/eliminar-cancelar-icono-4935-32.png"))); // NOI18N
        b_eliminar.setText("ELIMINAR");
        b_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b_add_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(b_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(b_add_producto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(b_eliminar))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("FACTURACION"));

        b_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/factura.png"))); // NOI18N
        b_factura.setText("FACTURAR");
        b_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_facturaActionPerformed(evt);
            }
        });

        bt_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/prohibe-icono-5792-32.png"))); // NOI18N
        bt_cancelar.setText("CANCELAR");
        bt_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelarActionPerformed(evt);
            }
        });

        b_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/salir-de-mi-perfil-icono-3964-32.png"))); // NOI18N
        b_close.setText("SALIR");
        b_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(b_factura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(bt_cancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(b_close, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(b_factura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_cancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_close)
                .addGap(149, 149, 149))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOTAL FACTURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N

        lbl_total_pagar.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        lbl_total_pagar.setForeground(new java.awt.Color(0, 0, 255));
        lbl_total_pagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_total_pagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_total_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(p_cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(p_cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_add_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_add_productoActionPerformed
        d_agregar_producto.setSize(600,310);
        d_agregar_producto.setLocationRelativeTo(null);
        d_agregar_producto.setModal(true);
        d_agregar_producto.setVisible(true);
        d_agregar_producto.pack();
    }//GEN-LAST:event_b_add_productoActionPerformed
    private void t_buscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscar_productoKeyReleased
        buscar_producto();
        
    }//GEN-LAST:event_t_buscar_productoKeyReleased
    private void t_buscar_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscar_productoKeyTyped
        char tecla=evt.getKeyChar();
        if(tecla==KeyEvent.VK_ENTER){
            if(t_filtro.getRowCount()>1){
                JOptionPane.showMessageDialog(null,"SELECCIONE ARTICULO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if (t_filtro.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"NO EXISTE PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                
            }else{
                this.t_cantidad.requestFocus();
                t_cantidad.setText("1");
            }
        }else if(tecla==KeyEvent.VK_ESCAPE){
            t_cantidad.setText("");         
            t_buscar_producto.setText("");
            ver_datos_producto();
            d_agregar_producto.dispose();
        }
    }//GEN-LAST:event_t_buscar_productoKeyTyped

    private void t_filtroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_filtroMouseClicked
        
        t_filtro.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
        if(e.getClickCount()==2){
            t_cantidad.requestFocus();
        }}
       });
    }//GEN-LAST:event_t_filtroMouseClicked

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        int fselect = t_filtro.getSelectedRow();
        String nl = System.getProperty("line.separator");
        if((fselect==-1)&&(t_cantidad.getText().equals(""))){
            JOptionPane.showMessageDialog(null, "SELECCIONE PRODUCTO"+ nl +"INGRESE CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (fselect==-1){
            JOptionPane.showMessageDialog(null,"SELECCIONE UN PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(t_cantidad.getText().equals("")){
            JOptionPane.showMessageDialog(null, "INGRESE CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_cantidad.requestFocus();
        }else if(t_cantidad.getText().equals("0")){
            JOptionPane.showMessageDialog(null, "SELECCIONE CANTIDAD"+ nl +"MAYOR A CERO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_cantidad.setText("");
            t_cantidad.requestFocus();
        }else{
            agregar_producto();
            t_cantidad.setText("");         
            t_buscar_producto.setText("");
            t_buscar_producto.requestFocus();
        }
    }//GEN-LAST:event_b_agregarActionPerformed

    private void b_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_salirActionPerformed
        t_cantidad.setText("");         
        t_buscar_producto.setText(""); 
        ver_datos_producto();
        d_agregar_producto.dispose();
    }//GEN-LAST:event_b_salirActionPerformed

    private void b_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_closeActionPerformed
        i_venta=null;
        if (tf_total.getText().equals("")) {
            b=3;
            cancelar_venta();
        }else{
            b=2;
            cancelar_venta();
        }
    }//GEN-LAST:event_b_closeActionPerformed

    private void t_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_cantidadKeyTyped
        char digito = evt.getKeyChar();
        char tecla=evt.getKeyChar();
        if(!Character.isDigit(digito)){  
            evt.consume(); 
        }
        if(tecla==KeyEvent.VK_ENTER){
            agregar_producto();
            t_cantidad.setText("");
            t_buscar_producto.setText("");
            t_buscar_producto.requestFocus();
        }else if(tecla==KeyEvent.VK_ESCAPE){
            t_cantidad.setText("");         
            t_buscar_producto.setText("");
            ver_datos_producto();
            d_agregar_producto.dispose();
        }
    }//GEN-LAST:event_t_cantidadKeyTyped

    private void b_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_eliminarActionPerformed
        b=0;
        eliminar_fila();
    }//GEN-LAST:event_b_eliminarActionPerformed

    private void b_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_facturaActionPerformed
       if (t_venta.getRowCount()<=0) {        
            JOptionPane.showMessageDialog(this, "CARGUE ARTICULO","STOCK",JOptionPane.WARNING_MESSAGE);
        }else{
            agregar_cabecera();
            agregar_detalle();
            limpiaTabla();
            nuevo_registro();
       }
    }//GEN-LAST:event_b_facturaActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    }//GEN-LAST:event_formInternalFrameClosing

    private void d_agregar_productoWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_d_agregar_productoWindowActivated
        ver_datos_producto();
    }//GEN-LAST:event_d_agregar_productoWindowActivated

    private void d_agregar_productoWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_d_agregar_productoWindowOpened
        ver_datos_producto();
    }//GEN-LAST:event_d_agregar_productoWindowOpened

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
    }//GEN-LAST:event_formInternalFrameClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        d_agregar_cliente.setSize(495,475);
        d_agregar_cliente.setLocationRelativeTo(null);
        d_agregar_cliente.setModal(true);
        d_agregar_cliente.setVisible(true);
        d_agregar_cliente.pack();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_busqueda_clienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_busqueda_clienteKeyReleased
        buscar_cliente();
    }//GEN-LAST:event_tf_busqueda_clienteKeyReleased

    private void t_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_clienteMouseClicked
        t_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){ 
                        agregar_cliente();
                        d_agregar_cliente.dispose();
                }}
            });
    }//GEN-LAST:event_t_clienteMouseClicked

    private void bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelarActionPerformed
        if (t_venta.getRowCount()<=0) {
            JOptionPane.showMessageDialog(null,"FACTURA VACIA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            b=1;
            cancelar_venta();
        }
    }//GEN-LAST:event_bt_cancelarActionPerformed

    private void tf_iva_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_iva_5ActionPerformed
    }//GEN-LAST:event_tf_iva_5ActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
    }//GEN-LAST:event_formKeyTyped

    private void t_buscar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_buscar_productoActionPerformed
    }//GEN-LAST:event_t_buscar_productoActionPerformed

    private void b_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_registrarActionPerformed
        String cliente = v_cliente.i_cliente;
        if (cliente==null) {
             v_cliente cl = new v_cliente();
            dp_principal.add(cl);
            cl.show();
            d_agregar_cliente.dispose();
        }else{
            JOptionPane.showMessageDialog(rootPane, "VENTANA ABIERTA");
        }
    }//GEN-LAST:event_b_registrarActionPerformed

    private void d_agregar_clienteWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_d_agregar_clienteWindowClosed
        tf_busqueda_cliente.setText("");          
        ver_datos_cliente();
    }//GEN-LAST:event_d_agregar_clienteWindowClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_add_producto;
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_close;
    private javax.swing.JButton b_eliminar;
    private javax.swing.JButton b_factura;
    private javax.swing.JButton b_registrar;
    private javax.swing.JButton b_salir;
    private javax.swing.ButtonGroup bg_busqueda;
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JDialog d_agregar_cliente;
    private javax.swing.JDialog d_agregar_producto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser jdt;
    private javax.swing.JLabel lbl_total_pagar;
    private javax.swing.JPanel p_busqueda;
    private javax.swing.JPanel p_cabecera;
    private javax.swing.JPanel p_detalle;
    private javax.swing.JTextField t_buscar_producto;
    private javax.swing.JTextField t_cantidad;
    private javax.swing.JTable t_cliente;
    private javax.swing.JTable t_filtro;
    private javax.swing.JTextField t_nro_factura;
    private javax.swing.JTable t_venta;
    private javax.swing.JTextField tf_busqueda_cliente;
    private javax.swing.JTextField tf_c_cr;
    private javax.swing.JTextField tf_c_nr;
    private javax.swing.JTextField tf_exentas;
    private javax.swing.JTextField tf_iva10;
    private javax.swing.JTextField tf_iva5;
    private javax.swing.JTextField tf_iva_10;
    private javax.swing.JTextField tf_iva_5;
    private javax.swing.JTextField tf_iva_total;
    public javax.swing.JTextField tf_total;
    public static final javax.swing.JTextField tf_usuario = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}
