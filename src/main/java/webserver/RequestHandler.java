package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import exception.RequestParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Request;
import util.RequestParser;
import util.Response;
import util.ResponseSender;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Servlet servlet;
    private RequestParser requestParser;
    private Request request;
    private Response response;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.servlet = new Servlet();

        try {
            this.requestParser = new RequestParser(connection.getInputStream());
        } catch (Exception e) {
            throw new RequestParsingException("잘못된 요청이 발생했습니다.");
        }
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            this.request = requestParser.parse();
            this.response = generateResponse();

            printRequest();

            ResponseSender.send(out, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequest() {
        if (logger.isDebugEnabled()) {
            logger.debug("method : " + request.getMethod().getMethod());
            logger.debug("path : " + request.getPath());
            logger.debug("version : " + request.getVersion());

            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                logger.debug(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    private Response generateResponse() {
        return servlet.service(request);
    }
}
