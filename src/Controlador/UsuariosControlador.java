package Controlador;

import Vista.Usuarios;
import Modelo.ModeloUsuarios;
import Modelo.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class UsuariosControlador implements ActionListener {

    private Usuarios frmUsuarios;
    private ModeloUsuarios modUsuarios;
    private Usuario usuario;

    public UsuariosControlador(Usuarios frmUsuarios, ModeloUsuarios modUsuarios) {

        this.frmUsuarios = frmUsuarios;
        this.modUsuarios = modUsuarios;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        registrar();
    }
    
    public void llenarPrivilegios() {
        frmUsuarios.jcbRol.addItem("Seleciona un privelegio");
        frmUsuarios.jcbRol.addItem("Administrador");
        frmUsuarios.jcbRol.addItem("Empleado");
    }

    private void registrar() {
        
        String username = frmUsuarios.jtxtNombreUsuario.getText();
        String nombre = frmUsuarios.jtxtNombre.getText();
        String apellidoPaterno = frmUsuarios.jtxtApellidoPaterno.getText();
        String apellidoMaterno = frmUsuarios.jtxtApellidoMaterno.getText();
        String contrasenia = frmUsuarios.jtxtContrasenia.getText();
        String privilegio = frmUsuarios.jcbRol.getSelectedItem().toString();

        if (!"".equals(username)
                || !"".equals(nombre) || !"".equals(apellidoPaterno) || !"".equals(apellidoMaterno) || !"".equals(contrasenia)) {

            usuario = modUsuarios.registrarUsuario(username, contrasenia, privilegio, nombre, apellidoPaterno, apellidoMaterno);

            if (usuario.getNombre() != null || usuario.getApellidoPaterno() != null || usuario.getApellidoMaterno() != null || usuario.getUsername() != null || usuario.getContrasenia() != null || usuario.getPrivilegio() != null) {
                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Llene los campos", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }

    }

}
