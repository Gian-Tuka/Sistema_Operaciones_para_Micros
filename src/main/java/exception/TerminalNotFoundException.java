package exception;

public class TerminalNotFoundException extends RuntimeException {
    public TerminalNotFoundException() { super(); }
    public TerminalNotFoundException(String message) { super(message); }
}

