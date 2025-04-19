package F1_Query;

import java.util.List;
import java.util.Optional;

public class Equipo {
    private String nombre;
    private String directorGeneral;
    private String paisOrigen;
    private int campeonatosGanados;
    private int puntosMundialConstructores;
    private List<Piloto> pilotos;

    public Equipo() {
        // Necesario para Gson
    }

    public Equipo(String nombre, String directorGeneral, String paisOrigen, int campeonatosGanados, int puntosMundialConstructores, List<Piloto> pilotos) {
        this.nombre = nombre;
        this.directorGeneral = directorGeneral;
        this.paisOrigen = paisOrigen;
        this.campeonatosGanados = campeonatosGanados;
        this.puntosMundialConstructores = puntosMundialConstructores;
        this.pilotos = pilotos;
    }

    public String getNombre() { return nombre; }
    public int getPuntosMundialConstructores() { return puntosMundialConstructores; }

    public List<Piloto> getPilotos() {
        return pilotos;
    }

    public String getDirectorGeneral() {
        return directorGeneral;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public int getCampeonatosGanados() {
        return campeonatosGanados;
    }
}

