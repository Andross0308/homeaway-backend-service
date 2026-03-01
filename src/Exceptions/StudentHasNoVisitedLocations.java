package Exceptions;

public class StudentHasNoVisitedLocations extends RuntimeException {
    private static final String message = "%s has not visited any locations!\n";

    public StudentHasNoVisitedLocations() {
        super(message);
    }
}
