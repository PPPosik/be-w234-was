package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Request;
import util.Response;
import webserver.servicehandler.ServiceHandlerMapper;

public class Servlet {
    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    public Response service(Request request) {
        try {
            return ServiceHandlerMapper.getHandler(request.getPath()).handle(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Response().setHttpStatusCode(500).setBody(e.getMessage());
        }
    }
}
