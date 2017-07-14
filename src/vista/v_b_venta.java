package vista;
import com.mxrck.autocompleter.TextAutoCompleter;
import controlador.comunes;
import controlador.conexion;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;
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

public class v_b_venta extends javax.swing.JInternalFrame {
    DefaultTableModel tbm;
    JTextField[] textfields;
    Double t_exentas=0d,t_iva_5=0d,t_iva_10=0d,total=0d;
    Double l_iva_5=0d,l_iva_10=0d,l_total=0d;
    DecimalFormat fd = new DecimalFormat("###0");
    DecimalFormat fd_total = new DecimalFormat("Gs #,##0");
    Icon grabar;
    Icon eliminar;
    public static String i_b_venta;
    int b=0;
    double total_factura=0d;

    public v_b_venta() {
        initComponents();
        grabar= new ImageIcon("src/graficos/guardar-archivo-icono-6713-32.png");
        eliminar= new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        int _Width = principal.dp_principal.getWidth()-this.getWidth();
        int _Height = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(_Width/2, _Height/2);
        i_b_venta="";
        textfields = new JTextField[]{tf_exentas,tf_iva_5,tf_iva_10,tf_total,
                                      tf_iva5,tf_iva10,tf_iva_total,tf_c_nr,
                                      tf_c_cr};
        grilla_venta();
        txt_busqueda.requestFocus();
    }
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    private void setear_cabecera(){
        Connection con = conexion.abrirConexion();
        String p,id="",total_f="",id_cliente="",id_usuario="",usuario="",
                nombre_razon="",ci_ruc="";
        Date fecha = null;
        Statement s1 = null;
        ResultSet r1 = null;
        Statement s2 = null;
        ResultSet r2 = null;
        Statement s3 = null;
        ResultSet r3 = null;
        try { 
                    String txt=txt_busqueda.getText().trim();
                    s1 = con.createStatement();
                    r1 = s1.executeQuery("SELECT id, total, f, id_cliente, id_usuario"
                            +"  FROM venta WHERE id = "+txt+";");
                    while (r1.next()){
                        id=r1.getString("id");
                        total_f=r1.getString("total");
                        total_factura = Double.parseDouble(total_f);
                        fecha=r1.getDate("f");
                        id_cliente=r1.getString("id_cliente");
                        id_usuario=r1.getString("id_usuario");
                    } 
                   
                    s2 = con.createStatement();
                    r2 = s2.executeQuery("SELECT usuario FROM usuario WHERE id = "+id_usuario+";");
                    while (r2.next()){
                        usuario=r2.getString("usuario");
                    }  
                    
                    s3 = con.createStatement();
                    r3 = s3.executeQuery("SELECT nombre_razon, ci_ruc FROM cliente WHERE id = "+id_cliente+";");
                    while (r3.next()){
                        nombre_razon=r3.getString("nombre_razon");
                        ci_ruc=r3.getString("ci_ruc");
                    }
        }catch(SQLException e){ 
        }
        t_nro_factura.setText(id);
        tf_c_nr.setText(nombre_razon);
        tf_c_cr.setText(ci_ruc);
        jdt.setDate(fecha);
        tf_usuario.setText(usuario);      
        lbl_total_pagar.setText(fd_total.format(total_factura));
    }
    
