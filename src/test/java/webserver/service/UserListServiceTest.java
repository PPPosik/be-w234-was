package webserver.service;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserListServiceTest {
    private final UserRepository repository = new UserMemoryRepository();
    private final UserService service = new UserService(repository);

    private final User user1 = new User("user1", "password1", "name1", "user1@abc.com");
    private final User user2 = new User("user2", "password2", "name2", "user2@abc.com");


    @BeforeEach
    void beforeEach() {
        repository.clear();
    }

    @Test
    void getUserListTest() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = service.getUserList();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    void getUserListNoneTest() {
        List<User> users = service.getUserList();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(0);
    }
}