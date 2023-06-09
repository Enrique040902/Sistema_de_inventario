package Controlador;

import java.sql.*;
import Modelo.Entrada;
import Modelo.ModeloEntrada;
import Vista.Entradas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class EntradasControlador implements ActionListener {

    Entradas frmEntradas;
    Entrada entrada = new Entrada();
    ModeloEntrada modEntrada = new ModeloEntrada();

    DefaultTableModel modTablaEntradas = new DefaultTableModel();

    public EntradasControlador(Entradas frmEntradas) {

        this.frmEntradas = frmEntradas;

        frmEntradas.btnNuevo.addActionListener(this);
        frmEntradas.btnGuardar.addActionListener(this);
        frmEntradas.btnEliminar.addActionListener(this);
        frmEntradas.btnEditar.addActionListener(this);
        frmEntradas.btnConsultar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmEntradas.btnConsultar) {
            listarEntradas();
        } else if (e.getSource() == frmEntradas.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmEntradas.btnEditar) {
            editarEntradas();
        } else if (e.getSource() == frmEntradas.btnGuardar) {
            actualizarEntrada();
        } else if (e.getSource() == frmEntradas.btnEliminar) {
            eliminarEntrada();
        }

    }

    public void llenarProveedores() {
        modEntrada.consultarProveedores(frmEntradas.jcbProveedor);
    }

    private void registrar() {

        String proveedor = frmEntradas.jcbProveedor.getSelectedItem().toString();
        String cantidad = frmEntradas.jtxtCantidad.getText();
        String fecha = ((JTextField) frmEntradas.jdcFecha.getDateEditor().getUiComponent()).getText();

        // Se valida si los campos estan vacios
        if ("".equals(proveedor) || "".equals(cantidad) || "".equals(fecha)) {

            JOptionPane.showMessageDialog(frmEntradas, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            entrada = modEntrada.registrarEntrada(proveedor, Integer.parseInt(cantidad), Date.valueOf(fecha));
            listarEntradas();
            limpiarCeldas();

            if (entrada.getCantidadProducto() != 0 || entrada.getFecha() != null || entrada.getIdProveedor() != 0) {
                JOptionPane.showMessageDialog(frmEntradas, "Entrada registrada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    // Método para listar a los usuarios en en jtUsuarios
    private void listarEntradas() {

        modTablaEntradas.setRowCount(0);
        modTablaEntradas.setColumnCount(0);

        modTablaEntradas.addColumn("Id entrada");
        modTablaEntradas.addColumn("Proveedor");
        modTablaEntradas.addColumn("Fecha");
        modTablaEntradas.addColumn("Cantidad de producto");

        frmEntradas.jtEntradas.setModel(modTablaEntradas);

        ResultSet rs = modEntrada.consultarEntrada();

        String[] datos = new String[4];

        try {

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                modTablaEntradas.addRow(datos);

            }
            frmEntradas.jtEntradas.setModel(modTablaEntradas);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private void eliminarEntrada() {
        int fila = frmEntradas.jtEntradas.getSelectedRow();

        if (fila >= 0) {

            String valor = frmEntradas.jtEntradas.getValueAt(fila, 0).toString();

            try {
                modEntrada.elimiarRegistro(valor);
                JOptionPane.showMessageDialog(null, "Entrada eliminada correctamente", "Información", JOptionPane.WARNING_MESSAGE);
                listarEntradas();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);

        }
    }

    private void editarEntradas() {

        int fila = frmEntradas.jtEntradas.getSelectedRow();

        if (fila >= 0) {

            String proveedor = frmEntradas.jtEntradas.getValueAt(fila, 1).toString();

            frmEntradas.jcbProveedor.setSelectedItem(proveedor);

            frmEntradas.jtxtCantidad.setText(frmEntradas.jtEntradas.getValueAt(fila, 2).toString());
            ((JTextField) frmEntradas.jdcFecha.getDateEditor().getUiComponent()).setText(frmEntradas.jtEntradas.getValueAt(fila, 3).toString());

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    // Método para limpar las celdas
    private void limpiarCeldas() {

        frmEntradas.jcbProveedor.setSelectedIndex(0);
        frmEntradas.jtxtCantidad.setText("");
        ((JTextField) frmEntradas.jdcFecha.getDateEditor().getUiComponent()).setText("");

    }

    private void actualizarEntrada() {

        String proveedor = frmEntradas.jcbProveedor.getSelectedItem().toString();

        if ("".equals(frmEntradas.jtxtCantidad.getText()) || "".equals(proveedor) || "".equals(((JTextField) frmEntradas.jdcFecha.getDateEditor().getUiComponent()).getText())) {

            JOptionPane.showMessageDialog(null, "Llene los campos a actualizar", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            String fecha = ((JTextField) frmEntradas.jdcFecha.getDateEditor().getUiComponent()).getText();
            modEntrada.actualizarRegistro(Integer.parseInt(frmEntradas.jtxtCantidad.getText()), proveedor, Date.valueOf(fecha));
            listarEntradas();
            limpiarCeldas();

            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
