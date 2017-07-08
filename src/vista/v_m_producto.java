
package vista;

import controlador.c_producto;
import controlador.comunes;
import controlador.conexion;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import modelo.m_producto;

public class v_m_producto extends javax.swing.JInternalFrame {
    JTextField[] textfields;
    JRadioButton[] jRadioButtons;
    public static String i_producto;
    public v_m_producto() {
        initComponents();
        t_codigo.setVisible(false);
        int a = principal.dp_principal.getWidth()-this.getWidth();
        int b = principal.dp_principal.getHeight()-this.getHeight();
        setLocation(a/2, b/2);
        i_producto="";
        textfields = new JTextField[]{t_producto,t_precio_compra,t_precio_venta,
            t_stock,t_buscar,t_cod_barra};
        jRadioButtons = new JRadioButton[]{rb_iva10,rb_iva5,rb_exentas,rb_si,rb_no}; 
        nuevo_registro();
        grilla();
        ver_datos();
        b_modificar.setEnabled(false);
    }

    private void agregar_registro(){
        Integer iva=0,stock=0;
        String activo="";
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
      
         
        if (rb_si.isSelected()==true) {
            activo="SI";
        }else if (rb_no.isSelected()==true) {
            activo="NO";
        }
        if(rb_iva5.isSelected()==true){
            iva=5;
        }else if (rb_iva10.isSelected()==true) {
            iva=10;
        }else if (rb_iva10.isSelected()==true){
            iva=0;
        }
 
        p.setCodigo(Integer.parseInt(t_codigo.getText().trim().toUpperCase()));
        p.setCod_barra(t_cod_barra.getText().trim());
        p.setProducto(t_producto.getText().trim().toUpperCase());
        p.setPrecio_compra(Double.parseDouble(t_precio_compra.getText().trim().toUpperCase()));
        p.setPrecio_venta(Double.parseDouble(t_precio_venta.getText().trim().toUpperCase()));
        if (t_stock.isEnabled()==true) {
            p.setStock(Integer.parseInt(t_stock.getText().trim().toUpperCase()));
        }else if (t_stock.isEnabled()==false) {
            p.setStock(stock);
        }

        p.setIva(iva); 
        p.setActivo(activo);
        c.agregar(p);
        conexion.cerrarConexion(con);
    }
    
