import org.json.JSONObject;
import org.json.JSONArray;

import java.lang.reflect.Method;

public class Validador {

    public static boolean validar(JSONObject reglas, Object objeto, boolean esPropiedadPoliza) throws Exception {
        JSONObject fieldValidation = reglas.getJSONObject("field_validation");

        // Validar campos obligatorios
        JSONArray requiredFields = fieldValidation.getJSONArray("required_fields");
        if (!validarCampos(requiredFields, objeto)) {
            return false;
        }

        // Validar campos generales
        JSONArray validationFields = fieldValidation.getJSONArray("validation_fields");
        if (!validarCampos(validationFields, objeto)) {
            return false;
        }

        // Validación adicional para propiedad de póliza
        if (esPropiedadPoliza) {
            JSONArray ownershipFields = fieldValidation.getJSONArray("ownership_check_fields");
            if (!validarCampos(ownershipFields, objeto)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validarCampos(JSONArray campos, Object objeto) throws Exception {
        for (int i = 0; i < campos.length(); i++) {
            String campo = campos.getString(i);

            // Genera el nombre del getter correspondiente (ejemplo: getFirstName para el campo "firstName")
            String getterName = "get" + campo.substring(0, 1).toUpperCase() + campo.substring(1);

            // Obtiene el método getter y lo invoca para obtener el valor del campo
            Method getter = objeto.getClass().getMethod(getterName);
            Object valor = getter.invoke(objeto);

            // Validación: verifica si el campo es null o vacío
            if (valor == null || valor.toString().isEmpty()) {
                System.out.println("El campo " + campo + " es obligatorio y no puede estar vacío.");
                return false;
            }
        }
        return true;
    }
}
