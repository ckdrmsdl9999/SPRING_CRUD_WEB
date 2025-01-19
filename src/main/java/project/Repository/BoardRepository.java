package project.Repository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
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
public class BoardRepository//extends JpaRepository<Board,Long>//        extends CrudRepository<Board,Long>
{
    private static final List<Board> aa = new ArrayList<>();//임시 db
    private final EntityManager em;

    @Transactional
    public void save(Board board)
    {
        em.persist(board);
    }

//    @Transactional
//    public void update(Board board)
//    {
////        return em.createQuery("update Board m Set m.id=:id, m.writer=:writer,m.content= :content",Board.class).setParameter("id",board.getId()).getResultList();
//    }
    @Transactional
    public void updateBoard(Board board) {
//        System.out.println("DB값1차확인"+board.getId()+board.getContent()+" "+board.getId());
//        Board board2 = em.find(Board.class, board.getId());
//        System.out.println("DB값2차확인"+board2.getTitle()+board2.getContent()+" "+board2.getId());
     //   if (board != null) {
//            board.setTitle(board.getTitle());
//            board.setContent(board.getContent());
//            board.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 갱신
//            em.merge(board); // 변경된 데이터 저장
        em.createQuery("UPDATE Board m SET m.title = :title, m.content = :content WHERE m.id = :id")
                .setParameter("title", board.getTitle())
                .setParameter("content", board.getContent())
                .setParameter("id", board.getId())
                .executeUpdate(); // 업데이트된 행의 개수 반환
        // }
    }
    @Transactional
    public void deleteById(Board board) {
        em.createQuery("DELETE FROM Board m WHERE m.id = :id")
                .setParameter("id", board.getId())
                .executeUpdate(); // 삭제된 행(row) 개수 반환
    }

    public Board findOne(Long id)
    {
        return em.find(Board.class,id);
    }

    public List<Board> findById(Integer id)
    {
        return em.createQuery("select m from Board m where m.id= :id",Board.class).setParameter("id",id).getResultList();
    }

    public List<Board> findAll(Pageable pagable, int offset, int limit) {
        return em.createQuery("select m from Board m").setFirstResult(offset).setMaxResults(limit).getResultList(); }



}
