package project.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "comments")
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column
    private Long parentid;

    @Column
    private Long depth;

    @Column
    private String writer;

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)//mappedby는 상대필드명
    private List<Replie> replies = new ArrayList<>();

//    /* 댓글 수정 */
//    public void update(String content) {
//        this.content = content;
//    }




}
