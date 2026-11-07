package bg.sofia.uni.fmi.mjt.tavern.exception;

public class UnpaidTabException extends RuntimeException {

    public UnpaidTabException(String message) {
        super(message);
    }

    public UnpaidTabException(String message, Throwable cause) {
        super(message, cause);
    }
}