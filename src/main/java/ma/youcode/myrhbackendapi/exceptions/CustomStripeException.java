package ma.youcode.myrhbackendapi.exceptions;

public class CustomStripeException extends RuntimeException {
    public CustomStripeException(String message) {
        super(message);
    }
}
