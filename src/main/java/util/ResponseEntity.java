package util;

public class ResponseEntity {
    private byte[] body;
    private HttpStatusCode httpStatusCode = HttpStatusCode.OK;

    public byte[] getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body.getBytes();
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
