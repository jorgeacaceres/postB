
package controlador;

import java.awt.HeadlessException;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.m_producto;

public class c_producto {
    Icon aceptar;
    Icon fail;
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_producto (Connection conn){
        this.conn=conn;
    }
  
    public String agregar(m_producto productos){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        String SQL ="INSERT INTO producto(codigo, nombre, precio_compra, "
        +"precio_venta, stock, iva, activo, cod_barra,id_usuario)" 
        +"VALUES (?, ?, ?, "
        + "?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, productos.getCodigo());
            ps.setString(2, productos.getProducto());
            ps.setDouble(3, productos.getPrecio_compra());
            ps.setDouble(4, productos.getPrecio_venta());
            ps.setInt(5, productos.getStock());
            ps.setInt(6, productos.getIva());
            ps.setString(7, productos.getActivo());
            ps.setString(8, productos.getCod_barra());
            ps.setInt(9, productos.getUsuario());
            if (ps.executeUpdate() > 0) {  
                JOptionPane.showMessageDialog(null, "AGREGADO","ATENCION",JOptionPane.WARNING_MESSAGE,aceptar);
                return "Agregado el registro.";
            }else {            
                JOptionPane.showMessageDialog(null, "NO AGREGADO","ATENCION",JOptionPane.WARNING_MESSAGE,fail);
                return "Error al agregar";
             }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
   
    public java.util.List<m_producto> listar() {
    String sql = "SELECT codigo, cod_barra, nombre, precio_compra, precio_venta,"
            + "stock, iva, activo "
            + "FROM producto";
    java.util.List<m_producto> listar = new ArrayList<m_producto>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
                  m_producto p = new m_producto();
                  p.setCodigo(rs.getInt(1));
                  p.setCod_barra(rs.getString(2));
                  p.setProducto(rs.getString(3));
                  p.setPrecio_compra(rs.getDouble(4));
                  p.setPrecio_venta(rs.getDouble(5));
                  p.setStock(rs.getInt(6));
                  p.setIva(rs.getInt(7));
                  p.setActivo(rs.getString(8));
                  listar.add(p);
            }
            return listar;
            } else {
            return null;
        }
     } catch (SQLException e) {
         return null;
       }
  }
    public java.util.List<m_producto> maxCodigo(m_producto p) {
    String sql = ("SELECT COALESCE (MAX (codigo),0)+1 FROM producto");
    java.util.List<m_producto> mxCodigo = new ArrayList<m_producto>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_producto mp = new m_producto();
            mp.setCodigo(Integer.parseInt(rs.getString(1)));
            mxCodigo.add(mp);
            }
              return mxCodigo;
            } else {
              return null;
        }
     } catch (SQLException e) {
       return null;
       }
  }  
    
  public  java.util.List<m_producto> buscar(m_producto productos) {
    String sql = "SELECT codigo, cod_barra, nombre, precio_compra, precio_venta,"
            + "stock, iva, activo "
            + "FROM producto WHERE  cod_barra like ? OR nombre like ?";
    java.util.List<m_producto> busqueda = new ArrayList<m_producto>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setString(1, '%'+productos.getCod_barra()+'%');
      ps.setString(2, '%'+productos.getProducto()+'%');
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_producto mp = new m_producto(); 
            mp.setCodigo(rs.getInt(1));
            mp.setCod_barra(rs.getString(2));
            mp.setProducto(rs.getString(3));
            mp.setPrecio_compra(rs.getDouble(4));
            mp.setPrecio_venta(rs.getDouble(5));
            mp.setStock(rs.getInt(6));
            mp.setIva(rs.getInt(7));
            mp.setActivo(rs.getString(8));
            busqueda.add(mp);
            }
            return busqueda;
            } else {
            return null;
        }
     } catch (SQLException e) {
       return null;
    }
  }
    
  public String modificar(m_producto productos){
      aceptar= new ImageIcon("src/graficos/aceptar.png");
      fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
      String sql = "UPDATE producto "           
                +"SET cod_barra=?, nombre=?, precio_compra=?, precio_venta=?, " 
                +"stock=?, iva=?, activo=?, id_usuario=?" 
                +"WHERE codigo=?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setString(1, productos.getCod_barra());
          ps.setString(2, productos.getProducto());
          ps.setDouble(3, productos.getPrecio_compra());
          ps.setDouble(4, productos.getPrecio_venta());
          ps.setInt(5, productos.getStock());
          ps.setInt(6, productos.getIva());
          ps.setString(7, productos.getActivo());
          ps.setInt(8, productos.getUsuario());
          ps.setInt(9, productos.getCodigo());
          if (ps.executeUpdate() > 0) {
             JOptionPane.showMessageDialog(null, "MODIFICADO","ATENCION",JOptionPane.WARNING_MESSAGE,aceptar);
             return "registro modificado";
          } else {
             JOptionPane.showMessageDialog(null, "NO MODIFICADO","ATENCION",JOptionPane.WARNING_MESSAGE,fail);
             return "registro no modificado";
            }
      } catch (SQLException e) {
          return e.getMessage();
      }
  }
  

}
