package project.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.Repository.MemberRepository;
import project.domain.Member;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null이면 로그인 실패
     */
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }


}
