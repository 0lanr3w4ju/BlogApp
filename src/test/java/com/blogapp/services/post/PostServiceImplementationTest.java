package com.blogapp.services.post;

import com.blogapp.data.models.Post;
import com.blogapp.data.repositories.PostRepository;
import com.blogapp.web.DTOs.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void whenSaveMethodIsCalledTheRepositoryIsAlsoCalled() {
        when(postService.addPost(new PostDTO())).thenReturn(testPost);
        postService.addPost(new PostDTO());

        verify(postRepository, times(1)).save(testPost);
    }
}