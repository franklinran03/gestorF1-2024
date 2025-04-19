package F1_Query;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.List;

public class DataLoader {
    public static List<Equipo> cargarEquipos(String archivo) {
        try {
            Gson gson = new Gson();
            System.out.println("Ruta de ejecución actual: " + System.getProperty("user.dir"));

            FileReader reader = new FileReader(archivo);
            return List.of(gson.fromJson(reader, Equipo[].class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