    private void setear_detalle(){
        Connection con = conexion.abrirConexion();
        String cantidad="",nombre="",precio="",exentas="",cinco="",diez="";
        Statement s1 = null;
        ResultSet r1 = null;
         tbm=(DefaultTableModel) t_venta.getModel();
        try{ 
        String f = t_nro_factura.getText();
        String sql = ("SELECT d.cantidad,p.nombre,d.precio_venta,\n" +
        "	case when p.iva=0 then d.precio_venta*d.cantidad else 0 end as exentas,\n" +
        "	case when p.iva=5 then d.precio_venta*d.cantidad else 0 end as cinco,\n" +
        "	case when p.iva=10 then d.precio_venta*d.cantidad else 0 end as diez	\n" +
        "FROM venta_detalle d\n" +
        "inner join producto p\n" +
        "on p.codigo=d.codigo_producto\n" +
        "where id = "+f+"");
            s1 = con.createStatement();
            r1 = s1.executeQuery(sql);
            while (r1.next()){
                cantidad=r1.getString("cantidad");
                nombre=r1.getString("nombre");
                precio=r1.getString("precio_venta");
                exentas=r1.getString("exentas");
                cinco=r1.getString("cinco");
                diez=r1.getString("diez");
                String filaelemento[] = {cantidad,nombre,precio,exentas,cinco,diez};
                tbm.addRow(filaelemento);
            } 
        }catch(SQLException e){}
        int t_exe=0,t_iva5=0,t_iva10=0;
        String f_e,f_5,f_10;
        for (int i = 0; i<t_venta.getRowCount(); i++) {
            f_e=tbm.getValueAt(i, 3).toString();
            t_exe=t_exe+Integer.parseInt(f_e);
            f_5=tbm.getValueAt(i, 4).toString();
            t_iva5=t_iva5+Integer.parseInt(f_5);                       
            f_10=tbm.getValueAt(i, 5).toString();
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
    
    private void autocompletado(){
        Connection con = conexion.abrirConexion();
        TextAutoCompleter autoc = new TextAutoCompleter(txt_busqueda);
        Statement s1 = null;
        ResultSet r1 = null;
        try { 
                    String txt =txt_busqueda.getText().trim();
                    s1 = con.createStatement();
                    r1 = s1.executeQuery("SELECT id FROM venta WHERE id::TEXT LIKE '%"+txt+"%';");
                    while (r1.next()){
                        autoc.addItem(r1.getString("id")+"                             ");
                    } 
        }catch(SQLException e){
        }
    }

    private void grilla_venta(){
        t_venta.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_venta.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        //centrar grilla   
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_venta.getColumnModel().getColumn(0).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(1).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(2).setCellRenderer(r);    
        t_venta.getColumnModel().getColumn(3).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(4).setCellRenderer(r);
        t_venta.getColumnModel().getColumn(5).setCellRenderer(r);
        //ajustar tamaño grilla
        t_venta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //ocultar columna
        t_venta.getColumnModel().getColumn(0).setPreferredWidth(90);
        t_venta.getColumnModel().getColumn(1).setPreferredWidth(232);
        t_venta.getColumnModel().getColumn(2).setPreferredWidth(120);
        t_venta.getColumnModel().getColumn(3).setPreferredWidth(100);
        t_venta.getColumnModel().getColumn(4).setPreferredWidth(100);
        t_venta.getColumnModel().getColumn(5).setPreferredWidth(100);
        //seleccion simple una sola fila
        t_venta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
    
    public void cancelar_venta(){
        switch(b){
            case 1:
            Object[] opcsi = { "Si", "No" };
            int i = JOptionPane.showOptionDialog(null,"DESEA CANCELAR FACTURA "
            +""+this.t_nro_factura.getText()+" ?", "BORRADO",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
            opcsi, opcsi[0]);
            if (i == JOptionPane.YES_OPTION) {
                limpiaTabla();
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
        jPanel3 = new javax.swing.JPanel();
        txt_busqueda = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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

        d_agregar_cliente.setTitle("CLIENTES");

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
                .addContainerGap(100, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_busqueda_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout d_agregar_clienteLayout = new javax.swing.GroupLayout(d_agregar_cliente.getContentPane());
        d_agregar_cliente.getContentPane().setLayout(d_agregar_clienteLayout);
        d_agregar_clienteLayout.setHorizontalGroup(
            d_agregar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregar_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );
        d_agregar_clienteLayout.setVerticalGroup(
            d_agregar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregar_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        setClosable(true);
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
                "CANTIDAD", "ARTICULO", "PRE. UNITARIO", "EXENTAS", "5%", "10%"
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
        jdt.setEnabled(false);
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
                        .addComponent(jButton1))
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
                                .addComponent(jLabel23)))))
                .addGap(94, 94, 94)
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        txt_busqueda.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        txt_busqueda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel12.setText("NRO FACTURA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOTAL FACTURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N

        lbl_total_pagar.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        lbl_total_pagar.setForeground(new java.awt.Color(0, 0, 255));
        lbl_total_pagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_total_pagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(p_cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_buscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscar_productoKeyReleased
       
    }//GEN-LAST:event_t_buscar_productoKeyReleased
    private void t_buscar_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscar_productoKeyTyped
    }//GEN-LAST:event_t_buscar_productoKeyTyped

    private void t_filtroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_filtroMouseClicked
    }//GEN-LAST:event_t_filtroMouseClicked

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
    }//GEN-LAST:event_b_agregarActionPerformed

    private void b_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_salirActionPerformed
    }//GEN-LAST:event_b_salirActionPerformed

    private void t_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_cantidadKeyTyped
    }//GEN-LAST:event_t_cantidadKeyTyped

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    }//GEN-LAST:event_formInternalFrameClosing

    private void d_agregar_productoWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_d_agregar_productoWindowActivated
        
    }//GEN-LAST:event_d_agregar_productoWindowActivated

    private void d_agregar_productoWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_d_agregar_productoWindowOpened
        
    }//GEN-LAST:event_d_agregar_productoWindowOpened

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        i_b_venta=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_busqueda_clienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_busqueda_clienteKeyReleased
    }//GEN-LAST:event_tf_busqueda_clienteKeyReleased

    private void t_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_clienteMouseClicked
    }//GEN-LAST:event_t_clienteMouseClicked

    private void tf_iva_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_iva_5ActionPerformed
    }//GEN-LAST:event_tf_iva_5ActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
    }//GEN-LAST:event_formKeyTyped

    private void t_buscar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_buscar_productoActionPerformed
    }//GEN-LAST:event_t_buscar_productoActionPerformed

    private void txt_busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyTyped
     autocompletado();
     String busqueda=txt_busqueda.getText().trim();
     txt_busqueda.setText(busqueda);
      char tecla=evt.getKeyChar();
        if(tecla==KeyEvent.VK_ENTER){
           if(t_nro_factura.getText()!=busqueda) {
                comunes.limpiar_txt(textfields);
                lbl_total_pagar.setText("");
                limpiaTabla();
                setear_cabecera(); 
                setear_detalle();
           }
        }
    }//GEN-LAST:event_txt_busquedaKeyTyped

    private void txt_busquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyPressed
    }//GEN-LAST:event_txt_busquedaKeyPressed

    private void txt_busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyReleased
    }//GEN-LAST:event_txt_busquedaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_salir;
    private javax.swing.ButtonGroup bg_busqueda;
    private javax.swing.JDialog d_agregar_cliente;
    private javax.swing.JDialog d_agregar_producto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JTextField txt_busqueda;
    // End of variables declaration//GEN-END:variables
}
