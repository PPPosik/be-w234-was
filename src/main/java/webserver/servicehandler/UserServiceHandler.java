package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import enums.Mime;
import exception.http.BadRequestException;
import exception.http.HttpException;
import model.User;
import util.htmlbuilder.UserListHtmlBuilder;
import util.http.Cookie;
import util.http.Request;
import util.http.Response;
import webserver.service.UserService;

import java.util.List;

public class UserServiceHandler implements ServiceHandler {
    private final String HOME_PAGE = "http://localhost:8080/index.html";
    private final String LOGIN_PAGE = "http://localhost:8080/user/login.html";
    private final String LOGIN_FAIL_PAGE = "http://localhost:8080/user/login_failed.html";

    private final UserService userService;

    public UserServiceHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response handle(Request request) throws HttpException {
        String path = request.getPath();
        HttpMethod method = request.getMethod();

        if (method == HttpMethod.GET && "/user/create".equals(path)) {
            return saveUserGet(request);
        } else if (method == HttpMethod.POST && "/user/create".equals(path)) {
            return saveUserPost(request);
        } else if (method == HttpMethod.POST && "/user/login".equals(path)) {
            return login(request);
        } else if (method == HttpMethod.GET && "/user/list".equals(path)) {
            return getUserList(request);
        } else {
            throw new BadRequestException(request.getMethod().getMethod() + " " + request.getPath() + "은 잘못된 요청입니다.");
        }
    }

    public Response login(Request request) {
        Response response = new Response();
        Cookie cookie = new Cookie();

        boolean login = userService.login(request.getBody().get("userId"), request.getBody().get("password"));
        if (login) {
            response.setHeader("Location", HOME_PAGE);
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

    public Response saveUserGet(Request request) throws HttpException {
        Response response = new Response();

        userService.saveUser(request.getParams());

        return response
                .setHttpStatusCode(HttpStatusCode.FOUND)
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setHeader("Location", HOME_PAGE);
    }

    public Response saveUserPost(Request request) throws HttpException {
        Response response = new Response();

        userService.saveUser(request.getBody());

        return response
                .setHttpStatusCode(HttpStatusCode.FOUND)
                .setHeader("Content-Type", Mime.getContentType(request.getPath(), request.getHeaders().get("accept")) + ";charset=utf-8")
                .setHeader("Location", HOME_PAGE);
    }

    public Response getUserList(Request request) throws HttpException {
        Mime mime = Mime.canAcceptHtml(request.getHeaders().get("accept"));
        Boolean isLogined = Boolean.valueOf(request.getCookie().get("logined"));
        Response response = new Response();

        if (isLogined) {
            List<User> users = userService.getUserList();
            String body = new UserListHtmlBuilder()
                    .setUsers(users)
                    .setHead("title", "header title")
                    .setBody("h2", "유저 목록입니다.")
                    .build();

            response.setHttpStatusCode(HttpStatusCode.OK)
                    .setBody(body)
                    .setHeader("Content-Type", mime.getMime() + ";charset=utf-8");
        } else {
            response.setHttpStatusCode(HttpStatusCode.FOUND)
                    .setHeader("Location", LOGIN_PAGE);
        }

        return response;
    }
}
