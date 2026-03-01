package Exceptions;

public class InvalidLeisureServiceDiscount extends RuntimeException {
    private static final String message = "Invalid discount price!";

    public InvalidLeisureServiceDiscount() {
        super(message);
    }
}
