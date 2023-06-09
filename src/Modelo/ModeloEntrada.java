package Modelo;

import java.sql.*;
import javax.swing.JComboBox;

public class ModeloEntrada {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();
    
    Entrada entrada = new Entrada();
    
    // Método para registrar un usuario nuevo
    public Entrada registrarEntrada(String proveedor, int cantidad, Date fecha) {

        // guardamos lo valores en los setters de la clase Usuario
        entrada.setCantidadProducto(cantidad);
        entrada.setFecha(fecha);

        try {

            // Se establecen las sentencias sql
            String sqlIdProveedor = "SELECT ID FROM Proveedores WHERE Nombre = ?";
            
            String sqlEntradas = "INSERT INTO Entradas (Id_proveedor, Fecha, Cantidad_producto) VALUES (?,?,?)";

            con = cn.getConnection();

            // Se pasa como parametro la setencia SQL y se retorna el id del usuario
            ps = con.prepareStatement(sqlIdProveedor);

            ps.setString(1, proveedor);
            rs = ps.executeQuery();

            if (rs.next()) {
                entrada.setIdProveedor(rs.getInt(1)); // el id se guarda en el setId()
            }

            ps = con.prepareStatement(sqlEntradas);

            ps.setInt(1, entrada.getIdProveedor());
            ps.setDate(2, entrada.getFecha());
            ps.setInt(3, entrada.getCantidadProducto());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return entrada;
    }
    
    public ResultSet consultarEntrada() {

        try {

            // Bloque de texto que hace la consulta a las tablas Nombre_usuario, Usuarios y Credenciales
            String sql = """
                     SELECT E.id, P.nombre, E.fecha, E.cantidad_producto
                     FROM Entradas E
                     JOIN Proveedores P WHERE P.id = E.id;
                     """;

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return rs;
    }
    
     public void elimiarRegistro(String valor) {

        // setencias que eliminan los valores.
        String sql = "DELETE FROM Entradas WHERE id = " + valor + ";";

        try {

            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

    }
     
     public void actualizarRegistro(int cantidad, String proveedor, Date fecha) {

        // se guardan los datos de los parametros
        entrada.setCantidadProducto(cantidad);
        entrada.setFecha(fecha);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sqlIdEntrada = "SELECT ID FROM Entradas";
            String sqlIdProveedor = "SELECT ID FROM Proveedores";
            
            String sqlEntradas = "UPDATE Entradas SET id_proveedor = ?, fecha = ?, Cantidad_producto = ?  WHERE id = ?;";

            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sqlIdEntrada);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                entrada.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }
            // System.out.println(""+usuario.getId());
            
            ps = con.prepareStatement(sqlIdProveedor);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                entrada.setIdProveedor(rs.getInt("ID"));
            }

            ps = con.prepareStatement(sqlEntradas);
            ps.setInt(1, entrada.getIdProveedor());
            ps.setDate(2, entrada.getFecha());
            ps.setInt(3, entrada.getCantidadProducto());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("actualizar 1" + e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("actualizar 2" + e.toString());
            }
        }
    }
     
     public void consultarProveedores (JComboBox jcb) {
        
        jcb.addItem("Seleccione un proveedor");
        
        try {
            
            String sql = "SELECT Nombre FROM Proveedores;";
            String proveedor;
            
            con = cn.getConnection();
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                proveedor = rs.getString("Nombre");
                jcb.addItem(proveedor);
                
            }
            
        } catch (SQLException e) {
            System.out.println("prove"+e.toString());
            
        }
        
    }
    
}
