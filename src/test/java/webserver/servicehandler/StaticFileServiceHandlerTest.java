package webserver.servicehandler;

import constant.LocalConst;
import enums.HttpMethod;
import enums.HttpStatusCode;
import enums.Mime;
import exception.http.HttpException;
import exception.http.PageNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.http.Request;
import util.http.RequestParser;
import util.http.Response;
import webserver.service.StaticFileService;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaticFileServiceHandlerTest {
    final StaticFileService service = new StaticFileService();
    final StaticFileServiceHandler handler = new StaticFileServiceHandler(service);

    Request htmlRequest, cssRequest, errRequest;

    @BeforeEach
    void beforeEach() {
        htmlRequest = new Request(HttpMethod.GET.getMethod(), LocalConst.HOME_PAGE_PATH, "HTTP/1.1");
        htmlRequest.addHeader("accept", "text/html");

        cssRequest = new Request(HttpMethod.GET.getMethod(), "/css/styles.css", "HTTP/1.1");
        cssRequest.addHeader("accept", "text/css");

        errRequest = new Request(HttpMethod.GET.getMethod(), "/not.found", "HTTP/1.1");
        errRequest.addHeader("accept", "text/html");
    }

    @Test
    void htmlHandle() throws HttpException {
        Response response = handler.handle(htmlRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(response.getHeaders().get("Content-Type")).contains(Mime.getByType("html").getMime());
    }

    @Test
    void cssHandle() throws HttpException {
        Response response = handler.handle(cssRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(response.getHeaders().get("Content-Type")).contains(Mime.getByType("css").getMime());
    }

    @Test
    void errorHandle() {
        assertThrows(PageNotFoundException.class, () -> handler.handle(errRequest));
    }
}