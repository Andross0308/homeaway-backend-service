package Exceptions;

public class NegativeLodgingServiceRoomPrice extends RuntimeException {
    private static final String message = "Invalid room price!";

    public NegativeLodgingServiceRoomPrice() {
        super(message);
    }
}
