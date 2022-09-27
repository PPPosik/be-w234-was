package exception;

import enums.HttpStatusCode;

public class RequestParsingException extends HttpException {
    public RequestParsingException(String message) {
        super(HttpStatusCode.INTERNAL_SERVER_ERROR, message);
    }
}
