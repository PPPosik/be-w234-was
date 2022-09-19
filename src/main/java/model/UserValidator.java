package model;

import java.util.regex.Pattern;

public class UserValidator {
    private static final String USER_ID_REGEX = "^[a-zA-Z0-9]*";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]*";
    private static final String NAME_REGEX = "^[a-zA-Z0-9]*";
    private static final String EMAIL_REGEX = "[\\w\\-\\.]+\\@[\\w\\-\\.]+";

    public static boolean isValidUserId(String userId) {
        return Pattern.matches(USER_ID_REGEX, userId);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isValidName(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidUser(User user) {
        return isValidUserId(user.getUserId()) &&
                isValidPassword(user.getPassword()) &&
                isValidName(user.getName()) &&
                isValidEmail(user.getEmail());
    }

    public static boolean isNotValidUser(User user) {
        return !isValidUser(user);
    }
}
