package webserver.service;

import model.User;
import webserver.RequestParser;
import webserver.repository.UserRepository;

public class SignUpServiceHandler implements ServiceHandler {
    private final UserRepository userRepository;

    public SignUpServiceHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String handle(RequestParser requestParser) {
        User user = new User(requestParser.params.get("userId"), requestParser.params.get("password"), requestParser.params.get("name"), requestParser.params.get("email"));

        if (userRepository.save(user).isPresent()) {
            return user + " 유저 정보 저장에 성공했습니다.";
        } else {
            return user + " 유저 정보 저장에 실패했습니다.";
        }
    }
}
