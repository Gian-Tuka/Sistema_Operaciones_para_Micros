package exception;

public class MicroNoDisponibleException extends Exception {
    public MicroNoDisponibleException(String patente, String fecha) {
        super("Error de Gestión: El micro con patente " + patente + " no está disponible para asignación en la fecha " + fecha + ".");
    }
}
