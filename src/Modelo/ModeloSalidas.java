package Modelo;

import java.sql.*;

public class ModeloSalidas {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();

    Salida salida = new Salida();

    public Salida registrarSalida(int cantidad, Date fecha) {

        // guardamos lo valores en los setters de la clase Usuario
        salida.setCantidadProducto(cantidad);
        salida.setFecha(fecha);

        try {

            String sqlEntradas = "INSERT INTO Salidas (Fecha, Cantidad_producto) VALUES (?,?)";

            con = cn.getConnection();

            ps = con.prepareStatement(sqlEntradas);

            ps.setDate(1, salida.getFecha());
            ps.setInt(2, salida.getCantidadProducto());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return salida;
    }
    
    public ResultSet consultarSalida() {

        try {

            String sql = """
                     SELECT * FROM Salidas;
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
        String sql = "DELETE FROM Salidas WHERE id = " + valor + ";";

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
    
    public void actualizarSalida(int cantidad, Date fecha) {

        // se guardan los datos de los parametros
        salida.setCantidadProducto(cantidad);
        salida.setFecha(fecha);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sqlIdSalida = "SELECT ID FROM Salidas";
            
            String sqlSalida = "UPDATE Salidas SET fecha = ?, Cantidad_producto = ?  WHERE id = ?;";

            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sqlIdSalida);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                salida.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }

            ps = con.prepareStatement(sqlSalida);
            ps.setDate(1, salida.getFecha());
            ps.setInt(2, salida.getCantidadProducto());
            ps.setInt(3, salida.getId());
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

}
