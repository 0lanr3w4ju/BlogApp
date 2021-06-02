package com.blogapp.services.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudStorageService {
    void uploadImage(File file, Map<?, ?> imageProperties) throws IOException;

    Map<?, ?> uploadImage(MultipartFile file, Map<?, ?> imageProperties) throws IOException;
}
