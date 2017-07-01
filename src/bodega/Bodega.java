package bodega;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import vista.principal;
import vista.v_sesion;

public class Bodega {
 
    
    public static void main(String[] args) {
         try {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                UIManager.put("nimbusBase",new Color(200,200,200));
                //UIManager.put("menu", new Color(61,89,171));
                //UIManager.put("control", new Color(0,0,0)) ;
                //UIManager.put("textForeground", new ColorUIResource(51,102,255));
	} catch (Exception e) {
		e.printStackTrace();
	}
        v_sesion v = new v_sesion();
        v.show();
    }
}
