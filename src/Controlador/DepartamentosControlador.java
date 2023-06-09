package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.Departamento;
import Modelo.ModeloDepartamentos;
import Vista.Departamentos;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DepartamentosControlador implements ActionListener {

    Departamentos frmDepartamentos;
    Departamento depa = new Departamento();
    ModeloDepartamentos modDepa = new ModeloDepartamentos();

    DefaultTableModel modTablaDepa = new DefaultTableModel();

    public DepartamentosControlador(Departamentos frmDepartamentos) {

        this.frmDepartamentos = frmDepartamentos;

        frmDepartamentos.btnNuevo.addActionListener(this);
        frmDepartamentos.btnGuardar.addActionListener(this);
        frmDepartamentos.btnEliminar.addActionListener(this);
        frmDepartamentos.btnEditar.addActionListener(this);
        frmDepartamentos.btnConsultar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmDepartamentos.btnConsultar) {
            listarDepartamentos();
        } else if (e.getSource() == frmDepartamentos.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmDepartamentos.btnEditar) {
            editarDepartamento();
        } else if (e.getSource() == frmDepartamentos.btnGuardar) {
            actualizarDepartamento();
        } else if (e.getSource() == frmDepartamentos.btnEliminar) {
            eliminarDepartamento();
        }

    }

    private void registrar() {

        String nombre = frmDepartamentos.jtxtNombreDepartamento.getText();

        if (nombre.equals("")) {

            JOptionPane.showMessageDialog(frmDepartamentos, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            if (modDepa.comprobarExistencia(nombre) == 0) {
                depa = modDepa.registrarDepartamento(nombre);
                listarDepartamentos();
                limpiarCeldas();

                if (depa.getNombreDepatamento()!= null) {
                    JOptionPane.showMessageDialog(frmDepartamentos, "Departamento registrado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Departamento existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                limpiarCeldas();
            }

        }

    }

    private void listarDepartamentos() {

        modTablaDepa.setRowCount(0);
        modTablaDepa.setColumnCount(0);

        modTablaDepa.addColumn("ID");
        modTablaDepa.addColumn("Nombre");

        frmDepartamentos.jtDepartamentos.setModel(modTablaDepa);

        ResultSet rs = modDepa.consultarDepartamento();

        String[] datos = new String[2];

        try {

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                modTablaDepa.addRow(datos);
            }
            frmDepartamentos.jtDepartamentos.setModel(modTablaDepa);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private void limpiarCeldas() {

        frmDepartamentos.jtxtNombreDepartamento.setText("");

    }

    private void editarDepartamento() {

        int fila = frmDepartamentos.jtDepartamentos.getSelectedRow();

        if (fila >= 0) {

            frmDepartamentos.jtxtNombreDepartamento.setText(frmDepartamentos.jtDepartamentos.getValueAt(fila, 1).toString());

        } else {
            JOptionPane.showMessageDialog(frmDepartamentos, "Seleccione una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void actualizarDepartamento() {

        if (frmDepartamentos.jtxtNombreDepartamento.getText().equals("")) {

            JOptionPane.showMessageDialog(frmDepartamentos, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            modDepa.actualizarRegistro(frmDepartamentos.jtxtNombreDepartamento.getText());
            listarDepartamentos();
            limpiarCeldas();
            JOptionPane.showMessageDialog(null, "Nombre del departamento actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    private void eliminarDepartamento() {

        int fila = frmDepartamentos.jtDepartamentos.getSelectedRow();

        if (fila >= 0) {

            String valor = frmDepartamentos.jtDepartamentos.getValueAt(fila, 0).toString();

            try {

                modDepa.elimiarRegistro(valor);
                listarDepartamentos();
                JOptionPane.showMessageDialog(frmDepartamentos, "Departamento eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

}
