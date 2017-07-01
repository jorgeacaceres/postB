
package vista;

import controlador.c_compra;
import controlador.c_producto;
import controlador.comunes;
import controlador.conexion;
import java.awt.Font;
import java.awt.JobAttributes;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.m_compra;
import modelo.m_producto;

public class v_compra extends javax.swing.JInternalFrame {
    Icon grabar;
    Icon eliminar;
    DefaultTableModel tbm;  
    public static String i_compra;
    public v_compra() {
        initComponents();
        grabar= new ImageIcon("src/graficos/guardar-archivo-icono-6713-32.png");
        eliminar= new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        i_compra="";
         grilla_compra();
        grilla_filtro();
        ver_datos();
        tf_usuario.setVisible(false);
        tf_nro_compra.setVisible(false);
        nuevo_registro();
    }
     private Connection conn;
         public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public v_compra (Connection conn){
        this.conn=conn;
    }
    private void buscar(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        if (rb_codigo.isSelected()==true) {
            p.setCodigo(Integer.parseInt(t_buscar.getText()));
            List<m_producto> verCodigo = new ArrayList<m_producto>();
            verCodigo=c.listarCodigo(p);
            DefaultTableModel tbm = (DefaultTableModel)t_filtro.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : verCodigo){
           tbm.addRow(new String[1]);
           t_filtro.setValueAt(mp.getCodigo(), i, 0);
           t_filtro.setValueAt(mp.getProducto(), i, 1);
           t_filtro.setValueAt(mp.getStock(), i, 2);
           t_filtro.setValueAt(mp.getPrecio_compra().intValue(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);  
        }else if (rb_producto.isSelected()==true) {
            p.setProducto(t_buscar.getText().trim().toUpperCase());
            List<m_producto> verNombre = new ArrayList<m_producto>();
            verNombre=c.listarNombre(p);
            DefaultTableModel tbm = (DefaultTableModel)t_filtro.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : verNombre){
           tbm.addRow(new String[1]);
           t_filtro.setValueAt(mp.getCodigo(), i, 0);
           t_filtro.setValueAt(mp.getProducto(), i, 1);
           t_filtro.setValueAt(mp.getStock(), i, 2);
           t_filtro.setValueAt(mp.getPrecio_compra().intValue(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);
        }
    }
    
    private void agregar_producto(){
         
        int fselect = t_filtro.getSelectedRow();
        try {
            String codigo, producto, stock, precio_compra,cantidad;
            
                tbm=(DefaultTableModel) t_filtro.getModel();
                codigo=t_filtro.getValueAt(fselect, 0).toString();
                producto=t_filtro.getValueAt(fselect, 1).toString();
                //stock=t_filtro.getValueAt(fselect, 2).toString();
                precio_compra=t_filtro.getValueAt(fselect, 3).toString();
                cantidad=(t_cantidad.getText());
                
                tbm=(DefaultTableModel) t_compra.getModel();
                String filaelemento[] = {codigo,producto,cantidad,precio_compra};
                tbm.addRow(filaelemento);
            
        } catch (Exception e) {
        }
    }
    
    private void ver_datos(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        List<m_producto> listar = new ArrayList<m_producto>();
        listar=c.listar();
        DefaultTableModel tbm = (DefaultTableModel)t_filtro.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : listar){
           tbm.addRow(new String[1]);
           t_filtro.setValueAt(mp.getCodigo(), i, 0);
           t_filtro.setValueAt(mp.getProducto(), i, 1);
           t_filtro.setValueAt(mp.getStock(), i, 2);
           t_filtro.setValueAt(mp.getPrecio_compra().intValue(), i, 3);
           i++;
           }
            conexion.cerrarConexion(con);  
    }
    
    private void limpiaTabla(){
        try{
            DefaultTableModel tbm = (DefaultTableModel) t_compra.getModel();
            for(int i=0; i<t_compra.getRowCount(); i++){
                tbm.removeRow(i); 
                 i-=1;
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void agregar_detalle(){
        Connection con = conexion.abrirConexion();
        String nro_factura,codigo_producto, cantidad,precio_compra;
        Integer id=0,cod_pro,cant,cod = 0;
        Double p_compra=0d;
        String sql = "INSERT INTO compra_detalle(id, codigo_producto, cantidad, codigo, precio_compra) " 
                    +"VALUES (?, ?, ?, ?, ?);";
        if (t_compra.getRowCount()>0) {
            for(int i=0; i<t_compra.getRowCount();i++){               
                try {
                    try {
                    Statement sentencia = null;
                    ResultSet resultado = null;
                    sentencia = con.createStatement();
                    resultado = sentencia.executeQuery("SELECT COALESCE (MAX (codigo),0)+1 FROM compra_detalle");
                    while (resultado.next()){
                        cod=resultado.getInt(1);
                    }  
                    } catch (Exception e) {
                    }
                    nro_factura=tf_nro_compra.getText();
                    codigo_producto = t_compra.getValueAt(i, 0).toString();
                    cantidad= t_compra.getValueAt(i, 2).toString();
                    precio_compra= t_compra.getValueAt(i, 3).toString();
                    id=Integer.parseInt(nro_factura);
                    cod_pro=Integer.parseInt(codigo_producto);
                    cant=Integer.parseInt(cantidad);
                    p_compra=Double.parseDouble(precio_compra);
                    
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, id);
                    ps.setInt(2, cod_pro);
                    ps.setInt(3, cant);
                    ps.setInt(4, cod);
                    ps.setDouble(5, p_compra);
                    ps.executeUpdate();
                    } catch (SQLException ex) {
                    Logger.getLogger(v_compra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JOptionPane.showMessageDialog(this,"REGISTRADO","ATENCION",JOptionPane.WARNING_MESSAGE,grabar);
        }else{
            JOptionPane.showMessageDialog(this, "CARGUE PRODUCTOS");
        }
        limpiaTabla();
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
        t_filtro.setAutoResizeMode(t_filtro.AUTO_RESIZE_OFF);
        t_filtro.getColumnModel().getColumn(0).setPreferredWidth(45);
        t_filtro.getColumnModel().getColumn(1).setPreferredWidth(290);
        t_filtro.getColumnModel().getColumn(2).setPreferredWidth(70);
        t_filtro.getColumnModel().getColumn(3).setPreferredWidth(108);
        t_filtro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void grilla_compra(){
        t_compra.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_compra.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_compra.getColumnModel().getColumn(0).setCellRenderer(r);  
        t_compra.getColumnModel().getColumn(1).setCellRenderer(r);
        t_compra.getColumnModel().getColumn(2).setCellRenderer(r);
        t_compra.getColumnModel().getColumn(3).setCellRenderer(r);
        t_compra.setAutoResizeMode(t_filtro.AUTO_RESIZE_OFF);
        t_compra.getColumnModel().getColumn(0).setPreferredWidth(50);
        t_compra.getColumnModel().getColumn(1).setPreferredWidth(240);
        t_compra.getColumnModel().getColumn(2).setPreferredWidth(90);
        t_compra.getColumnModel().getColumn(3).setPreferredWidth(115);
        t_compra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void eliminar(){
        int fsel,resp,fil;

        try {
            fsel = t_compra.getSelectedRow();
            if (fsel==-1) {
                JOptionPane.showMessageDialog(null,"SELECCIONE UN PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                resp=JOptionPane.showConfirmDialog(null,"DESEA ELIMINAR ","ELIMINAR",JOptionPane.YES_OPTION);
                
                if (resp==JOptionPane.YES_OPTION) {
                     tbm=(DefaultTableModel) t_compra.getModel();                 
                     tbm.removeRow(fsel);
                     JOptionPane.showMessageDialog(null,"PRODUCTO ELIMINADO","ELIMINAR",JOptionPane.WARNING_MESSAGE,eliminar);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"PRODUCTO NO ELIMINADO","ELIMINAR",JOptionPane.ERROR);
        }
    }
    
    private void nuevo_registro(){
        Connection con = conexion.abrirConexion();
        m_compra v = new m_compra();
        c_compra c = new c_compra(con);
        List<m_compra> maxC = new ArrayList<m_compra>();
        maxC=c.maxCodigo(v);
        for(m_compra mc : maxC){
            this.tf_nro_compra.setText(String.valueOf(mc.getId()));
        }
        conexion.cerrarConexion(con);  
    }
    private void agregar_cabecera(){
        Connection con = conexion.abrirConexion();
        m_compra cp = new m_compra();
        c_compra c = new c_compra(con);
        String usuario="";
        int u=0,t=0;
        try {
                    
                    usuario=tf_usuario.getText();
                    Statement sentencia = null;
                    ResultSet resultado = null;
                    sentencia = con.createStatement();
                    resultado = sentencia.executeQuery("SELECT id FROM usuario WHERE usuario = '"+usuario+"';");
                    while (resultado.next()){
                        u=resultado.getInt(1);
                    }  
        } catch (Exception e) {
        }
        cp.setId(Integer.parseInt(tf_nro_compra.getText()));
        cp.setUsuario(u);
        cp.setTotal(t);
        c.agregar(cp);
        conexion.cerrarConexion(con);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        d_agregar = new javax.swing.JDialog();
        p_busqueda = new javax.swing.JPanel();
        t_buscar = new javax.swing.JTextField();
        rb_codigo = new javax.swing.JRadioButton();
        rb_producto = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_filtro = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        b_agregar = new javax.swing.JButton();
        b_salir = new javax.swing.JButton();
        t_cantidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        bg_busqueda = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_compra = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        b_add = new javax.swing.JButton();
        b_eliminar = new javax.swing.JButton();
        b_close = new javax.swing.JButton();
        b_grabar = new javax.swing.JButton();
        tf_nro_compra = new javax.swing.JTextField();

        d_agregar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        d_agregar.setTitle("PRODUCTO");
        d_agregar.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                d_agregarWindowActivated(evt);
            }
        });

        p_busqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        t_buscar.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        t_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_buscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_buscarKeyTyped(evt);
            }
        });

        bg_busqueda.add(rb_codigo);
        rb_codigo.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        rb_codigo.setText("CODIGO");
        rb_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_codigoActionPerformed(evt);
            }
        });

        bg_busqueda.add(rb_producto);
        rb_producto.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        rb_producto.setText("ARTICULO");
        rb_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_productoActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        javax.swing.GroupLayout p_busquedaLayout = new javax.swing.GroupLayout(p_busqueda);
        p_busqueda.setLayout(p_busquedaLayout);
        p_busquedaLayout.setHorizontalGroup(
            p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_busquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(t_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rb_codigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rb_producto)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        p_busquedaLayout.setVerticalGroup(
            p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_busquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(p_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(t_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rb_codigo)
                        .addComponent(rb_producto)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        t_filtro.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_filtro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "ARTICULO", "STOCK", "P. COMPRA"
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

        t_cantidad.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        t_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_cantidadKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel1.setText("CANTIDAD");

        javax.swing.GroupLayout d_agregarLayout = new javax.swing.GroupLayout(d_agregar.getContentPane());
        d_agregar.getContentPane().setLayout(d_agregarLayout);
        d_agregarLayout.setHorizontalGroup(
            d_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregarLayout.createSequentialGroup()
                .addGroup(d_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(d_agregarLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(t_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(b_agregar)
                        .addGap(28, 28, 28)
                        .addComponent(b_salir))
                    .addGroup(d_agregarLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(d_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(p_busqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        d_agregarLayout.setVerticalGroup(
            d_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_agregarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(p_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(d_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_salir)
                    .addComponent(b_agregar)
                    .addComponent(t_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("CARGAR STOCK");
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

        t_compra.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_compra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "ARTICULO", "CANTIDAD", "P. COMPRA"
            }
        ));
        jScrollPane1.setViewportView(t_compra);

        b_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/agregar-editar-icono-6607-32.png"))); // NOI18N
        b_add.setText("ARTICULO");
        b_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_addActionPerformed(evt);
            }
        });

        b_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/eliminar-cancelar-icono-4935-32.png"))); // NOI18N
        b_eliminar.setText("ELIMINAR");
        b_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_eliminarActionPerformed(evt);
            }
        });

        b_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/salir-de-mi-perfil-icono-3964-32.png"))); // NOI18N
        b_close.setText("SALIR");
        b_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_closeActionPerformed(evt);
            }
        });

        b_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/guardar-archivo-icono-6713-32.png"))); // NOI18N
        b_grabar.setText("CARGAR");
        b_grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_grabarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_grabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b_close, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nro_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(b_add)
                        .addGap(18, 18, 18)
                        .addComponent(b_eliminar)
                        .addGap(18, 18, 18)
                        .addComponent(b_grabar)
                        .addGap(18, 18, 18)
                        .addComponent(b_close)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_nro_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_addActionPerformed
        d_agregar.setSize(580,475);
        d_agregar.setLocationRelativeTo(null);
        d_agregar.setModal(true);
        d_agregar.setVisible(true);
        ver_datos();
    }//GEN-LAST:event_b_addActionPerformed

    private void t_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscarKeyReleased
         buscar();
    }//GEN-LAST:event_t_buscarKeyReleased

