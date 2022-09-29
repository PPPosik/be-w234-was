package webserver.service;

import model.User;
import webserver.repository.UserRepository;

import java.util.List;

public class UserListService {
    private final UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return userRepository.findAll().orElse(List.of());
    }
}
