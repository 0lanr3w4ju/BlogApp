package com.blogapp;

import com.blogapp.services.utils.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogappApplication {

	private final CloudinaryConfig cloudinaryConfig;

	@Autowired
	public BlogappApplication(CloudinaryConfig cloudinaryConfig) {
		this.cloudinaryConfig = cloudinaryConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogappApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinary(){
		return new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudinaryConfig.getCloudName(),
				"api_key", cloudinaryConfig.getApiKey(),
				"api_secret", cloudinaryConfig.getApiSecret()));
	}


}
