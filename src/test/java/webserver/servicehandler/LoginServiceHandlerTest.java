package webserver.servicehandler;

import enums.HttpStatusCode;
import exception.RequestParsingException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.http.Request;
import util.http.RequestParser;
import util.http.Response;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.LoginService;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceHandlerTest {
    private final String LOGIN_SUCCESS_PAGE = "http://localhost:8080/index.html";
    private final String LOGIN_FAIL_PAGE = "http://localhost:8080/user/login_failed.html";

    private final UserRepository repository = new UserMemoryRepository();
    private final LoginService service = new LoginService(repository);
    private final LoginServiceHandler handler = new LoginServiceHandler(service);

    private Request getRequest, loginSuccessRequest, loginFailRequest;

    private final User user = new User("user1", "password1", "name1", "user1@abc.com");

    @BeforeEach
    void beforeEach() throws Exception {
        getRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /user/login?userId=user1&password=password1 HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").getBytes())).parse();

        loginSuccessRequest = new RequestParser(
                new ByteArrayInputStream((
                        "POST /user/login HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 31\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n\n" +
                        "userId=user1&password=password1").getBytes())).parse();

        loginFailRequest = new RequestParser(
                new ByteArrayInputStream((
                        "POST /user/login HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 31\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n\n" +
                        "userId=user2&password=password2").getBytes())).parse();

        repository.clear();
        repository.save(user);
    }

    @Test
    void getLoginFailTest() {
        assertThrows(RequestParsingException.class, () -> handler.handle(getRequest));
    }

    @Test
    void loginTest() {
        Response response = handler.handle(loginSuccessRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_SUCCESS_PAGE);
        assertThat(response.getCookie().get("logined")).isEqualTo("true");
        assertThat(response.getCookie().get("id")).isEqualTo("user1");
    }

    @Test
    void loginFailTest() {
        Response response = handler.handle(loginFailRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_FAIL_PAGE);
        assertThat(response.getCookie().get("logined")).isEqualTo("false");
    }
}