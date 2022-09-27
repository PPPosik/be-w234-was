package webserver.servicehandler;

import exception.HttpException;
import util.Request;
import util.Response;

public interface ServiceHandler {
    Response handle(Request request) throws HttpException;
}
