package Exceptions;

public class NoServiceWithThatRating extends RuntimeException {
    private static final String message = "No %s services with average!\n";

    public NoServiceWithThatRating() {
        super(message);
    }
}
