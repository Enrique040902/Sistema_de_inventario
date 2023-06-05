package Modelo;

public class Usuario {
    
    // referencia a la tabla usuarios.
    private int id;
    private String username;
    
    // referencia a la tabla credenciasles
    private String contrasenia;
    private String privilegio;
    
    // referencia a la tabla NombreUsuario
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Usuario() {
    }

    public Usuario(int id, String username, String contrasenia, String privilegio, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.id = id;
        this.username = username;
        this.contrasenia = contrasenia;
        this.privilegio = privilegio;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }        
}
