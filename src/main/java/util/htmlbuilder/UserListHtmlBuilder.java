package util.htmlbuilder;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListHtmlBuilder {
    private List<User> users;
    private List<String> headTags;
    private List<String> headValues;
    private List<String> bodyTags;
    private List<String> bodyValues;

    public UserListHtmlBuilder() {
        this.users = new ArrayList<>();
        this.headTags = new ArrayList<>();
        this.headValues = new ArrayList<>();
        this.bodyTags = new ArrayList<>();
        this.bodyValues = new ArrayList<>();
    }

    public UserListHtmlBuilder setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public UserListHtmlBuilder setHead(String tag, String value) {
        headTags.add(tag);
        headValues.add(value);
        return this;
    }

    public UserListHtmlBuilder setBody(String tag, String value) {
        bodyTags.add(tag);
        bodyValues.add(value);
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"ko\">\n");

        sb.append("<head>\n");
        sb.append("<meta charset=\"UTF-8\">\n");
        addHead(sb);
        sb.append("</head>\n");

        sb.append("<body>\n");
        addBody(sb);
        addUsersInBody(sb);
        sb.append("</body>\n");

        sb.append("</html>");

        return sb.toString();
    }

    private void addHead(StringBuilder sb) {
        for (int i = 0; i < headTags.size(); i++) {
            sb.append("<" + headTags.get(i) + ">");
            sb.append(headValues.get(i));
            sb.append("</" + headTags.get(i) + ">\n");
        }
    }

    private void addBody(StringBuilder sb) {
        for (int i = 0; i < bodyTags.size(); i++) {
            sb.append("<" + bodyTags.get(i) + ">");
            sb.append(bodyValues.get(i));
            sb.append("</" + bodyTags.get(i) + ">\n");
        }
    }

    private void addUsersInBody(StringBuilder sb) {
        if (users.size() > 0) {
            sb.append("<ul>");
            for (User user : users) {
                sb.append("<li>" + user.getName() + " 사용자의 아이디는 " + user.getUserId() + " 입니다." + "</li>");
            }
            sb.append("</ul>");
        }
    }
}
