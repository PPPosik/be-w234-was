package exception;

import enums.HttpStatusCode;

public class PageNotFoundException extends HttpException {
    public PageNotFoundException(String message) {
        super(HttpStatusCode.NOT_FOUND, message);
    }
}
