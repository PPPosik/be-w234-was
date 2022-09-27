package exception;

import enums.HttpStatusCode;

public class UnauthorizedUserException extends HttpException {
    public UnauthorizedUserException(String message) {
        super(HttpStatusCode.UNAUTHORIZED, message);
    }
}
