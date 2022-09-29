package exception.http;

import enums.HttpStatusCode;
import exception.http.HttpException;

public class UnauthorizedUserException extends HttpException {
    public UnauthorizedUserException(String message) {
        super(HttpStatusCode.UNAUTHORIZED, message);
    }
}
