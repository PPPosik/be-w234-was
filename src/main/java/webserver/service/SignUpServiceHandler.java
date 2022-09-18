package webserver.service;

import exception.UserNotValidException;
import model.User;
import model.UserValidator;
import webserver.RequestParser;
import webserver.repository.UserRepository;

public class SignUpServiceHandler implements ServiceHandler {
    private final UserRepository userRepository;

    public SignUpServiceHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String handle(RequestParser requestParser) {
        final String userId = requestParser.params.get("userId");
        final String password = requestParser.params.get("password");
        final String name = requestParser.params.get("name");
        final String email = requestParser.params.get("email");

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
