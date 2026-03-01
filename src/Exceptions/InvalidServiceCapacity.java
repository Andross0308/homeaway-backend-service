package Exceptions;

public class InvalidServiceCapacity extends RuntimeException {
    private static final String message = "Invalid capacity!";

    public InvalidServiceCapacity() {
        super(message);
    }
}
