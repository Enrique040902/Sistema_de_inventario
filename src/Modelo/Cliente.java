package Modelo;

public class Cliente {
    
    // Atributos de la tabla Clientes
    private int id;
    private String nombre;
    private String telefono;
    private int idDireccion;
    
    // Atributos de la tabla Direccion_cliente
    private String nombreCalle;
    private String numeroCalle;
    private String estado;
    
    public Cliente() {}

    public Cliente(int id, String nombre, String telefono, int idDireccion, String nombreCalle, String numeroCalle, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.idDireccion = idDireccion;
        this.nombreCalle = nombreCalle;
        this.numeroCalle = numeroCalle;
        this.estado = estado;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
