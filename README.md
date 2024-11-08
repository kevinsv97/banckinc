import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IndexadorArchivo {
    public static List<Long> generarIndices(String rutaArchivo) {
        List<Long> indices = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            long pos = 0;
            String linea;

            while ((linea = br.readLine()) != null) {
                indices.add(pos);
                pos += linea.length() + System.lineSeparator().length();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indices;
    }
}
Acceder directamente usando el índice:

java
Copy code
public List<String> procesarLoteConIndices(String rutaArchivo, List<Long> indices, int tamañoLote, String rutaErrores, int numeroLote) {
    List<String> loteActual = new ArrayList<>();

    int lineaInicial = (numeroLote - 1) * tamañoLote;

    try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r");
         FileWriter escritorErrores = new FileWriter(rutaErrores, true)) {

        for (int i = lineaInicial; i < lineaInicial + tamañoLote && i < indices.size(); i++) {
            raf.seek(indices.get(i)); // Saltar a la posición exacta
            String linea = raf.readLine();

            if (esValido(linea)) {
                loteActual.add(linea);
            } else {
                escritorErrores.write(linea + System.lineSeparator());
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    return loteActual;
}
