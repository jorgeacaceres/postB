
package controlador;

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
        +"precio_venta, stock, iva, activo)" 
        +"VALUES (?, ?, ?, "
        + "?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, productos.getCodigo());
            ps.setString(2, productos.getProducto());
            ps.setDouble(3, productos.getPrecio_compra());
            ps.setDouble(4, productos.getPrecio_venta());
            ps.setInt(5, productos.getStock());
            ps.setInt(6, productos.getIva());
            ps.setString(7, productos.getActivo());
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
    String sql = "SELECT codigo, nombre, precio_compra, precio_venta,"
            + "stock, iva, activo "
            + "FROM producto ORDER BY codigo";
    java.util.List<m_producto> listar = new ArrayList<m_producto>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
                  m_producto p = new m_producto();
                  p.setCodigo(rs.getInt(1));
                  p.setProducto(rs.getString(2));
                  p.setPrecio_compra(rs.getDouble(3));
                  p.setPrecio_venta(rs.getDouble(4));
                  p.setStock(rs.getInt(5));
                  p.setIva(rs.getInt(6));
                  p.setActivo(rs.getString(7));
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
    
  public  java.util.List<m_producto> listarCodigo(m_producto productos) {
    String sql = "SELECT codigo, nombre, precio_compra, precio_venta,"
            + "stock, iva, activo "
            + "FROM producto WHERE codigo = ? ORDER BY codigo";
    java.util.List<m_producto> listaCodigo = new ArrayList<m_producto>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setInt(1, productos.getCodigo());
      
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_producto mp = new m_producto();
            mp.setCodigo(rs.getInt(1));
            mp.setProducto(rs.getString(2));
            mp.setPrecio_compra(rs.getDouble(3));
            mp.setPrecio_venta(rs.getDouble(4));
            mp.setStock(rs.getInt(5));
            mp.setIva(rs.getInt(6));
            mp.setActivo(rs.getString(7));
            listaCodigo.add(mp);
            }
            return listaCodigo;
            } else {
            return null;
        }
     } catch (SQLException e) {
       return null;
    }
  }
  
    public  java.util.List<m_producto> listarNombre(m_producto productos) {
    String sql = "SELECT codigo, nombre, precio_compra, precio_venta,"
            + "stock, iva, activo "
            + "FROM producto WHERE  nombre like ? ORDER BY nombre";
    java.util.List<m_producto> listaNombre = new ArrayList<m_producto>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setString(1, '%'+productos.getProducto()+'%');
      
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_producto mp = new m_producto();
            mp.setCodigo(rs.getInt(1));
            mp.setProducto(rs.getString(2));
            mp.setPrecio_compra(rs.getDouble(3));
            mp.setPrecio_venta(rs.getDouble(4));
            mp.setStock(rs.getInt(5));
            mp.setIva(rs.getInt(6));
            mp.setActivo(rs.getString(7));
            listaNombre.add(mp);
            }
            return listaNombre;
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
                +"SET nombre=?, precio_compra=?, precio_venta=?, " 
                +"stock=?, iva=?, activo=?" 
                +"WHERE codigo=?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setString(1, productos.getProducto());
          ps.setDouble(2, productos.getPrecio_compra());
          ps.setDouble(3, productos.getPrecio_venta());
          ps.setInt(4, productos.getStock());
          ps.setInt(5, productos.getIva());
          ps.setString(6, productos.getActivo());
          ps.setInt(7, productos.getCodigo());
          if (ps.executeUpdate() > 0) {
             JOptionPane.showMessageDialog(null, "MODIFICADO","ATENCION",JOptionPane.WARNING_MESSAGE,aceptar);
             return "registro modificado";
          } else {
             JOptionPane.showMessageDialog(null, "NO MODIFICADO","ATENCION",JOptionPane.WARNING_MESSAGE,fail);
             return "registro no modificado";
            }
      } catch (Exception e) {
          return e.getMessage();
      }
  }
  
  public String eliminar(m_producto productos){
      aceptar= new ImageIcon("src/graficos/aceptar.png");
      fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
      String sql= "DELETE FROM producto WHERE codigo = ?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setInt(1, productos.getCodigo());
            if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "ELIMINADO","ATENCION",JOptionPane.WARNING_MESSAGE,aceptar);
                return "Se ha borrado registro...";
            }else{
                JOptionPane.showMessageDialog(null, "NO ELIMINADO","ATENCION",JOptionPane.WARNING_MESSAGE,fail);
                return "Error al querer borrar...";
              }
        }catch (SQLException e) {
            return e.getMessage();
     }
    }

}
