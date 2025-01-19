package project.Controller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.Repository.BoardRepository;
import project.Repository.CommentRepository;
import project.Repository.MemberRepository;
import project.Repository.ReplieRepository;
import project.Service.Pagination;
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

    @ResponseBody
    @GetMapping("/board/write2")//글쓰기 이동
    public List<Comment> BoardWrite2(@ModelAttribute Board board, BindingResult result)
    {
        List<Comment> comment = commentRepository.findAll();
        return comment;
    }

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
        return "home";     //가안되는이유-home이나 html파일명하는거지 /만하면 /파일이없음 redirect

    }

    @PostMapping("/boardReply")//대대댓글달
    public String BoardReply(@ModelAttribute Comment comment, Model model){
        comment.setParentid(comment.getParentid());
        //따라서 comment.getId->comment.getparentid  해주고 id값은 여기서 정하면안됨
        // dbㅎ에서저장될때정해져야지 당연히 null값나옴
        comment.setWriter(comment.getWriter());
        comment.setDepth((long)1);
        System.out.println(comment.getWriter()+"작성자하고 부모아이디"+comment.getParentid());
        commentRepository.saveReply(comment);
        return "redirect:/board/boardContent/"+comment.getBoardid();
    }

    @GetMapping("/board/write")//글쓰기 이동
    public String BoardWrite(@ModelAttribute Board board, BindingResult result)
    { return "boardwrite"; }
//    @GetMapping("/board/edit/{id}")//글수정 수정화면 이동
    @GetMapping("/board/edit")//글수정 수정화면 이동
    public String BoardEdit( @ModelAttribute Board board, Model model, BindingResult result) {
//        boardRepository.updateBoard(boardRepository.findOne(id));
        System.out.println("수정체크ㅇㅇㅇㅇ"+board.getId());
      //  model.addAttribute("id",id);
//        model.addAttribute("",)
        return "boardEdit"; }

    @PostMapping("/boardedit")//글수정
    public String BoardEdit2(@ModelAttribute  Board board, BindingResult result,
                             HttpServletRequest request, HttpSession httpSession){
        LoginForm loginForm = (LoginForm) httpSession.getAttribute("mysessionmember");
        System.out.print("게시판확인"+loginForm.getLoginId());
        System.out.println("ㅋㅋㅋㅋ"+board.getId());
//        board.setViewcount((long)0);
        board.setWriter(loginForm.getLoginId());
        Member member = memberRepository.findByLoginId(loginForm.getLoginId()).orElse(null);
        System.out.println("글수정값확인"+board.getWriter()+board.getId()+board.getContent());
        board.setMember(member);

        boardRepository.updateBoard(board);///////////////////////////////
//        boardRepository.save(board);

        return "redirect:/board";
    }

    @GetMapping("/board/delete")//글 삭제
    public String BoardDelete(@ModelAttribute Board board, BindingResult result) {
        System.out.println("삭제체크ㅇㅇㅇㅇ1");
        boardRepository.deleteById(boardRepository.findOne(board.getId()));
        System.out.println("삭제체크ㅇㅇㅇㅇ2");
        return "redirect:/board"; }

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

   }
    @PostMapping("/board")//글쓰기
    public String BoardWrite2(@ModelAttribute  Board board, BindingResult result,
                              HttpServletRequest request, HttpSession httpSession){
        LoginForm loginForm = (LoginForm) httpSession.getAttribute("mysessionmember");
        System.out.print("게시판확인"+loginForm.getLoginId());
        board.setViewcount((long)0);
        board.setWriter(loginForm.getLoginId());
        Member member = memberRepository.findByLoginId(loginForm.getLoginId()).orElse(null);
        board.setMember(member);//외래키

        boardRepository.save(board);

        return "redirect:/board";
    }



    @GetMapping("/board")//게시판출력
    public String Board(Model model, HttpServletRequest request, @PageableDefault(size = 10,page=5)Pageable pageable){
        int offset=0; int limit=5;
        List<Board> board = boardRepository.findAll(pageable,offset,limit);
        System.out.println("사이즈테스트:"+pageable.getPageSize()+"사이즈테스트2:"+pageable.getPageNumber());//10,5

//      Page<BoDard> dto=board.map(a->new Board());               //새로운코드시험중
        int page=pageable.getPageNumber()-1;
        int pageLimit=3;
        model.addAttribute("startPage",2);
        model.addAttribute("endPage",5);
        model.addAttribute("board",board);
        //붙이는게날까 아니면 board, loginboard이렇게 따로놓는게 나을까
        HttpSession session=request.getSession(false);
        if(session==null){System.out.println("세션이없음세션이없음세션이없음세션이없음세션이없음세션이없음");
            return "board";}
        return "board";

    }



}
