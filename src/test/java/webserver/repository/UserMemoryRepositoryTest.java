package webserver.repository;

import exception.UserSaveException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserMemoryRepositoryTest {
    UserRepository userRepository = new UserMemoryRepository();

    User user1 = new User("user1_id", "user1_pw", "user1", "user1@kakaocorp.com");
    User user2 = new User("user2_id", "user2_pw", "user2", "user2@kakaocorp.com");

    @BeforeEach
    void beforeEach() {
        userRepository.clear();
    }

    @Test
    void saveTest() {
        assertThat(userRepository.save(user1)).isNotNull();
        assertThat(userRepository.save(user2)).isNotNull();
        assertThat(userRepository.findAll().get().size()).isEqualTo(2);
    }

    @Test
    void saveDuplicateTest() {
        assertThat(userRepository.save(user1).isPresent()).isTrue();
        assertThrows(UserSaveException.class, () -> userRepository.save(user1));
        assertThat(userRepository.findAll().get().size()).isEqualTo(1);
    }

    @Test
    void clearTest() {
        userRepository.save(user1);
        userRepository.save(user2);

        assertThat(userRepository.findAll().get().size()).isEqualTo(2);
        userRepository.clear();
        assertThat(userRepository.findAll().get().size()).isEqualTo(0);
    }

    @Test
    void findAllTest() {
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll().get();

        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    void findAllNoneTest() {
        List<User> users = userRepository.findAll().get();
        assertThat(users.size()).isEqualTo(0);
    }
}