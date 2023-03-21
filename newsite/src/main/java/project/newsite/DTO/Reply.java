package project.newsite.DTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="reply")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    @Id
    @Column(name="reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reply_id;

    @Column(name="reply_comment", length=50)
    private String reply_comment;

    @Column(name="reply_author")
    private String reply_author;

    @Column(name="post_id")
    private Long board_id;

}
