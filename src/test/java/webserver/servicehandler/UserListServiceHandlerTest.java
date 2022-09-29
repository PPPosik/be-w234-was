package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import enums.Mime;
import exception.http.HttpException;
import exception.http.NotAcceptableException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.http.Request;
import util.http.Response;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserListServiceHandlerTest {
    private final String LOGIN_PAGE = "http://localhost:8080/user/login.html";

    private final UserRepository repository = new UserMemoryRepository();
    private final UserService service = new UserService(repository);
    private final UserServiceHandler handler = new UserServiceHandler(service);

    private final User user1 = new User("user1", "password1", "name1", "user1@abc.com");
    private final User user2 = new User("user2", "password2", "name2", "user2@abc.com");

    Request successRequest, noCookieRequest, loginFalseRequest, notAcceptableRequest;

    @BeforeEach
    void beforeEach() {
        repository.clear();

        repository.save(user1);
        repository.save(user2);

        successRequest = new Request(HttpMethod.GET.getMethod(), "/user/list", "HTTP/1.1");
        successRequest.addHeader("accept", "text/html");
        successRequest.addCookie("logined", "true");

        noCookieRequest = new Request(HttpMethod.GET.getMethod(), "/user/list", "HTTP/1.1");
        noCookieRequest.addHeader("accept", "text/html");

        loginFalseRequest = new Request(HttpMethod.GET.getMethod(), "/user/list", "HTTP/1.1");
        loginFalseRequest.addHeader("accept", "text/html");
        loginFalseRequest.addCookie("logined", "false");

        notAcceptableRequest = new Request(HttpMethod.GET.getMethod(), "/user/list", "HTTP/1.1");
        notAcceptableRequest.addHeader("accept", "text/css");
        notAcceptableRequest.addCookie("logined", "true");
    }

    @Test
    void successTest() throws HttpException {
        Response response = handler.handle(successRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo(Mime.HTML.getMime() + ";charset=utf-8");
        assertThat(new String(response.getBody())).contains(user1.getUserId(), user1.getName(), user2.getUserId(), user2.getName());
    }

    @Test
    void noCookieTest() throws HttpException {
        Response response = handler.handle(noCookieRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_PAGE);
    }

    @Test
    void loginFalseTest() throws HttpException {
        Response response = handler.handle(loginFalseRequest);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_PAGE);
    }

    @Test
    void notAcceptableTest() {
        assertThrows(NotAcceptableException.class, () -> handler.handle(notAcceptableRequest));
    }
}