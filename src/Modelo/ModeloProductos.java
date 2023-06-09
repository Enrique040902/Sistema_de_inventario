package Modelo;

import java.sql.*;

public class ModeloProductos {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();
    
    Producto producto = new Producto();
    
    public Producto registrarProducto(String id, String nombreProducto, String marca, String descripcionCorta, String precionVenta, String precioCompra, String stock) {
        
        producto.setId(id);
        producto.setNombreProducto(nombreProducto);
        producto.setMarca(marca);
        producto.setDescripcionCorta(descripcionCorta);
        producto.setPrecioVenta(precionVenta);
        producto.setPrecioCompra(precioCompra);
        producto.setStock(stock);
        
        try {
            
            
            
        } catch (Exception e) {
        }
        
    }
    
}
