package project.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Comment;
import project.domain.Replie;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class ReplieRepository {
    EntityManager em; //final 안붙이니까 nullpointerexception뜨는데?. 생성자 생성안되서 그럼 @RequiredArgsConstructor는 finalaksgownsl


    public List<Replie> findAll(){//null값대비
        return em.createQuery("select m from Replie m").getResultList();
    }

    //    List<Comment> findAll();
    @Transactional
    public void save(Replie replie){
        em.persist(replie);
    }


}
