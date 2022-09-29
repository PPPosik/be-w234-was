package webserver.servicehandler.mapper;

import webserver.repository.BoardMySQLRepository;
import webserver.repository.BoardRepository;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.*;
import webserver.servicehandler.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ServiceHandlerMapper {
    private static final ServiceHandler defaultHandler;
    private static final Map<Pattern, ServiceHandler> handlers;

    private static final UserRepository userRepository;
    private static final BoardRepository boardRepository;

    private static final StaticFileService staticFileService;
    private static final UserService userSevice;
    private static final BoardService boardService;

    static {
        userRepository = new UserMemoryRepository();
        boardRepository = new BoardMySQLRepository();

        staticFileService = new StaticFileService();
        userSevice = new UserService(userRepository);
        boardService = new BoardService(userRepository, boardRepository);

        defaultHandler = new StaticFileServiceHandler(staticFileService);
        handlers = new HashMap<>();
        handlers.put(Pattern.compile("^(/user)[/\\S]*"), new UserServiceHandler(userSevice));
        handlers.put(Pattern.compile("^(/board)[/\\S]*"), new BoardServiceHandler(boardService));
    }

    private ServiceHandlerMapper() {
    }

    public static ServiceHandler getHandler(String path) {
        if(isFileRequest(path)) {
            return defaultHandler;
        }

        for (Map.Entry<Pattern, ServiceHandler> entry : handlers.entrySet()) {
            if (entry.getKey().matcher(path).matches()) {
                return entry.getValue();
            }
        }

        return defaultHandler;
    }

    private static boolean isFileRequest(String path) {
        return path.contains(".");
    }
}
