package F1_Query;

import java.util.Date;
import java.util.Map;

public class CarreraPrincipal extends Carrera {
    public CarreraPrincipal(Circuito circuito, Date fecha, Map<Piloto, int[]> resultados) {
        super(circuito, fecha, false, resultados);
    }
}


