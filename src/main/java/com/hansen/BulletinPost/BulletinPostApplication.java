package com.hansen.BulletinPost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hansen.BulletinPost"})
@EntityScan(basePackages = {"com.hansen.BulletinPost.model"})
public class BulletinPostApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BulletinPostApplication.class, args);
	}

}
