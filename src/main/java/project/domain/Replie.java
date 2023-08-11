package project.domain;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="replie")
@Data
public class Replie {

    @Id @GeneratedValue
    @Column(name="replie_id")
    private Long id;

    @Column
    private String content;

    @Column
    private String parentid;

    @Column//order로하면 오류남
    private Integer orders;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;




}
