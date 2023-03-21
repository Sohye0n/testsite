package project.newsite.DTO;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="post")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @Column(name="postid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postid;

    @Column(name="title", length = 50)
    private String title;

    @Column(name="post_component")
    private String component;

    @Column(name="post_author")
    private String author;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany
    @JoinColumn(name="post_id")
    private List<Reply> reply=new ArrayList<>();

    public void addreply(Reply r){
        reply.add(r);
    }
}
