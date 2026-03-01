package Exceptions;

public class LodgingIsFull extends RuntimeException {
    private static final String message = "lodging %s is full!\n";

    public LodgingIsFull() {
        super(message);
    }
}
