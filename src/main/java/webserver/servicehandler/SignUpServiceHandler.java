package webserver.servicehandler;

import model.User;
import util.Request;
import util.Response;
import util.ResponseEntity;
import webserver.service.SignUpService;

public class SignUpServiceHandler implements ServiceHandler {
    private final SignUpService signUpService;

    public SignUpServiceHandler(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public Response handle(Request request) {
        final String userId = request.getParams().get("userId");
        final String password = request.getParams().get("password");
        final String name = request.getParams().get("name");
        final String email = request.getParams().get("email");

        User user = new User(userId, password, name, email);
        ResponseEntity entity = signUpService.service(user);

        String accept = request.getHeaders().get("accept");
        return new Response()
                .setHttpStatusCode(entity.getHttpStatusCode())
                .setHeader("Content-Length", String.valueOf(entity.getBody().length))
                .setHeader("Content-Type", accept.split(",")[0] + ";charset=utf-8")
                .setBody(entity.getBody());
    }
}
