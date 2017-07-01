
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class conexion {
   
     public static Connection abrirConexion() {
        
        String login = "postgres";
        String pasword = "56789";
        String _JDBC = "org.postgresql.Driver";
        String _BDATOS = "jdbc:postgresql://localhost:5432/bode" ;
        Connection con = null;
        try {
            Class.forName(_JDBC );
            con = DriverManager.getConnection(_BDATOS,login,pasword);
            Statement sentencia = con.createStatement();
            //JOptionPane.showMessageDialog(null, "Conexion Exitosa ");
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Conexion Fallida ");
            //System.out.println(e.getMessage());
            
        } catch (ClassNotFoundException e) {
            //System.out.println(e.getMessage());
        } 
        return con;
    }
    
    public static void cerrarConexion(Connection con) {
        try {
           con.close();
              //JOptionPane.showMessageDialog(null, "Conexion Cerrada");
        } catch (SQLException e) {
           //System.out.println(e.getMessage());
        } catch (Exception e) {
           //System.out.println(e.getMessage());
        }
    }
}
