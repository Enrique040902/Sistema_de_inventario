package Modelo;

import java.sql.*;

public class ModeloDepartamentos {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();
    
    Departamento departamento = new Departamento();
    
    public Departamento registrarDepartamento(String nombre) {

        departamento.setNombreDepatamento(nombre);

        try {

            String sql = "INSERT INTO Departamentos (Nombre_departamento) VALUES (?);";

            con = cn.getConnection();

            // Se obtiene el ID_direccion
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, departamento.getNombreDepatamento());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                departamento.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return departamento;
    }
    
    public ResultSet consultarDepartamento() {

        try {

            String sql = """
                          SELECT * FROM Departamentos;
                          """;

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return rs;

    }
    
    
    // Método para eliminar los registros.
    public void elimiarRegistro(String valor) {

        // setencias que eliminan los valores.
        String sql = "DELETE FROM Departamentos WHERE id = " + valor + ";";
        
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
    
    // Método para actualizar un registro
    public void actualizarRegistro(String nombre) {

        // se guardan los datos de los parametros
        departamento.setNombreDepatamento(nombre);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sql = "SELECT ID FROM Departamentos WHERE Nombre_departamento = ?";
            String sqlActualizar = "UPDATE Departamentos SET Nombre_departamento = ? WHERE ID = ?;";
            
            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sql);
            ps.setString(1, departamento.getNombreDepatamento());

            // retorna un ResultSet y se almacena en rs
            rs = ps.executeQuery();
            if (rs.next()) {
                departamento.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }

            // Actulizar los datos de tabla direccion_cliente
            ps = con.prepareStatement(sqlActualizar);
            ps.setString(1, departamento.getNombreDepatamento());
            ps.setInt(2, departamento.getId());
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
    
    public int comprobarExistencia(String nombre) {

        departamento.setNombreDepatamento(nombre);

        String sql = "SELECT COUNT(*) FROM Departamentos WHERE Nombre_departamento = ?;";

        try {

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, departamento.getNombreDepatamento());
            rs = ps.executeQuery();

            // Si rs tiene un dato se retorna es datos
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1; // retorna 1 si encuentra alguna username igual

        } catch (SQLException e) {
            System.out.println("existencia 1 " + e.toString());
            return 1; // se retorna como valor predeterminado
        }
    }
    
}
