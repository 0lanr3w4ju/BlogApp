package com.blogapp.data.repositories;

import com.blogapp.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByTitle(String title);

    @Query("select post from Post post where post.title = ?1")
    Post findByPostTitle(String title);

    @Query("select post from Post post where post.title = :title")
    Post findPostByTitle(@Param("title") String title);

    List<Post> findByOrderByDateCreatedDesc();
}
