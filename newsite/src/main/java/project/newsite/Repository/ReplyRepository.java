package project.newsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.newsite.DTO.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
