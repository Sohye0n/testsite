package project.newsite.Controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyForm {
    @NotNull
    @Size(min=3, max=100)
    private String reply_comment;
}
