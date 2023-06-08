package Modelo;

import java.sql.*;

public class ModeloClientes {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();

    Cliente cliente = new Cliente();

    public Cliente registrarCliente(String nombre, String telefono, String nombreCalle, String numeroCalle, String estado) {

        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setNombreCalle(nombreCalle);
        cliente.setNumeroCalle(numeroCalle);
        cliente.setEstado(estado);

        try {

            String sqlTablaDireccionCliente = "INSERT INTO Direccion_cliente(Nombre_calle, Numero_calle, Estado) VALUES (?, ?, ?);";
            String sqlTablaClientes = "INSERT INTO Clientes(Nombre, Telefono, ID_direccionC) VALUES (?, ?, ?);";

            con = cn.getConnection();

            // Se obtiene el ID_direccion
            ps = con.prepareStatement(sqlTablaDireccionCliente, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNombreCalle());
            ps.setString(2, cliente.getNumeroCalle());
            ps.setString(3, cliente.getEstado());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }

            ps = con.prepareStatement(sqlTablaClientes);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setInt(3, cliente.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cliente;
    }

    public ResultSet consultarCliente() {

        try {

            String sql = """
                          SELECT C.id, C.nombre, C.telefono, D.Nombre_calle, D.Numero_calle, D.Estado
                          FROM Clientes C 
                          JOIN Direccion_cliente D WHERE C.id_direccionC = D.id;
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
        String sqlDireccionCliente = "DELETE FROM Direccion_cliente WHERE id = " + valor + ";";
        String sqlClientes = "DELETE FROM Clientes WHERE ID_direccionC = " + valor + ";";
        
        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sqlClientes);
            ps.executeUpdate();

            ps = con.prepareStatement(sqlDireccionCliente);
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
    public void actualizarRegistro(String nombre, String telefono, String nombreCalle, String numeroCalle, String estado) {

        // se guardan los datos de los parametros
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setNombreCalle(nombreCalle);
        cliente.setNumeroCalle(numeroCalle);
        cliente.setEstado(estado);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sqlIdCliente = "SELECT ID FROM Clientes WHERE Nombre = ?";

            String sqlDireccionCliente = "UPDATE Direccion_cliente SET Nombre_calle = ?, Numero_calle = ?, Estado = ? WHERE id = ?;";
            String sqlCliente = "UPDATE Clientes SET Nombre = ?, Telefono = ? WHERE id_direccionC = ?;";

            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sqlIdCliente);
            ps.setString(1, cliente.getNombre());

            // retorna un ResultSet y se almacena en rs
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }

            // Actulizar los datos de tabla direccion_cliente
            ps = con.prepareStatement(sqlDireccionCliente);
            ps.setString(1, cliente.getNombreCalle());
            ps.setString(2, cliente.getNumeroCalle());
            ps.setString(3, cliente.getEstado());
            ps.setInt(4, cliente.getId());
            ps.executeUpdate();

            // Actulizar los datos de tabla clientes
            ps = con.prepareStatement(sqlCliente);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setInt(3, cliente.getId());
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

        cliente.setNombre(nombre);

        // Con esta sentencia se cuenta cuantos clientes con el mismo nombre hay la tabla Usuarios
        String sql = "SELECT COUNT(*) FROM Clientes WHERE Nombre = ?;";

        try {

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
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
