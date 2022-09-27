package exception.http;

import enums.HttpStatusCode;
import exception.http.HttpException;

public class PageNotFoundException extends HttpException {
    public PageNotFoundException(String message) {
        super(HttpStatusCode.NOT_FOUND, message);
    }
}
