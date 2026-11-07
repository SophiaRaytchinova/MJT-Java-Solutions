package bg.sofia.uni.fmi.mjt.tavern.exception;

public class CheatingException extends ProvocationException {

    public CheatingException(String message) {
        super(message);
    }

    public CheatingException(String message, Throwable cause) {
        super(message, cause);
    }
}