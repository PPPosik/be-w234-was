package webserver.service;

import exception.UnauthorizedUserException;
import model.Board;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.repository.BoardMySQLRepository;
import webserver.repository.BoardRepository;
import webserver.repository.UserMemoryRepository;
import webserver.repository.UserRepository;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class BoardServiceTest {
    private final UserRepository userRepository = new UserMemoryRepository();
    private final BoardRepository boardRepository = new BoardMySQLRepository();
    private final BoardService service = new BoardService(userRepository, boardRepository);

    @BeforeEach
    void beforeEach() {
        userRepository.clear();

        userRepository.save(new User("user1", "1111", "user1", "user1@abc.com"));
        userRepository.save(new User("user2", "2222", "user2", "user2@abc.com"));
    }

    @Test
    void saveBoardTest() {
        Board savedBoard = service.saveBoard("user1", "user1_content");

        assertThat(savedBoard.getAuthor()).isEqualTo("user1");
        assertThat(savedBoard.getContent()).isEqualTo("user1_content");
        assertThat(boardRepository.findAll().get()).contains(savedBoard);
    }

    @Test
    void saveBoardDuplicateUserTest() {
        assertDoesNotThrow(() -> service.saveBoard("user1", "user1_content"));
        assertDoesNotThrow(() -> service.saveBoard("user1", "user1_content"));
    }

    @Test
    void saveBoardNotValidUserTest() {
        assertThrows(UnauthorizedUserException.class, () -> service.saveBoard("user3", "user3_content"));
    }

    @Test
    void getBoardListTest() {
        Board savedBoard1 = service.saveBoard("user1", "user1_content");
        Board savedBoard2 = service.saveBoard("user2", "user2_content");

        List<Board> list = service.getBoardList();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(savedBoard1, savedBoard2);
    }

    @Test
    void getBoardListDuplicatedUserTest() {
        Board savedBoard1 = service.saveBoard("user1", "user1_content");
        Board savedBoard11 = service.saveBoard("user1", "user1_content");

        List<Board> list = service.getBoardList();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(savedBoard1, savedBoard11);
    }
}