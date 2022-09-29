package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import exception.http.BadRequestException;
import exception.http.HttpException;
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

class LoginServiceHandlerTest {
    private final String LOGIN_SUCCESS_PAGE = "http://localhost:8080/index.html";
    private final String LOGIN_FAIL_PAGE = "http://localhost:8080/user/login_failed.html";

    private final UserRepository repository = new UserMemoryRepository();
    private final UserService service = new UserService(repository);
    private final UserServiceHandler handler = new UserServiceHandler(service);

    private Request getRequest, loginSuccessRequest, loginFailRequest;

    private final User user = new User("user1", "password1", "name1", "user1@abc.com");

    @BeforeEach
    void beforeEach() {
        getRequest = new Request(HttpMethod.GET.getMethod(), "/user/login", "HTTP/1.1");
        getRequest.addHeader("accept", "text/html");
        getRequest.addParam("userId", "user1");
        getRequest.addParam("password", "password1");

        loginSuccessRequest = new Request(HttpMethod.POST.getMethod(), "/user/login", "HTTP/1.1");
        loginSuccessRequest.addHeader("accept", "text/html");
        loginSuccessRequest.addBody("userId", "user1");
        loginSuccessRequest.addBody("password", "password1");

        loginFailRequest = new Request(HttpMethod.POST.getMethod(), "/user/login", "HTTP/1.1");
        loginFailRequest.addHeader("accept", "text/html");
        loginFailRequest.addBody("userId", "user2");
        loginFailRequest.addBody("password", "password2");

        repository.clear();
        repository.save(user);
    }

    @Test
    void getLoginFailTest() {
        assertThrows(BadRequestException.class, () -> handler.handle(getRequest));
    }

    @Test
    void loginTest() throws HttpException {
        Response response = handler.handle(loginSuccessRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_SUCCESS_PAGE);
        assertThat(response.getCookie().get("logined")).isEqualTo("true");
        assertThat(response.getCookie().get("id")).isEqualTo("user1");
    }

    @Test
    void loginFailTest() throws HttpException {
        Response response = handler.handle(loginFailRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.getHeaders().get("Location")).isEqualTo(LOGIN_FAIL_PAGE);
        assertThat(response.getCookie().get("logined")).isEqualTo("false");
    }
}