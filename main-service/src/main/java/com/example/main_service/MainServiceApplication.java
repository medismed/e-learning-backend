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
			for (int i  = 0 ; i < 10 ; i++ ){
				User user = new User();
				user.setFirstname("firstname"+i);
				user.setLastname("lastname"+i);
				user.setPassword("1234");
				user.setAddress("address"+i);
				user.setLinkdin("linkdin"+i);
				user.setEmail("user"+i+"@gmail.com");
				user.setRole((i % 2) == 0 ?  "student" : "teacher");
				user.setPhone("phone"+i);
				userRepo.save(user);
			}
			for (int i  = 1 ; i <= 5 ; i++ ){
				Course course = new Course();
				course.setName("course"+i);
				if (i % 2 == 0)
				course.setDescription("Explore the fundamentals of topic, designed to provide practical skills and knowledge for real-world applications.");
				else course.setDescription("Learn the essentials of web development and build dynamic, responsive websites from scratch.");
				course.setTeacherId(i);
				course.setLink("/Users/med/Desktop/e-learning/upload/test.pdf");
				course.setTeacherlastname("teacherlastname"+i);
				courseRepo.save(course);
			}

			for (int i  = 6 ; i <= 8 ; i++ ){
				Course course = new Course();
				course.setName("course"+i);
				course.setDescription("description"+i);
				course.setTeacherId(1);
				course.setLink("/Users/med/Desktop/e-learning/upload/test.pdf");
				courseRepo.save(course);
			}

		};
	}
}
