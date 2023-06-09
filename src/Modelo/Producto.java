package Modelo;

public class Producto {
    
    private String id;
    private String nombreProducto;
    private String marca;
    private String descripcionCorta;
    private String precioVenta;
    private String precioCompra;
    private String stock;
    private int id_provedor;
    private int id_departamento;

    public Producto() {
    }

    public Producto(String id, String nombreProducto, String marca, String descripcionCorta, String precionVenta, String precioCompra, String stock, int id_provedor, int id_departamento) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.marca = marca;
        this.descripcionCorta = descripcionCorta;
        this.precioVenta = precionVenta;
        this.precioCompra = precioCompra;
        this.stock = stock;
        this.id_provedor = id_provedor;
        this.id_departamento = id_departamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precionVenta) {
        this.precioVenta = precionVenta;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getId_provedor() {
        return id_provedor;
    }

    public void setId_provedor(int id_provedor) {
        this.id_provedor = id_provedor;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }
    
    
    
}
