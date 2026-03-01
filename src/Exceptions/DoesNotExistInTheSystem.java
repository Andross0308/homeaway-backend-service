package Exceptions;

public class DoesNotExistInTheSystem extends RuntimeException{

    private static final String message = "%s does not exist!\n";

    public DoesNotExistInTheSystem(){super(message);}
}
