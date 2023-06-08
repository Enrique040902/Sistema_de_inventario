package Modelo;

/**
 *
 * @author dilan
 */

/*
La clase credenciales es la que importa a lo hora de ingresar al sistema
*/
public class Credencial {
    
    // Se crean atributos de la tabla Credenciales
    private int id;
    private String username;
    private String contrasenia;
    private String privilegio;

    public Credencial() {
        
    }
    
    public Credencial(int id, String username, String contrasenia, String privilegio) {
        this.id = id;
        this.username = username;
        this.contrasenia = contrasenia;
        this.privilegio = privilegio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }
    
}
