package webserver.repository;

import exception.BoardSaveException;
import model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BoardMySQLRepositoryTest {
    private final BoardRepository repository = new BoardMySQLRepository();

    private final Board board1 = new Board("user1", "user1_content");
    private final Board board2 = new Board("user2", "user2_content");

    @Test
    void saveTest() {
        Board savedBoard1 = repository.save(board1).get();
        Board savedBoard2 = repository.save(board2).get();

        assertThat(savedBoard1).isEqualTo(board1);
        assertThat(savedBoard2).isEqualTo(board2);
    }

    @Test
    void findAllTest() {
        repository.save(board1);
        repository.save(board2);

        List<Board> boards = repository.findAll().get();
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards).contains(board1, board2);
    }

    @Test
    void findNoneTest() {
        List<Board> boards = repository.findAll().get();
        assertThat(boards.size()).isEqualTo(0);
    }
}