package Controlador;

import Modelo.Credencial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.Login;
import Modelo.ModeloLogin;
import Vista.Home;
import javax.swing.JOptionPane;

/**
 *
 * @author dilan
 */
public class LoginControlador implements ActionListener {

    private final Login frmLogin;
    private final ModeloLogin modLogin;
    private Credencial credencial = new Credencial();

    public LoginControlador(Login login, ModeloLogin modLogin) {

        this.frmLogin = login;
        this.modLogin = modLogin;
        this.frmLogin.btnEntrar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        validar();
    }

    private void validar() {
        String username = frmLogin.txtUsuario.getText();
        String contrasenia = String.valueOf(frmLogin.txtPassword.getPassword());

        if (!"".equals(username) || !"".equals(contrasenia)) {
            
            credencial.setUsername(username);
            credencial.setContrasenia(contrasenia);

            if (modLogin.log(credencial)) {

                Home home = new Home();
                home.setVisible(true);
                frmLogin.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            JOptionPane.showMessageDialog(null, "Llene los campos correspondientes", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

        }
    }

}
