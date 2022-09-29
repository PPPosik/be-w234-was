package webserver.service;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

class LoginServiceTest {
    private final UserRepository repository = new UserMemoryRepository();
    private final UserService service = new UserService(repository);

    private final User user1 = new User("user1", "password1", "name1", "user1@abc.com");
    private final User user2 = new User("user2", "password2", "name2", "user2@abc.com");

    @BeforeEach
    void beforeEach() {
        repository.clear();
        repository.save(user1);
        repository.save(user2);
    }

    @Test
    void loginTest() {
        assertThat(service.login(user1.getUserId(), user1.getPassword())).isTrue();
        assertThat(service.login(user1.getUserId(), user2.getPassword())).isFalse();
        assertThat(service.login(user2.getUserId(), user2.getPassword())).isTrue();
        assertThat(service.login(user2.getUserId(), user1.getPassword())).isFalse();
    }

    @Test
    void loginInvaidUserTest() {
        assertThat(service.login("user3", "password3")).isFalse();
    }
}