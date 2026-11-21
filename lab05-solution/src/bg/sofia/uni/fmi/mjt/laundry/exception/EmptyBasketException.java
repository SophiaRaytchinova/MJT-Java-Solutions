package bg.sofia.uni.fmi.mjt.laundry.exception;

public class EmptyBasketException extends RuntimeException {

    public EmptyBasketException() {
        super();
    }

    public EmptyBasketException(String message) {
        super(message);
    }

}
