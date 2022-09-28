package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import exception.http.HttpException;
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
    void beforeEach() {
        repository.clear();

        getRequest = new Request(HttpMethod.GET.getMethod(), "/user/create", "HTTP/1.1");
        getRequest.addHeader("accept", "text/html");
        getRequest.addParam("userId", "1");
        getRequest.addParam("password", "1");
        getRequest.addParam("name", "1");
        getRequest.addParam("email", "1@1");

        postRequest = new Request(HttpMethod.POST.getMethod(), "/user/create", "HTTP/1.1");
        postRequest.addHeader("accept", "text/html");
        postRequest.addBody("userId", "2");
        postRequest.addBody("password", "2");
        postRequest.addBody("name", "2");
        postRequest.addBody("email", "2@2");

        errRequest = new Request(HttpMethod.PATCH.getMethod(), "/user/create", "HTTP/1.1");
        errRequest.addHeader("accept", "text/html");
    }

    @Test
    void getHandleTest() throws HttpException {
        Response response = handler.handle(getRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders()).contains(Map.entry("Location", REDIRECT_PAGE));
    }

    @Test
    void postHandleTest() throws HttpException {
        Response response = handler.handle(postRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders()).contains(Map.entry("Location", REDIRECT_PAGE));
    }

    @Test
    void errorHandleTest() {
        assertThrows(RequestParsingException.class, () -> handler.handle(errRequest));
    }
}