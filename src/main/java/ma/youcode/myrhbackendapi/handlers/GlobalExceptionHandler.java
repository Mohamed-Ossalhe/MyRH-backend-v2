package ma.youcode.myrhbackendapi.handlers;

import ma.youcode.myrhbackendapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exception occurred by the request DTOs when posting data using a form
     * @param exception - {@link MethodArgumentNotValidException}
     * @return HashMap with all the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> messages = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    messages.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }

    /**
     * Resource not found exception handler returns customizable error response entity
     * @param exception - {@link ResourceNotFoundException}
     * @return {@link ErrorResponse} contains all details about the exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Resource already exist exception handler returns customizable error response entity
     * @param exception - {@link ResourceAlreadyExistException}
     * @return {@link ErrorResponse} contains all details about the exception
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistException(ResourceAlreadyExistException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * throw a customizable error response to the frontend if the token is expired
     * @param exception {@link TokenExpirationException}
     * @return {@link ErrorResponse} contains all details about the exception
     */
    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpirationException(TokenExpirationException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Throw a customizable error response to the frontend if the token is invalid
     * @param exception {@link InvalidVerificationCodeException}
     * @return {@link ErrorResponse} contains all the details about the exception
     */
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidVerificationCodeException(InvalidVerificationCodeException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Throw a customizable error response to the frontend if the credentials provided don't match the existing ones
     * @param exception {@link BadCredentialsException}
     * @return {@link ErrorResponse} custom error response contains all details about the exception
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles SomethingWentWrongException exception when something unexpected happened
     * @param exception {@link SomethingWentWrongException}
     * @return {@link ErrorResponse} custom error response contains all details about the exception
     */
    @ExceptionHandler(SomethingWentWrongException.class)
    public ResponseEntity<ErrorResponse> handleSomethingWentWrongException(SomethingWentWrongException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles Stripe Exception when processing payments
     * @param exception {@link CustomStripeException}
     * @return {@link ErrorResponse} custom error response contains all details about the exception
     */
    @ExceptionHandler(CustomStripeException.class)
    public ResponseEntity<ErrorResponse> handleCustomStripeException(CustomStripeException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.EXPECTATION_FAILED, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Unverified User Exception when he commits an action
     * @param exception {@link UnverifiedUserException}
     * @return {@link ErrorResponse} custom error response contains all details about the exception
     */
    @ExceptionHandler(UnverifiedUserException.class)
    public ResponseEntity<ErrorResponse> handleUnverifiedUserException(UnverifiedUserException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handle {@link InActiveSubscriptionException} if user subscription is not active
     * @param exception {@link InActiveSubscriptionException}
     * @return {@link ErrorResponse} custom error response contains all details about the exception
     */
    @ExceptionHandler(InActiveSubscriptionException.class)
    public ResponseEntity<ErrorResponse> handleInActiveSubscriptionException(InActiveSubscriptionException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handle {@link NotAllowedToCreateOffersException} if user subscription pack number of offers
     * is not unlimited, and he reached the limit of offers he can post
     * @param exception {@link NotAllowedToCreateOffersException}
     * @return {@link ErrorResponse} custom error response contains all details about the exception
     */
    @ExceptionHandler(NotAllowedToCreateOffersException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedToCreateOffersException(NotAllowedToCreateOffersException exception) {
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.PAYMENT_REQUIRED, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.PAYMENT_REQUIRED);
    }
}
