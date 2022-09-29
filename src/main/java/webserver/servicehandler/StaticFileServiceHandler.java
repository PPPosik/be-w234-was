package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import exception.http.HttpException;
import exception.http.PageNotFoundException;
import util.http.Request;
import util.http.Response;
import webserver.service.StaticFileService;

import java.io.File;

public class StaticFileServiceHandler implements ServiceHandler {
    private final StaticFileService staticFileService;

    public StaticFileServiceHandler(StaticFileService staticFileService) {
        this.staticFileService = staticFileService;
    }

    @Override
    public Response handle(Request request) throws HttpException{
        File file = new File(staticFileService.getResourcePath(request.getPath()));

        byte[] body;
        if (file.exists()) {
            body = staticFileService.serviceStaticFile(file);
        } else {
            throw new PageNotFoundException("파일을 찾을 수 없습니다.");
        }

        return new Response()
                .setHttpStatusCode(HttpStatusCode.OK)
                .setHeader("Content-Length", String.valueOf(body.length))
                .setContentType(request)
                .setBody(body);
    }
}
