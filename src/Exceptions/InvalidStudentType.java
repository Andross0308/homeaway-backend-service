package Exceptions;

public class InvalidStudentType extends RuntimeException {
    private static final String message = "Invalid student type!";
    public InvalidStudentType() {
        super(message);
    }
}
