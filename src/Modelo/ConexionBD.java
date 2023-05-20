package Modelo; // Java with Ant

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dilan
 */
public class ConexionBD {
    
    private final String bd = "Sistema_inventario";
    private final String user = "root";
    private final String pass = "root";
    private final String url = "jdbc:mysql://localhost:3306/" + bd;
    private Connection con = null;
    
    public Connection getConnection() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
}
