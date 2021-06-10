package com.blogapp.services.cloud;

import com.blogapp.web.DTOs.PostDTO;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.PATH;

@SpringBootTest
@Slf4j
class CloudinaryStorageServiceImplementationTest {

    @Autowired
    CloudStorageService cloudStorageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadImage() {
        //define file
        File file = new File("/home/lanre/Pictures/ScreenShot/Screenshot_20210324-125925.png");

        assertThat(file.exists()).isTrue();
        Map<Object, Object> params = new HashMap<>();
//        params.put("public_id", "/");

        try {
            cloudStorageService.uploadImage(file, params);
        } catch (IOException error) {
            log.info("Error occurred --> {}", error.getMessage());
        }
    }
    @Test
    void uploadMultiPartImageFileTest() throws IOException {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Test");
        postDTO.setContent("Test");

        Path path = Paths.get("/home/lanre/Pictures/ScreenShot/xerox1.png");
        assertThat(path.toFile().exists());

        MultipartFile multipartFile = new MockMultipartFile("xerox1.png",
                "xerox1.png",
                "img/png", Files.readAllBytes(path));
        log.info("Multipart Object Created --> {}", multipartFile);
        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();
        postDTO.setImageFile(multipartFile);

        log.info("File name --> {}", postDTO.getImageFile().getOriginalFilename());
        cloudStorageService.uploadImage(multipartFile, ObjectUtils.asMap("public_id", "blogapp/"+extractFileName(Objects.requireNonNull(postDTO.getImageFile().getOriginalFilename()))));
        assertThat(postDTO.getImageFile().getOriginalFilename()).isEqualTo("xerox1.png");
    }

    private String extractFileName(String fileName) {
        return fileName.split("\\.")[0];
    }
}