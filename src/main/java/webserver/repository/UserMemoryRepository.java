package webserver.repository;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepository implements UserRepository {
    private static Map<Integer, User> users = new ConcurrentHashMap<>();
    private static int sequence = 0;

    @Override
    public Optional<User> save(User user) {
        if (users.containsValue(user)) {
            return Optional.empty();
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
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        for (User user : users.values()) {
            list.add(user);
        }

        return list;
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
