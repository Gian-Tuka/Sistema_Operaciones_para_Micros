package exception;

public class DuplicateOriginDestinyException extends RuntimeException {
    public DuplicateOriginDestinyException(String origen, String destino) {
        super("El origen no puede ser igual que el destino: \n"+ origen  + "-" + destino);
    }
}
