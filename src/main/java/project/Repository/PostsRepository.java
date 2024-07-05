package project.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.domain.Board;
//import project.domain.Posts;

public interface PostsRepository extends JpaRepository<Board,Long> {



}
