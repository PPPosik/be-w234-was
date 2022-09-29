package webserver.service;

import exception.UserSaveException;
import exception.http.UnauthorizedUserException;
import model.User;
import util.AuthValidator;
import util.UserValidator;
import webserver.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(Map<String, String> userInfo) throws UnauthorizedUserException {
        User user = generateUser(userInfo);

        if (UserValidator.isNotValidUser(user)) {
            throw new UnauthorizedUserException(user + " 유효하지 않은 유저 정보입니다.");
        }

        return userRepository.save(user).orElseThrow(() -> new UserSaveException(user + " 유저 정보 저장에 실패했습니다."));
    }

    public boolean login(String userId, String password) {
        AtomicBoolean ret = new AtomicBoolean(false);
        userRepository.findByUserId(userId).ifPresent(user -> ret.set(AuthValidator.canLogin(user, userId, password)));
        return ret.get();
    }

    public List<User> getUserList() {
        return userRepository.findAll().orElse(List.of());
    }

    private User generateUser(Map<String, String> userInfo) {
        final String userId = userInfo.get("userId");
        final String password = userInfo.get("password");
        final String name = userInfo.get("name");
        final String email = userInfo.get("email");

        return new User(userId, password, name, email);
    }
}
