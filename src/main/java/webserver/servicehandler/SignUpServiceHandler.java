package webserver.servicehandler;

import exception.RequestParsingException;
import model.User;
import util.*;
import webserver.service.SignUpService;

public class SignUpServiceHandler implements ServiceHandler {
    private final SignUpService signUpService;

    public SignUpServiceHandler(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public Response handle(Request request) {
        HttpMethod method = HttpMethod.getByMethod(request.getMethod());

        if (method == HttpMethod.GET) {
            return getHandle(request);
        } else if (method == HttpMethod.POST) {
            return postHandle(request);
        } else {
            throw new RequestParsingException(request.getMethod() + " 은 지원하지 않는 메서드입니다.");
        }
    }

    private Response getHandle(Request request) {
        final String userId = request.getParams().get("userId");
        final String password = request.getParams().get("password");
        final String name = request.getParams().get("name");
        final String email = request.getParams().get("email");

        User user = new User(userId, password, name, email);
        ResponseEntity entity = signUpService.service(user);

        return new Response().setHttpStatusCode(entity.getHttpStatusCode()).setHeader("Content-Length", String.valueOf(entity.getBody().length)).setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8").setBody(entity.getBody());
    }

    private Response postHandle(Request request) {
        return new Response().setHttpStatusCode(HttpStatusCode.CREATED).setBody("created");
    }
}
