package com.example.main_service;

import com.example.main_service.model.Course;
import com.example.main_service.model.User;
import com.example.main_service.repo.CourseRepo;
import com.example.main_service.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepo userRepo, CourseRepo courseRepo) {
		return args -> {
			for (int i  = 0 ; i < 5 ; i++ ){
				User user = new User();
				user.setFirstname("firstname"+i);
				user.setLastname("lastname"+i);
				user.setEmail("email"+i);
				user.setPassword("password"+i);
				user.setAddress("address"+i);
				user.setLinkdin("linkdin"+i);
				user.setEmail("Email"+i+"@email.com");
				user.setRole("Role"+i);
				user.setPhone("phone"+i);
				userRepo.save(user);
			}
			for (int i  = 1 ; i <= 5 ; i++ ){
				Course course = new Course();
				course.setName("course"+i);
				course.setDescription("description"+i);
				course.setTeacherId(i);
				course.setLink("link"+i);
				courseRepo.save(course);
			}

			for (int i  = 6 ; i <= 8 ; i++ ){
				Course course = new Course();
				course.setName("course"+i);
				course.setDescription("description"+i);
				course.setTeacherId(1);
				course.setLink("link"+i);
				courseRepo.save(course);
			}

		};
	}
}
