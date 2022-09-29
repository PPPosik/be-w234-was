package exception.http;

import enums.HttpStatusCode;

public class BadRequestException extends HttpException {
    public BadRequestException(String message) {
        super(HttpStatusCode.BAD_REQUEST, message);
    }
}
