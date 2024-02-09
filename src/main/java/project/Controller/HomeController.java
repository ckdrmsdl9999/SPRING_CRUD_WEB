package project.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.Repository.BoardRepository;
import project.Repository.MemberRepository;
import project.domain.Board;
import project.domain.Member;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
//    @GetMapping("/board")//게시판출력
//    public String Board(Model model){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        return "board";
//    }


    @GetMapping("/") //requestmapping으로해도 무방
    public String homeLogin(@CookieValue(value = "memberId",required = false) String memberId, Model model)
    {
        log.info("로그인성공전 들어간다."+memberId);
        if (memberId == null) {
            return "home";
        }
        //로그인
//        Member loginMember = memberRepository.findById(memberId);
        Member loginMember = memberRepository.findByLoginId(String.valueOf(memberId)).orElse(null);
//        System.out.println("나온값:"+loginMember.getUsername());
        if (loginMember == null) { log.info("null이얌 ㅠㅠ.");
            return "home";
        }
       log.info("로그인성공후 들어간다.");
        model.addAttribute("member", loginMember);
        return "loginhome";
    }
    @GetMapping("/test") //requestmapping으로해도 무방
    public String test()
    {
        return "testpage";
    }

    }
