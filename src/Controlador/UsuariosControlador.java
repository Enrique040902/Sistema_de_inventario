package Controlador;

import java.sql.*;
import Vista.Usuarios;
import Modelo.ModeloUsuarios;
import Modelo.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuariosControlador implements ActionListener {

    private Usuarios frmUsuarios;
    private ModeloUsuarios modUsuarios = new ModeloUsuarios();
    private Usuario usuario;

    DefaultTableModel modTablaUsuarios = new DefaultTableModel();

    public UsuariosControlador(Usuarios frmUsuarios, ModeloUsuarios modUsuarios) {

        this.frmUsuarios = frmUsuarios;
        this.modUsuarios = modUsuarios;

        // Se agrega funcionalida a los botones
        frmUsuarios.btnNuevo.addActionListener(this);
        frmUsuarios.btnGuardar.addActionListener(this);
        frmUsuarios.btnConsultar.addActionListener(this);
        frmUsuarios.btnEliminar.addActionListener(this);
        frmUsuarios.btnEditar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // se valida cual boton se presiono
        if (e.getSource() == frmUsuarios.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmUsuarios.btnGuardar) {
            actualizarUsuario();
        } else if (e.getSource() == frmUsuarios.btnConsultar) {
            listarUsuarios();
        } else if (e.getSource() == frmUsuarios.btnEliminar) {
            eliminarUsuario();
        } else if (e.getSource() == frmUsuarios.btnEditar) {
            editarUsuario();
        }
    }

    public void llenarPrivilegios() {
        // se llenan los privilegios
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

        boolean rolSeleccionado = frmUsuarios.jcbRol.getSelectedIndex() != 0;

        // se valida si algún campo esta vacío
        if ("".equals(frmUsuarios.jtxtNombreUsuario.getText()) || "".equals(frmUsuarios.jtxtNombre.getText()) || "".equals(frmUsuarios.jtxtApellidoPaterno.getText())
                || "".equals(frmUsuarios.jtxtApellidoMaterno.getText()) || "".equals(frmUsuarios.jtxtContrasenia.getText()) || "".equals(frmUsuarios.jcbRol.getSelectedItem().toString())) {

            JOptionPane.showMessageDialog(null, "Llene los campos correcpondientes", "Advertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            // Se valida si no se selecciono un rol para el usuario a registrar
            if (!rolSeleccionado) {
                JOptionPane.showMessageDialog(null, "Seleccione un rol para el usuario", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {

                if (modUsuarios.comprobarExistencia(username) == 0) {
                    usuario = modUsuarios.registrarUsuario(username, contrasenia, privilegio, nombre, apellidoPaterno, apellidoMaterno);
                    listarUsuarios();
                    limpiarCeldas();

                    if (usuario.getNombre() != null || usuario.getApellidoPaterno() != null || usuario.getApellidoMaterno() != null || usuario.getUsername() != null || usuario.getContrasenia() != null || usuario.getPrivilegio() != null) {
                        JOptionPane.showMessageDialog(frmUsuarios, "Usuario registrado correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "Usuario existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    // Método para listar a los usuarios en en jtUsuarios
    private void listarUsuarios() {

        modTablaUsuarios.setRowCount(0);
        modTablaUsuarios.setColumnCount(0);

        modTablaUsuarios.addColumn("Id usuario");
        modTablaUsuarios.addColumn("Nombre");
        modTablaUsuarios.addColumn("Apellido pat.");
        modTablaUsuarios.addColumn("Apellido mat.");
        modTablaUsuarios.addColumn("Username");
        modTablaUsuarios.addColumn("Contraseña");
        modTablaUsuarios.addColumn("Rol");

        frmUsuarios.jtUsuarios.setModel(modTablaUsuarios);

        ResultSet rs = modUsuarios.consultarUsuarios();

        String[] datos = new String[7];

        try {

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                modTablaUsuarios.addRow(datos);

            }
            frmUsuarios.jtUsuarios.setModel(modTablaUsuarios);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    // Método eliminar registro
    private void eliminarUsuario() {
        int fila = frmUsuarios.jtUsuarios.getSelectedRow();

        if (fila >= 0) {

            String valor = frmUsuarios.jtUsuarios.getValueAt(fila, 0).toString();

            try {
                modUsuarios.elimiarRegistro(valor);
                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                listarUsuarios();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);

        }
    }

    private void editarUsuario() {

        int fila = frmUsuarios.jtUsuarios.getSelectedRow();

        if (fila >= 0) {

            String rol = frmUsuarios.jtUsuarios.getValueAt(fila, 6).toString();

            frmUsuarios.jtxtNombre.setText(frmUsuarios.jtUsuarios.getValueAt(fila, 1).toString());
            frmUsuarios.jtxtApellidoPaterno.setText(frmUsuarios.jtUsuarios.getValueAt(fila, 2).toString());
            frmUsuarios.jtxtApellidoMaterno.setText(frmUsuarios.jtUsuarios.getValueAt(fila, 3).toString());
            frmUsuarios.jtxtNombreUsuario.setText(frmUsuarios.jtUsuarios.getValueAt(fila, 4).toString());
            frmUsuarios.jtxtContrasenia.setText(frmUsuarios.jtUsuarios.getValueAt(fila, 5).toString());

            frmUsuarios.jcbRol.setSelectedItem(rol);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    // Método para limpar las celdas
    private void limpiarCeldas() {

        frmUsuarios.jtxtNombreUsuario.setText("");
        frmUsuarios.jtxtNombre.setText("");
        frmUsuarios.jtxtApellidoPaterno.setText("");
        frmUsuarios.jtxtApellidoMaterno.setText("");
        frmUsuarios.jtxtContrasenia.setText("");
        frmUsuarios.jcbRol.setSelectedIndex(0);

    }

    private void actualizarUsuario() {

        String rol = frmUsuarios.jcbRol.getSelectedItem().toString();

        if ("".equals(frmUsuarios.jtxtNombreUsuario.getText()) || "".equals(rol) || "".equals(frmUsuarios.jtxtNombre.getText()) || "".equals(frmUsuarios.jtxtContrasenia.getText()) || "".equals(frmUsuarios.jtxtApellidoPaterno.getText()) || "".equals(frmUsuarios.jtxtApellidoMaterno.getText())) {

            JOptionPane.showMessageDialog(null, "Llene los campos a actualizar", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            modUsuarios.actualizarRegistro(frmUsuarios.jtxtNombreUsuario.getText(), rol, frmUsuarios.jtxtNombre.getText(), frmUsuarios.jtxtContrasenia.getText(), frmUsuarios.jtxtApellidoPaterno.getText(), frmUsuarios.jtxtApellidoMaterno.getText());
            listarUsuarios();
            limpiarCeldas();

            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
