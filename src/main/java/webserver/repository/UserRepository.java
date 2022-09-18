package webserver.repository;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);

    Optional<User> findByUserId(String id);

    List<User> findAll();

    Optional<User> update(User user);

    boolean delete(User user);

    void clear();
}
