package webserver.servicehandler;

import webserver.repository.BoardMySQLRepository;
import webserver.repository.BoardRepository;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceHandlerMapper {
    private static final Map<String, ServiceHandler> handlers;

    private static final UserRepository userRepository;
    private static final BoardRepository boardRepository;

    private static final StaticFileService staticFileService;
    private static final SignUpService signUpService;
    private static final LoginService loginService;
    private static final UserListService userListService;
    private static final BoardService boardService;

    static {
        handlers = new HashMap<>();

        userRepository = new UserMemoryRepository();
        boardRepository = new BoardMySQLRepository();

        staticFileService = new StaticFileService();
        signUpService = new SignUpService(userRepository);
        loginService = new LoginService(userRepository);
        userListService = new UserListService(userRepository);
        boardService = new BoardService(userRepository, boardRepository);

        // TODO 정규식으로 path 관리
        handlers.put("/static", new StaticFileServiceHandler(staticFileService));
        handlers.put("/user/create", new SignUpServiceHandler(signUpService));
        handlers.put("/user/login", new LoginServiceHandler(loginService));
        handlers.put("/user/list", new UserListServiceHandler(userListService));
        handlers.put("/board", new BoardServiceHandler(boardService));
        handlers.put("/board/list", new BoardServiceHandler(boardService));
    }

    private ServiceHandlerMapper() {
    }

    public static ServiceHandler getHandler(String path) {
        if (handlers.containsKey(path)) {
            return handlers.get(path);
        } else {
            return handlers.get("/static");
        }
    }
}
