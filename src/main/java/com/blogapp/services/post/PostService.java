package com.blogapp.services.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.web.DTOs.PostDTO;

import java.util.List;

public interface PostService {

    Post addPost(PostDTO postDTO);

    List<Post> getAllPost();

    Post updatePost(PostDTO postDTO);

    Post findByID(Integer ID);

    void deletePostByID(Integer ID);

    Post addCommentToPost(Integer ID, Comment comment);
}