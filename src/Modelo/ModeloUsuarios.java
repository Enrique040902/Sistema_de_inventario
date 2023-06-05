package Modelo;

import java.sql.*;

public class ModeloUsuarios {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ConexionBD cn = new ConexionBD();
    
    public Usuario registrarUsuario(String username, String contrasenia, String privilegio, String nombre, String apellidoPat, String apellidoMat) {
        
        Usuario usuario = new Usuario();
        
        String sqlTablaUsuarios = "INSERT INTO Usuarios(username) VALUES(?);";
        String sqlTablaCredenciales = "INSERT INTO Credenciales(username, contrasenia,privilegio) VALUES(?, ?, ?);";
        String sqlTablaNombreUsuario = "insert into Nombre_usuario(Nombre, ApellidoPat, ApellidoMat) values (?, ?, ?);";
        
        try {
            
            con = cn.getConnection();
            
            ps = con.prepareStatement(sqlTablaUsuarios);
            ps.setString(1, username);
            ps.executeUpdate();
            
            ps = con.prepareStatement(sqlTablaCredenciales);
            ps.setString(1, username);
            ps.setString(2, contrasenia);
            ps.setString(3, privilegio);
            ps.executeUpdate();
            
            
            ps = con.prepareStatement(sqlTablaNombreUsuario);
            ps.setString(1, nombre);
            ps.setString(2, apellidoPat);
            ps.setString(3, apellidoMat);
            ps.executeUpdate();
            
            usuario.setNombre(nombre);
            usuario.setApellidoPaterno(apellidoPat);
            usuario.setApellidoMaterno(apellidoMat);
            usuario.setUsername(username);
            usuario.setContrasenia(contrasenia);
            usuario.setPrivilegio(privilegio);
            
            
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return usuario;
    }
    
}
