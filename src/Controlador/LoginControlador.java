/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Credenciales;
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
public class LoginControlador implements ActionListener{
    
    private final Login login;
    private final ModeloLogin modLogin;
    private Credenciales credencial;
    
    public LoginControlador (Login login, ModeloLogin modLogin) {
        
        this.login = login;
        this.modLogin = modLogin;
        this.login.btnEntrar.addActionListener(this);
        
    }
    
    // Método que inicia el frame
    public void iniciarLogin() {
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
            
            JOptionPane.showMessageDialog(null, "Llene los campos correspondientes", "ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            
        }
    }

}
