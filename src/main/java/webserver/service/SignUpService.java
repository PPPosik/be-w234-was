package webserver.service;

import exception.http.UnauthorizedUserException;
import exception.UserSaveException;
import model.User;
import util.UserValidator;
import webserver.repository.UserRepository;

import java.util.Map;

public class SignUpService {
    private final UserRepository userRepository;

    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User service(Map<String, String> userInfo) throws UnauthorizedUserException {
        User user = generateUser(userInfo);

        if (UserValidator.isNotValidUser(user)) {
            throw new UnauthorizedUserException(user + " 유효하지 않은 유저 정보입니다.");
        }

        return userRepository.save(user).orElseThrow(() -> new UserSaveException(user + " 유저 정보 저장에 실패했습니다."));
    }

    private User generateUser(Map<String, String> userInfo) {
        final String userId = userInfo.get("userId");
        final String password = userInfo.get("password");
        final String name = userInfo.get("name");
        final String email = userInfo.get("email");

        return new User(userId, password, name, email);
    }
}
