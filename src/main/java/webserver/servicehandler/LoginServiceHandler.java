package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import enums.Mime;
import exception.http.BadRequestException;
import util.http.Cookie;
import util.http.Request;
import util.http.Response;
import webserver.service.LoginService;

public class LoginServiceHandler implements ServiceHandler {
    private final String LOGIN_SUCCESS_PAGE = "http://localhost:8080/index.html";
    private final String LOGIN_FAIL_PAGE = "http://localhost:8080/user/login_failed.html";
    private final LoginService loginService;

    public LoginServiceHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Response handle(Request request) throws BadRequestException {
        if (request.getMethod() != HttpMethod.POST) {
            throw new BadRequestException(request.getMethod().getMethod() + " 은 지원하지 않는 메서드입니다.");
        }

        Response response = new Response();
        Cookie cookie = new Cookie();

        boolean login = loginService.login(request.getBody().get("userId"), request.getBody().get("password"));
        if (login) {
            response.setHeader("Location", LOGIN_SUCCESS_PAGE);
        } else {
            response.setHeader("Location", LOGIN_FAIL_PAGE);
        }

        cookie.put("logined", String.valueOf(login));
        cookie.put("id", request.getBody().get("userId"));
        cookie.put("Path", "/");

        return response
                .setHttpStatusCode(HttpStatusCode.FOUND)
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setCookie(cookie);
    }
}
