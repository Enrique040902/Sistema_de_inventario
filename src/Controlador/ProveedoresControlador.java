package Controlador;

import Modelo.ModeloProveedores;
import Modelo.Proveedor;
import Vista.Proveedores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProveedoresControlador implements ActionListener {

    Proveedores frmProveedores;
    Proveedor proveedor = new Proveedor();
    ModeloProveedores modProveedores = new ModeloProveedores();

    DefaultTableModel modTablaProveedores = new DefaultTableModel();

    public ProveedoresControlador(Proveedores frmProveedores) {

        this.frmProveedores = frmProveedores;

        frmProveedores.btnConsultar.addActionListener(this);
        frmProveedores.btnNuevo.addActionListener(this);
        frmProveedores.btnGuardar.addActionListener(this);
        frmProveedores.btnEditar.addActionListener(this);
        frmProveedores.btnEliminar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmProveedores.btnConsultar) {
            listarProveedores();
        } else if (e.getSource() == frmProveedores.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmProveedores.btnEditar) {
            editarProveedores();
        } else if (e.getSource() == frmProveedores.btnGuardar) {
            actualizarProveedor();
        } else if (e.getSource() == frmProveedores.btnEliminar) {
            eliminarProveedores();
        }

    }

    private void registrar() {

        String nombreProveedor = frmProveedores.jtxtNombre.getText();
        String telefono = frmProveedores.jtxtTelefono.getText();
        String nombreCalle = frmProveedores.jtxtNombreCalle.getText();
        String numeroCalle = frmProveedores.jtxtNumeroCalle.getText();
        String codigoPostal = frmProveedores.jtxtCodigoPostal.getText();
        String estado = frmProveedores.jtxtEstado.getText();

        if (nombreProveedor.equals("") || telefono.equals("") || nombreCalle.equals("") || numeroCalle.equals("")
                || codigoPostal.equals("") || estado.equals("")) {
            JOptionPane.showMessageDialog(frmProveedores, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            if (modProveedores.comprobarExistencia(nombreProveedor) == 0) {
                proveedor = modProveedores.registrarCliente(nombreProveedor, telefono, nombreCalle, numeroCalle, codigoPostal, estado);
                listarProveedores();
                limpiarCeldas();

                if (proveedor.getNombre() != null || proveedor.getTelefono() != null || proveedor.getNombreCalle() != null || proveedor.getNumeroCalle() != null || proveedor.getCodigoPostal() != null || proveedor.getEstado() != null) {
                    JOptionPane.showMessageDialog(frmProveedores, "Cliente registrado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Proveedor existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                limpiarCeldas();
            }

        }

    }

    private void listarProveedores() {

        modTablaProveedores.setRowCount(0);
        modTablaProveedores.setColumnCount(0);

        modTablaProveedores.addColumn("ID");
        modTablaProveedores.addColumn("Nombre");
        modTablaProveedores.addColumn("Telefono");
        modTablaProveedores.addColumn("Nombre de la calle");
        modTablaProveedores.addColumn("Numero de la calle");
        modTablaProveedores.addColumn("Código postal");
        modTablaProveedores.addColumn("Estado");

        frmProveedores.jtProveedores.setModel(modTablaProveedores);

        ResultSet rs = modProveedores.consultarProveedores();

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
                modTablaProveedores.addRow(datos);
            }
            frmProveedores.jtProveedores.setModel(modTablaProveedores);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private void editarProveedores() {

        int fila = frmProveedores.jtProveedores.getSelectedRow();

        if (fila >= 0) {
            frmProveedores.jtxtNombre.setText(frmProveedores.jtProveedores.getValueAt(fila, 1).toString());
            frmProveedores.jtxtTelefono.setText(frmProveedores.jtProveedores.getValueAt(fila, 2).toString());
            frmProveedores.jtxtNombreCalle.setText(frmProveedores.jtProveedores.getValueAt(fila, 3).toString());
            frmProveedores.jtxtNumeroCalle.setText(frmProveedores.jtProveedores.getValueAt(fila, 4).toString());
            frmProveedores.jtxtCodigoPostal.setText(frmProveedores.jtProveedores.getValueAt(fila, 5).toString());
            frmProveedores.jtxtEstado.setText(frmProveedores.jtProveedores.getValueAt(fila, 6).toString());
        } else {
            JOptionPane.showMessageDialog(frmProveedores, "Seleccione una fila", "Aviso", JOptionPane.WARNING_MESSAGE);

        }

    }
    
    private void actualizarProveedor() {
        
        if (frmProveedores.jtxtNombre.getText().equals("") || frmProveedores.jtxtTelefono.getText().equals("") || frmProveedores.jtxtNombreCalle.getText().equals("")
                || frmProveedores.jtxtNumeroCalle.getText().equals("") || frmProveedores.jtxtCodigoPostal.getText().equals("") || frmProveedores.jtxtEstado.getText().equals("")) {

            JOptionPane.showMessageDialog(frmProveedores, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            modProveedores.actualizarRegistro(frmProveedores.jtxtNombre.getText(), frmProveedores.jtxtTelefono.getText(), frmProveedores.jtxtNombreCalle.getText(), frmProveedores.jtxtNumeroCalle.getText(), frmProveedores.jtxtCodigoPostal.getText(), frmProveedores.jtxtEstado.getText());
            listarProveedores();
            limpiarCeldas();
            JOptionPane.showMessageDialog(null, "Datos del proveedor actualizados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

        }
        
    }
    
    private void eliminarProveedores() {

        int fila = frmProveedores.jtProveedores.getSelectedRow();

        if (fila >= 0) {

            String valor = frmProveedores.jtProveedores.getValueAt(fila, 0).toString();

            try {

                modProveedores.elimiarRegistro(valor);
                listarProveedores();
                JOptionPane.showMessageDialog(frmProveedores, "Proveedor eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void limpiarCeldas() {

        frmProveedores.jtxtNombre.setText("");
        frmProveedores.jtxtTelefono.setText("");
        frmProveedores.jtxtNombreCalle.setText("");
        frmProveedores.jtxtNumeroCalle.setText("");
        frmProveedores.jtxtCodigoPostal.setText("");
        frmProveedores.jtxtEstado.setText("");

    }

}
