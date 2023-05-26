/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Controlador.LoginControlador;
import Modelo.ModeloLogin;
import Vista.Login;

/**
 *
 * @author dilan
 */
public class Main {
    public static void main(String[] args) {
        
        ModeloLogin modLogin = new ModeloLogin();
        Login login = new Login();
        
        LoginControlador loginControlador = new LoginControlador(login, modLogin);
        loginControlador.iniciarLogin();
        login.setVisible(true);
    }
}
