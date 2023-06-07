package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Esta es la clase es la que se realizan las consultas en la tabla Credenciales
public class ModeloLogin {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();

    public boolean log(CredencialesLogin usuario) {

        String sql = "SELECT ID_usuario, Username, Contrasenia, Privilegio FROM Credenciales WHERE Username = ?";

        try {

            con = cn.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getUsername());
            rs = ps.executeQuery();

            if (rs.next()) {

                if (usuario.getContrasenia().equals(rs.getString(3))) {
                    usuario.setId(rs.getInt(1));
                    usuario.setPrivilegio(rs.getString(4));
                    return true;
                } else {
                    return false;
                }
            }
            return false;

        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

}
