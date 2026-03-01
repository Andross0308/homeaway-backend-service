package Exceptions;

public class ServiceHasNoStudents extends RuntimeException {
  private static final String message = "No students on %s!\n";

    public ServiceHasNoStudents() {
        super(message);
    }
}
