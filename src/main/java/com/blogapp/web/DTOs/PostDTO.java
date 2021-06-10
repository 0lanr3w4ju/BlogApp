package com.blogapp.web.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDTO {
    @NotEmpty(message = "Title can not be empty")
    private String title;
    @NotEmpty(message = "Content should not be empty")
    private String content;

    private MultipartFile imageFile;
}
