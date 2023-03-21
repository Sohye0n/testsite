package project.newsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.newsite.DTO.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
    Post findBypostid(Long no);
}
