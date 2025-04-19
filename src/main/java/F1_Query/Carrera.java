package F1_Query;

import java.util.Date;
import java.util.Map;

public class Carrera {
    protected Circuito circuito;
    protected Date fecha;
    protected boolean esSprint;
    protected Map<Piloto, int[]> resultados; // { piloto: [posición_inicio, posición_final] }

    public Carrera(Circuito circuito, Date fecha, boolean esSprint, Map<Piloto, int[]> resultados) {
        this.circuito = circuito;
        this.fecha = fecha;
        this.esSprint = esSprint;
        this.resultados = resultados;
    }

    public void mostrarResultados() {
        for (Map.Entry<Piloto, int[]> resultado : resultados.entrySet()) {
            System.out.println(resultado.getKey().getNombreCompleto() + " - Posición final: " + resultado.getValue()[1]);
        }
    }
}

