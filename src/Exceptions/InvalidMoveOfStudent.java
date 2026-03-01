package Exceptions;

public class InvalidMoveOfStudent extends RuntimeException {

    private static final String message = "Move is not acceptable for %s!\n";
    public InvalidMoveOfStudent() {
        super(message);
    }
}
