package Exceptions;

public class SystemNotDefinedBounds extends RuntimeException {
    private static final String message = "System bounds not defined.";

    public SystemNotDefinedBounds() {
        super(message);
    }
}
