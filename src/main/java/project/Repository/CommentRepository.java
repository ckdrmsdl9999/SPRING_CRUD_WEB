package project.Repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;
import project.domain.Comment;


import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.List;

//@RequiredArgsConstructor
//@Slf4j
@Repository
@Slf4j
@AllArgsConstructor
public class CommentRepository {
     EntityManager em;
    public List<Comment> findAll(){//null값대비
       return em.createQuery("select m from Comment m ORDER BY m.parentid, m.id").getResultList(); }
//    public List<Comment> findAll(){//null값대비
//        return em.createQuery("select m from Comment m").getResultList();
//    }

    public List<Comment> findBy(Long boardid){//null값대비
        return em.createQuery("select m from Comment m where m.boardid = :boardid ORDER BY m.parentid, m.id").setParameter("boardid",boardid).getResultList();
    }

    @Transactional
    public void save(Comment comment){
        em.persist(comment);
        comment.setParentid(comment.getId());
        System.out.println("시험입니다"+comment.getParentid());
        //댓글과 대댓글save다르게해보자
        em.persist(comment);
    }
    @Transactional
    public void saveReply(Comment comment){
        em.persist(comment);
        System.out.println("시험입니다"+comment.getParentid());

    }

}
