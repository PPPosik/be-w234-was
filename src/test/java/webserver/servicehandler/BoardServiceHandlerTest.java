package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import exception.BadRequestException;
import exception.HttpException;
import exception.PageNotFoundException;
import exception.UnauthorizedUserException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Request;
import util.Response;
import webserver.repository.BoardMySQLRepository;
import webserver.repository.BoardRepository;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.BoardService;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class BoardServiceHandlerTest {
    private final UserRepository userRepository = new UserMemoryRepository();
    private final BoardRepository boardRepository = new BoardMySQLRepository();
    private final BoardService service = new BoardService(userRepository, boardRepository);
    private final BoardServiceHandler handler = new BoardServiceHandler(service);

    private final User user1 = new User("user1", "password1", "name1", "user1@abc.com");
    private final User user2 = new User("user2", "password2", "name2", "user2@abc.com");

    private Request boardSaveRequest, boardListRequest;

    @BeforeEach
    void beforeEach() {
        userRepository.clear();
        userRepository.save(user1);
        userRepository.save(user2);

        boardSaveRequest = new Request(HttpMethod.POST.getMethod(), "/board", "HTTP/1.1");
        boardSaveRequest.addCookie("logined", "true");
        boardSaveRequest.addCookie("id", user1.getUserId());
        boardSaveRequest.addBody("content", "This is content");

        boardListRequest = new Request(HttpMethod.GET.getMethod(), "/board/list", "HTTP/1.1");
    }

    @Test
    void saveBoardTest() throws HttpException {
        Response response = handler.handle(boardSaveRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.CREATED);
        assertThat(new String(response.getBody())).isEqualTo("게시글 작성에 성공했습니다.");
    }

    @Test
    void saveBoardNotValidUserTest() {
        boardSaveRequest.addCookie("id", "user3");

        assertThrows(UnauthorizedUserException.class, () -> handler.handle(boardSaveRequest));
    }

    @Test
    void getBoardListTest() throws HttpException {
        boardSaveRequest.addCookie("id", user1.getUserId());
        boardSaveRequest.addBody("content", "content1");
        handler.handle(boardSaveRequest);

        boardSaveRequest.addCookie("id", user2.getUserId());
        boardSaveRequest.addBody("content", "content2");
        handler.handle(boardSaveRequest);

        Response response = handler.handle(boardListRequest);
        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(new String(response.getBody())).contains(user1.getName(), "content1", user2.getName(), "content2");
    }

    @Test
    void wrongMethodTest() {
        Request request1 = new Request(HttpMethod.GET.getMethod(), "/board", "HTTP/1.1");
        Request request2 = new Request(HttpMethod.POST.getMethod(), "/board/list", "HTTP/1.1");

        assertThrows(PageNotFoundException.class, () -> handler.handle(request1));
        assertThrows(PageNotFoundException.class, () -> handler.handle(request2));
    }

    @Test
    void noCookieTest() {
        Request request = new Request(HttpMethod.POST.getMethod(), "/board", "HTTP/1.1");

        assertThrows(BadRequestException.class, () -> handler.handle(request));
    }
}
