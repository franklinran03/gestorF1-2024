package F1_Query;


import java.util.Date;
import java.util.Map;

public class CarreraSprint extends Carrera {
    public CarreraSprint(Circuito circuito, Date fecha, Map<Piloto, int[]> resultados) {
        super(circuito, fecha, true, resultados);
    }
}
