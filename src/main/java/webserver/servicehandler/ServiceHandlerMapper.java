package webserver.servicehandler;

import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;
import webserver.service.LoginService;
import webserver.service.SignUpService;
import webserver.service.StaticFileService;

import java.util.HashMap;
import java.util.Map;

public class ServiceHandlerMapper {
    private static final Map<String, ServiceHandler> handlers;

    private static final UserRepository userRepository;

    private static final StaticFileService staticFileService;
    private static final SignUpService signUpService;
    private static final LoginService loginService;

    static {
        handlers = new HashMap<>();

        userRepository = new UserMemoryRepository();

        staticFileService = new StaticFileService();
        signUpService = new SignUpService(userRepository);
        loginService = new LoginService(userRepository);

        handlers.put("/static", new StaticFileServiceHandler(staticFileService));
        handlers.put("/user/create", new SignUpServiceHandler(signUpService));
        handlers.put("/user/login", new LoginServiceHandler(loginService));
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
