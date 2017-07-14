
package controlador;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.m_proveedor;

public class c_proveedor {
    Icon aceptar;
    Icon fail;
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_proveedor (Connection conn){
        this.conn=conn;
    }
    
    public java.util.List<m_proveedor> maxCodigo(m_proveedor pr) {
    String sql = ("SELECT COALESCE (MAX (id),0)+1 FROM proveedor");
    java.util.List<m_proveedor> mxCodigo = new ArrayList<m_proveedor>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_proveedor mpr = new m_proveedor();
            mpr.setCodigo(Integer.parseInt(rs.getString(1)));
            mxCodigo.add(mpr);
            }
              return mxCodigo;
            } else {
              return null;
        }
     } catch (SQLException e) {
       return null;
       }
  }  
    
    public String agregar(m_proveedor proveedores){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        String SQL ="INSERT INTO proveedor(id, ci_ruc, nombre_razon, telefono) " +
                    "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, proveedores.getCodigo());
            ps.setString(2, proveedores.getCi_ruc());
            ps.setString(3, proveedores.getNombre_razon());
            ps.setString(4, proveedores.getTelefono());
            if (ps.executeUpdate() > 0) {  
                JOptionPane.showMessageDialog(null, "AGREGADO","ATENCION",JOptionPane.WARNING_MESSAGE,aceptar);
                return "Agregado el registro.";
            }else {            
                JOptionPane.showMessageDialog(null, "NO AGREGADO","ATENCION",JOptionPane.WARNING_MESSAGE,fail);
                return "Error al agregar";
             }
        } catch (HeadlessException | SQLException e) {
            return e.getMessage();
        }
    }
    
    public java.util.List<m_proveedor> listar() {
    String sql = "SELECT id, ci_ruc, nombre_razon, telefono " +
                 "FROM proveedor ORDER BY id;";
    java.util.List<m_proveedor> listar = new ArrayList<m_proveedor>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
                  m_proveedor mp = new m_proveedor();
                  mp.setCodigo(rs.getInt(1));
                  mp.setCi_ruc(rs.getString(2));
                  mp.setNombre_razon(rs.getString(3));
                  mp.setTelefono(rs.getString(4));
                  listar.add(mp);
            }
            return listar;
            } else {
            return null;
        }
     } catch (SQLException e) {
         return null;
       }
  }
    
    public  java.util.List<m_proveedor> buscarProveedor(m_proveedor proveedores) {
    String sql = "SELECT id, ci_ruc, nombre_razon, telefono " +
                 "FROM proveedor WHERE ci_ruc LIKE ? OR nombre_razon LIKE ?;";
    java.util.List<m_proveedor> listaNombre = new ArrayList<m_proveedor>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setString(1, '%'+proveedores.getCi_ruc()+'%');
      ps.setString(2, '%'+proveedores.getNombre_razon()+'%');
      
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_proveedor mp = new m_proveedor();
            mp.setCodigo(rs.getInt(1));
            mp.setCi_ruc(rs.getString(2));
            mp.setNombre_razon(rs.getString(3));
            mp.setTelefono(rs.getString(4));
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
    
    public String modificar(m_proveedor proveedores){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
      String sql = "UPDATE proveedor "           
                  +"SET ci_ruc=?, nombre_razon=?, telefono=? " 
                  +"WHERE id=?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setString(1, proveedores.getCi_ruc());
          ps.setString(2, proveedores.getNombre_razon());
          ps.setString(3, proveedores.getTelefono());
          ps.setInt(4, proveedores.getCodigo());
          if (ps.executeUpdate() > 0) {
             JOptionPane.showMessageDialog(null, "MODIFICADO","ATENCION",JOptionPane.WARNING_MESSAGE,aceptar);
             return "registro modificado";
          } else {
             JOptionPane.showMessageDialog(null, "NO MODIFICADO","ATENCION",JOptionPane.WARNING_MESSAGE,fail);
             return "registro no modificado";
            }
      } catch (HeadlessException | SQLException e) {
          return e.getMessage();
      }
  }
}
