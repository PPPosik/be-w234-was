package webserver.service;

import model.User;
import webserver.repository.UserRepository;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String userId, String password) {
        AtomicBoolean ret = new AtomicBoolean(false);
        userRepository.findByUserId(userId).ifPresent(user -> ret.set(validateIdAndPassword(user, userId, password)));
        return ret.get();
    }

    private boolean validateIdAndPassword(User user, String userId, String password) {
        return user.getUserId().equals(userId) && user.getPassword().equals(password);
    }
}
