package com.blogapp.web.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDTO {

    private String title;

    private String content;

    private MultipartFile imageFile;
}
