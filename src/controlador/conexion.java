
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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
        } catch (SQLException | ClassNotFoundException e) {
        }
        return con;
    }
    
    public static void cerrarConexion(Connection con) {
        try {
           con.close();
        } catch (SQLException e) {
        } catch (Exception e) {
        }
    }
}
