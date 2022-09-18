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

        handlers.put("static file", new StaticFileServiceHandler());
        handlers.put("/user/create", new SignUpServiceHandler(userRepository));
    }

    private ServiceHandlerMapper() {
    }

    public static ServiceHandler getHandler(String url) {
        if (handlers.containsKey(url)) {
            return handlers.get(url);
        } else {
            return handlers.get("static file");
        }
    }
}
