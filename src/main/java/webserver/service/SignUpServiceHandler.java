package webserver.service;

import exception.UserNotValidException;
import model.User;
import model.UserValidator;
import util.Request;
import webserver.repository.UserRepository;

public class SignUpServiceHandler implements ServiceHandler {
    private final UserRepository userRepository;

    public SignUpServiceHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String handle(Request request) {
        final String userId = request.getParams().get("userId");
        final String password = request.getParams().get("password");
        final String name = request.getParams().get("name");
        final String email = request.getParams().get("email");

        User user = new User(userId, password, name, email);
        if (UserValidator.isNotValidUser(user)) {
            throw new UserNotValidException(user + " 유효하지 않은 유저 정보입니다.");
        }

        if (userRepository.save(user).isPresent()) {
            return user + " 유저 정보 저장에 성공했습니다.";
        } else {
            return user + " 유저 정보 저장에 실패했습니다.";
        }
    }
}
