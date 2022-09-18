package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import exception.RequestParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Request;
import util.RequestParser;
import util.ResponseGenerator;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Servlet servlet;
    private RequestParser requestParser;
    private Request request;
    private ResponseGenerator response;

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

        try (OutputStream out = connection.getOutputStream()) {
            this.request = requestParser.parse();
            this.response = new ResponseGenerator(new DataOutputStream(out));
            byte[] body = generateBody().getBytes();
            String accept = request.getHeaders().get("accept");

            printRequest();

            response
                    .setHttpStatusCode(200)
                    .setHeader("Content-Length", String.valueOf(body.length))
                    .setHeader("Content-Type", accept.split(",")[0] + ";charset=utf-8")
                    .setBody(body)
                    .send();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequest() {
        if (logger.isDebugEnabled()) {
            logger.debug("method : " + request.getMethod());
            logger.debug("path : " + request.getPath());
            logger.debug("version : " + request.getVersion());

            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                logger.debug(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    private String generateBody() {
        return servlet.service(request);
    }
}
