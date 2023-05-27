package Main;

import Controlador.HomeControlador;
import Controlador.LoginControlador;
import Modelo.ModeloLogin;
import Vista.Login;
import Vista.Usuarios;

/**
 *
 * @author dilan
 */
public class Main {
    public static void main(String[] args) {
        
        ModeloLogin modLogin = new ModeloLogin();
        Login login = new Login();
        
        Usuarios usuarios = new Usuarios();
        
        // Inicia la vista de Login
        LoginControlador loginControlador = new LoginControlador(login, modLogin);
        loginControlador.iniciarLogin();
        login.setVisible(true);
    }
}
