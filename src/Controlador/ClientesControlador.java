package Controlador;

import Modelo.Cliente;
import Modelo.ModeloClientes;
import Vista.Clientes;

import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClientesControlador implements ActionListener {

    Clientes frmClientes;
    Cliente cliente;
    ModeloClientes modCliente;

    DefaultTableModel modTablaClientes = new DefaultTableModel();

    public ClientesControlador(Clientes frmClientes) {

        this.frmClientes = frmClientes;
        this.cliente = new Cliente();
        this.modCliente = new ModeloClientes();

        frmClientes.btnNuevo.addActionListener(this);
        frmClientes.btnGuardar.addActionListener(this);
        frmClientes.btnEliminar.addActionListener(this);
        frmClientes.btnEditar.addActionListener(this);
        frmClientes.btnConsultar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmClientes.btnConsultar) {
            listarClientes();
        } else if (e.getSource() == frmClientes.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmClientes.btnEditar) {
            editarCliente();
        } else if (e.getSource() == frmClientes.btnGuardar) {
            actuazliarCliente();
        } else if (e.getSource() == frmClientes.btnEliminar) {
            eliminarCliente();
        }

    }

    private void registrar() {

        String nombreCliente = frmClientes.jtxtNombre.getText();
        String telefono = frmClientes.jtxtTelefono.getText();
        String nombreCalle = frmClientes.jtxtNombreCalle.getText();
        String numeroCalle = frmClientes.jtxtNumeroCalle.getText();
        String estado = frmClientes.jtxtEstado.getText();

        // Se valida si los campos estan vacios
        if (nombreCliente.equals("") || telefono.equals("") || nombreCalle.equals("")
                || numeroCalle.equals("") || estado.equals("")) {

            JOptionPane.showMessageDialog(frmClientes, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else if (!telefono.matches("\\d+")) {

            JOptionPane.showMessageDialog(frmClientes, "Solo valores númericos", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else if (telefono.length() > 10) {
            JOptionPane.showMessageDialog(frmClientes, "Debe tener un maximo de 10 digitos", "Avertencia", JOptionPane.WARNING_MESSAGE);
        } else {

            if (modCliente.comprobarExistencia(nombreCliente) == 0) {
                cliente = modCliente.registrarCliente(nombreCliente, telefono, nombreCalle, numeroCalle, estado);
                listarClientes();
                limpiarCeldas();

                if (cliente.getNombre() != null || cliente.getTelefono() != null || cliente.getNombreCalle() != null || cliente.getNumeroCalle() != null || cliente.getEstado() != null) {
                    JOptionPane.showMessageDialog(frmClientes, "Cliente registrado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Cliente existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                limpiarCeldas();
            }

        }

    }

    // Método para listar los clientes registrados
    private void listarClientes() {

        modTablaClientes.setRowCount(0);
        modTablaClientes.setColumnCount(0);

        modTablaClientes.addColumn("ID");
        modTablaClientes.addColumn("Nombre");
        modTablaClientes.addColumn("Telefono");
        modTablaClientes.addColumn("Nombre de la calle");
        modTablaClientes.addColumn("Numero de la calle");
        modTablaClientes.addColumn("Estado");

        frmClientes.jtClientes.setModel(modTablaClientes);

        ResultSet rs = modCliente.consultarCliente();

        String[] datos = new String[6];

        try {

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modTablaClientes.addRow(datos);
            }
            frmClientes.jtClientes.setModel(modTablaClientes);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private void limpiarCeldas() {

        frmClientes.jtxtNombre.setText("");
        frmClientes.jtxtTelefono.setText("");
        frmClientes.jtxtNombreCalle.setText("");
        frmClientes.jtxtNumeroCalle.setText("");
        frmClientes.jtxtEstado.setText("");

    }

    private void editarCliente() {

        int fila = frmClientes.jtClientes.getSelectedRow();

        if (fila >= 0) {

            frmClientes.jtxtNombre.setText(frmClientes.jtClientes.getValueAt(fila, 1).toString());
            frmClientes.jtxtTelefono.setText(frmClientes.jtClientes.getValueAt(fila, 2).toString());
            frmClientes.jtxtNombreCalle.setText(frmClientes.jtClientes.getValueAt(fila, 3).toString());
            frmClientes.jtxtNumeroCalle.setText(frmClientes.jtClientes.getValueAt(fila, 4).toString());
            frmClientes.jtxtEstado.setText(frmClientes.jtClientes.getValueAt(fila, 5).toString());

        } else {
            JOptionPane.showMessageDialog(frmClientes, "Seleccione una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void actuazliarCliente() {

        if (frmClientes.jtxtNombre.getText().equals("") || frmClientes.jtxtTelefono.getText().equals("") || frmClientes.jtxtNombreCalle.getText().equals("")
                || frmClientes.jtxtNumeroCalle.getText().equals("") || frmClientes.jtxtEstado.getText().equals("")) {

            JOptionPane.showMessageDialog(frmClientes, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            modCliente.actualizarRegistro(frmClientes.jtxtNombre.getText(), frmClientes.jtxtTelefono.getText(), frmClientes.jtxtNombreCalle.getText(), frmClientes.jtxtNumeroCalle.getText(), frmClientes.jtxtEstado.getText());
            listarClientes();
            limpiarCeldas();
            JOptionPane.showMessageDialog(null, "Datos del cliente actualizados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    private void eliminarCliente() {

        int fila = frmClientes.jtClientes.getSelectedRow();

        if (fila >= 0) {

            String valor = frmClientes.jtClientes.getValueAt(fila, 0).toString();

            try {

                modCliente.elimiarRegistro(valor);
                listarClientes();
                JOptionPane.showMessageDialog(frmClientes, "Cliente eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

}
