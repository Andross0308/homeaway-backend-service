package Exceptions;

public class StudentLocationIsTheNewLocation extends RuntimeException {
    private static final String message = "Already there!";

    public StudentLocationIsTheNewLocation() {
        super(message);
    }
}
