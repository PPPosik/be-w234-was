package util;

import model.User;
import org.junit.jupiter.api.Test;
import util.UserValidator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserValidatorTest {
    private final User validUser = new User("user1", "112233", "user1", "user1@abc.com");
    private final User invalidUser = new User("아이디", "비밀번호", "이름", "user2email");

    // TODO 테스트 값 보강
    private final List<String> validUserIds = new ArrayList<>(List.of("user1"));
    private final List<String> validPasswords = new ArrayList<>(List.of("112233"));
    private final List<String> validNames = new ArrayList<>(List.of("user1"));
    private final List<String> validEmails = new ArrayList<>(List.of("user1@abc.com"));

    private final List<String> invalidUserIds = new ArrayList<>(List.of("", "아이디"));
    private final List<String> invalidPasswords = new ArrayList<>(List.of("", "비밀번호"));
    private final List<String> invalidNames = new ArrayList<>(List.of("", "이름"));
    private final List<String> invalidEmails = new ArrayList<>(List.of("", "user2email"));

    @Test
    void validUserIdTest() {
        for (String userId : validUserIds) {
            assertThat(UserValidator.isValidUserId(userId)).isTrue();
        }
    }

    @Test
    void validPasswordTest() {
        for (String password : validPasswords) {
            assertThat(UserValidator.isValidPassword(password)).isTrue();
        }
    }

    @Test
    void validNameTest() {
        for (String name : validNames) {
            assertThat(UserValidator.isValidName(name)).isTrue();
        }
    }

    @Test
    void validEmailTest() {
        for (String email : validEmails) {
            assertThat(UserValidator.isValidEmail(email)).isTrue();
        }
    }

    @Test
    void invalidUserIdTest() {
        for (String userId : invalidUserIds) {
            assertThat(UserValidator.isValidUserId(userId)).isFalse();
        }
    }

    @Test
    void invalidPasswordTest() {
        for (String password : invalidPasswords) {
            assertThat(UserValidator.isValidPassword(password)).isFalse();
        }
    }

    @Test
    void invalidNamesTest() {
        for (String name : invalidNames) {
            assertThat(UserValidator.isValidName(name)).isFalse();
        }
    }

    @Test
    void invalidEmailTest() {
        for (String email : invalidEmails) {
            assertThat(UserValidator.isValidEmail(email)).isFalse();
        }
    }

    @Test
    void validUserTest() {
        assertThat(UserValidator.isValidUser(validUser)).isTrue();
        assertThat(UserValidator.isNotValidUser(validUser)).isFalse();
    }

    @Test
    void invalidUserTest() {
        assertThat(UserValidator.isValidUser(invalidUser)).isFalse();
        assertThat(UserValidator.isNotValidUser(invalidUser)).isTrue();
    }

    @Test
    void nullTest() {
        assertThat(UserValidator.isValidUserId(null)).isFalse();
        assertThat(UserValidator.isValidPassword(null)).isFalse();
        assertThat(UserValidator.isValidName(null)).isFalse();
        assertThat(UserValidator.isValidEmail(null)).isFalse();
    }
}