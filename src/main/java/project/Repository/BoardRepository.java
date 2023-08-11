package project.Repository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;
import project.domain.Item;
import project.domain.Member;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@AllArgsConstructor
public class BoardRepository //extends JpaRepository
{
    private static final List<Board> aa = new ArrayList<>();//임시 db

    private final EntityManager em;

    @Transactional
    public void save(Board board)
    {
        em.persist(board);

    }
    public Board findOne(Long id)
    {
        return em.find(Board.class,id);
    }
    public List<Board> findById(Integer id)
    {
        return em.createQuery("select m from Board m where m.id= :id",Board.class).
                setParameter("id",id).getResultList();
    }

    public List<Board> findAll() {
        return em.createQuery("select m from Board m").getResultList(); }

}
