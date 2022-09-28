package webserver.repository;

import exception.BoardSaveException;
import model.Board;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class BoardMySQLRepository implements BoardRepository {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final EntityTransaction tx;

    public BoardMySQLRepository() {
        this.emf = Persistence.createEntityManagerFactory("java_was_2022");
        this.em = emf.createEntityManager();
        this.tx = em.getTransaction();
    }

    @Override
    public Optional<Board> save(Board board) {
        tx.begin();

        try {
            em.persist(board);
            tx.commit();
            return Optional.of(board);
        } catch (Exception e) {
            tx.rollback();
            throw new BoardSaveException(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<List<Board>> findAll() {
        return Optional.ofNullable(em.createQuery("select b from Board b", Board.class).getResultList());
    }
}
