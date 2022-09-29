package webserver.servicehandler;

import util.Request;
import util.Response;

import java.io.IOException;

public interface ServiceHandler {
    Response handle(Request request) throws IOException;
}
