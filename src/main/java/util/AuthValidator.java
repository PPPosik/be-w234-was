package util;

import util.http.Cookie;

public class AuthValidator {
    public static boolean canWriteBoard(Cookie cookie) {
        return cookie != null && "true".equals(cookie.get("logined"));
    }
}
