package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vista.Usuarios;

/**
 *
 * @author dilan
 */
public class HomeControlador implements ActionListener {

    Usuarios usuariosFrame = new Usuarios();
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if ("Usuarios".equals(e.getActionCommand())) {

            if (!usuariosFrame.isVisible()) {
                usuariosFrame.setLocationRelativeTo(null);
                usuariosFrame.setVisible(true);
            }
        }

    }

}
