package com.blogapp.services.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
//        File file = new File("/home/lanre/blogapp/src/main/resources/static/asset/images/amazon.png");
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
}