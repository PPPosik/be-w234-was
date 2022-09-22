package webserver.servicehandler;

import enums.HttpStatusCode;
import enums.Mime;
import exception.NotAcceptableException;
import model.User;
import util.Request;
import util.Response;
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
    public Response handle(Request request) {
        Response response = new Response();
        Boolean isLogined = Boolean.valueOf(request.getCookie().get("logined"));

        if (isLogined) {
            List<User> users = service.getUserList();
            String body = new UserListHtmlBuilder()
                    .setUsers(users)
                    .setHead("title", "header title")
                    .setBody("h2", "유저 목록입니다.")
                    .build();
            Mime mime = canAcceptHtml(request.getHeaders().get("accept"));

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

    private Mime canAcceptHtml(String accept) {
        if (accept.contains(Mime.HTML.getMime())) {
            return Mime.HTML;
        } else {
            throw new NotAcceptableException("지원하지 않는 형식입니다.");
        }
    }
}
