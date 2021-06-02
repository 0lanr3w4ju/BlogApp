package com.blogapp.services;

import com.blogapp.data.models.Post;
import com.blogapp.data.repositories.PostRepository;
import com.blogapp.services.post.PostServiceImplementation;
import com.blogapp.web.DTOs.PostDTO;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PostServiceImplementationTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImplementation postService = new PostServiceImplementation();
    Post testPost;

    @BeforeEach
    void setUp() {
        testPost = new Post();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSaveMethodIsCalledTheRepositoryIsAlsoCalled() throws PostObjectIsNullException {
        when(postService.addPost(new PostDTO())).thenReturn(testPost);
        postService.addPost(new PostDTO());

        verify(postRepository, times(1)).save(testPost);
    }

    @Test
    void returnListOfPostWhenTheFindAllMethodIsCalled() {
        List<Post> postList = new ArrayList<>();
        when(postService.getAllPost()).thenReturn(postList);
        postService.getAllPost();

        verify(postRepository, times(1)).findAll();
    }
}