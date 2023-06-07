package Modelo;

import java.sql.*;

public class ModeloUsuarios {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();

    Usuario usuario = new Usuario();

    // Método para registrar un usuario nuevo
    public Usuario registrarUsuario(String username, String contrasenia, String privilegio, String nombre, String apellidoPat, String apellidoMat) {

        // guardamos lo valores en los setters de la clase Usuario
        usuario.setNombre(nombre);
        usuario.setApellidoPaterno(apellidoPat);
        usuario.setApellidoMaterno(apellidoMat);
        usuario.setUsername(username);
        usuario.setContrasenia(contrasenia);
        usuario.setPrivilegio(privilegio);

        try {

            // Se establecen las sentencias sql
            String sqlTablaUsuarios = "insert into Usuarios(Username) values(?);";
            String sqlTablaCredenciales = "insert into Credenciales(ID_usuario, username, contrasenia, privilegio) values (?, ?, ?, ?);";
            String sqlTablaNombreUsuario = "insert into Nombre_usuario(ID_usuario, Nombre, ApellidoPat, ApellidoMat) values (?, ?, ?, ?);";

            con = cn.getConnection();

            // Se pasa como parametro la setencia SQL y se retorna el id del usuario
            ps = con.prepareStatement(sqlTablaUsuarios, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, usuario.getUsername());
            ps.executeUpdate();

            // se obtiene el id generado
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1)); // el id se guarda en el setId()
            }

            ps = con.prepareStatement(sqlTablaCredenciales);

            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getContrasenia());
            ps.setString(4, usuario.getPrivilegio());
            ps.executeUpdate();

            ps = con.prepareStatement(sqlTablaNombreUsuario);

            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellidoPaterno());
            ps.setString(4, usuario.getApellidoMaterno());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return usuario;
    }

    // Método para consultar los usuarios que hay en el sistema
    public ResultSet consultarUsuarios() {

        try {

            // Bloque de texto que hace la consulta a las tablas Nombre_usuario, Usuarios y Credenciales
            String sql = """
                     SELECT U.id, N.nombre, N.apellidoPat, N.apellidoMat, U.username,C.contrasenia, C.privilegio
                     FROM nombre_usuario N
                     JOIN usuarios U ON U.id = N.id_usuario
                     JOIN Credenciales C ON U.id = C.id_usuario;
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
        String sqlUsuarios = "DELETE FROM Usuarios WHERE id = " + valor + ";";
        String sqlCredenciales = "DELETE FROM Credenciales WHERE id_usuario = " + valor + ";";
        String sqlNombreUsuario = "DELETE FROM Nombre_usuario WHERE id_usuario = " + valor + ";";

        try {

            ps = con.prepareStatement(sqlCredenciales);
            ps.executeUpdate();

            ps = con.prepareStatement(sqlNombreUsuario);
            ps.executeUpdate();

            ps = con.prepareStatement(sqlUsuarios);
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
    public void actualizarRegistro(String username, String privilegio, String nombre, String contrasenia, String apellidoPat, String apellidoMat) {

        // se guardan los datos de los parametros
        usuario.setNombre(nombre);
        usuario.setApellidoPaterno(apellidoPat);
        usuario.setApellidoMaterno(apellidoMat);
        usuario.setUsername(username);
        usuario.setContrasenia(contrasenia);
        usuario.setPrivilegio(privilegio);

        try {

            // Se hace una consulta para saber el id del usuario y poder actualizar los datos
            String sqlIdUsuario = "SELECT ID FROM Usuarios WHERE Username = ?";
            
            String sqlUsuarios = "UPDATE Usuarios SET Username = ?  WHERE id = ?;";
            String sqlCredenciales = "UPDATE Credenciales SET Username = ?, Contrasenia = ?, privilegio = ? WHERE id_usuario = ?;";
            String sqlNombreUsuario = "UPDATE Nombre_usuario SET Nombre = ?, ApellidoPat = ?, ApellidoMat = ? WHERE id_usuario = ?;";

            con = cn.getConnection();

            // Obtener el id para actualizar los datos.
            ps = con.prepareStatement(sqlIdUsuario);
            ps.setString(1, usuario.getUsername());

            // retorna un ResultSet y se almacena en rs
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getInt("ID")); // se guarda en setId() y usarlo para las demas sentencias SQL
            }
            // System.out.println(""+usuario.getId());

            // Actulizar los datos de tabla usuarios
            ps = con.prepareStatement(sqlUsuarios);
            ps.setString(1, usuario.getUsername());
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();

            // Actulizar los datos de tabla Credenciales
            ps = con.prepareStatement(sqlCredenciales);
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getPrivilegio());
            ps.setInt(4, usuario.getId());
            ps.executeUpdate();

            // Actualizar los datos de tablas Nombre_usuario
            ps = con.prepareStatement(sqlNombreUsuario);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidoPaterno());
            ps.setString(3, usuario.getApellidoMaterno());
            ps.setInt(4, usuario.getId());
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

    // Método para comprobar si un username existe
    public int comprobarExistencia(String username) {

        usuario.setUsername(username);

        // Con esta sentencia se cuenta cuntos idx_usuario hay la tabla Usuarios
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE username = ?;";

        try {

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getUsername());
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
