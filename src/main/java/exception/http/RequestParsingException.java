package exception.http;

import enums.HttpStatusCode;
import exception.http.HttpException;

public class RequestParsingException extends HttpException {
    public RequestParsingException(String message) {
        super(HttpStatusCode.INTERNAL_SERVER_ERROR, message);
    }
}
