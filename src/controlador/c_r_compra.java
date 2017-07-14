
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.m_r_compra;


public class c_r_compra {
    private Connection conn;
    public Connection getConn() {
        return conn;
    }
  
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public c_r_compra (Connection conn){
        this.conn=conn;
    }
    public String agregar_fecha ( m_r_compra rc)
       {
      String SQL = "INSERT INTO r_compra(f_inicio,f_fin) "
                  +"VALUES (?,?);";
       try {
           PreparedStatement ps = getConn().prepareStatement(SQL);          
           ps.setString(1,rc.getF_inicio());
           ps.setString(2,rc.getF_fin());        
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
