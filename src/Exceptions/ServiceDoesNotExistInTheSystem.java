package Exceptions;

public class ServiceDoesNotExistInTheSystem extends RuntimeException {
    private static final String message = "Unknown %s!\n";

    public ServiceDoesNotExistInTheSystem() {
        super(message);
    }
}
