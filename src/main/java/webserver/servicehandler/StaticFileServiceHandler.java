package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import util.*;
import webserver.service.StaticFileService;

public class StaticFileServiceHandler implements ServiceHandler {
    private final StaticFileService staticFileService;

    public StaticFileServiceHandler(StaticFileService staticFileService) {
        this.staticFileService = staticFileService;
    }

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        byte[] body;

        try {
            body = staticFileService.serviceStaticFile(request.getPath());
            response.setHttpStatusCode(HttpStatusCode.OK);
        } catch (Exception e) {
            body = staticFileService.serviceDefault();
            response.setHttpStatusCode(HttpStatusCode.NOT_FOUND);
            System.out.println("e = " + e);
        }

        return response
                .setHeader("Content-Length", String.valueOf(body.length))
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setBody(body);
    }
}
