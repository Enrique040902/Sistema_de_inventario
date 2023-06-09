package Controlador;

import Modelo.ModeloProductos;
import Modelo.Producto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.Productos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ProductosControlador implements ActionListener {

    Productos frmProductos;
    Producto producto = new Producto();
    ModeloProductos modProductos = new ModeloProductos();

    DefaultTableModel modTablaProductos = new DefaultTableModel();

    public ProductosControlador(Productos frmProductos) {

        this.frmProductos = frmProductos;

        frmProductos.btnConsultar.addActionListener(this);
        frmProductos.btnEditar.addActionListener(this);
        frmProductos.btnEliminar.addActionListener(this);
        frmProductos.btnNuevo.addActionListener(this);
        frmProductos.btnGuardar.addActionListener(this);

        frmProductos.jcbDepartamento.addActionListener(this);
        frmProductos.jcbProveedor.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmProductos.btnConsultar) {
            listarProductos();
        } else if (e.getSource() == frmProductos.btnNuevo) {
            registrar();
        } else if (e.getSource() == frmProductos.btnEditar) {
            editarProductos();
        } else if (e.getSource() == frmProductos.btnGuardar) {
            actualizarProductos();
        } else if (e.getSource() == frmProductos.btnEliminar) {
            eliminarProducto();
        }

    }

    public void llenarProveedoresYDepartamentos() {
        modProductos.consultarProveedores(frmProductos.jcbProveedor);
        modProductos.consultarDepartamento(frmProductos.jcbDepartamento);
    }

    private void registrar() {

        String id = frmProductos.jtxtId.getText();
        String nombre = frmProductos.jtxtNombreProducto.getText();
        String marca = frmProductos.jtxtMarca.getText();
        String descripcion = frmProductos.jtxtDescipción.getText();
        String precioVenta = frmProductos.jtxtPrecioVenta.getText();
        String precioCompra = frmProductos.jtxtPrecioCompra.getText();
        String stock = frmProductos.jtxtStock.getText();
        String departamento = frmProductos.jcbDepartamento.getSelectedItem().toString();
        String proveedor = frmProductos.jcbProveedor.getSelectedItem().toString();

        boolean departamentoSeleccionado = frmProductos.jcbDepartamento.getSelectedIndex() != 0;
        boolean proveedorSeleccionado = frmProductos.jcbProveedor.getSelectedIndex() != 0;

        // Se valida si los campos estan vacios
        if (id.equals("") || nombre.equals("") || marca.equals("")
                || descripcion.equals("") || precioVenta.equals("") || precioCompra.equals("") || stock.equals("")) {

            JOptionPane.showMessageDialog(frmProductos, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            if (!departamentoSeleccionado) {

                JOptionPane.showMessageDialog(null, "Seleccione un proveedor", "Aviso", JOptionPane.WARNING_MESSAGE);

            } else {

                if (!proveedorSeleccionado) {

                    JOptionPane.showMessageDialog(null, "Seleccione un departamento", "Aviso", JOptionPane.WARNING_MESSAGE);

                } else {

                    if (modProductos.comprobarExistencia(id) == 0) {
                        producto = modProductos.registrarProducto(id, nombre, marca, descripcion, precioVenta, precioCompra, stock, proveedor, departamento);
                        listarProductos();
                        limpiarCeldas();

                        if (producto.getId() != null || producto.getNombreProducto() != null || producto.getMarca() != null || producto.getDescripcionCorta() != null
                                || producto.getPrecioVenta() != null || producto.getPrecioCompra() != null || producto.getStock() != null) {

                            JOptionPane.showMessageDialog(frmProductos, "Producto registrado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Producto existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        limpiarCeldas();
                    }

                }

            }

        }

    }

    private void listarProductos() {

        modTablaProductos.setRowCount(0);
        modTablaProductos.setColumnCount(0);

        modTablaProductos.addColumn("ID");
        modTablaProductos.addColumn("Nombre");
        modTablaProductos.addColumn("Marca");
        modTablaProductos.addColumn("Descripción");
        modTablaProductos.addColumn("Precio de venta");
        modTablaProductos.addColumn("Precio de compra");
        modTablaProductos.addColumn("Stock");
        modTablaProductos.addColumn("Proveedor");
        modTablaProductos.addColumn("Departamento");

        frmProductos.jtProductos.setModel(modTablaProductos);

        ResultSet rs = modProductos.consultarProducto();

        String[] datos = new String[9];

        try {

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                modTablaProductos.addRow(datos);
            }

            frmProductos.jtProductos.setModel(modTablaProductos);

        } catch (SQLException e) {
            System.out.println("que pacho: " + e.toString());
        }
    }

    private void limpiarCeldas() {

        frmProductos.jtxtId.setText("");
        frmProductos.jtxtNombreProducto.setText("");
        frmProductos.jtxtMarca.setText("");
        frmProductos.jtxtDescipción.setText("");
        frmProductos.jtxtPrecioVenta.setText("");
        frmProductos.jtxtPrecioCompra.setText("");
        frmProductos.jtxtStock.setText("");

        frmProductos.jcbDepartamento.setSelectedIndex(0);
        frmProductos.jcbProveedor.setSelectedIndex(0);

    }

    private void editarProductos() {

        int fila = frmProductos.jtProductos.getSelectedRow();

        if (fila >= 0) {

            String proveedor = frmProductos.jtProductos.getValueAt(fila, 7).toString();
            String departamento = frmProductos.jtProductos.getValueAt(fila, 8).toString();

            frmProductos.jtxtId.setText(frmProductos.jtProductos.getValueAt(fila, 0).toString());
            frmProductos.jtxtNombreProducto.setText(frmProductos.jtProductos.getValueAt(fila, 1).toString());
            frmProductos.jtxtMarca.setText(frmProductos.jtProductos.getValueAt(fila, 2).toString());
            frmProductos.jtxtDescipción.setText(frmProductos.jtProductos.getValueAt(fila, 3).toString());
            frmProductos.jtxtPrecioVenta.setText(frmProductos.jtProductos.getValueAt(fila, 4).toString());
            frmProductos.jtxtPrecioCompra.setText(frmProductos.jtProductos.getValueAt(fila, 5).toString());
            frmProductos.jtxtStock.setText(frmProductos.jtProductos.getValueAt(fila, 6).toString());
            frmProductos.jcbDepartamento.setSelectedItem(departamento);
            frmProductos.jcbProveedor.setSelectedItem(proveedor);

        } else {
            JOptionPane.showMessageDialog(frmProductos, "Seleccione una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarProductos() {

        String departamento = frmProductos.jcbDepartamento.getSelectedItem().toString();
        String proveedor = frmProductos.jcbProveedor.getSelectedItem().toString();

        boolean departamentoSeleccionado = frmProductos.jcbDepartamento.getSelectedIndex() != 0;
        boolean proveedorSeleccionado = frmProductos.jcbProveedor.getSelectedIndex() != 0;

        if (frmProductos.jtxtId.getText().equals("") || frmProductos.jtxtNombreProducto.getText().equals("") || frmProductos.jtxtMarca.getText().equals("")
                || frmProductos.jtxtDescipción.getText().equals("") || frmProductos.jtxtPrecioVenta.getText().equals("") || frmProductos.jtxtPrecioCompra.getText().equals("")
                || frmProductos.jtxtStock.getText().equals("") || proveedorSeleccionado || departamentoSeleccionado) {

            JOptionPane.showMessageDialog(frmProductos, "Llene los campos correspondientes", "Avertencia", JOptionPane.WARNING_MESSAGE);

        } else {

            modProductos.actualizarRegistro(frmProductos.jtxtId.getText(), frmProductos.jtxtNombreProducto.getText(), frmProductos.jtxtMarca.getText(),
                    frmProductos.jtxtDescipción.getText(), frmProductos.jtxtPrecioVenta.getText(), frmProductos.jtxtPrecioCompra.getText(),
                    frmProductos.jtxtStock.getText(), proveedor, departamento);
            listarProductos();
            limpiarCeldas();
            JOptionPane.showMessageDialog(null, "Datos del productos actualizados", "Información", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    private void eliminarProducto() {

        int fila = frmProductos.jtProductos.getSelectedRow();

        if (fila >= 0) {

            String valor = frmProductos.jtProductos.getValueAt(fila, 0).toString();

            try {

                modProductos.elimiarRegistro(valor);
                listarProductos();
                JOptionPane.showMessageDialog(frmProductos, "Producto eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila, por favor", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

}
