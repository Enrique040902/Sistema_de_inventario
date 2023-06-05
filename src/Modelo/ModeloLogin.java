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
    
    public CredencialesLogin log (String username, String constrasenia) {
        
        CredencialesLogin l = new CredencialesLogin();
        
        String sql = "SELECT * FROM Credenciales WHERE Username = ? and Contrasenia = ?";
        
        try {
            
            con = cn.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, constrasenia);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                l.setId(rs.getInt("ID_usuarios"));
                l.setUsername(rs.getString("username"));
                l.setContrasenia(rs.getString("contrasenia"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    } 
    
}
