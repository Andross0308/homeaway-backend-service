package Exceptions;

public class InvalidServiceType extends RuntimeException {
    private static final String message = "Invalid service type!";

    public InvalidServiceType() {
        super(message);
    }
}
