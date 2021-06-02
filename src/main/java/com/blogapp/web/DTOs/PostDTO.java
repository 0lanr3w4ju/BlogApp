package com.blogapp.web.DTOs;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDTO {
    @NotNull(message = "Title can not be null")
    private String title;
    @NotNull(message = "Content should not be null")
    private String content;

    private MultipartFile imageFile;
}
