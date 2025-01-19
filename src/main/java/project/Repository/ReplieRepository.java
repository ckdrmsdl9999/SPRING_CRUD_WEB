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
    EntityManager em;


    public List<Replie> findAll(){//null값대비
        return em.createQuery("select m from Replie m").getResultList();
    }

    @Transactional
    public void save(Replie replie){
        em.persist(replie);
    }


}
