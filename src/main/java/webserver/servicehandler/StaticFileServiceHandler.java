package webserver.servicehandler;

import util.*;
import webserver.service.StaticFileService;

import java.io.File;
import java.io.IOException;

public class StaticFileServiceHandler implements ServiceHandler {
    private final String RESOURCE_DIR = "webapp";
    private final StaticFileService staticFileService;

    public StaticFileServiceHandler(StaticFileService staticFileService) {
        this.staticFileService = staticFileService;
    }

    @Override
    public Response handle(Request request) throws IOException {
        File file = new File(getResourcePath(request.getPath()));

        ResponseEntity entity = staticFileService.service(file);

        return new Response()
                .setHttpStatusCode(entity.getHttpStatusCode())
                .setHeader("Content-Length", String.valueOf(entity.getBody().length))
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setBody(entity.getBody());
    }

    private String getResourcePath(String path) {
        return RESOURCE_DIR + path;
    }
}
