package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.http.Request;
import util.http.RequestParser;
import util.http.Response;
import util.http.ResponseSender;

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
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8))) {
            this.requestParser = new RequestParser(br);
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
