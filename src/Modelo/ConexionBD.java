package Modelo; // Java with Ant

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dilan
 */
public class ConexionBD {
    
    private final String bd = "Sistema_inventario";
    private final String user = "proyectoIS";
    private final String pass = "proyectoIS";
    private final String url = "jdbc:mysql://127.0.0.1:3306/" + bd;
    private Connection con = null;
    
    public Connection getConnection() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
//            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        }
        
        return con;
    }
    
}
