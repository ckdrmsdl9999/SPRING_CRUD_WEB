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
import project.domain.Board;
import project.domain.Comment;
import project.domain.Member;
import project.domain.Replie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        Gson gson = new GsonBuilder().

        return comment; }

    @GetMapping("/board/write")//글쓰기 이동
    public String BoardWrite(@ModelAttribute Board board, BindingResult result)
    { return "boardwrite"; }

    @GetMapping("/board/boardContent/{id}")// 글 세부내용
    public String BoardRead(@PathVariable("id")Long id,
                            @ModelAttribute("board") Board board,Model model){
        board=boardRepository.findOne(id);
        board.setViewcount(board.getViewcount()+1);//조회수

        List<Comment> comment = commentRepository.findAll(); //null처리?

        boardRepository.save(board);

        model.addAttribute("board",board);
        model.addAttribute("comment",comment);

        return "boardcontent";
        //알아볼거 누르면 controlleㄱ단에서 가는건가? 아님브라우저저장된 htmlboard만불러오나 불러오기만하네 model값이랑랑
   }
    @PostMapping("/board")//글쓰기
    public String BoardWrite2(@ModelAttribute  Board board, BindingResult result,
                              HttpServletRequest request){
      String username="";
      Cookie[] cookie=request.getCookies();
      for(int i=0;i<cookie.length;i++)
       {
           if(cookie[i].getName().equals("memberId"))
           {username=cookie[i].getValue();}
       }
        board.setViewcount((long)0);
        board.setWriter(username);
        Member member = memberRepository.findByLoginId(username).orElse(null);
        board.setMember(member);//외래키~~
        boardRepository.save(board);
        return "redirect:/board";
    }

    @GetMapping("/board")//게시판출력
    public String Board(Model model){
        List<Board> board = boardRepository.findAll();
        model.addAttribute("board",board);
        return "board";
    }

    @PostMapping("/boardContent")//댓글
    public String BoardContent(@ModelAttribute Comment comment, Model model){
        comment.setDepth((long)0);
        comment.setParentid(comment.getId());
        commentRepository.save(comment);

        return "redirect:/board";
    }

    @PostMapping("/boardReply")//댓글달기
    public String BoardReply(@ModelAttribute Comment comment, Model model){
        comment.setParentid(comment.getId());////////부모댓글의 아이디를 넣어야하는데;
        comment.setDepth((long)1);
        commentRepository.save(comment);

        return "redirect:/board";
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
