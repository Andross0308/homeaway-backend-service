package Exceptions;

public class StarOutsideOfThePossibleInterval extends RuntimeException {
    private static final String message = "Invalid stars!";

    public StarOutsideOfThePossibleInterval() {
        super(message);
    }
}
