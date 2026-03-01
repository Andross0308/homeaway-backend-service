package Exceptions;

public class EatingServiceIsFull extends RuntimeException {
    private static final String message = "eating %s is full!\n";

    public EatingServiceIsFull() {
        super(message);
    }
}
