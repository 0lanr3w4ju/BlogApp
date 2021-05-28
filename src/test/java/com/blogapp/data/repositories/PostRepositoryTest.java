package com.blogapp.data.repositories;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    private final PostRepository postRepository;
    @Autowired
    PostRepositoryTest(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @BeforeEach
    void setUp() {
    }
    @Test
    void savePost() {
        Post post = new Post();
        post.setTitle("What is Fintech?");
        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        log.info("Created a BlogPost --> {}", post);
        postRepository.save(post);
        assertThat(post.getId()).isNotNull();
    }

    @Test
    void throwExceptionWhenSavingSameTitle() {
        Post post = new Post();
        post.setTitle("What is Fintech?");
        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        log.info("Created a BlogPost --> {}", post);
        postRepository.save(post);
        assertThat(post.getId()).isNotNull();

        Post post1 = new Post();
        post1.setTitle("What is Fintech?");
        post1.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        log.info("Created a BlogPost --> {}", post1);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(post1));
    }

    @Test
    void whenPostIsSavedAuthorShouldBeSavedToo() {
        Post post = new Post();
        post.setTitle("What is Fintech?");
        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        log.info("Created a BlogPost --> {}", post);
//        postRepository.save(post);
//        assertThat(post.getId()).isNotNull();
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Wick");
        author.setEmail("Johnwick@email.com");
        author.setPhoneNumber("09090996744");

        post.setAuthor(author);
        author.addPost(post);

        postRepository.save(post);
        log.info("Blog Post after saving --> {}", post);
    }

    @Test
    void findAllPostInTheDB() {
        List<Post> existingPost = postRepository.findAll();
        assertThat(existingPost).isNotNull();
        assertThat(existingPost).hasSize(5);
    }
}