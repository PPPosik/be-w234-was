package webserver.servicehandler;

import exception.RequestParsingException;
import model.User;
import util.*;
import webserver.service.SignUpService;

import java.util.Map;

public class SignUpServiceHandler implements ServiceHandler {
    private final SignUpService signUpService;

    public SignUpServiceHandler(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public Response handle(Request request) {
        HttpMethod method = HttpMethod.getByMethod(request.getMethod());

        User user;
        if (method == HttpMethod.GET) {
            user = generateUser(request.getParams());
        } else if (method == HttpMethod.POST) {
            user = generateUser(request.getBody());
        } else {
            throw new RequestParsingException(request.getMethod() + " 은 지원하지 않는 메서드입니다.");
        }

        return generateResponse(request, signUpService.service(user));
    }

    private User generateUser(Map<String, String> data) {
        final String userId = data.get("userId");
        final String password = data.get("password");
        final String name = data.get("name");
        final String email = data.get("email");

        return new User(userId, password, name, email);
    }

    private Response generateResponse(Request request, ResponseEntity entity) {
        return new Response()
                .setHttpStatusCode(entity.getHttpStatusCode())
                .setHeader("Content-Length", String.valueOf(entity.getBody().length))
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setBody(entity.getBody());
    }
}
