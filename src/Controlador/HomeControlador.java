package Controlador;

import Modelo.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vista.*;
import javax.swing.JFrame;

public class HomeControlador implements ActionListener {

    Usuarios frmUsuarios;
    Clientes frmClientes;
    Productos frmProductos;
    Proveedores frmProveedores;
    Entradas frmEntradas;
    Salidas frmSalidas;
    Departamentos frmDepartamentos;
    
    Credencial credencial;
    
    Home frmHome;
    private JFrame frmAbierto;
    
    public HomeControlador(Home frmHome) {

        this.frmHome = frmHome;

        frmHome.jmiUsuarios.addActionListener(this);
        frmHome.jmiClientes.addActionListener(this);
        frmHome.jmiProductos.addActionListener(this);
        frmHome.jmiProveedores.addActionListener(this);
        frmHome.jmiEntradas.addActionListener(this);
        frmHome.jmiSalidas.addActionListener(this);
        frmHome.jmiDepa.addActionListener(this);

    }

    public HomeControlador(Home frmHome, Credencial credencial) {

        this.frmHome = frmHome;
        this.credencial = credencial;

        frmHome.jmiUsuarios.addActionListener(this);
        frmHome.jmiClientes.addActionListener(this);
        frmHome.jmiProductos.addActionListener(this);
        frmHome.jmiProveedores.addActionListener(this);
        frmHome.jmiEntradas.addActionListener(this);
        frmHome.jmiSalidas.addActionListener(this);
        frmHome.jmiDepa.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmHome.jmiUsuarios) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmUsuarios = new Usuarios();
                abrirFrame(frmUsuarios);
            }
            
        } else if (e.getSource() == frmHome.jmiClientes) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmClientes = new Clientes(credencial);
                abrirFrame(frmClientes);
            }
            
        } else if (e.getSource() == frmHome.jmiProductos) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmProductos = new Productos(credencial);
                abrirFrame(frmProductos);
            }
            
        } else if (e.getSource() == frmHome.jmiProveedores) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmProveedores = new Proveedores(credencial);
                abrirFrame(frmProveedores);
            }
            
        } else if (e.getSource() == frmHome.jmiEntradas) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmEntradas = new Entradas(credencial);
                abrirFrame(frmEntradas);
            }
            
        } else if (e.getSource() == frmHome.jmiSalidas) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmSalidas = new Salidas(credencial);
                abrirFrame(frmSalidas);
            }
            
        } else if (e.getSource() == frmHome.jmiDepa) {
            
            if (frmAbierto == null || !frmAbierto.isVisible()) {
                frmDepartamentos = new Departamentos();
                abrirFrame(frmDepartamentos);
            }
            
        }

    }
    
    private void abrirFrame(JFrame frm) {
        
        frmAbierto = frm;
        if (frmAbierto == null || !frm.isVisible() || frm != frmAbierto) {
            restringirFrameExtras(frmAbierto);
        }
        
    }
    
    private void restringirFrameExtras(JFrame frm) {

        if (!frm.isVisible()) {
            frm.setLocationRelativeTo(null);
            frm.setVisible(true);
        }

    }

}
