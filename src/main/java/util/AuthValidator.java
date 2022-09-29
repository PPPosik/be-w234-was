package util;

import model.User;
import util.http.Cookie;

public class AuthValidator {
    public static boolean canWriteBoard(Cookie cookie) {
        return cookie != null && "true".equals(cookie.get("logined"));
    }

    public static boolean canLogin(User user, String userId, String password) {
        return user.getUserId().equals(userId) && user.getPassword().equals(password);
    }
}
