package exception;

public class RequestParsingException extends RuntimeException {
    public RequestParsingException(String message) {
        super(message);
    }
}
