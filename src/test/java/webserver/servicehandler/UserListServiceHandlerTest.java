package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import exception.http.NotAcceptableException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.http.Request;
import util.http.RequestParser;
import util.http.Response;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.UserListService;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserListServiceHandlerTest {
    private final String LOGIN_PAGE = "login.html";

    private final UserRepository repository = new UserMemoryRepository();
    private final UserListService service = new UserListService(repository);
    private final UserListServiceHandler handler = new UserListServiceHandler(service);

    private final User user1 = new User("user1", "password1", "name1", "user1@abc.com");
    private final User user2 = new User("user2", "password2", "name2", "user2@abc.com");

    Request successRequest, noCookieRequest, loginFalseRequest, notAcceptableRequest;

    @BeforeEach
    void beforeEach() throws Exception {
        repository.clear();

        repository.save(user1);
        repository.save(user2);

        successRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /user/list HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: text/html, text/plain, */*\n" +
                        "Cookie: logined=true;").getBytes())).parse();

        noCookieRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /user/list HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: text/html, text/plain, */*").getBytes())).parse();

        loginFalseRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /user/list HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: text/html, */*\n" +
                        "Cookie: logined=false;").getBytes())).parse();

        notAcceptableRequest = new RequestParser(
                new ByteArrayInputStream((
                        "GET /user/list HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: text/css\n" +
                        "Cookie: logined=true;").getBytes())).parse();
    }

    @Test
    void successTest() {
        Response response = handler.handle(successRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo(Mime.HTML.getMime() + ";charset=utf-8");
        assertThat(new String(response.getBody())).contains(user1.getUserId(), user1.getName(), user2.getUserId(), user2.getName());
    }

    @Test
    void noCookieTest() {
        Response response = handler.handle(noCookieRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_PAGE);
    }

    @Test
    void loginFalseTest() {
        Response response = handler.handle(loginFalseRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_PAGE);
    }

    @Test
    void notAcceptableTest() {
        assertThrows(NotAcceptableException.class, () -> handler.handle(notAcceptableRequest));
    }
}