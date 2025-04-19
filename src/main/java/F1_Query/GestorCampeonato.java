package F1_Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GestorCampeonato {
    private List<Equipo> equipos;
    private List<Carrera> carreras;

    public GestorCampeonato(List<Equipo> equipos, List<Carrera> carreras) {
        this.equipos = equipos;
        this.carreras = carreras;
    }

    public Optional<Equipo> obtenerEquipo(String nombreParcial) {
        return equipos.stream()
                .filter(e -> e.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .findFirst();
    }


    public Optional<Piloto> obtenerPiloto(String nombreParcial) {
        return equipos.stream()
                .flatMap(e -> e.getPilotos().stream())
                .filter(p -> p.getNombreCompleto().toLowerCase().contains(nombreParcial.toLowerCase()))
                .findFirst();
    }

    public void mostrarPosicionesCarrera(String nombreCarrera) {
        for (Carrera carrera : carreras) {
            if (carrera.circuito.getNombre().equalsIgnoreCase(nombreCarrera)) {
                carrera.mostrarResultados();
            }
        }
    }
}

