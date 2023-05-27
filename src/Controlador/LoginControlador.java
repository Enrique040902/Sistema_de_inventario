package Controlador;

import Modelo.Credenciales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.Login;
import Modelo.ModeloLogin;
import Vista.Home;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author dilan
 */
public class LoginControlador implements ActionListener {

    private final Login login;
    private final ModeloLogin modLogin;
    private Credenciales credencial;

    public LoginControlador(Login login, ModeloLogin modLogin) {

        this.login = login;
        this.modLogin = modLogin;
        this.login.btnEntrar.addActionListener(this);

    }

    // Método que inicia el frame
    public void iniciarLogin() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        login.setTitle("Login");
        login.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        validar();
    }

    private void validar() {
        String username = login.txtUsuario.getText();
        String contrasenia = String.valueOf(login.txtPassword.getPassword());

        if (!"".equals(username) || !"".equals(contrasenia)) {

            credencial = modLogin.log(username, contrasenia);

            if (credencial.getUsername() != null || credencial.getContrasenia() != null) {

                Home home = new Home();
                home.setVisible(true);
                login.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            JOptionPane.showMessageDialog(null, "Llene los campos correspondientes", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

        }
    }

}
