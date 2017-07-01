
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.m_venta_detalle;

public class c_venta_detalle {
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_venta_detalle (Connection conn){
        this.conn=conn;
    }
    public String eliminarFila(m_venta_detalle venta_detalles){
      String sql= "DELETE FROM venta_detalle WHERE id = ? AND codigo = ?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setInt(1, venta_detalles.getId());
          ps.setInt(2, venta_detalles.getCod());
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                return "Se ha borrado registro...";
            }else{
                JOptionPane.showMessageDialog(null, "Registro no Eliminado");
                return "Error al querer borrar...";
              }
        }catch (SQLException e) {
            return e.getMessage();
     }
    }
    
    public String eliminarTabla(m_venta_detalle venta_detalles){
      String sql= "DELETE FROM venta_detalle WHERE id = ?;";
      try {
          PreparedStatement ps = getConn().prepareStatement(sql);
          ps.setInt(1, venta_detalles.getId());
            if (ps.executeUpdate() > 0) {
                //JOptionPane.showMessageDialog(null, "TABLA ELIMINADA CANCELADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                return "";
            }else{
                //JOptionPane.showMessageDialog(null, "TABLA ELIMINADA NO CANCELADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                return "";
              }
        }catch (SQLException e) {
            return e.getMessage();
     }
    }
    
    public java.util.List<m_venta_detalle> maxCodigo(m_venta_detalle venta_detalles) {
    String sql = ("SELECT COALESCE (MAX (codigo),0)+1 FROM venta_detalle");
    java.util.List<m_venta_detalle> mxCodigo = new ArrayList<m_venta_detalle>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_venta_detalle mvd = new m_venta_detalle();
            mvd.setCod(Integer.parseInt(rs.getString(1)));
            mxCodigo.add(mvd);
            }
              return mxCodigo;
            } else {
              return null;
        }
     } catch (SQLException e) {
       return null;
       }
  }  
}
