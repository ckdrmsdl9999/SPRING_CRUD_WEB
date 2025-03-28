    package project.Controller;
    import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import project.Security.session.SessionManager;
    import project.Service.LoginService;
import project.Service.MemberService;
    import project.domain.Board;
    import project.domain.Comment;
    import project.domain.LoginForm;
import project.domain.Member;
import org.springframework.util.StringUtils;////
import javax.servlet.http.Cookie;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    import javax.validation.Valid;
    import java.util.List;

    @Slf4j
    @Controller
    @RequiredArgsConstructor
    public class LoginController {
    private final MemberService memberService;
    private final LoginService loginService;
    @GetMapping("/memberJoinForm") //
    public String addForm(@ModelAttribute("member") Member member) { return "memberJoin"; }
    @PostMapping("/memberJoinForm")//회원가입버튼 정보적고 누르면 이동
    public String createMember(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.print("error바인딩출력:"+bindingResult);
            }

        if (!StringUtils.hasText(member.getPassword())) {
            bindingResult.addError(new FieldError("member","password","패스워드문제라능"));
            bindingResult.addError(new ObjectError("member","글로벌메시지라능"));
            System.out.println("로그용패스워드애러");
            return "memberJoin";
        }
        memberService.join(member);
        return "memberSave";
    }
    @GetMapping("/login")//requestmapping으로하면 패킷에는 어떻게 뜰까
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
         return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response,
                        RedirectAttributes redirectAttributes, HttpServletRequest request,HttpSession session) {

        session.setAttribute("mysessionmember",form);

        if (bindingResult.hasErrors()) {
            return "login";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }
        request.setAttribute("loginId",form.getLoginId());
        //쿠키생성      //SETPATH설정안해도됨   GETMAPPING에서 /MEMBER/LOGIN 이면
        // 아마 /MEMBER/의 하위디렉토리에만적용될거임 쿠키, 홈을 /member/으로해보기 하고지우고고
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getUsername())); 세션으로대체
////      idCookie.setPath("/");
//        response.addCookie(idCookie);
//        System.out.println("logincontroller에서 memberid"+loginMember.getUsername()+"쿠키패스"+idCookie.getPath());
        //로그인 성공 처리 TODO
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutForm(HttpServletRequest request, HttpServletResponse response,HttpSession session)
    {
//        expireCookie("mysessionname",response);
//        HttpSession session = request.getSession(false);
        System.out.println("세션목록"+session.getId());
        if (session != null) {
            session.invalidate();
            System.out.println("세션제거부분"+session.getId());
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/logout/board/{something}/{something2}","/logout/board/{something}"})//requestmapping으로하면 패킷에는 어떻게 뜰까, postmapping쓸때와차이는? 해결후삭제
    public String logoutForm5(@PathVariable(required = false) String something,
                                  @PathVariable(required = false) String something2,
                                  HttpServletRequest request, HttpServletResponse response, HttpSession session)
        {

            System.out.println("something은뭘까"+something+"something2은뭘까"+something2);


            if (session != null) {
                session.invalidate();
                System.out.println("세션제거부분"+session.getId());
            }
            if (something.equals("main"))
            {return "redirect:/";}
            if (something.equals("null"))
            {something="";
                return "redirect:/board/"+something;}
            if (something2==null&&!something.equals(null))//equals는 null예외생김
            {
                return "redirect:/board/"+something;}
            return "redirect:/board/"+something+"/"+something2;
        }


    void expireCookie(String cookiename ,HttpServletResponse response){
        Cookie cookie = new Cookie(cookiename,"5");
        cookie.setMaxAge(0);//-1이면 브라우져종료시만료
        response.addCookie(cookie);
    }

}
