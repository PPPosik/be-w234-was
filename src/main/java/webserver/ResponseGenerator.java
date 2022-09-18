package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseGenerator {
    private final DataOutputStream out;

    public ResponseGenerator(DataOutputStream out) {
        this.out = out;
    }

    public ResponseGenerator set200Header(int lengthOfBodyContent) throws IOException {
        out.writeBytes("HTTP/1.1 200 OK \r\n");
        out.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        return this;
    }

    public ResponseGenerator setContentType(String accept) throws IOException {
        out.writeBytes("Content-Type: " + accept.split(",")[0] + ";charset=utf-8\r\n");
        return this;
    }

    public void setBody(byte[] body) throws IOException {
        out.writeBytes("\r\n");
        out.write(body, 0, body.length);
        out.flush();
    }
}
