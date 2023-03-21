package project.newsite.Controller;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.jetbrains.annotations.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinForm {
    @NotNull
    @Size(min=3, max=100)
    private String username;

    @NotNull
    @Size(min=3, max=100)
    private String password;
}
