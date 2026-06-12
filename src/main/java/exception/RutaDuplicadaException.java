package exception;

public class RutaDuplicadaException extends Exception {
    public RutaDuplicadaException(String origen, String destino) {
        super("Error de Planificación: Ya se encuentra registrada una ruta con el trayecto " + origen + " - " + destino + ".");
    }
}
