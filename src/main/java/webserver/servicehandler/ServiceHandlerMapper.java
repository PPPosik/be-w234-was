package webserver.servicehandler;

import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class ServiceHandlerMapper {
    private static final Map<String, ServiceHandler> handlers;
    private static final UserRepository userRepository;

    static {
        handlers = new HashMap<>();
        userRepository = new UserMemoryRepository();

        handlers.put("/static", new StaticFileServiceHandler());
        handlers.put("/user/create", new SignUpServiceHandler(userRepository));
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
