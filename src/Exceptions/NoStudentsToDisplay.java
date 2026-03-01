package Exceptions;

public class NoStudentsToDisplay extends RuntimeException {
    private static final String message = "No students yet!";

    public NoStudentsToDisplay() {
        super(message);
    }
}
