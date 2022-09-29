package exception.http;

import enums.HttpStatusCode;
import exception.http.HttpException;

public class NotAcceptableException extends HttpException {
    public NotAcceptableException(String message) {
        super(HttpStatusCode.NOT_ACCEPTABLE, message);
    }
}
