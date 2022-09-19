package webserver.service;

import exception.UserNotValidException;
import model.User;
import model.UserValidator;
import util.HttpStatusCode;
import util.ResponseEntity;
import webserver.repository.UserRepository;

public class SignUpService {
    private final UserRepository userRepository;

    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity service(User user) {
        if (UserValidator.isNotValidUser(user)) {
            throw new UserNotValidException(user + " 유효하지 않은 유저 정보입니다.");
        }

        ResponseEntity response = new ResponseEntity();

        if (userRepository.save(user).isPresent()) {
            response.setBody(user + " 유저 정보 저장에 성공했습니다.");
            response.setHttpStatusCode(HttpStatusCode.FOUND);
        } else {
            response.setBody(user + " 유저 정보 저장에 실패했습니다.");
            response.setHttpStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        return response;
    }
}
