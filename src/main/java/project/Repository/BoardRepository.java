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
public class BoardRepository
        //extends JpaRepository<Board,Long>
        //        extends CrudRepository<Board,Long>

{
    private static final List<Board> aa = new ArrayList<>();//임시 db
    private final EntityManager em;
//    public Page<PostsResponseDto> paging(Pageable pageable) {
//        int page = pageable.getPageNumber() - 1; // page 위치에 있는 값은 0부터 시작한다.
//        int pageLimit = 3; // 한페이지에 보여줄 글 개수
//        // 한 페이지당 3개식 글을 보여주고 정렬 기준은 ID기준으로 내림차순
//        Page<Posts> postsPages = postsRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "id")));
//    public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
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
