package webserver.servicehandler;

import enums.HttpStatusCode;
import exception.http.RequestParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.http.Request;
import util.http.RequestParser;
import util.http.Response;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.SignUpService;

import java.io.ByteArrayInputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignUpServiceHandlerTest {
    final String REDIRECT_PAGE = "http://localhost:8080/index.html";
    final UserRepository repository = new UserMemoryRepository();
    final SignUpService service = new SignUpService(repository);
    final SignUpServiceHandler handler = new SignUpServiceHandler(service);

    Request getRequest, postRequest, errRequest;

    @BeforeEach
    void beforeEach() throws Exception {
        repository.clear();

        getRequest = new RequestParser(
                    new ByteArrayInputStream((
                        "GET /user/create?userId=2&password=1&name=1&email=1@1 HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").getBytes())).parse();

        postRequest = new RequestParser(
                    new ByteArrayInputStream((
                        "POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 36\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n\n" +
                        "userId=2&password=1&name=1&email=1@1").getBytes())).parse();

        errRequest = new RequestParser(
                    new ByteArrayInputStream((
                        "PATCH /user/create?userId=2&password=1&name=1&email=1@1 HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").getBytes())).parse();
    }

    @Test
    void getHandleTest() {
        Response response = handler.handle(getRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders()).contains(Map.entry("Location", REDIRECT_PAGE));
    }

    @Test
    void postHandleTest() {
        Response response = handler.handle(postRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders()).contains(Map.entry("Location", REDIRECT_PAGE));
    }

    @Test
    void errorHandleTest() {
        assertThrows(RequestParsingException.class, () -> handler.handle(errRequest));
    }
}