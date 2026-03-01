package Exceptions;

public class NegativeLeisureServiceTicketPrice extends RuntimeException {
    private static final String message = "Invalid ticket price!";

    public NegativeLeisureServiceTicketPrice() {super(message);}

}
