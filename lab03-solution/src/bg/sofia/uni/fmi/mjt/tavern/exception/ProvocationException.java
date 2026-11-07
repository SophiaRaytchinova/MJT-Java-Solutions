package bg.sofia.uni.fmi.mjt.tavern.exception;

public class ProvocationException extends RuntimeException {

    public ProvocationException(String message) {
        super(message);
    }

    public ProvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}