package model;

import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern USER_ID_REGEX = Pattern.compile("^[a-zA-Z0-9]*");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9]*");
    private static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z0-9]*");
    private static final Pattern EMAIL_REGEX = Pattern.compile("[\\w\\-\\.]+\\@[\\w\\-\\.]+");

    public static boolean isValidUserId(String userId) {
        return USER_ID_REGEX.matcher(userId).matches();
    }

    public static boolean isValidPassword(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
    }

    public static boolean isValidName(String name) {
        return NAME_REGEX.matcher(name).matches();
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
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
