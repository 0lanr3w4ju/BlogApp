package com.blogapp.web.controllers;

import com.blogapp.data.models.Post;
import com.blogapp.services.post.PostService;
import com.blogapp.web.DTOs.PostDTO;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/allPost")
    public String getIndex(Model model) {
        List<Post> postList = postService.getAllPost();
        model.addAttribute("postList", postList);

        return "index";
    }
    @GetMapping("/createPost")
    public String getPostForm(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        return "create";
    }
    @PostMapping("/savePost")
    public String savePost(@ModelAttribute @Valid PostDTO postDTO) {
        log.info("Post dto received --> {}", postDTO);
        try {
            postService.addPost(postDTO);
        } catch (PostObjectIsNullException e) {
            log.info("Exception occurred --> {}", e.getMessage());
        }

        return "redirect:/allPost";
    }
}
