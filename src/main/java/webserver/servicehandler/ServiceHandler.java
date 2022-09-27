package webserver.servicehandler;

import exception.HttpException;
import util.http.Request;
import util.http.Response;

public interface ServiceHandler {
    Response handle(Request request) throws HttpException;
}
