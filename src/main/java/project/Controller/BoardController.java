package project.Controller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.Repository.BoardRepository;
import project.Repository.CommentRepository;
import project.Repository.MemberRepository;
import project.Repository.ReplieRepository;
import project.domain.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
@AllArgsConstructor
@Slf4j
public class BoardController {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ReplieRepository replieRepository;

    @ResponseBody        //이거 없애버려서 오류떳음 이걸왜붙일까->
    @GetMapping("/board/write2")//글쓰기 이동
    public List<Comment> BoardWrite2(@ModelAttribute Board board, BindingResult result)
    {
        List<Comment> comment = commentRepository.findAll();
        return comment;
//        return "redirect:/comment";
    }
            //이거 없애버려서 오류떳음 이걸왜붙일까->
    @GetMapping("/board/write3")//댓글출력부분
    public String BoardWrite3(@ModelAttribute Board board,Model model, BindingResult result)
    {
        List<Comment> comment = commentRepository.findBy(board.getId());
        System.out.print("testttttttttttttttt"+board.getId()+"ddd ");
        model.addAttribute("comment",comment);
        return "/boardcontent :: #rta";
//        /boardcontent로하면 통으로붙음  #rta면 rta안에값만 붙음
//        return "redirect:/comment";
    }
    @PostMapping("/boardContent")//댓글작성
    public String BoardContent(@ModelAttribute Comment comment, Model model){
        comment.setDepth((long)0);
        commentRepository.save(comment);
        comment.setParentid(comment.getId());    //저장하고 em에서 가져오기?
        System.out.println(comment.getId());
        return "home";     //redirect아니면에러나네..는아니고 /가안되는이유-home이나 html파일명하는거지 /만하면 /파일이없음 redirect
        //해야함 url식// getmapping /board하면랜더링안됨 리다이렉트해야댐
//        return "redirect:/board";     //이 값 왜들어가는지 이해안됨 시발 까먹은건지몰랏던건지** 콘솔에는찍힘 board소스가..
    }

    @PostMapping("/boardReply")//대대댓글달
    public String BoardReply(@ModelAttribute Comment comment, Model model){
        comment.setParentid(comment.getParentid());
        ////////부모댓글의 아이디를 넣어야하고 지금기준 null나오는이유는 db에 저장이되고 나오니까 아직없지
        //따라서 comment.getId->comment.getparentid  해주고 id값은 여기서 정하면안됨
        // dbㅎ에서저장될때정해져야지 당연히 null값나옴
        comment.setWriter(comment.getWriter());
        comment.setDepth((long)1);
        System.out.println(comment.getWriter()+"작성자하고 부모아이디"+comment.getParentid());
//        commentRepository.save(comment);
        commentRepository.saveReply(comment);
//        return "redirect:/board";
        return "redirect:/board/boardContent/"+comment.getBoardid();
    }

    @GetMapping("/board/write")//글쓰기 이동
    public String BoardWrite(@ModelAttribute Board board, BindingResult result)
    { return "boardwrite"; }

    @GetMapping("/board/boardContent/{id}")// 글 세부내용
    public String BoardRead(@PathVariable("id")Long id, @ModelAttribute("board") Board board,Model model,
                            HttpServletRequest request, HttpSession httpSession){
        board=boardRepository.findOne(id);
        board.setViewcount(board.getViewcount()+1);//조회수
        List<Comment> comment = commentRepository.findBy(id); //null처리?
        boardRepository.save(board);
        model.addAttribute("board",board);
        model.addAttribute("comment",comment);
        LoginForm form = (LoginForm) httpSession.getAttribute("mysessionmember");//세션추가
        if(form==null){
            model.addAttribute("writer2", null);
        }
        else {
            model.addAttribute("writer2", form.getLoginId());//username->loginForm.getloginId
        }
        return "boardcontent";
        //알아볼거 누르면 controlleㄱ단에서 가는건가? 아님브라우저저장된 htmlboard만불러오나 불러오기만하네 model값이랑랑
   }
    @PostMapping("/board")//글쓰기
    public String BoardWrite2(@ModelAttribute  Board board, BindingResult result,
                              HttpServletRequest request, HttpSession httpSession){
        LoginForm loginForm = (LoginForm) httpSession.getAttribute("mysessionmember");
        System.out.print("게시판확인"+loginForm.getLoginId());
        board.setViewcount((long)0);

        board.setWriter(loginForm.getLoginId());
        Member member = memberRepository.findByLoginId(loginForm.getLoginId()).orElse(null);
        board.setMember(member);//외래키~~

        boardRepository.save(board);
        return "redirect:/board";
    }
//    @GetMapping("/board")//게시판출력
//    public String Board(Model model){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        //세션이 존재하면-header loginheader선택할수있는걸로  "/boardcontent :: #rta";이렇게?
//        //  return "/boardcontent :: #rta"
//        //붙이는게날까 아니면 board, loginboard이렇게 따로놓는게 나을까
//        return "board";
//    }

    @GetMapping("/board")//게시판출력
    public String Board(Model model,HttpServletRequest request){
        List<Board> board = boardRepository.findAll();
        model.addAttribute("board",board);
        //붙이는게날까 아니면 board, loginboard이렇게 따로놓는게 나을까
        HttpSession session=request.getSession(false);
//        System.out.print("세션체크:"+session.getAttribute("mysessionmember"));
        if(session==null){System.out.println("세션이없음세션이없음세션이없음세션이없음세션이없음세션이없음");
            return "board";}
        return "board";
    }


//    /* 게시글 목록 */
//    @GetMapping("/")
//    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
//        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
//        Integer[] pageList = boardService.getPageList(pageNum);
//
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pageList", pageList);
//
//        return "board/list.html";
//    }


}
