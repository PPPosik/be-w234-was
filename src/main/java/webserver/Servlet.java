package webserver;

import util.Request;
import webserver.service.ServiceHandlerMapper;

public class Servlet {
    public String service(Request request) {
        try {
            return ServiceHandlerMapper.getHandler(request.getPath()).handle(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
