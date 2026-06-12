package exception;

public class TerminalDesconectadaException extends Exception {
    public TerminalDesconectadaException(String codigoTerminal) {
        super("Error de Conexión: La terminal [" + codigoTerminal + "] se encuentra actualmente desconectada del mapa de rutas.");
    }
}
