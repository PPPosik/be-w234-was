package webserver.repository;

import exception.UserSaveException;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepository implements UserRepository {
    private static final Map<Integer, User> users = new ConcurrentHashMap<>();
    private static int sequence = 0;

    @Override
    public Optional<User> save(User user) {
        if (users.containsValue(user)) {
            throw new UserSaveException(user + " 정보가 이미 존재합니다.");
        }

        users.put(sequence++, user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUserId(String id) {
        // TODO
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.of(new ArrayList<>(users.values()));
    }

    @Override
    public Optional<User> update(User user) {
        // TODO
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) {
        // TODO
        return false;
    }

    @Override
    public void clear() {
        users.clear();
    }
}
