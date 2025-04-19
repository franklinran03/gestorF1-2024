package F1_Query;

import java.util.Map;

public class Piloto {
    private String nombre;
    private String apellido;
    private Equipo equipo;
    private int edad;
    private String paisOrigen;
    private int campeonatosGanados;
    private int carrerasDisputadas;
    private int puntosTemporada;
    private Map<String, int[]> posiciones; // { "GP Bahréin": [posición_inicio, posición_final] }

    public Piloto() {
        // Necesario para que Gson pueda crear instancias
    }

    public Piloto(String nombre, String apellido, Equipo equipo, int edad, String paisOrigen, int campeonatosGanados, int carrerasDisputadas, int puntosTemporada, Map<String, int[]> posiciones) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.equipo = equipo;
        this.edad = edad;
        this.paisOrigen = paisOrigen;
        this.campeonatosGanados = campeonatosGanados;
        this.carrerasDisputadas = carrerasDisputadas;
        this.puntosTemporada = puntosTemporada;
        this.posiciones = posiciones;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public int getPuntosTemporada() {
        return puntosTemporada;
    }

    public Map<String, int[]> getPosiciones() {
        return posiciones;
    }

    public int getCarrerasDisputadas() {
        return carrerasDisputadas;
    }

    public int getCampeonatosGanados() {
        return campeonatosGanados;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public int getEdad() {
        return edad;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}

