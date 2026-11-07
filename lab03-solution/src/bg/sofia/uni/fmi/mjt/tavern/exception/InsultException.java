package bg.sofia.uni.fmi.mjt.tavern.exception;

public class InsultException extends ProvocationException {

    public InsultException(String message) {
        super(message);
    }

    public InsultException(String message, Throwable cause) {
        super(message, cause);
    }
}