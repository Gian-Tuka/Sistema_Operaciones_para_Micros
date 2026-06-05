package TDAs.exceptions;

public class EmptyADTException extends GenericADTException {
    public EmptyADTException() {
        super("The ADT is empty.");
    }

    public EmptyADTException(String message) {
        super(message);
    }
}
