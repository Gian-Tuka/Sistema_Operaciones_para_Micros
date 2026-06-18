package exception;

public class DuplicateTerminalException extends RuntimeException {
    public DuplicateTerminalException() { super(); }
    public DuplicateTerminalException(String message) { super(message); }
}

