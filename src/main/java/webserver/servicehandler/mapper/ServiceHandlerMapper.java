package webserver.servicehandler.mapper;

import webserver.repository.BoardMySQLRepository;
import webserver.repository.BoardRepository;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.*;
import webserver.servicehandler.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceHandlerMapper {
    private static final Map<String, ServiceHandler> handlers;

    private static final UserRepository userRepository;
    private static final BoardRepository boardRepository;

    private static final StaticFileService staticFileService;
    private static final UserService userSevice;
    private static final BoardService boardService;

    static {
        handlers = new HashMap<>();

        userRepository = new UserMemoryRepository();
        boardRepository = new BoardMySQLRepository();

        staticFileService = new StaticFileService();
        userSevice = new UserService(userRepository);
        boardService = new BoardService(userRepository, boardRepository);

        // TODO 정규식으로 path 관리
        handlers.put("/static", new StaticFileServiceHandler(staticFileService));
        handlers.put("/user/create", new UserServiceHandler(userSevice));
        handlers.put("/user/login", new UserServiceHandler(userSevice));
        handlers.put("/user/list", new UserServiceHandler(userSevice));
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
