package bg.sofia.uni.fmi.mjt.tavern.exception;

public class BannedGuestException extends RuntimeException {

    public BannedGuestException(String message) {
        super(message);
    }

    public BannedGuestException(String message, Throwable cause) {
        super(message, cause);
    }
}