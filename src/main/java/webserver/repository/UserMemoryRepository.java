package webserver.repository;

import exception.UserSaveException;
import model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepository implements UserRepository {
    private static final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public Optional<User> save(User user) {
        if (users.containsValue(user)) {
            throw new UserSaveException(user + " 유저의 정보가 이미 존재합니다.");
        }

        users.put(user.getUserId(), user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.of(new ArrayList<>(users.values()));
    }

    @Override
    public Optional<User> update(User user) {
        // TODO 기능 구현
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) {
        // TODO 기능 구현
        return false;
    }

    @Override
    public void clear() {
        users.clear();
    }
}
