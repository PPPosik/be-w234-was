package util.http;

import enums.HttpStatusCode;
import enums.Mime;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final Map<String, String> headers;

    private HttpStatusCode httpStatusCode;
    private byte[] body;
    private Cookie cookie;

    public Response() {
        this.httpStatusCode = HttpStatusCode.OK;
        this.headers = new HashMap<>();
        this.cookie = new Cookie();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public byte[] getBody() {
        return body;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public Response setHttpStatusCode(int code) {
        this.httpStatusCode = HttpStatusCode.getByValue(code);
        return this;
    }

    public Response setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public Response setHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public Response setRedirect(String url) {
        this.headers.put("Location", url);
        return this;
    }

    public Response setContentType(Request request) {
        this.headers.put("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8");
        return this;
    }

    public Response setContentType(Mime mime) {
        this.headers.put("Content-Type", mime.getMime() + ";charset=utf-8");
        return this;
    }

    public Response setBody(String body) {
        this.body = body.getBytes();
        return this;
    }

    public Response setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public Response setCookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }
}
