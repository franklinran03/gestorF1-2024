package F1_Query;

import java.util.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Cargar equipos desde el JSON
        List<Equipo> equipos = DataLoader.cargarEquipos("Equipos.json");

        if (equipos == null) {
            System.out.println("ERROR: No se pudieron cargar los equipos.");
            return;
        }

        // ✅ Asignar el equipo manualmente a cada piloto
        for (Equipo equipo : equipos) {
            for (Piloto piloto : equipo.getPilotos()) {
                piloto.setEquipo(equipo);
            }
        }

        // Mostrar cuántos equipos se cargaron
        System.out.println("Equipos cargados: " + equipos.size());
        for (Equipo equipo : equipos) {
            System.out.println("Equipo: " + equipo.getNombre());
        }

        // Crear circuitos manualmente
        List<Circuito> circuitos = List.of(
                new Circuito("GP Bahréin", "Baréin", 5.4, 57),
                new Circuito("GP Arabia Saudi", "Arabia Saudita", 6.1, 50),
                new Circuito("GP Australia", "Australia", 5.2, 58),
                new Circuito("GP Japon", "Japón", 5.8, 53),
                new Circuito("GP China", "China", 5.4, 56),
                new Circuito("GP Miami", "Miami", 5.4, 57),
                new Circuito("GP Emilia Romagna", "Emilia Romaña", 4.9, 63),
                new Circuito("GP Monaco", "Mónaco", 3.3, 78),
                new Circuito("GP Canada", "Canadá", 4.3, 70),
                new Circuito("GP España", "España", 4.6, 66),
                new Circuito("GP Austria", "Austria", 4.3, 71),
                new Circuito("GP Gran Bretaña", "Gran Bretaña", 5.8, 52),
                new Circuito("GP Hungria", "Hungría", 4.3, 70),
                new Circuito("GP Belgica", "Bélgica", 7.0, 44),
                new Circuito("GP Paises Bajos", "Holanda", 5.4, 57),
                new Circuito("GP Italia", "Italia", 5.7, 53),
                new Circuito("GP Azerbaijan", "Azerbaijan", 6.0, 51),
                new Circuito("GP Singapur", "Singapur", 4.9, 62),
                new Circuito("GP Estados Unidos", "Estados Unidos", 5.5, 56),
                new Circuito("GP Ciudad de Mexico", "México", 4.3, 71),
                new Circuito("GP Brasil", "Brasil", 4.3, 69),
                new Circuito("GP Las Vegas", "Las Vegas", 6.2, 50),
                new Circuito("GP Qatar", "Qatar", 5.4, 57),
                new Circuito("GP Abu Dhabi", "Abu Dhabi", 5.2, 58)
        );

        // Crear las carreras a partir de los circuitos
        List<Carrera> carreras = new ArrayList<>();
        for (Circuito circuito : circuitos) {
            Map<Piloto, int[]> resultadosCarrera = new HashMap<>();

            for (Equipo equipo : equipos) {
                for (Piloto piloto : equipo.getPilotos()) {
                    int[] posiciones = piloto.getPosiciones().get(circuito.getNombre());
                    if (posiciones != null) {
                        int inicio = posiciones[0];
                        int fin = posiciones[1];
                        if (!(inicio == 0 && fin == 0) && fin != 0) {
                            resultadosCarrera.put(piloto, posiciones);
                        }
                    }
                }
            }

            boolean esSprint = false; // futura lógica para carreras sprint
            Carrera carrera = esSprint
                    ? new CarreraSprint(circuito, new Date(), resultadosCarrera)
                    : new CarreraPrincipal(circuito, new Date(), resultadosCarrera);

            carreras.add(carrera);
        }

        // Crear el gestor
        GestorCampeonato gestor = new GestorCampeonato(equipos, carreras);

        // Menú interactivo
        while (true) {
            String opcion = JOptionPane.showInputDialog(
                    null,
                    """
                    🏁 Menú F1 2024 🏁
                    1. Consultar información de un equipo
                    2. Consultar información de un piloto
                    3. Consultar posiciones de un piloto en cada carrera
                    4. Consultar puntos de escuderías
                    5. Consultar puntos de un piloto
                    6. Salir
                    """,
                    "F1 Query System",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == null || opcion.equals("6")) break;

            switch (opcion) {
                case "1" -> {
                    String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo:");
                    Optional<Equipo> equipo = gestor.obtenerEquipo(nombreEquipo);
                    equipo.ifPresentOrElse(
                            e -> {
                                StringBuilder info = new StringBuilder();
                                info.append("Nombre: ").append(e.getNombre()).append("\n")
                                        .append("Director: ").append(e.getDirectorGeneral()).append("\n")
                                        .append("País: ").append(e.getPaisOrigen()).append("\n")
                                        .append("Campeonatos: ").append(e.getCampeonatosGanados()).append("\n")
                                        .append("Puntos 2024: ").append(e.getPuntosMundialConstructores()).append("\n")
                                        .append("Pilotos:\n");
                                for (Piloto p : e.getPilotos()) {
                                    info.append("  - ").append(p.getNombreCompleto()).append("\n");
                                }
                                JOptionPane.showMessageDialog(null, info.toString());
                            },
                            () -> JOptionPane.showMessageDialog(null, "Equipo no encontrado.")
                    );
                }
                case "2" -> {
                    String nombrePiloto = JOptionPane.showInputDialog("Ingrese nombre y apellido del piloto:");
                    Optional<Piloto> piloto = gestor.obtenerPiloto(nombrePiloto);
                    piloto.ifPresentOrElse(
                            p -> {
                                String info = """
                                Nombre: %s
                                Equipo: %s
                                Edad: %d
                                País: %s
                                Campeonatos: %d
                                Carreras: %d
                                Puntos 2024: %d
                                """.formatted(
                                        p.getNombreCompleto(),
                                        p.getEquipo().getNombre(),
                                        p.getEdad(),
                                        p.getPaisOrigen(),
                                        p.getCampeonatosGanados(),
                                        p.getCarrerasDisputadas(),
                                        p.getPuntosTemporada()
                                );
                                JOptionPane.showMessageDialog(null, info);
                            },
                            () -> JOptionPane.showMessageDialog(null, "Piloto no encontrado.")
                    );
                }
                case "3" -> {
                    String nombrePiloto = JOptionPane.showInputDialog("Ingrese nombre y apellido del piloto:");
                    Optional<Piloto> piloto = gestor.obtenerPiloto(nombrePiloto);
                    piloto.ifPresentOrElse(
                            p -> {
                                StringBuilder info = new StringBuilder();
                                info.append("Posiciones de ").append(p.getNombreCompleto()).append(":\n");
                                p.getPosiciones().forEach((carrera, posiciones) -> {
                                    info.append("- ").append(carrera)
                                            .append(": Salida ").append(posiciones[0])
                                            .append(", Llegada ").append(posiciones[1]).append("\n");
                                });
                                JOptionPane.showMessageDialog(null, info.toString());
                            },
                            () -> JOptionPane.showMessageDialog(null, "Piloto no encontrado.")
                    );
                }
                case "4" -> {
                    StringBuilder info = new StringBuilder("Puntos por Escudería (Constructores 2024):\n");
                    for (Equipo e : equipos) {
                        info.append("- ").append(e.getNombre())
                                .append(": ").append(e.getPuntosMundialConstructores()).append(" pts\n");
                    }
                    JOptionPane.showMessageDialog(null, info.toString());
                }
                case "5" -> {
                    String nombrePiloto = JOptionPane.showInputDialog("Ingrese nombre y apellido del piloto:");
                    Optional<Piloto> piloto = gestor.obtenerPiloto(nombrePiloto);
                    piloto.ifPresentOrElse(
                            p -> JOptionPane.showMessageDialog(null, p.getNombreCompleto() + " tiene " + p.getPuntosTemporada() + " puntos."),
                            () -> JOptionPane.showMessageDialog(null, "Piloto no encontrado.")
                    );
                }
                default -> JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
            }
        }
    }
}
