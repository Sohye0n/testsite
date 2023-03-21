package project.newsite.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.newsite.Auth.AuthToken;
import project.newsite.DTO.Post;
import project.newsite.DTO.Reply;
import project.newsite.DTO.User;
import project.newsite.Repository.PostRepository;
import project.newsite.Repository.ReplyRepository;
import project.newsite.Repository.UserRepository;

@Controller
public class PostController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReplyRepository replyRepository;

    @GetMapping("/post/{no}")
    public String showpost(@PathVariable("no")Long no, Model model, @AuthenticationPrincipal AuthToken userauthtoken){
        Post post=postRepository.findBypostid(no);
        User user=userRepository.findByUsername(userauthtoken.getUsername());

        model.addAttribute("post",post);
        model.addAttribute("user",user);
        return "/board/component";
    }

    @PostMapping("/ReplyForm/{no}")
    public String reply(Reply reply, @PathVariable("no")Long no, @AuthenticationPrincipal AuthToken userauthtoken){

        Post post=postRepository.findBypostid(no);
        reply.setReply_comment(reply.getReply_comment());
        post.addreply(reply);

        System.out.println(userauthtoken.getUsername());
        User user=userRepository.findByUsername(userauthtoken.getUsername());
        user.addreply(reply);

        replyRepository.save(reply);

        //원글 작성자에게 알림
        User postauthor=post.getUser();


        return "redirect:/";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no")Long no, Model model){
        Post post=postRepository.findBypostid(no);
        model.addAttribute(post);
        return "/board/edit";
    }

    @PutMapping("/post/edit/{no}")
    public String saveedit(@PathVariable("no")Long no, Post editpost){
        Post post=postRepository.findBypostid(no);
        post.setComponent(editpost.getComponent());
        System.out.println(editpost.getComponent());
        postRepository.save(post);
        return "redirect:/";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no")Long no){
        Post post=postRepository.findBypostid(no);
        postRepository.delete(post);
        return "redirect:/";
    }
}
