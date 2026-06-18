package exception;

public class MicroNotFoundException extends RuntimeException {
    public MicroNotFoundException() { super(); }
    public MicroNotFoundException(String message) { super(message); }
}

