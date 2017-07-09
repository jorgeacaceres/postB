
package reporte;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class reporte {
    public void reporte_stock() throws SQLException, JRException{
        String login = "postgres";
        String pasword = "56789";
        String _BDATOS = "jdbc:postgresql://localhost:5432/bode" ;
        Connection con = null;
        con=DriverManager.getConnection(_BDATOS,login,pasword);
        JasperReport reporte = null;
        reporte=(JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\jck\\Documents\\NetBeansProjects\\bodega\\src\\reporte\\r_stock.jasper");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, con);
        JasperViewer ver = new JasperViewer(print,false);
        ver.setExtendedState(MAXIMIZED_BOTH);
        ver.setTitle("STOCK");
        ver.setVisible(true);
    }
    public void reporte_productos() throws SQLException, JRException{
        String login = "postgres";
        String pasword = "56789";
        String _BDATOS = "jdbc:postgresql://localhost:5432/bode" ;
        Connection con = null;
        con=DriverManager.getConnection(_BDATOS,login,pasword);
        JasperReport reporte = null;
        reporte=(JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\jck\\Documents\\NetBeansProjects\\bodega\\src\\reporte\\r_producto.jasper");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, con);
        JasperViewer ver = new JasperViewer(print,false);
        ver.setExtendedState(MAXIMIZED_BOTH);
        ver.setTitle("PRODUCTOS");
        ver.setVisible(true);
    }
    public void reporte_compra() throws SQLException, JRException{
        String login = "postgres";
        String pasword = "56789";
        String _BDATOS = "jdbc:postgresql://localhost:5432/bode" ;
        Connection con = null;
        con=DriverManager.getConnection(_BDATOS,login,pasword);
        JasperReport reporte = null;
        reporte=(JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\jck\\Documents\\NetBeansProjects\\bodega\\src\\reporte\\r_compra.jasper");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, con);
        JasperViewer ver = new JasperViewer(print,false);
        ver.setExtendedState(MAXIMIZED_BOTH);
        ver.setTitle("COMPRA");
        ver.setVisible(true);
    }
    public void reporte_venta() throws SQLException, JRException{
        String login = "postgres";
        String pasword = "56789";
        String _BDATOS = "jdbc:postgresql://localhost:5432/bode" ;
        Connection con = null;
        con=DriverManager.getConnection(_BDATOS,login,pasword);
        JasperReport reporte = null;
        reporte=(JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\jck\\Documents\\NetBeansProjects\\bodega\\src\\reporte\\r_venta.jasper");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, con);
        JasperViewer ver = new JasperViewer(print,false);
        ver.setExtendedState(MAXIMIZED_BOTH);
        ver.setTitle("VENTA");
        ver.setVisible(true);
    }


}
