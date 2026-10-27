package com.fireal99.springboot_springmvc;

import com.fireal99.springboot_springmvc.domain.Post;
import com.fireal99.springboot_springmvc.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SpringbootSpringmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSpringmvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PostRepository postRepository) {
		return args -> {
			postRepository.save(new Post("Title 1", new Date()));
			postRepository.save(new Post("Title 2", new Date()));
			postRepository.save(new Post("Title 3", new Date()));
		};
	}
}
