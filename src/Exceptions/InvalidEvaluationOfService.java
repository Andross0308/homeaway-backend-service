package Exceptions;

public class InvalidEvaluationOfService extends RuntimeException {
    private static final String message = "Invalid evaluation!";
    public InvalidEvaluationOfService() {
        super(message);
    }
}
