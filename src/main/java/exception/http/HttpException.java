package exception.http;

import enums.HttpStatusCode;
import enums.Mime;
import util.http.Response;

public class HttpException extends Exception {
    private HttpStatusCode httpStatusCode;
    private String message;

    public HttpException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public Response generateErrorResponse() {
        return new Response()
                .setHttpStatusCode(httpStatusCode)
                .setHeader("Content-Type", Mime.NONE.getMime()+";charset=utf-8")
                .setBody(message);
    }
}
