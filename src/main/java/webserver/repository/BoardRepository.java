package webserver.repository;

import model.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Optional<Board> save(Board board);

    Optional<List<Board>> findAll();
}
