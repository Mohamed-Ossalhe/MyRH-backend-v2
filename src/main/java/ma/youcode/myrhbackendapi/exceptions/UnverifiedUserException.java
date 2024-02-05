package ma.youcode.myrhbackendapi.exceptions;

public class UnverifiedUserException extends RuntimeException {
    public UnverifiedUserException(String message) {
        super(message);
    }
}
