package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import exception.http.HttpException;
import model.User;
import util.http.Request;
import util.http.Response;
import util.htmlbuilder.UserListHtmlBuilder;
import webserver.service.UserListService;

import java.util.List;

public class UserListServiceHandler implements ServiceHandler {
    private final String LOGIN_PAGE = "login.html";
    private final UserListService service;

    public UserListServiceHandler(UserListService service) {
        this.service = service;
    }

    @Override
    public Response handle(Request request) throws HttpException {
        Mime mime = Mime.canAcceptHtml(request.getHeaders().get("accept"));
        Boolean isLogined = Boolean.valueOf(request.getCookie().get("logined"));
        Response response = new Response();

        if (isLogined) {
            List<User> users = service.getUserList();
            String body = new UserListHtmlBuilder()
                    .setUsers(users)
                    .setHead("title", "header title")
                    .setBody("h2", "유저 목록입니다.")
                    .build();

            response.setHttpStatusCode(HttpStatusCode.OK)
                    .setBody(body)
                    .setHeader("Content-Type", mime.getMime() + ";charset=utf-8");
        }
        else {
            response.setHttpStatusCode(HttpStatusCode.FOUND)
                    .setHeader("Location", LOGIN_PAGE);
        }

        return response;
    }
}
