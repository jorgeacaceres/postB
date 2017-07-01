
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.m_cliente;

public class c_cliente {
    Icon aceptar;
    Icon fail;
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_cliente (Connection conn){
        this.conn=conn;
    }
    
    public java.util.List<m_cliente> maxCodigo(m_cliente c) {
    String sql = ("SELECT COALESCE (MAX (id),0)+1 FROM cliente");
    java.util.List<m_cliente> mxCodigo = new ArrayList<m_cliente>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_cliente mc = new m_cliente();
            mc.setCodigo(Integer.parseInt(rs.getString(1)));
            mxCodigo.add(mc);
            }
              return mxCodigo;
            } else {
              return null;
        }
     } catch (SQLException e) {
       return null;
       }
  }  
    
    public String agregar(m_cliente clientes){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        String SQL ="INSERT INTO cliente(id, ci_ruc, nombre_razon,telefono) " +
                    "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, clientes.getCodigo());
            ps.setString(2, clientes.getCi_ruc());
            ps.setString(3, clientes.getNombre_razon());
            ps.setString(4, clientes.getTelefono());
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
    
    public java.util.List<m_cliente> listar() {
    String sql = "SELECT id, ci_ruc, nombre_razon, telefono " +
                 "FROM cliente ORDER BY id ;";
    java.util.List<m_cliente> listar = new ArrayList<m_cliente>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
                  m_cliente c = new m_cliente();
                  c.setCodigo(rs.getInt(1));
                  c.setCi_ruc(rs.getString(2));
                  c.setNombre_razon(rs.getString(3));
                  c.setTelefono(rs.getString(4));
                  listar.add(c);
            }
            return listar;
            } else {
            return null;
        }
     } catch (SQLException e) {
         return null;
       }
  }
    
    public  java.util.List<m_cliente> buscarCliente(m_cliente clientes) {
    String sql = "SELECT id, ci_ruc, nombre_razon, telefono " +
                 "FROM cliente WHERE ci_ruc LIKE ? OR nombre_razon LIKE ?;";
    java.util.List<m_cliente> listaNombre = new ArrayList<m_cliente>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setString(1, '%'+clientes.getCi_ruc()+'%');
      ps.setString(2, '%'+clientes.getNombre_razon()+'%');
      
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_cliente mc = new m_cliente();
            mc.setCodigo(rs.getInt(1));
            mc.setCi_ruc(rs.getString(2));
            mc.setNombre_razon(rs.getString(3));
            mc.setTelefono(rs.getString(4));
            listaNombre.add(mc);
            }
            return listaNombre;
            } else {
            return null;
        }
     } catch (SQLException e) {
       return null;
    }
  }
    
    public String modificar(m_cliente clientes){
      aceptar= new ImageIcon("src/graficos/aceptar.png");
      fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
      String sql = "UPDATE cliente "           
                  +"SET ci_ruc=?, nombre_razon=?, telefono=? " 
                  +"WHERE id=?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setString(1, clientes.getCi_ruc());
          ps.setString(2, clientes.getNombre_razon());
          ps.setString(3, clientes.getTelefono());
          ps.setInt(4, clientes.getCodigo());
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
}
