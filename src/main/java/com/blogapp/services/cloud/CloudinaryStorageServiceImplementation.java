package com.blogapp.services.cloud;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryStorageServiceImplementation implements CloudStorageService{

    private final Cloudinary cloudinary;
    @Autowired
    public CloudinaryStorageServiceImplementation(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public void uploadImage(File file, Map<?, ?> imageProperties) throws IOException {
        cloudinary.uploader().upload(file, imageProperties);
    }

    @Override
    public Map<?, ?> uploadImage(MultipartFile file, Map<?, ?> imageProperties) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), imageProperties);
    }
}
