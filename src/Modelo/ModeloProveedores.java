package Modelo;

import java.sql.*;

public class ModeloProveedores {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();

    Proveedor proveedor = new Proveedor();

    public Proveedor registrarCliente(String nombre, String telefono, String nombreCalle, String numeroCalle, String codigoPostal, String estado) {

        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setNombreCalle(nombreCalle);
        proveedor.setNumeroCalle(numeroCalle);
        proveedor.setCodigoPostal(codigoPostal);
        proveedor.setEstado(estado);

        try {

            String sqlTablaDireccionProveedores = "INSERT INTO Direccion_proveedores (Nombre_calle, Numero_calle, Codigo_postal, Estado) VALUES (?, ?, ?, ?);";
            String sqlTablaProveedores = "INSERT INTO Proveedores (Nombre, Telefono, ID_direccion) VALUES (?, ?, ?);";

            con = cn.getConnection();

            // Se obtiene el ID_direccion
            ps = con.prepareStatement(sqlTablaDireccionProveedores, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, proveedor.getNombreCalle());
            ps.setString(2, proveedor.getNumeroCalle());
            ps.setString(3, proveedor.getCodigoPostal());
            ps.setString(4, proveedor.getEstado());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                proveedor.setId(rs.getInt(1));
            }

            ps = con.prepareStatement(sqlTablaProveedores);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getTelefono());
            ps.setInt(3, proveedor.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return proveedor;
    }

    public ResultSet consultarProveedores() {

        try {

            String sql = """
                          SELECT P.id, P.nombre, P.telefono, D.Nombre_calle, D.Numero_calle, D.Codigo_postal, D.Estado
                          FROM Proveedores P
                          JOIN Direccion_proveedores D WHERE P.id_direccion = D.id;
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
        String sqlDireccionProveedores = "DELETE FROM Direccion_proveedores WHERE id = " + valor + ";";
        String sqlProveedores = "DELETE FROM Proveedores WHERE ID_direccion = " + valor + ";";
        
        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sqlProveedores);
            ps.executeUpdate();

            ps = con.prepareStatement(sqlDireccionProveedores);
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
    public void actualizarRegistro(String nombre, String telefono, String nombreCalle, String numeroCalle, String codigoPostal, String estado) {

        // se guardan los datos de los parametros
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setNombreCalle(nombreCalle);
        proveedor.setNumeroCalle(numeroCalle);
        proveedor.setCodigoPostal(codigoPostal);
        proveedor.setEstado(estado);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sqlIdCliente = "SELECT ID FROM Proveedores WHERE Nombre = ?";

            String sqlDireccionCliente = "UPDATE Direccion_proveedores SET Nombre_calle = ?, Numero_calle = ?, Codigo_postal = ?, Estado = ? WHERE id = ?;";
            String sqlCliente = "UPDATE Proveedores SET Nombre = ?, Telefono = ? WHERE id_direccion = ?;";

            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sqlIdCliente);
            ps.setString(1, proveedor.getNombre());

            // retorna un ResultSet y se almacena en rs
            rs = ps.executeQuery();
            if (rs.next()) {
                proveedor.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }

            // Actulizar los datos de tabla direccion_cliente
            ps = con.prepareStatement(sqlDireccionCliente);
            ps.setString(1, proveedor.getNombreCalle());
            ps.setString(2, proveedor.getNumeroCalle());
            ps.setString(3, proveedor.getEstado());
            ps.setString(4, proveedor.getCodigoPostal());
            ps.setInt(5, proveedor.getId());
            ps.executeUpdate();

            // Actulizar los datos de tabla clientes
            ps = con.prepareStatement(sqlCliente);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getTelefono());
            ps.setInt(3, proveedor.getId());
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

        proveedor.setNombre(nombre);

        // Con esta sentencia se cuenta cuantos clientes con el mismo nombre hay la tabla Usuarios
        String sql = "SELECT COUNT(*) FROM Proveedores WHERE Nombre = ?;";

        try {

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
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
