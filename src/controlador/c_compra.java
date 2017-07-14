
package controlador;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.m_compra;

public class c_compra {
    private Connection conn;
        public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public c_compra (Connection conn){
        this.conn=conn;
    }
    
    public java.util.List<m_compra> maxCodigo(m_compra compras) {
    String sql = ("SELECT COALESCE (MAX (id),0)+1 FROM compra");
    java.util.List<m_compra> mxCodigo = new ArrayList<m_compra>();
    try {
      PreparedStatement ps = getConn().prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
            m_compra mc = new m_compra();
            mc.setId(Integer.parseInt(rs.getString(1)));
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
    public String agregar(m_compra compras){
        String SQL ="INSERT INTO compra(id, id_usuario, total) " +
                    "VALUES (?, ?, ?);";
        try {
            PreparedStatement ps = getConn().prepareStatement(SQL); 
            ps.setInt(1, compras.getId());
            ps.setInt(2, compras.getUsuario());
            ps.setDouble(3, compras.getTotal());
           
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
}
