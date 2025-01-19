package project.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import project.Repository.MemberRepository;
import project.domain.Board;
import project.domain.LoginForm;
import project.domain.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String homeLogin(@CookieValue(value = "memberId",required = false) String memberId,
                            Model model, HttpSession session,HttpServletRequest request) {
        session=request.getSession(false);
        LoginForm form=(LoginForm) session.getAttribute("mysessionmember");

        log.info("로그인성공전 들어간다."+form);
        if (form == null) { return "home"; }

        Member loginMember = memberRepository.findByLoginId(form.getLoginId()).orElse(null);

       log.info("로그인성공후 들어간다.");
        model.addAttribute("member", loginMember);
        return "loginhome";
    }
    @GetMapping("/test")
    public String test()
    {
        return "boardEdit";
    }

    }
