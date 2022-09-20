package webserver.servicehandler;

import exception.RequestParsingException;
import util.*;
import webserver.service.SignUpService;

import java.util.Map;

public class SignUpServiceHandler implements ServiceHandler {
    private final String REDIRET_PAGE = "http://localhost:8080/index.html";
    private final SignUpService signUpService;

    public SignUpServiceHandler(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public Response handle(Request request) {
        Response response = new Response();

        if (request.getMethod() == HttpMethod.GET) {
            serviceAndRedirect(request.getParams(), response);
        } else if (request.getMethod() == HttpMethod.POST) {
            serviceAndRedirect(request.getBody(), response);
        } else {
            throw new RequestParsingException(request.getMethod().getMethod() + " 은 지원하지 않는 메서드입니다.");
        }

        return response
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8");
    }

    private void serviceAndRedirect(Map<String, String> userInfo, Response response) {
        signUpService.service(userInfo);
        response.setHttpStatusCode(HttpStatusCode.FOUND);
        response.setHeader("Location", REDIRET_PAGE);
    }
}
