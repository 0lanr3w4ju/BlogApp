package com.blogapp.services.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.web.DTOs.PostDTO;
import com.blogapp.web.exceptions.PostNotFoundException;
import com.blogapp.web.exceptions.PostObjectIsNullException;

import java.util.List;

public interface PostService {

    Post addPost(PostDTO postDTO) throws PostObjectIsNullException;

    List<Post> getAllPost();

    List<Post> findAllPostInDescOrder();

    Post updatePost(PostDTO postDTO);

    Post findByID(Integer ID) throws PostNotFoundException;

    void deletePostByID(Integer ID);

    Post addCommentToPost(Integer ID, Comment comment);
}
