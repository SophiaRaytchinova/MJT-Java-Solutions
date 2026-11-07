package bg.sofia.uni.fmi.mjt.tavern.exception;

public class BrawlException extends RuntimeException {

    public BrawlException(String message) {
        super(message);
    }

    public BrawlException(String message, Throwable cause) {
        super(message, cause);
    }
}