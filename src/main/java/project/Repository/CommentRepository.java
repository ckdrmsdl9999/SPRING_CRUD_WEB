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

     EntityManager em; //final 안붙이니까 nullpointerexception뜨는데?. 생성자 생성안되서 그럼 @RequiredArgsConstructor는 finalaksgownsl
    /* 게시글 댓글 목록 가져오기 */
//    List<Comment> getCommentByPostsOrderById(Board board);

    public List<Comment> findAll(){//null값대비
       return em.createQuery("select m from Comment m").getResultList();
    }

//    List<Comment> findAll();
    @Transactional
    public void save(Comment comment){
       em.persist(comment);
    }


}
