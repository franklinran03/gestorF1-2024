package F1_Query;

import F1_Query.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class F1QueryTests {
    private GestorCampeonato gestor;
    private Equipo ferrari;
    private Piloto leclerc;
    private Piloto sainz;
    private Circuito monza;
    private Carrera carreraMonza;

    @BeforeEach
    public void setUp() {
        leclerc = new Piloto("Charles", "Leclerc", null, 26, "Mónaco", 0, 100, 180,
                Map.of("GP Monza", new int[]{1, 2}));
        sainz = new Piloto("Carlos", "Sainz", null, 28, "España", 0, 120, 182,
                Map.of("GP Monza", new int[]{3, 1}));

        ferrari = new Equipo("Ferrari", "Fred", "Italia", 16, 300, List.of(leclerc, sainz));
        leclerc.setEquipo(ferrari);
        sainz.setEquipo(ferrari);

        monza = new Circuito("GP Monza", "Italia", 5.79, 53);
        Map<Piloto, int[]> resultados = new HashMap<>();
        resultados.put(leclerc, leclerc.getPosiciones().get("GP Monza"));
        resultados.put(sainz, sainz.getPosiciones().get("GP Monza"));
        carreraMonza = new CarreraPrincipal(monza, new Date(), resultados);

        gestor = new GestorCampeonato(List.of(ferrari), List.of(carreraMonza));
    }

    // Piloto
    @Test public void testGetNombreCompleto() {
        assertEquals("Charles Leclerc", leclerc.getNombreCompleto());
    }

    @Test public void testGetEdad() {
        assertEquals(26, leclerc.getEdad());
    }

    @Test public void testGetPaisOrigen() {
        assertEquals("Mónaco", leclerc.getPaisOrigen());
    }

    @Test public void testGetCampeonatosGanados() {
        assertEquals(0, leclerc.getCampeonatosGanados());
    }

    @Test public void testGetCarrerasDisputadas() {
        assertEquals(100, leclerc.getCarrerasDisputadas());
    }

    @Test public void testGetPuntosTemporada() {
        assertEquals(180, leclerc.getPuntosTemporada());
    }

    @Test public void testSetEquipo() {
        Piloto p = new Piloto();
        p.setEquipo(ferrari);
        assertEquals("Ferrari", p.getEquipo().getNombre());
    }

    @Test
    public void testGetNombrePiloto() {
        assertEquals("Charles", leclerc.getNombre());
    }

    @Test
    public void testGetApellidoPiloto() {
        assertEquals("Leclerc", leclerc.getApellido());
    }


    @Test public void testGetPosiciones() {
        assertTrue(leclerc.getPosiciones().containsKey("GP Monza"));
        assertArrayEquals(new int[]{1, 2}, leclerc.getPosiciones().get("GP Monza"));
    }

    // Equipo
    @Test public void testGetNombreEquipo() {
        assertEquals("Ferrari", ferrari.getNombre());
    }

    @Test public void testGetDirectorGeneral() {
        assertEquals("Fred", ferrari.getDirectorGeneral());
    }

    @Test public void testGetPaisOrigenEquipo() {
        assertEquals("Italia", ferrari.getPaisOrigen());
    }

    @Test public void testGetCampeonatosEquipo() {
        assertEquals(16, ferrari.getCampeonatosGanados());
    }

    @Test public void testGetPuntosConstructores() {
        assertEquals(300, ferrari.getPuntosMundialConstructores());
    }

    @Test public void testGetPilotos() {
        assertEquals(2, ferrari.getPilotos().size());
    }

    // GestorCampeonato
    @Test public void testObtenerPilotoNombreCompleto() {
        Optional<Piloto> resultado = gestor.obtenerPiloto("Carlos Sainz");
        assertTrue(resultado.isPresent());
    }

    @Test public void testObtenerPilotoNombreParcial() {
        Optional<Piloto> resultado = gestor.obtenerPiloto("sain");
        assertTrue(resultado.isPresent());
    }

    @Test public void testPilotoNoExiste() {
        Optional<Piloto> resultado = gestor.obtenerPiloto("Max Verstappen");
        assertFalse(resultado.isPresent());
    }

    @Test public void testObtenerEquipoNombreCompleto() {
        Optional<Equipo> resultado = gestor.obtenerEquipo("Ferrari");
        assertTrue(resultado.isPresent());
    }

    @Test public void testObtenerEquipoNombreParcial() {
        Optional<Equipo> resultado = gestor.obtenerEquipo("Ferr");
        assertTrue(resultado.isPresent());
    }

    @Test public void testEquipoNoExiste() {
        Optional<Equipo> resultado = gestor.obtenerEquipo("Mercedes");
        assertFalse(resultado.isPresent());
    }

    // Carrera
    @Test public void testCarreraContienePilotos() {
        assertEquals(2, carreraMonza.resultados.size());
        assertTrue(carreraMonza.resultados.containsKey(leclerc));
    }

    @Test public void testCarreraPosicionFinalCorrecta() {
        assertEquals(2, carreraMonza.resultados.get(leclerc)[1]);
        assertEquals(1, carreraMonza.resultados.get(sainz)[1]);
    }

    @Test public void testCarreraEsSprintYNoSprint() {
        Carrera carreraSprint = new CarreraSprint(monza, new Date(), carreraMonza.resultados);
        assertTrue(carreraSprint.esSprint);
        assertFalse(carreraMonza.esSprint);
    }

    @Test public void testMostrarResultados() {
        assertDoesNotThrow(() -> carreraMonza.mostrarResultados());
    }

    // DataLoader
    @Test public void testCargarEquiposArchivoNoExiste() {
        List<Equipo> equipos = DataLoader.cargarEquipos("archivo_que_no_existe.json");
        assertNull(equipos);
    }

    @Test public void testCargarEquiposDesdeArchivo() throws Exception {
        String json = """
        [
          {
            "nombre": "MiniTeam",
            "directorGeneral": "Alguien",
            "paisOrigen": "Neverland",
            "campeonatosGanados": 0,
            "puntosMundialConstructores": 0,
            "pilotos": [
              {
                "nombre": "Test",
                "apellido": "Driver",
                "equipo": null,
                "edad": 30,
                "paisOrigen": "Nowhere",
                "campeonatosGanados": 0,
                "carrerasDisputadas": 0,
                "puntosTemporada": 0,
                "posiciones": {}
              }
            ]
          }
        ]
        """;
        try (PrintWriter writer = new PrintWriter(new FileWriter("temp_equipos.json"))) {
            writer.write(json);
        }
        List<Equipo> equipos = DataLoader.cargarEquipos("temp_equipos.json");
        assertNotNull(equipos);
        assertEquals("MiniTeam", equipos.get(0).getNombre());
    }

    @Test
    public void testMostrarPosicionesCarreraPorNombre() {
        assertDoesNotThrow(() -> gestor.mostrarPosicionesCarrera("GP Monza"));
    }
}
