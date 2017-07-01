
package controlador;

import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class comunes {
    public static void limpiar_txt(JTextField[] textfields)
    {    
        for(int  i=0; i<textfields.length; i++){
            textfields[i].setText("");
        }
    }
    public static void limpiar_radiobutton(JRadioButton[] jRadioButtons)
    {    
        for(int  i=0; i<jRadioButtons.length; i++){
            jRadioButtons[i].setSelected(false);
        }
    }
}
