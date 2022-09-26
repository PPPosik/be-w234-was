package webserver.service;

import exception.UserNotValidException;
import model.Board;
import model.User;
import webserver.repository.BoardRepository;
import webserver.repository.UserRepository;

import java.util.List;

public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public BoardService(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    public Board saveBoard(String userId, String content) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotValidException("존재하지 않는 유저입니다."));
        return boardRepository.save(new Board(user.getName(), content)).get();
    }

    public List<Board> getBoardList() {
        return boardRepository.findAll().orElse(List.of());
    }
}
