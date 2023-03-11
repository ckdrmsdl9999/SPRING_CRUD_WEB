package project.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.Service.LoginService;
import project.Service.MemberService;
import project.domain.LoginForm;
import project.domain.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/memberJoinForm") //
    public String addForm() {
        return "memberJoin";
    }

    @PostMapping("/memberJoinForm")//회원가입버튼 정보적고 누르면 이동
    public String createMember(@ModelAttribute Member member){
        memberService.join(member);
        return "memberSave"; }

    @GetMapping("/login")//requestmapping으로하면 패킷에는 어떻게 뜰까
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
         return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }
        //쿠키생성      //SETPATH설정안해도됨   GETMAPPING에서 /MEMBER/LOGIN 이면
        // 아마 /MEMBER/의 하위디렉토리에만적용될거임 쿠키, 홈을 /member/으로해보기 하고지우고고
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getUsername()));
//      idCookie.setPath("/");
        response.addCookie(idCookie);
        System.out.println("logincontroller에서 memberid"+loginMember.getUsername()+"쿠키패스"+idCookie.getPath());
        //로그인 성공 처리 TODO
        return "redirect:/";
    }

    @GetMapping("/logout")//requestmapping으로하면 패킷에는 어떻게 뜰까, postmapping쓸때와차이는? 해결후삭제
    public String logoutForm(HttpServletResponse response)
    {
        expireCookie("memberId",response);
        return "redirect:/";
    }

    void expireCookie(String cookiename ,HttpServletResponse response){
        Cookie cookie = new Cookie(cookiename,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
