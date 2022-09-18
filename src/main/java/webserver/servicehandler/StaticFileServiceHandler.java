package webserver.servicehandler;

import util.HttpStatusCode;
import util.Request;
import util.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileServiceHandler implements ServiceHandler {
    private final String RESOURCE_DIR = "webapp";

    @Override
    public Response handle(Request request) throws IOException {
        File file = new File(getResourcePath(request.getPath()));

        byte[] body;
        HttpStatusCode httpStatusCode = HttpStatusCode.OK;
        if (file.exists()) {
            body = Files.readString(file.toPath()).getBytes();
        } else {
            body = defaultHandle().getBytes();
            httpStatusCode = HttpStatusCode.NOT_FOUND;
        }

        String accept = request.getHeaders().get("accept");
        return new Response()
                .setHttpStatusCode(httpStatusCode)
                .setHeader("Content-Length", String.valueOf(body.length))
                .setHeader("Content-Type", accept.split(",")[0] + ";charset=utf-8")
                .setBody(body);
    }

    private String getResourcePath(String path) {
        return RESOURCE_DIR + path;
    }

    private String defaultHandle() {
        return "Hello World";
    }
}