    private void click(){
      Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        String codigo_barra="";
        int cod=0;
         try{
                codigo_barra=t_cod_barra.getText().trim();
                Statement sentencia = null;
                ResultSet resultado = null;           
                sentencia = con.createStatement();
                String sql ="SELECT codigo FROM producto WHERE cod_barra like '"+codigo_barra+"%';";
                resultado = sentencia.executeQuery(sql);                          
                while (resultado.next()){
                    cod =resultado.getInt(1);
                } 
                if(cod!=0){
                    JOptionPane.showMessageDialog(null, "CODIGO DE BARRA DE USO");
                    t_cod_barra.setText("");
                    t_cod_barra.requestFocus();
                } else{
                    t_producto.requestFocus();
                }
         }catch(SQLException e){} 
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
           t_product.setValueAt(mp.getCod_barra(), i, 1);
           t_product.setValueAt(mp.getProducto(), i, 2);
           t_product.setValueAt(mp.getPrecio_compra().intValue(), i, 3);
           t_product.setValueAt(mp.getPrecio_venta().intValue(), i, 4);
           t_product.setValueAt(mp.getStock(), i, 5);
           t_product.setValueAt(mp.getIva(), i, 6);
           t_product.setValueAt(mp.getActivo(), i, 7);
           i++;
           }
            conexion.cerrarConexion(con);  
    }
    
    private void nuevo_registro(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        List<m_producto> maxC = new ArrayList<m_producto>();
        maxC=c.maxCodigo(p);
        for(m_producto mp : maxC){
            this.t_codigo.setText(String.valueOf(mp.getCodigo()));
        }
        this.t_cod_barra.requestFocus();
        comunes.limpiar_txt(textfields);
        bg_activo.clearSelection();
        bg_iva.clearSelection();
        bg_busqueda.clearSelection();
        conexion.cerrarConexion(con);  
    }
    
    private void buscar(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
            p.setCod_barra(t_buscar.getText().trim());
            p.setProducto(t_buscar.getText().trim().toUpperCase());
            List<m_producto> verNombre = new ArrayList<m_producto>();
            verNombre=c.buscar(p);
            DefaultTableModel tbm = (DefaultTableModel)t_product.getModel();
        for(int i = tbm.getRowCount()-1; i >= 0; i--){
            tbm.removeRow(i);
            }
        int i = 0;
        for(m_producto mp : verNombre){
           tbm.addRow(new String[1]);
           t_product.setValueAt(mp.getCodigo(), i, 0);
           t_product.setValueAt(mp.getCod_barra(), i, 1);
           t_product.setValueAt(mp.getProducto(), i, 2);
           t_product.setValueAt(mp.getPrecio_compra().intValue(), i, 3);
           t_product.setValueAt(mp.getPrecio_venta().intValue(), i, 4);
           t_product.setValueAt(mp.getStock(), i, 5);
           t_product.setValueAt(mp.getIva(), i, 6);
           t_product.setValueAt(mp.getActivo(), i, 7);
           i++;
           }
            conexion.cerrarConexion(con);
        
    }
    
    private void setear(){
     //JOptionPane.showMessageDialog(null, "doble click");
          DefaultTableModel tbm;  
          int index = t_product.getSelectedRow();
          try {
              String codigo, c_barra, nombre, precio_compra, precio_venta, stock, iva,activo_;
              if(index==-1){
                    JOptionPane.showMessageDialog(this, "SELECCIONE UN PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                }else{
                  tbm= (DefaultTableModel) t_product.getModel();
                  codigo=t_product.getValueAt(index, 0).toString();
                  c_barra=t_product.getValueAt(index, 1).toString();
                  nombre=t_product.getValueAt(index, 2).toString();
                  precio_compra=t_product.getValueAt(index, 3).toString();
                  precio_venta=t_product.getValueAt(index, 4).toString();
                  stock=t_product.getValueAt(index, 5).toString();
                  iva=t_product.getValueAt(index, 6).toString();
                  activo_=t_product.getValueAt(index, 7).toString();
                  
                  if(iva.equals("5")){
                        t_codigo.setText(codigo);
                        t_cod_barra.setText(c_barra);
                        t_producto.setText(nombre);
                        t_precio_compra.setText(precio_compra);
                        t_precio_venta.setText(precio_venta);
                        t_stock.setText(stock);
                        rb_iva5.setSelected(true);
                        if(activo_.equals("SI")){
                          rb_si.setSelected(true);
                        }else if(activo_.equals("NO")){
                          rb_no.setSelected(true);
                        }
                  }
                  if(iva.equals("10")){
                    t_codigo.setText(codigo);
                    t_cod_barra.setText(c_barra);
                    t_producto.setText(nombre);
                    t_precio_compra.setText(precio_compra);
                    t_precio_venta.setText(precio_venta);
                    t_stock.setText(stock);
                    rb_iva10.setSelected(true);
                    if(activo_.equals("SI")){
                          rb_si.setSelected(true);
                    }else if(activo_.equals("NO")){
                          rb_no.setSelected(true);
                    }
                  }
                  if(iva.equals("0")){
                    t_codigo.setText(codigo);
                    t_cod_barra.setText(c_barra);
                    t_producto.setText(nombre);
                    t_precio_compra.setText(precio_compra);
                    t_precio_venta.setText(precio_venta);
                    t_stock.setText(stock);
                    rb_exentas.setSelected(true);
                    if(activo_.equals("SI")){
                          rb_si.setSelected(true);
                    }else if(activo_.equals("NO")){
                          rb_no.setSelected(true);
                    }
                  }            
              }
          } catch (HeadlessException e) {
          }
          this.t_producto.requestFocus();
    }
    
    private void modificar_registro(){
        Connection con = conexion.abrirConexion();
        m_producto p = new m_producto();
        c_producto c = new c_producto(con);
        Integer iva=0;
        String activo_="";
        p.setCod_barra(t_cod_barra.getText().trim());
        p.setProducto(t_producto.getText().trim().toUpperCase());
        p.setPrecio_compra(Double.parseDouble(t_precio_compra.getText().trim().toUpperCase()));
        p.setPrecio_venta(Double.parseDouble(t_precio_venta.getText().trim().toUpperCase()));
        p.setStock(Integer.parseInt(t_stock.getText().trim().toUpperCase()));
        if(rb_iva5.isSelected()==true){
            iva=5;
        }else if (rb_iva10.isSelected()==true) {
             iva=10;
        }else if (rb_exentas.isSelected()==true) {
             iva=0;
        }
        if (rb_si.isSelected()==true) {
            activo_="SI";
        }else if (rb_no.isSelected()==true) {
            activo_="NO";
        }
        p.setIva(iva);
        p.setActivo(activo_);
        p.setCodigo(Integer.parseInt(t_codigo.getText()));
        c.modificar(p);
        conexion.cerrarConexion(con);
    }
    
    private void grilla(){
        t_product.getTableHeader().setFont(new Font("Arial", 1, 13)); 
        ((DefaultTableCellRenderer)t_product.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        t_product.getColumnModel().getColumn(1).setCellRenderer(r);
        t_product.getColumnModel().getColumn(2).setCellRenderer(r);
        t_product.getColumnModel().getColumn(3).setCellRenderer(r);
        t_product.getColumnModel().getColumn(4).setCellRenderer(r);
        t_product.getColumnModel().getColumn(5).setCellRenderer(r);
        t_product.getColumnModel().getColumn(6).setCellRenderer(r);
        t_product.getColumnModel().getColumn(7).setCellRenderer(r);
        t_product.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t_product.setAutoResizeMode(t_product.AUTO_RESIZE_OFF);
        t_product.getColumnModel().getColumn(0).setMaxWidth(0);
        t_product.getColumnModel().getColumn(0).setMinWidth(0);
        t_product.getColumnModel().getColumn(0).setPreferredWidth(0);
        t_product.getColumnModel().getColumn(1).setPreferredWidth(130);
        t_product.getColumnModel().getColumn(2).setPreferredWidth(270);
        t_product.getColumnModel().getColumn(3).setPreferredWidth(103);
        t_product.getColumnModel().getColumn(4).setPreferredWidth(90);
        t_product.getColumnModel().getColumn(5).setPreferredWidth(55);
        t_product.getColumnModel().getColumn(6).setPreferredWidth(36);
        t_product.getColumnModel().getColumn(7).setPreferredWidth(65);
    }
    
     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_iva = new javax.swing.ButtonGroup();
        bg_busqueda = new javax.swing.ButtonGroup();
        bg_activo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rb_iva5 = new javax.swing.JRadioButton();
        t_precio_venta = new javax.swing.JTextField();
        rb_iva10 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        t_codigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        t_precio_compra = new javax.swing.JTextField();
        t_producto = new javax.swing.JTextField();
        b_agregar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        b_nuevo = new javax.swing.JButton();
        b_modificar = new javax.swing.JButton();
        rb_exentas = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        rb_si = new javax.swing.JRadioButton();
        rb_no = new javax.swing.JRadioButton();
        t_cod_barra = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        t_buscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_product = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };

        setClosable(true);
        setIconifiable(true);
        setTitle("ARTICULO");
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

        bg_iva.add(rb_iva5);
        rb_iva5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_iva5.setText("IVA 5%");

        t_precio_venta.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_precio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_precio_ventaKeyTyped(evt);
            }
        });

        bg_iva.add(rb_iva10);
        rb_iva10.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_iva10.setText("IVA 10%");
        rb_iva10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_iva10ActionPerformed(evt);
            }
        });

        t_stock.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_stockKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("PRECIO VENTA");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("ACTIVO");

        t_codigo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_codigo.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("CODIGO");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("ARTICULO");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("PRECIO COMPRA");

        t_precio_compra.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_precio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_precio_compraKeyTyped(evt);
            }
        });

        t_producto.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        b_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/agregar-editar-icono-6607-32.png"))); // NOI18N
        b_agregar.setText("AGREGAR");
        b_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("IVA");

        b_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/bg-nueva-pestana-icono-8859-32.png"))); // NOI18N
        b_nuevo.setText("NUEVO");
        b_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_nuevoActionPerformed(evt);
            }
        });

        b_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/cambio-de-registro-de-documentos-una-pluma-para-escribir-texto-icono-5178-32.png"))); // NOI18N
        b_modificar.setText("MODIFICAR");
        b_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_modificarActionPerformed(evt);
            }
        });

        bg_iva.add(rb_exentas);
        rb_exentas.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_exentas.setText("EXENTAS");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("STOCK");

        bg_activo.add(rb_si);
        rb_si.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_si.setText("SI");

        bg_activo.add(rb_no);
        rb_no.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        rb_no.setText("NO");

        t_cod_barra.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_cod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                t_cod_barraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_cod_barraKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabel6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel7)))
                                .addGap(53, 53, 53))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(10, 10, 10)))
                                .addGap(18, 18, 18))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(t_stock, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                .addComponent(t_precio_venta, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(t_precio_compra, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(t_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rb_si)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_no))
                            .addComponent(t_cod_barra, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(t_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rb_iva5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_iva10)
                        .addGap(2, 2, 2)
                        .addComponent(rb_exentas)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b_modificar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(t_cod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(t_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(t_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(t_precio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(t_precio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(t_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rb_si)
                            .addComponent(rb_no))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_iva5)
                                .addComponent(rb_iva10)
                                .addComponent(rb_exentas))
                            .addComponent(jLabel6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSQUEDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        t_buscar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        t_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_buscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_buscarKeyTyped(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graficos/herramienta-de-busqueda-de-icono-8960-32.png"))); // NOI18N

        t_product.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        t_product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "COD BARRA", "ARTICULO", "PRE. COMPRA", "PRE. VENTA", "STOCK", "IVA", "ACTIVO"
            }
        ));
        t_product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_productMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_product);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addGap(29, 29, 29)
                .addComponent(t_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(t_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        String p,prc,prv,s,cod;
        cod=t_cod_barra.getText();
        p=t_producto.getText();
        prc=t_precio_compra.getText();
        prv=t_precio_venta.getText();
        s=t_stock.getText();
        if (t_stock.isEnabled()==true) {
            if (cod.equals("")&&p.equals("")&& prc.equals("")&&prv.equals("")&&s.equals("")&&rb_iva5.isSelected()==false&&rb_iva10.isSelected()==false&&rb_exentas.isSelected()==false) {
                JOptionPane.showMessageDialog(this, "COMPLETE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_cod_barra.requestFocus();
            }else if (cod.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE CODIGO DE BARRA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_cod_barra.requestFocus();
            }else if (p.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_producto.requestFocus();
            }else if (prc.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE PRECIO COMPRA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_precio_compra.requestFocus();
            }else if (prv.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE PRECIO VENTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_precio_venta.requestFocus();
            }else if (s.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE STOCK","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_stock.requestFocus();
            }else if (rb_si.isSelected()==false&&rb_no.isSelected()==false) {
                JOptionPane.showMessageDialog(this, "SELECCIONE ACTIVIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
            else if (rb_iva5.isSelected()==false&&rb_iva10.isSelected()==false&&rb_exentas.isSelected()==false) {
                JOptionPane.showMessageDialog(this, "SELECCIONE VALOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
            else{
                  agregar_registro();
                  nuevo_registro();
                  ver_datos();
                  b_modificar.setEnabled(false);
            }
        }else if (t_stock.isEnabled()==false) {
            if (p.equals("")&& prc.equals("")&&prv.equals("")&&rb_iva5.isSelected()==false&&rb_iva10.isSelected()==false&&rb_exentas.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "COMPLETE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_producto.requestFocus();
            }else if (p.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_producto.requestFocus();
            }else if (prc.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE PRECIO COMPRA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_precio_compra.requestFocus();
            }else if (prv.equals("")){
                JOptionPane.showMessageDialog(this, "INGRESE PRECIO VENTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                t_precio_venta.requestFocus();
            }else if (rb_si.isSelected()==false&&rb_no.isSelected()==false) {
                JOptionPane.showMessageDialog(this, "SELECCIONE ACTIVIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
            else if (rb_iva5.isSelected()==false&&rb_iva10.isSelected()==false&&rb_exentas.isSelected()==false) {
                JOptionPane.showMessageDialog(this, "SELECCIONE VALOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
            else{
                  agregar_registro();
                  nuevo_registro();
                  ver_datos();
                  b_modificar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_b_agregarActionPerformed

    private void t_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscarKeyReleased
        buscar();
    }//GEN-LAST:event_t_buscarKeyReleased

    private void t_productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_productMouseClicked
      t_product.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
      if(e.getClickCount()==2){
          setear();
          b_modificar.setEnabled(true);
          b_agregar.setEnabled(false);
      }}
     });
    }//GEN-LAST:event_t_productMouseClicked

    private void b_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevoActionPerformed
        nuevo_registro();
        b_agregar.setEnabled(true);
        b_modificar.setEnabled(false);
    }//GEN-LAST:event_b_nuevoActionPerformed

    private void t_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_buscarKeyTyped
        char tecla=evt.getKeyChar();
        if(tecla==KeyEvent.VK_ENTER){
           buscar();
        }else if(tecla==KeyEvent.VK_ESCAPE){
            t_buscar.setText("");
        }
    }//GEN-LAST:event_t_buscarKeyTyped

    private void b_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_modificarActionPerformed
        String p,prc,prv,s,cod;
        cod=t_cod_barra.getText();
        p=t_producto.getText();
        prc=t_precio_compra.getText();
        prv=t_precio_venta.getText();
        s=t_stock.getText();
        if (cod.equals("")&&p.equals("")&& prc.equals("")&&prv.equals("")&&s.equals("")&&rb_iva5.isSelected()==false&&rb_iva10.isSelected()==false&&rb_exentas.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "COMPLETE LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_cod_barra.requestFocus();
        }else if (cod.equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE CODIGO DE BARRA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_cod_barra.requestFocus();
        }else if (p.equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE PRODUCTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_producto.requestFocus();
        }else if (prc.equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE PRECIO COMPRA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_precio_compra.requestFocus();
        }else if (prv.equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE PRECIO VENTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_precio_venta.requestFocus();
        }else if (s.equals("")){
            JOptionPane.showMessageDialog(this, "INGRESE STOCK","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            t_stock.requestFocus();
        }else if (rb_si.isSelected()==false&&rb_no.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "SELECCIONE ACTIVIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else if (rb_iva5.isSelected()==false&&rb_iva10.isSelected()==false&&rb_exentas.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "SELECCIONE VALOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            modificar_registro();
            nuevo_registro();
            ver_datos();
            b_agregar.setEnabled(true);
            b_modificar.setEnabled(false);
        }
    }//GEN-LAST:event_b_modificarActionPerformed

    private void rb_iva10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_iva10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_iva10ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        i_producto=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        nuevo_registro();
    }//GEN-LAST:event_formInternalFrameActivated

    private void t_precio_compraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_precio_compraKeyTyped
            char digito = evt.getKeyChar();
            if(!Character.isDigit(digito)){  
            evt.consume();
            } 
    }//GEN-LAST:event_t_precio_compraKeyTyped

    private void t_precio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_precio_ventaKeyTyped
            char digito = evt.getKeyChar();
            if(!Character.isDigit(digito)){  
            evt.consume();
            } 
    }//GEN-LAST:event_t_precio_ventaKeyTyped

    private void t_stockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_stockKeyTyped
            char digito = evt.getKeyChar();
            if(!Character.isDigit(digito)){  
            evt.consume();
            } 
    }//GEN-LAST:event_t_stockKeyTyped

    private void t_cod_barraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_cod_barraKeyTyped
        char tecla=evt.getKeyChar();
        if(tecla==KeyEvent.VK_ENTER){
           click(); 
        }
    }//GEN-LAST:event_t_cod_barraKeyTyped

    private void t_cod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_cod_barraKeyPressed
    }//GEN-LAST:event_t_cod_barraKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_agregar;
    public javax.swing.JButton b_modificar;
    private javax.swing.JButton b_nuevo;
    private javax.swing.ButtonGroup bg_activo;
    private javax.swing.ButtonGroup bg_busqueda;
    private javax.swing.ButtonGroup bg_iva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rb_exentas;
    private javax.swing.JRadioButton rb_iva10;
    private javax.swing.JRadioButton rb_iva5;
    private javax.swing.JRadioButton rb_no;
    private javax.swing.JRadioButton rb_si;
    private javax.swing.JTextField t_buscar;
    private javax.swing.JTextField t_cod_barra;
    private javax.swing.JTextField t_codigo;
    private javax.swing.JTextField t_precio_compra;
    private javax.swing.JTextField t_precio_venta;
    private javax.swing.JTable t_product;
    private javax.swing.JTextField t_producto;
    public static final javax.swing.JTextField t_stock = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}
