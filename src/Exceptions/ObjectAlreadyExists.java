package Exceptions;

public class ObjectAlreadyExists extends RuntimeException {
    private static final String message = "%s already exists!\n";

    public ObjectAlreadyExists() {
        super(message);
    }
}
