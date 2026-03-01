package Exceptions;

public class HomeDoesNotExistInTheSystem extends RuntimeException {
    private static final String message = "lodging %s does not exist!\n";

    public HomeDoesNotExistInTheSystem() {
        super(message);
    }
}
