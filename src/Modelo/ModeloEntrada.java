package Modelo;

import java.sql.*;

public class ModeloEntrada {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();

    Entrada entrada = new Entrada();

    public Entrada registrarEntrada(int cantidad, Date fecha) {

        // guardamos lo valores en los setters de la clase Usuario
        entrada.setCantidadProducto(cantidad);
        entrada.setFecha(fecha);

        try {

            String sqlEntradas = "INSERT INTO Entradas (Fecha, Cantidad_producto) VALUES (?,?)";

            con = cn.getConnection();

            ps = con.prepareStatement(sqlEntradas);

            ps.setDate(1, entrada.getFecha());
            ps.setInt(2, entrada.getCantidadProducto());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return entrada;
    }

    public ResultSet consultarEntrada() {

        try {

            String sql = """
                     SELECT * FROM Entradas;
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

    public void actualizarRegistro(int cantidad, Date fecha) {

        // se guardan los datos de los parametros
        entrada.setCantidadProducto(cantidad);
        entrada.setFecha(fecha);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sqlIdEntrada = "SELECT ID FROM Entradas";

            String sqlEntradas = "UPDATE Entradas SET fecha = ?, Cantidad_producto = ?  WHERE id = ?;";

            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sqlIdEntrada);

            rs = ps.executeQuery();
            if (rs.next()) {
                entrada.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }

            ps = con.prepareStatement(sqlEntradas);
            ps.setDate(1, entrada.getFecha());
            ps.setInt(2, entrada.getCantidadProducto());
            ps.setInt(3, entrada.getId());
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
