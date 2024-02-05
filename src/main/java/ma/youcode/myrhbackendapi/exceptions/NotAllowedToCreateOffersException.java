package ma.youcode.myrhbackendapi.exceptions;

public class NotAllowedToCreateOffersException extends RuntimeException {
    public NotAllowedToCreateOffersException(String message) {
        super(message);
    }
}
