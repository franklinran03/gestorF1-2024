package F1_Query;

public class Circuito {
    private String nombre;
    private String pais;
    private double longitudKm;
    private int vueltas;

    public Circuito(String nombre, String pais, double longitudKm, int vueltas) {
        this.nombre = nombre;
        this.pais = pais;
        this.longitudKm = longitudKm;
        this.vueltas = vueltas;
    }

    public String getNombre() { return nombre; }
}

