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
        User user;
        if (request.getMethod() == HttpMethod.GET) {
            user = generateUser(request.getParams());
        } else if (request.getMethod() == HttpMethod.POST) {
            user = generateUser(request.getBody());
        } else {
            throw new RequestParsingException(request.getMethod().getMethod() + " 은 지원하지 않는 메서드입니다.");
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
        Response response = new Response()
                .setHttpStatusCode(entity.getHttpStatusCode())
                .setHeader("Content-Length", String.valueOf(entity.getBody().length))
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setBody(entity.getBody());

        if (response.getHttpStatusCode() == HttpStatusCode.FOUND) {
            response.setHeader("Location", "http://localhost:8080/index.html");
        }

        return response;
    }
}
