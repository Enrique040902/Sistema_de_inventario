package Modelo;

import java.sql.Date;

public class Entrada {
    
    private int id;
    private int idProveedor;
    private Date Fecha;
    private int cantidadProducto;

    public Entrada(int id, int idProveedor, Date Fecha, int cantidadProducto) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.Fecha = Fecha;
        this.cantidadProducto = cantidadProducto;
    }

    public Entrada() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
    
    
    
    
}
