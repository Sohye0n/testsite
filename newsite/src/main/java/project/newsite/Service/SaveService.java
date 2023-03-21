package project.newsite.Service;

import org.springframework.stereotype.Service;
import project.newsite.Repository.PostRepository;

@Service
public class SaveService {

    private final PostRepository postRepository;


    public SaveService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

}
