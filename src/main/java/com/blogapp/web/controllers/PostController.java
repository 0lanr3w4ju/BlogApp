package com.blogapp.web.controllers;

import com.blogapp.data.models.Post;
import com.blogapp.services.post.PostService;
import com.blogapp.web.DTOs.PostDTO;
import com.blogapp.web.exceptions.PostNotFoundException;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<Post> postList = postService.findAllPostInDescOrder();
        model.addAttribute("postList", postList);
        return "index";
    }
    @GetMapping("/createPost")
    public String getPostForm(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("error", false);
        return "create";
    }
    @PostMapping("/savePost")
    public String savePost(@ModelAttribute @Valid PostDTO postDTO,
                           BindingResult bindingResult,
                           Model model) {
        log.info("Post dto received --> {}", postDTO);

        if (bindingResult.hasErrors()) {
            return "create";
        }

        try {
            postService.addPost(postDTO);
        } catch (PostObjectIsNullException error) {
            log.info("Exception occurred --> {}", error.getMessage());
        } catch (DataIntegrityViolationException error) {
            log.info("Constraint exception occurred --> {}", error.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", error.getMessage());
            return "create";
        }

        return "redirect:/allPost";
    }
    @GetMapping("/viewPost/{id}")
    public String viewPost(@PathVariable("id") Integer id, Model model) {
        log.info("Request for a post path --> {}", id);

        try {
            Post savedPost = postService.findByID(id);
            model.addAttribute("postInfo", savedPost);
        } catch (PostNotFoundException error) {
            log.info("exception occurred");
        }
        return "post";
    }

//    @ModelAttribute
//    public void createPostModel(Model model) {
//        model.addAttribute("postDTO", new PostDTO());
//    }
}
