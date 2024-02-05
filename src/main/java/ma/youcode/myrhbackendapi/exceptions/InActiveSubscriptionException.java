package ma.youcode.myrhbackendapi.exceptions;

public class InActiveSubscriptionException extends RuntimeException {
    public InActiveSubscriptionException(String message) {
        super(message);
    }
}
