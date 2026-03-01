package Exceptions;

public class NoStudentOfCountryToDisplay extends RuntimeException {
    private static final String message = "No students from %s!\n";

    public NoStudentOfCountryToDisplay() {
        super(message);
    }
}
