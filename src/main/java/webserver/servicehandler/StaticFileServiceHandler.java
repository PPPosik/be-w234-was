package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import exception.HttpException;
import util.*;
import webserver.service.StaticFileService;

public class StaticFileServiceHandler implements ServiceHandler {
    private final StaticFileService staticFileService;

    public StaticFileServiceHandler(StaticFileService staticFileService) {
        this.staticFileService = staticFileService;
    }

    @Override
    public Response handle(Request request) throws HttpException{
        byte[] body = staticFileService.serviceStaticFile(request.getPath());

        return new Response()
                .setHttpStatusCode(HttpStatusCode.OK)
                .setHeader("Content-Length", String.valueOf(body.length))
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setBody(body);
    }
}
