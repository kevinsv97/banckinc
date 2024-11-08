import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Validador {

    public static boolean validar(String jsonConfig, Object objeto, boolean esPropiedadPoliza) {
        Gson gson = new Gson();

        // Parsear el JSON de configuración
        JsonObject jsonObject = JsonParser.parseString(jsonConfig).getAsJsonObject();
        
        // Obtener el valor de "field_validation"
        JsonObject fieldValidation = jsonObject.getAsJsonObject("field_validation");

        // Convertir las listas de campos desde el JSON
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> requiredFields = gson.fromJson(fieldValidation.get("required_fields"), type);
        List<String> ownershipCheckFields = gson.fromJson(fieldValidation.get("ownership_check_fields"), type);

        try {
            // Validar los campos obligatorios
            for (String field : requiredFields) {
                // Construir el nombre del getter
                String getterName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
                Method getterMethod = objeto.getClass().getMethod(getterName);

                Object value = getterMethod.invoke(objeto);
                if (value == null) {
                    System.out.println("El campo " + field + " es obligatorio y está vacío o nulo.");
                    return false;
                }
            }

            // Validar los campos de verificación de propiedad si es necesario
            if (esPropiedadPoliza) {
                for (String field : ownershipCheckFields) {
                    String getterName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
                    Method getterMethod = objeto.getClass().getMethod(getterName);

                    Object value = getterMethod.invoke(objeto);
                    if (value == null) {
                        System.out.println("El campo " + field + " es obligatorio para la verificación de propiedad y está vacío o nulo.");
                        return false;
                    }
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }








        public List<List<MiObjeto>> procesarArchivoPorLotes(String rutaArchivo, int tamañoLote, String rutaErrores) {
        List<List<MiObjeto>> lotes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
             FileWriter escritorErrores = new FileWriter(rutaErrores, true)) {  // Abrir en modo append

            String linea;
            List<MiObjeto> loteActual = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split("\\|");
                MiObjeto objeto = new MiObjeto(atributos[0], atributos[1], atributos[2]);

                if (objeto.esValido()) {
                    loteActual.add(objeto);
                } else {
                    // Escribir la línea inválida en el archivo de errores
                    escritorErrores.write(linea + System.lineSeparator());
                }

                if (loteActual.size() == tamañoLote) {
                    lotes.add(new ArrayList<>(loteActual));
                    loteActual.clear();
                }
            }

            // Agregar el último lote si tiene elementos
            if (!loteActual.isEmpty()) {
                lotes.add(loteActual);
            }

        } catch (IOException e) {
            e.printStackTrace();  // Manejar excepción según sea necesario
        }

        return lotes;
    }
}

        return true;
    }
}
