package webserver.service;

import util.Request;

import java.io.IOException;

public interface ServiceHandler {
    String handle(Request request) throws IOException;
}
