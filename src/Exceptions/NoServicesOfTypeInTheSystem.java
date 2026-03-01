package Exceptions;

public class NoServicesOfTypeInTheSystem extends RuntimeException {
    private static final String message = "No %s services!\n";

    public NoServicesOfTypeInTheSystem() {
        super(message);
    }
}
