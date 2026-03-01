package Exceptions;

public class StudentTriesMovingToSameHouse extends RuntimeException {
    private static final String message = "That is %s's home!\n";

    public StudentTriesMovingToSameHouse() {
        super(message);
    }
}
