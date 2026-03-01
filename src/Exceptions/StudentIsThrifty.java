package Exceptions;

public class StudentIsThrifty extends RuntimeException {
    private static final String message = "%s is thrifty!\n";

    public StudentIsThrifty() {
        super(message);
    }
}
