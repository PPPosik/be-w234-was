package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import exception.RequestParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestParser requestParser;
    private Servlet servlet;

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
        printRequest();

        try (OutputStream out = connection.getOutputStream()) {
            ResponseGenerator response = new ResponseGenerator(new DataOutputStream(out));
            byte[] body = generateBody();
            String accept = requestParser.headers.get("accept");
            response.set200Header(body.length).setContentType(accept).setBody(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequest() {
        if (logger.isDebugEnabled()) {
            logger.debug("method : " + requestParser.method);
            logger.debug("path : " + requestParser.path);
            logger.debug("version : " + requestParser.version);

            for (Map.Entry<String, String> entry : requestParser.headers.entrySet()) {
                logger.debug(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    private byte[] generateBody() {
        return servlet.service(requestParser).getBytes();
    }
}
