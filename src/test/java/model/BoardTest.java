package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Transactional
class BoardTest {
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction tx;

    @BeforeEach
    void beforeEach() {
        emf = Persistence.createEntityManagerFactory("java_was_2022");
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @AfterEach
    void afterEach() {
        tx.commit();
        em.close();
        emf.close();
    }

    @Test
    void persistTest() {
        Board board = new Board("user1", "user1_content");
        assertDoesNotThrow(() -> em.persist(board));
    }
}