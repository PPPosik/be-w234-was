package webserver;

import exception.UserNotValidException;
import exception.UserSaveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import enums.HttpStatusCode;
import enums.Mime;
import util.Request;
import util.Response;
import webserver.servicehandler.ServiceHandlerMapper;

public class Servlet {
    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    public Response service(Request request) {
        try {
            return ServiceHandlerMapper.getHandler(request.getPath()).handle(request);
        } catch (UserSaveException | UserNotValidException e) {
            logger.error(e.toString());
            return generateErrorResponse(HttpStatusCode.BAD_REQUEST, e);
        } catch (Exception e) {
            logger.error(e.toString());
            return generateErrorResponse(HttpStatusCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private Response generateErrorResponse(HttpStatusCode httpStatusCode, Exception e) {
        return new Response()
                .setHttpStatusCode(httpStatusCode)
                .setHeader("Content-Type", Mime.NONE.getMime()+";charset=utf-8")
                .setBody(e.getMessage());
    }
}
