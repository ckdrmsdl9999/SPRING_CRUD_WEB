package project.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.domain.Member;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L;

    private final EntityManager em;

    public void saveMember(Member member){
        em.persist(member);
    }//DB에 MEMBER저장

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){//
        return em.createQuery("select m from Member m")
                .getResultList();
    }

    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public Optional<Member> findByLoginId(String loginId) {////////////
        return findAll().stream()
                .filter(m -> m.getUsername().equals(loginId))///getid->getusername
                .findFirst();
    }


}
