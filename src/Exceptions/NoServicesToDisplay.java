package Exceptions;

public class NoServicesToDisplay extends RuntimeException {
    private static final String message = "No services yet!";

    public NoServicesToDisplay() {
        super(message);
    }
}
