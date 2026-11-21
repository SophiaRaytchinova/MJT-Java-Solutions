package bg.sofia.uni.fmi.mjt.laundry.exception;

public class EmptyQueueException extends RuntimeException {

    public EmptyQueueException() {
        super();
    }

    public EmptyQueueException(String message) {
        super(message);
    }

}
