package Exceptions;

public class ServiceOutsideOfBounds extends RuntimeException {
    private static final String message = "Invalid location!";

    public ServiceOutsideOfBounds() {
        super(message);
    }
}
