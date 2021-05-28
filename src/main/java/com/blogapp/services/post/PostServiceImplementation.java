package com.blogapp.services.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repositories.PostRepository;
import com.blogapp.web.DTOs.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    public Post addPost(PostDTO postDTO) {
        Post post = new Post();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(postDTO, post);

        log.info("Post object after mapping --> {}", post);

        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {
        return null;
    }

    @Override
    public Post updatePost(PostDTO postDTO) {
        return null;
    }

    @Override
    public Post findByID(Integer ID) {
        return null;
    }

    @Override
    public void deletePostByID(Integer ID) {

    }

    @Override
    public Post addCommentToPost(Integer ID, Comment comment) {
        return null;
    }
}