    private void t_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscarKeyTyped
        if (rb_codigo.isSelected()==true) {
            char digito = evt.getKeyChar();
            if(!Character.isDigit(digito)){  
            evt.consume();
            } 
        }
    }//GEN-LAST:event_t_buscarKeyTyped

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        int fselect = t_filtro.getSelectedRow();
        String nl = System.getProperty("line.separator");

            if((fselect==-1)&&(t_cantidad.getText().equals(""))){   
                 JOptionPane.showMessageDialog(null, "INGRESE PRODUCTO"+ nl +"INGRESE CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if (fselect==-1){
                JOptionPane.showMessageDialog(null,"SELECCIONE UN PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if(t_cantidad.getText().equals("")){
                JOptionPane.showMessageDialog(null, "INGRESE CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_cantidad.requestFocus();
            }else{
                 agregar_producto();
                 ver_datos();
                 t_cantidad.setText("");
            }
    }//GEN-LAST:event_b_agregarActionPerformed

    private void b_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_grabarActionPerformed
       
         int c = t_compra.getRowCount();
        if (c==0) {
            JOptionPane.showMessageDialog(this, "CARGUE PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        agregar_cabecera();
        agregar_detalle();
        nuevo_registro();
        }
    }//GEN-LAST:event_b_grabarActionPerformed

    private void rb_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_codigoActionPerformed
        t_buscar.setText("");
        t_buscar.requestFocus();
    }//GEN-LAST:event_rb_codigoActionPerformed

    private void rb_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_productoActionPerformed
        t_buscar.setText("");
        t_buscar.requestFocus();
    }//GEN-LAST:event_rb_productoActionPerformed

    private void b_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_salirActionPerformed
         d_agregar.setVisible(false);
    }//GEN-LAST:event_b_salirActionPerformed

    private void b_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_closeActionPerformed
        i_compra=null;
        this.dispose();
    }//GEN-LAST:event_b_closeActionPerformed

    private void t_filtroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_filtroMouseClicked
        
    }//GEN-LAST:event_t_filtroMouseClicked

    private void b_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_eliminarActionPerformed
       eliminar();
    }//GEN-LAST:event_b_eliminarActionPerformed

    private void t_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_cantidadKeyTyped
            char digito = evt.getKeyChar();
            char tecla=evt.getKeyChar();
            if(!Character.isDigit(digito)){  
            evt.consume(); 
            }
            if(tecla==KeyEvent.VK_ENTER){
            agregar_producto();
             t_cantidad.setText("");
            }else if(tecla==KeyEvent.VK_ESCAPE){
                d_agregar.dispose();
            }
    }//GEN-LAST:event_t_cantidadKeyTyped

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_compra=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void d_agregarWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_d_agregarWindowActivated
        ver_datos();
    }//GEN-LAST:event_d_agregarWindowActivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_add;
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_close;
    private javax.swing.JButton b_eliminar;
    private javax.swing.JButton b_grabar;
    private javax.swing.JButton b_salir;
    private javax.swing.ButtonGroup bg_busqueda;
    private javax.swing.JDialog d_agregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel p_busqueda;
    private javax.swing.JRadioButton rb_codigo;
    private javax.swing.JRadioButton rb_producto;
    private javax.swing.JTextField t_buscar;
    private javax.swing.JTextField t_cantidad;
    private javax.swing.JTable t_compra;
    private javax.swing.JTable t_filtro;
    private javax.swing.JTextField tf_nro_compra;
    public static final javax.swing.JTextField tf_usuario = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}
