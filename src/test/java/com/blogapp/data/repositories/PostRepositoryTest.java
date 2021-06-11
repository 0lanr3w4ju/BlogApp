package com.blogapp.data.repositories;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
//    @Test
//    void savePost() {
//        Post post = new Post();
//        post.setTitle("What is Fintech?");
//        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
//
//        log.info("Created a BlogPost --> {}", post);
//        postRepository.save(post);
//        assertThat(post.getId()).isNotNull();
//    }
//    @Test
//    void throwExceptionWhenSavingSameTitle() {
//        Post post = new Post();
//        post.setTitle("What is Fintech?");
//        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
//
//        log.info("Created a BlogPost --> {}", post);
//        postRepository.save(post);
//        assertThat(post.getId()).isNotNull();
//
//        Post post1 = new Post();
//        post1.setTitle("What is Fintech?");
//        post1.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
//
//        log.info("Created a BlogPost --> {}", post1);
//        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(post1));
//    }
    @Test
    void whenPostIsSavedAuthorShouldBeSavedToo() {
        Post post = new Post();
        post.setTitle("What is Fintech?");
        post.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        log.info("Created a BlogPost --> {}", post);

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Wick");
        author.setEmail("Johnwick@email.com");
        author.setPhoneNumber("09090996744");

        post.setAuthor(author);
        author.addPost(post);

        postRepository.save(post);
        log.info("Blog Post after saving --> {}", post);

        Post savedPost = postRepository.findPostByTitle("What is Fintech?");
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNotNull();
    }
    @Test
    void findAllPostInTheDB() {
        List<Post> existingPost = postRepository.findAll();
        assertThat(existingPost).isNotNull();
        assertThat(existingPost).hasSize(3);
    }
    @Test
    @Transactional
    @Rollback(value = false) //rolling back the result of this transaction is set to false.
    void deletePostInDB() {
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();

        log.info("Post fetched from database --> {}", savedPost);
        postRepository.deleteById(41);
        Post deletedPost = postRepository.findById(41).orElse(null);
        assertThat(deletedPost).isNull();
    }
    @Test
    void updateSavedPost() {
        Post savedPost = postRepository.findById(42).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle().equals("title post two: awkward!"));

        log.info("Post fetched from database --> {}", savedPost);
        savedPost.setTitle("Title post 2");

        postRepository.save(savedPost);
        Post changedTitle = postRepository.findById(42).get();
        assertThat(changedTitle.getTitle().equals("Title post 2"));
    }
    @Test
    @Transactional
    void updatePostAuthor() {
        Post savedPost = postRepository.findById(42).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNull();

        Author author = new Author();
        author.setFirstName("Dan");
        author.setLastName("Brown");
        author.setPhoneNumber("09089776532");
        author.setEmail("brown@yahoo.com");
        author.setProfession("artist");

        savedPost.setAuthor(author);
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor().getEmail().equals("brown@yahoo.com"));

        log.info("Upated Post --> {}", updatedPost);
    }
    @Test
    @Transactional
    @Rollback(value = false)
    void addCommentToPost() {
        Post savedPost = postRepository.findById(42).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getComments()).hasSize(0);

        Comment comment = new Comment("Arrow", "great work here");

        savedPost.addComments(comment);
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).get();

        assertThat(updatedPost.getComments()).isNotNull();
    }
    @Test
    @Transactional
    void findAllPostInDescOrderTest() {
        List<Post> allPost = postRepository.findByOrderByDateCreatedDesc();
        assertThat(allPost).isNotEmpty();
        log.info("All post --> {}", allPost);
        assertTrue(allPost.get(0).getDateCreated().isAfter(allPost.get(1).getDateCreated()));
        allPost.forEach(post -> {
            log.info("post Date {}", post.getDateCreated());
        });
    }
}