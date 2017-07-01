
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.m_r_venta;


public class c_r_venta {
    private Connection conn;
    public Connection getConn() {
        return conn;
    }
  
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public c_r_venta (Connection conn){
        this.conn=conn;
    }
    public String agregar_fecha ( m_r_venta rv)
       {
      String SQL = "INSERT INTO r_venta(f_inicio,f_fin) "
                  +"VALUES (?,?);";
       try {
           PreparedStatement ps = getConn().prepareStatement(SQL);          
           ps.setString(1,rv.getF_inicio());
           ps.setString(2,rv.getF_fin());        
           if (ps.executeUpdate() > 0) {
              return "Agregado el registro.";             
           } else {           
               return "Error al agregar";
             }
          } catch (SQLException e) {
              return e.getMessage();
          }
    } 
}
