package webserver.service;

import exception.UserNotValidException;
import exception.UserSaveException;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignUpServiceTest {
    static final Map<String, String> userInfo1 = new HashMap<>();
    static final Map<String, String> userInfo2 = new HashMap<>();
    static final Map<String, String> invalidUser = new HashMap<>();

    final UserRepository repository = new UserMemoryRepository();
    final SignUpService service = new SignUpService(repository);

    @BeforeAll
    static void beforeAll() {
        generateUserInfo(userInfo1, "user1", "user1", "user1", "user1@abc.com");
        generateUserInfo(userInfo2, "user2", "user2", "user2", "user2@abc.com");
        generateUserInfo(invalidUser, "user3", "user3", "user3", "!!!!!");
    }

    @BeforeEach
    void beforeEach() {
        repository.clear();
    }

    private static void generateUserInfo(Map<String, String> userInfo, String userId, String password, String name, String email) {

        userInfo.put("userId", userId);
        userInfo.put("password", password);
        userInfo.put("name", name);
        userInfo.put("email", email);
    }

    @Test
    void signUpTest() {
        User savedUser = service.service(userInfo1);
        User generatedUser = generateUser(userInfo1);

        assertThat(savedUser).isEqualTo(generatedUser);
    }

    @Test
    void signUpManyUserTest() {
        User savedUser1 = service.service(userInfo1);
        User generatedUser1 = generateUser(userInfo1);

        User savedUser2 = service.service(userInfo2);
        User generatedUser2 = generateUser(userInfo2);

        assertThat(savedUser1).isEqualTo(generatedUser1);
        assertThat(savedUser2).isEqualTo(generatedUser2);
        assertThat(savedUser1).isNotEqualTo(savedUser2);
    }

    private User generateUser(Map<String, String> userInfo) {
        final String userId = userInfo.get("userId");
        final String password = userInfo.get("password");
        final String name = userInfo.get("name");
        final String email = userInfo.get("email");

        return new User(userId, password, name, email);
    }

    @Test
    void signUpDuplicatedUserTest() {
        service.service(userInfo1);

        assertThrows(UserSaveException.class, () -> service.service(userInfo1));
        assertDoesNotThrow(() -> service.service(userInfo2));
    }

    @Test
    void signUpInvlalidUserTest() {
        assertThrows(UserNotValidException.class, () -> service.service(invalidUser));
    }
}