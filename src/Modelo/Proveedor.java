package Modelo;

public class Proveedor {
    
    // referencia a la tabla proveedores
    private int id;
    private String nombre;
    private String telefono;
    
    // referencia a la tabla direccion_proveedores
    private String nombreCalle;
    private String numeroCalle;
    private String codigoPostal;
    private String estado;

    public Proveedor() {
    }

    public Proveedor(int id, String nombre, String telefono, String nombreCalle, String numeroCalle, String codigoPostal, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nombreCalle = nombreCalle;
        this.numeroCalle = numeroCalle;
        this.codigoPostal = codigoPostal;
        this.estado = estado;
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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
