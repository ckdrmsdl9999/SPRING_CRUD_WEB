package project.domain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name="member")
@Getter @Setter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

//    @Column( length = 30, unique = true)//unique만먹히고 null중복안되는거 nullable은안먹히는상황
    @Column
    @NotEmpty
    private String username; // 아이디

    @Column(length = 100)
    private String password;

    @Column(nullable = true, length = 50, unique = true)
//    @Email
    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    protected Member() {}

}
