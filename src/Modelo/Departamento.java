package Modelo;

public class Departamento {
    
    private int id;
    private String nombreDepatamento;

    public Departamento() {
    }

    public Departamento(int id, String nombreDepatamento) {
        this.id = id;
        this.nombreDepatamento = nombreDepatamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreDepatamento() {
        return nombreDepatamento;
    }

    public void setNombreDepatamento(String nombreDepatamento) {
        this.nombreDepatamento = nombreDepatamento;
    }
    
    
    
}
