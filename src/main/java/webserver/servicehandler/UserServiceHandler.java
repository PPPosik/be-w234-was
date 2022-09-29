package webserver.servicehandler;

import constant.LocalConst;
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
            response.setRedirect(LocalConst.HOME_PAGE_URL);
        } else {
            response.setRedirect(LocalConst.LOGIN_FAIL_PAGE_URL);
        }

        cookie.put("logined", String.valueOf(login));
        cookie.put("id", request.getBody().get("userId"));
        cookie.put("Path", "/");

        return response
                .setHttpStatusCode(HttpStatusCode.FOUND)
                .setContentType(request)
                .setCookie(cookie);
    }

    public Response saveUserGet(Request request) throws HttpException {
        Response response = new Response();

        userService.saveUser(request.getParams());

        return response
                .setHttpStatusCode(HttpStatusCode.FOUND)
                .setContentType(request)
                .setRedirect(LocalConst.HOME_PAGE_URL);
    }

    public Response saveUserPost(Request request) throws HttpException {
        Response response = new Response();

        userService.saveUser(request.getBody());

        return response
                .setHttpStatusCode(HttpStatusCode.FOUND)
                .setContentType(request)
                .setRedirect(LocalConst.HOME_PAGE_URL);
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
                    .setContentType(mime)
                    .setBody(body);
        } else {
            response.setHttpStatusCode(HttpStatusCode.FOUND)
                    .setRedirect(LocalConst.LOGIN_PAGE_URL);
        }

        return response;
    }
}
