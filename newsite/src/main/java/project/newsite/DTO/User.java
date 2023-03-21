package project.newsite.DTO;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="`user`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userid;

    @Column(name="username", unique=true)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name="email")
    private String email;

    @Column(name="provider")
    private String provider;

    @Column(name="providerId")
    private String providerId;

    @Builder
    public User(String username, String password, String email, String provider, String providerId, String role){
        this.username=username;
        this.password=password;
        this.email=email;
        this.provider=provider;
        this.providerId=providerId;
        this.role=role;
    }

    @ManyToMany
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="authority_name",referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany
    @JoinColumn(name="author_id")//fk 설정
    //내 pk가 N테이블의 저 행의 값으로 들어감.
    private List<Post> mypost=new ArrayList<>();

    @OneToMany
    @JoinColumn(name="reply_author")
    //내가 남긴 댓글 확인
    private List<Reply> myreply=new ArrayList<>();

    public void addpost(Post p){
        mypost.add(p);
    }

    public void addreply(Reply r){
        myreply.add(r);
    }

}
