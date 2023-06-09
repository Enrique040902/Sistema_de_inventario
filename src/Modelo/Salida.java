package Modelo;

import java.sql.Date;

public class Salida {
    
    private int id;
    private int cantidadProducto;
    private Date fecha;

    public Salida(int id, int cantidadProducto, Date fecha) {
        this.id = id;
        this.cantidadProducto = cantidadProducto;
        this.fecha = fecha;
    }

    public Salida() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
