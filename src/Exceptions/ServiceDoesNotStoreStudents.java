package Exceptions;

public class ServiceDoesNotStoreStudents extends RuntimeException {
    private static final String message = "%s does not control student entry and exit!\n";

    public ServiceDoesNotStoreStudents() {
        super(message);
    }
}
