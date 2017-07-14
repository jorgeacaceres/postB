
package controlador;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.m_usuario;

public class c_usuario {
    Icon aceptar;
    Icon fail;
    private Connection conn;
    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_usuario (Connection conn){
        this.conn=conn;
    }
    
    
    public java.util.List<m_usuario> maxCodigo(m_usuario u) {
    String sql = ("SELECT COALESCE (MAX (id),0)+1 FROM usuario");
    java.util.List<m_usuario> mxCodigo = new ArrayList<m_usuario>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_usuario mu = new m_usuario();
            mu.setId(Integer.parseInt(rs.getString(1)));
            mxCodigo.add(mu);
            }
              return mxCodigo;
            } else {
              return null;
        }
     } catch (SQLException e) {
       return null;
       }
  }  
    
    public String agregar(m_usuario usuarios){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
        String SQL ="INSERT INTO usuario(id, usuario, contra, activo, tipo) " +
                    "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, usuarios.getId());
            ps.setString(2, usuarios.getUsuario());
            ps.setString(3, usuarios.getContraseña());
            ps.setString(4, usuarios.getActivo());
            ps.setString(5, usuarios.getTipo());
            
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
    
    
    
    public java.util.List<m_usuario> listar() {
    String sql = "SELECT id, usuario, contra, activo, tipo " +
                 "FROM usuario ORDER BY id ;";
    java.util.List<m_usuario> listar = new ArrayList<m_usuario>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
                  m_usuario u = new m_usuario();
                  u.setId(rs.getInt(1));
                  u.setUsuario(rs.getString(2));
                  u.setContraseña(rs.getString(3));
                  u.setActivo(rs.getString(4));
                  u.setTipo(rs.getString(5));
                  listar.add(u);
            }
            return listar;
            } else {
            return null;
        }
     } catch (SQLException e) {
         return null;
       }
  }
    
    public  java.util.List<m_usuario> buscarCliente(m_usuario usuarios) {
    String sql = "SELECT id, usuario, contra, activo, tipo " +
                 "FROM usuario WHERE usuario LIKE ? ORDER BY usuario ;";
    java.util.List<m_usuario> listaNombre = new ArrayList<m_usuario>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setString(1, '%'+usuarios.getUsuario()+'%');
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_usuario mu = new m_usuario();
            mu.setId(rs.getInt(1));
            mu.setUsuario(rs.getString(2));
            mu.setContraseña(rs.getString(3));
            mu.setActivo(rs.getString(4));
            mu.setTipo(rs.getString(5));
            listaNombre.add(mu);
            }
            return listaNombre;
            } else {
            return null;
        }
     } catch (SQLException e) {
       return null;
    }
  }
    
    public String modificar(m_usuario usuarios){
      aceptar= new ImageIcon("src/graficos/aceptar.png");
      fail=new ImageIcon("src/graficos/eliminar-cancelar-icono-4935-32.png");
      String sql = "UPDATE usuario "           
                  +"SET contra=? " 
                  +"WHERE id=?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setString(1, usuarios.getContraseña());
          ps.setInt(2, usuarios.getId());
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
    public  List<m_usuario> buscarCodigo(m_usuario usuarios) {
    String sql = "SELECT usuario, contra, tipo "
           +" FROM usuario WHERE "
           +" usuario = ? AND contra = ? ";
    List<m_usuario> listaCodigo = new ArrayList<m_usuario>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ps.setString(1, usuarios.getUsuario());
      ps.setString(2, usuarios.getContraseña());
      
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_usuario mu = new m_usuario();
            mu.setUsuario(rs.getString(1));
            mu.setContraseña(rs.getString(2));
            mu.setTipo(rs.getString(3));
            listaCodigo.add(mu);
            }
            return listaCodigo;
            } else {
            return null;
            }
     } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "USUARIO O CONTRASEÑA INCORRECTA","ERROR",JOptionPane.ERROR);
           return null;
       }
    }
}
