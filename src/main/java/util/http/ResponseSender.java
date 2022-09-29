package util.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseSender {
    public static void send(DataOutputStream out, Response response) throws IOException {
        out.writeBytes("HTTP/1.1 " + response.getHttpStatusCode().getValue() + " " + response.getHttpStatusCode().getDescription() + "\r\n");

        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            out.writeBytes(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }

        if (response.getCookie().getSize() > 0) {
            out.writeBytes("Set-Cookie: " + response.getCookie().toString() + "\r\n");
        }

        out.writeBytes("\r\n");
        out.write(response.getBody(), 0, response.getBody().length);
        out.flush();
    }
}
