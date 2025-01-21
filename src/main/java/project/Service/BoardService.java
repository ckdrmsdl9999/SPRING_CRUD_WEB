package project.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.Repository.BoardRepository;
import project.domain.Board;

import javax.persistence.EntityManager;


@Slf4j
@AllArgsConstructor
@Service
public class BoardService {

    EntityManager em;
    private final BoardRepository boardRepository;

    public void save(Board board)
    {
        boardRepository.save(board);
    }
//
////    /* Paging */
////    @Transactional(readOnly = true)
////    public Page<Posts> pageList(Pageable pageable) {
////    return postsRepository.findAll(pageable);
//
//
//
//
//




}
