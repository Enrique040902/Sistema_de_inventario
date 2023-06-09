package Controlador;

import java.sql.*;
import Modelo.ModeloSalidas;
import Modelo.Salida;
import Vista.Salidas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SalidasControlador implements ActionListener {

    Salidas frmSalidas;
    Salida salida = new Salida();
    ModeloSalidas modSalida = new ModeloSalidas();

    DefaultTableModel modTablaSalidas = new DefaultTableModel();

    public SalidasControlador(Salidas frmSalidas) {
        this.frmSalidas = frmSalidas;

        frmSalidas.btnNuevo.addActionListener(this);
        frmSalidas.btnGuardar.addActionListener(this);
        frmSalidas.btnEliminar.addActionListener(this);
        frmSalidas.btnEditar.addActionListener(this);
        frmSalidas.btnConsultar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmSalidas.btnConsultar) {
            listarSalidas();
        } else if (e.getSource() == frmSalidas.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmSalidas.btnEditar) {
            editarSalida();
        } else if (e.getSource() == frmSalidas.btnGuardar) {
            actualizarSalida();
        } else if (e.getSource() == frmSalidas.btnEliminar) {
            eliminarSalida();
        }
    }

    private void registrar() {

        String cantidad = frmSalidas.jtxtCantidad.getText();
        String fecha = ((JTextField) frmSalidas.jdcFecha.getDateEditor().getUiComponent()).getText();

        // Se valida si los campos estan vacios
        if ("".equals(cantidad) || "".equals(fecha)) {

            JOptionPane.showMessageDialog(frmSalidas, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else if (!cantidad.matches("\\d+")) {

            JOptionPane.showMessageDialog(frmSalidas, "Solo valores númericos", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            salida = modSalida.registrarSalida(Integer.parseInt(cantidad), Date.valueOf(fecha));
            listarSalidas();
            limpiarCeldas();

            if (salida.getCantidadProducto() != 0 || salida.getFecha() != null) {
                JOptionPane.showMessageDialog(frmSalidas, "Salida registrada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    // Método para listar a los usuarios en en jtUsuarios
    private void listarSalidas() {

        modTablaSalidas.setRowCount(0);
        modTablaSalidas.setColumnCount(0);

        modTablaSalidas.addColumn("Id salida");
        modTablaSalidas.addColumn("Fecha");
        modTablaSalidas.addColumn("Cantidad de producto");

        frmSalidas.jtSalidas.setModel(modTablaSalidas);

        ResultSet rs = modSalida.consultarSalida();

        String[] datos = new String[3];

        try {

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modTablaSalidas.addRow(datos);

            }
            frmSalidas.jtSalidas.setModel(modTablaSalidas);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private void eliminarSalida() {
        int fila = frmSalidas.jtSalidas.getSelectedRow();

        if (fila >= 0) {

            String valor = frmSalidas.jtSalidas.getValueAt(fila, 0).toString();

            try {
                modSalida.elimiarRegistro(valor);
                listarSalidas();
                JOptionPane.showMessageDialog(null, "Salida eliminada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);

        }
    }

    private void editarSalida() {

        int fila = frmSalidas.jtSalidas.getSelectedRow();

        if (fila >= 0) {

            ((JTextField) frmSalidas.jdcFecha.getDateEditor().getUiComponent()).setText(frmSalidas.jtSalidas.getValueAt(fila, 1).toString());
            frmSalidas.jtxtCantidad.setText(frmSalidas.jtSalidas.getValueAt(fila, 2).toString());

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCeldas() {

        frmSalidas.jtxtCantidad.setText("");
        ((JTextField) frmSalidas.jdcFecha.getDateEditor().getUiComponent()).setText("");

    }

    private void actualizarSalida() {

        if ("".equals(frmSalidas.jtxtCantidad.getText()) || "".equals(((JTextField) frmSalidas.jdcFecha.getDateEditor().getUiComponent()).getText())) {

            JOptionPane.showMessageDialog(null, "Llene los campos a actualizar", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            String fecha = ((JTextField) frmSalidas.jdcFecha.getDateEditor().getUiComponent()).getText();
            modSalida.actualizarSalida(Integer.parseInt(frmSalidas.jtxtCantidad.getText()), Date.valueOf(fecha));
            listarSalidas();
            limpiarCeldas();

            JOptionPane.showMessageDialog(null, "Salida actualizada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
