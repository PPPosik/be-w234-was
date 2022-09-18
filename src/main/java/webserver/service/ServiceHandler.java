package webserver.service;

import util.RequestParser;

import java.io.IOException;

public interface ServiceHandler {
    String handle(RequestParser requestParser) throws IOException;
}
