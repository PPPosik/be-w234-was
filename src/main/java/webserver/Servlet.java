package webserver;

import exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import enums.HttpStatusCode;
import util.http.Request;
import util.http.Response;
import webserver.servicehandler.mapper.ServiceHandlerMapper;

public class Servlet {
    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    public Response service(Request request) {
        try {
            return ServiceHandlerMapper.getHandler(request.getPath()).handle(request);
        } catch (HttpException e) {
            logger.error(e.toString());
            return e.generateErrorResponse();
        } catch (Exception e) {
            logger.error(e.toString());
            return new Response()
                    .setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .setBody(e.getMessage());
        }
    }
}
