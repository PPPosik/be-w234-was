package util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private final DataOutputStream out;
    private final Map<String, String> headers;

    private HttpStatusCode httpStatusCode;
    private byte[] body;

    public Response(DataOutputStream out) {
        this.out = out;
        this.httpStatusCode = HttpStatusCode.OK;
        this.headers = new HashMap<>();
    }

    public Response setHttpStatusCode(int code) {
        this.httpStatusCode = HttpStatusCode.getByValue(code);
        return this;
    }

    public Response setHeader(String name, String value) {
        this.headers.put(name, value);
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

    public void send() throws IOException {
        out.writeBytes("HTTP/1.1 " + httpStatusCode.getValue() + " " + httpStatusCode.getDescription() + "\r\n");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            out.writeBytes(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }

        out.writeBytes("\r\n");
        out.write(body, 0, body.length);
        out.flush();
    }
}
