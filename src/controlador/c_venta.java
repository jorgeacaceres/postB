
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
import modelo.m_venta;


public class c_venta {
    Icon aceptar;
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_venta (Connection conn){
        this.conn=conn;
    }
  
    public String agregar_f(m_venta ventas){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        String SQL ="INSERT INTO venta( id, fecha,total,id_usuario,id_cliente) " +
                    "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, ventas.getId());
            ps.setString(2, ventas.getFecha());
            ps.setInt(3, ventas.getTotal());
            ps.setInt(4, ventas.getUsuario());
            ps.setInt(5, ventas.getCliente());
           
            if (ps.executeUpdate() > 0) {  
                return "Agregado el registro.";              
            }else {            
                JOptionPane.showMessageDialog(null, "No Agregado"); 
                return "Error al agregar";
             }
        } catch (HeadlessException | SQLException e) {
            return e.getMessage();
        }
    }
    
    
    
    public java.util.List<m_venta> maxCodigo(m_venta ventas) {
    String sql = ("SELECT COALESCE (MAX (id),0)+1 FROM venta");
    java.util.List<m_venta> mxCodigo = new ArrayList<m_venta>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_venta mv = new m_venta();
            mv.setId(Integer.parseInt(rs.getString(1)));
            mxCodigo.add(mv);
            }
              return mxCodigo;
            } else {
              return null;
        }
     } catch (SQLException e) {
       return null;
       }
    }
  public String agregar_t(m_venta ventas){
        aceptar= new ImageIcon("src/graficos/aceptar.png");
        String SQL ="INSERT INTO venta( id, fecha,total,id_usuario) " +
                    "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, ventas.getId());
            ps.setString(2, ventas.getFecha());
            ps.setInt(3, ventas.getTotal());
            ps.setInt(4, ventas.getUsuario());
           
            if (ps.executeUpdate() > 0) {  
                return "Agregado el registro.";              
            }else {            
                JOptionPane.showMessageDialog(null, "No Agregado"); 
                return "Error al agregar";
             }
        } catch (HeadlessException | SQLException e) {
            return e.getMessage();
        }
    }

   public String eliminar(m_venta ventas){
      String sql= "DELETE FROM venta WHERE id = ?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setInt(1, ventas.getId());
            if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "FACTURA CANCELADA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                return "";
            }else{
             //JOptionPane.showMessageDialog(null, "FACTURA NO CANCELADA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                return "";
              }
        }catch (SQLException e) {
            return e.getMessage();
     }
    }

}
