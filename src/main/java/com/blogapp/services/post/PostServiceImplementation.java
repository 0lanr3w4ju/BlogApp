package com.blogapp.services.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repositories.PostRepository;
import com.blogapp.services.cloud.CloudStorageService;
import com.blogapp.web.DTOs.PostDTO;
import com.blogapp.web.exceptions.PostNotFoundException;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CloudStorageService cloudStorageService;

    @Override
    public Post addPost(PostDTO postDTO) throws PostObjectIsNullException {

        if (postDTO == null) {
            throw new PostObjectIsNullException("Post can not be null!");
        }

        Post post = new Post();

        if (postDTO.getImageFile() != null && !postDTO.getImageFile().isEmpty()) {
//            Map<Object, Object> params = new HashMap<>();
//            params.put("public_id", "blogapp/" + postDTO.getImageFile().getName());
//            params.put("overwrite", true);
//            log.info("Image parameters --> {}", params);
            try {
                Map<?, ?> uploadResult =
                        cloudStorageService.uploadImage(postDTO.getImageFile(), ObjectUtils.asMap("public_id",
                        "blogapp/" + extractFileName(Objects.requireNonNull(postDTO.getImageFile().getOriginalFilename()))));
                post.setCoverImageURL(String.valueOf(uploadResult.get("url")));
                log.info("Image url --> {}", uploadResult.get("url"));
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.map(postDTO, post);
//
//        log.info("Post object after mapping --> {}", post);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        try {
            return postRepository.save(post);
        } catch (DataIntegrityViolationException error) {
            log.info("Exception occurred --> {}", error.getMessage());
            throw error;
        }
    }
    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
    @Override
    public List<Post> findAllPostInDescOrder() {
        return postRepository.findByOrderByDateCreatedDesc();
    }
    @Override
    public Post updatePost(PostDTO postDTO) {
        return null;
    }
    @Override
    public Post findByID(Integer ID) throws PostNotFoundException {
        if (ID == null) {
            throw new NullPointerException("Post Id can not be null");
        }

        Optional<Post> post = postRepository.findById(ID);

        if (post.isPresent()) {
            return post.get();
        } else {
            throw new PostNotFoundException("Post with ID --> {}");
        }
    }
    @Override
    public void deletePostByID(Integer ID) {}
    @Override
    public Post addCommentToPost(Integer ID, Comment comment) {
        return null;
    }

    private String extractFileName(String fileName) {
        return fileName.split("\\.")[0];
    }
}
