package Exceptions;

public class InvalidChangeOfLocation extends RuntimeException {

    private static final String message = "%s is not a valid service!\n";

    public InvalidChangeOfLocation() {
        super(message);
    }
}
