package project.newsite.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.newsite.Auth.AuthToken;
import project.newsite.DTO.Post;
import project.newsite.DTO.Reply;
import project.newsite.DTO.User;
import project.newsite.Repository.PostRepository;
import project.newsite.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home() { return "home"; }

    //로그인하기
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model){
        String errno=request.getParameter("error");
        if(errno==null) return "login";
        System.out.println(errno);
        model.addAttribute("errno",errno);
        return "/loginexceed";
    }

    //회원가입하기
    @GetMapping("/join")
    public String join(){return "join";}

    @PostMapping("/JoinForm")
    public String create(User user){
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/";
    }

    //글쓰기
    @GetMapping("/board")
    public String board() {return "board";}

    @PostMapping("/BoardForm")
    public String write(Post board, @AuthenticationPrincipal AuthToken userauthtoken){
        System.out.println(userauthtoken.getUsername());
        System.out.println(board.getComponent());
        board.setAuthor(userauthtoken.getUsername());
        User user=userRepository.findByUsername(userauthtoken.getUsername());
        user.getMypost().add(board);
        board.setUser(user);
        postRepository.save(board);
        return "redirect:/";
    }

    //글 목록 확인하기
    @GetMapping("/posts")
    public String posts(RedirectAttributes attributes){
        attributes.addAttribute("pagenum","1");
        return "redirect:/postlist";
    }

    @GetMapping("/postlist")
    public String postlist(HttpServletRequest request, Model model){
        System.out.println("got postlist for page mapping...");
        int pn=Integer.parseInt(request.getParameter("pagenum"));
        System.out.println(pn);

        List<Post> postlist=postRepository.findAll();
        ArrayList<Post> newpostlist;
        if(pn*5<=postlist.size()) {
            newpostlist=new ArrayList<>(postlist.subList((pn-1)*5,pn*5));
        }
        else if(pn*5>postlist.size()+5){
            return "pageerror";
        }
        else{
            newpostlist=new ArrayList<>(postlist.subList((pn-1)*5,postlist.size()));
        }

        model.addAttribute("posts",newpostlist);
        model.addAttribute("pagenum",pn);

        return "postlist";
    }

    //마이 페이지
    @GetMapping("/mypage")
    public String mypage(Model model, @AuthenticationPrincipal AuthToken userauthtoken){
        User user=userRepository.findByUsername(userauthtoken.getUsername());
        List<Post>myposts=user.getMypost();
        List<Reply>myreplys=user.getMyreply();
        model.addAttribute("posts",myposts);
        model.addAttribute("replys",myreplys);
        return "mypage";
    }
}