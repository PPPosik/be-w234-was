package exception;

import enums.HttpStatusCode;

public class NotAcceptableException extends HttpException {
    public NotAcceptableException(String message) {
        super(HttpStatusCode.NOT_ACCEPTABLE, message);
    }
}
