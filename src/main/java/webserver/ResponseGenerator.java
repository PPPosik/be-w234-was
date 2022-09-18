package webserver;

import util.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseGenerator {
    private final DataOutputStream out;

    private HttpStatusCode httpStatusCode;
    private Map<String, String> headers;
    private byte[] body;

    public ResponseGenerator(DataOutputStream out) {
        this.out = out;
        this.httpStatusCode = HttpStatusCode.OK;
        this.headers = new HashMap<>();
    }

    public ResponseGenerator setHttpStatusCode(int code) {
        this.httpStatusCode = HttpStatusCode.getByValue(code);
        return this;
    }

    public ResponseGenerator setHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public ResponseGenerator setBody(String body) {
        this.body = body.getBytes();
        return this;
    }

    public ResponseGenerator setBody(byte[] body) {
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
