package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Request;
import util.RequestParser;
import util.Response;
import webserver.service.StaticFileService;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileServiceHandlerTest {
    final StaticFileService service = new StaticFileService();
    final StaticFileServiceHandler handler = new StaticFileServiceHandler(service);

    Request htmlRequest, cssRequest, errRequest;

    @BeforeEach
    void beforeEach() throws Exception {
        htmlRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /index.html HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: text/html, */*").getBytes())).parse();

        cssRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /css/styles.css HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*, text/css").getBytes())).parse();

        errRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /not.found HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").getBytes())).parse();
    }

    @Test
    void htmlHandle() {
        Response response = handler.handle(htmlRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(response.getHeaders().get("Content-Type")).contains(Mime.getByType("html").getMime());
    }

    @Test
    void cssHandle() {
        Response response = handler.handle(cssRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(response.getHeaders().get("Content-Type")).contains(Mime.getByType("css").getMime());
    }

    @Test
    void errorHandle() {
        Response response = handler.handle(errRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.NOT_FOUND);
    }
}