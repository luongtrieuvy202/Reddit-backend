package com.programming.technie.springredditclone;

import com.programming.technie.springredditclone.config.SwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.annotations.SwaggerDefinition;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SpringRedditCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(com.programming.technie.springredditclone.SpringRedditCloneApplication.class, args);
	}

}
