package Modelo;

import java.sql.*;
import javax.swing.JComboBox;

public class ModeloProductos {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();
    
    Producto producto = new Producto();
    
    public Producto registrarProducto(String id, String nombreProducto, String marca, String descripcionCorta, String precioVenta, String precioCompra, String stock, String proveedor, String departamento) {
        
        producto.setId(id);
        producto.setNombreProducto(nombreProducto);
        producto.setMarca(marca);
        producto.setDescripcionCorta(descripcionCorta);
        producto.setPrecioVenta(precioVenta);
        producto.setPrecioCompra(precioCompra);
        producto.setStock(stock);
        
        try {

            String sqlProveedor = "SELECT ID FROM Proveedores WHERE Nombre = ?;";
            String sqlDepartamentos = "SELECT ID FROM Departamentos WHERE Nombre_departamento = ?;";
            String sqlProductos = "INSERT INTO Productos (ID, Nombre_producto, Marca, Descripcion_corta, Precio_venta, Precio_compra, Stock, ID_proveedor, ID_departamento) VALUES (?,?,?,?,?,?,?,?,?);";
            
            
            con = cn.getConnection();
            
            ps = con.prepareStatement(sqlProveedor);
            ps.setString(1, proveedor);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId_provedor(rs.getInt("ID"));
            }
            
            ps = con.prepareStatement(sqlDepartamentos);
            ps.setString(1, departamento);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId_departamento(rs.getInt("ID"));
            }
            
            ps = con.prepareStatement(sqlProductos);
            ps.setString(1, producto.getId());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getMarca());
            ps.setString(4, producto.getDescripcionCorta());
            ps.setString(5, producto.getPrecioVenta());
            ps.setString(6, producto.getPrecioCompra());
            ps.setString(7, producto.getStock());
            ps.setInt(8, producto.getId_provedor());
            ps.setInt(9, producto.getId_departamento());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("hola 1 :" + e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("hola : " + e.toString());
            }
        }

        return producto;
    }
    
    public ResultSet consultarProducto() {

        try {

            String sql = """
                          SELECT P.id, P.Nombre_producto, P.Marca, P.Descripcion_corta, P.Precio_venta, P.Precio_compra, P.Stock, Pro.Nombre, D.Nombre_departamento
                          FROM Productos P
                          JOIN Proveedores Pro ON Pro.ID = P.ID_proveedor
                          JOIN Departamentos D ON D.ID = P.ID_departamento;
                          """;

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("consultar 1: " + e.toString());
        }

        return rs;

    }
    
    public void elimiarRegistro(String valor) {

        // setencias que eliminan los valores.
        String sql = "DELETE FROM Productos WHERE ID = '" + valor + "';";
        
        try {

            con = cn.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("delete 1: " +e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("delete 2: " +e.toString());
            }
        }

    }
    
    // Método para actualizar un registro
    public void actualizarRegistro(String id, String nombreProducto, String marca, String descripcionCorta, String precioVenta, String precioCompra, String stock, String proveedor, String departamento) {

        // se guardan los datos de los parametros
        producto.setId(id);
        producto.setNombreProducto(nombreProducto);
        producto.setMarca(marca);
        producto.setDescripcionCorta(descripcionCorta);
        producto.setPrecioVenta(precioVenta);
        producto.setPrecioCompra(precioCompra);
        producto.setStock(stock);

        try {

            String sqlIdProveedor = "SELECT ID FROM Proveedores WHERE Nombre = ?";
            String sqlIdDepartamento = "SELECT ID FROM Departamentos WHERE Nombre_departamento = ?";
            String sqlProductos = "UPDATE Productos SET Nombre_producto = ?, Marca = ?, Descripcion_corta = ?, Precio_venta = ?, Precio_compra = ?, Stock = ?, ID_proveedor = ?, ID_departamento = ? WHERE ID = ?;";

            con = cn.getConnection();
            
            ps = con.prepareStatement(sqlIdProveedor);
            ps.setString(1, proveedor);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId_provedor(rs.getInt("ID"));
            }
            
            ps = con.prepareStatement(sqlIdDepartamento);
            ps.setString(1, departamento);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId_departamento(rs.getInt("ID"));
            }

            // Actulizar los 
            ps = con.prepareStatement(sqlProductos);
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getMarca());
            ps.setString(3, producto.getDescripcionCorta());
            ps.setString(4, producto.getPrecioVenta());
            ps.setString(5, producto.getPrecioCompra());
            ps.setString(6, producto.getStock());
            ps.setInt(7, producto.getId_provedor());
            ps.setInt(8, producto.getId_departamento());
            ps.setString(9, producto.getId());
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
    
    public int comprobarExistencia(String clave) {

        producto.setId(clave);
        
        // Con esta sentencia se cuenta cuantos clientes con el mismo nombre hay la tabla Usuarios
        String sql = "SELECT COUNT(*) FROM Productos WHERE ID = ?;";

        try {

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getId());
            rs = ps.executeQuery();

            // Si rs tiene un dato se retorna es datos
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1; // retorna 1 si encuentra alguna username igual

        } catch (SQLException e) {
            System.out.println("existencia 1 " + e.toString());
            return 1; // se retorna como valor predeterminado
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("esite 2" + e.toString());
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
    
    public void consultarDepartamento (JComboBox jcb) {
        
        jcb.addItem("Seleccione un departamento");
        
        try {
            
            String sql = "SELECT Nombre_departamento FROM Departamentos;";
            String departamento;
            
            con = cn.getConnection();
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                departamento = rs.getString("Nombre_departamento");
                jcb.addItem(departamento);
                
            }
            
        } catch (SQLException e) {
            System.out.println("depa" + e.toString());
            
        }
        
    }
    
}
