package Exceptions;

public class NegativeEatingServicePrice extends RuntimeException {
    private static final String message = "Invalid menu price!";

    public NegativeEatingServicePrice() {
        super(message);
    }
}
