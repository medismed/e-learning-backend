package com.example.main_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main_service.model.Course;
import com.example.main_service.model.User;
import com.example.main_service.repo.CourseRepo;
import com.example.main_service.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;
    @Autowired
    CourseRepo courseRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    @Override
    public User updateUser(User user) {
        System.out.println("the user i the update user service " + user.getPassword());
        User oldUser = userRepo.findById(user.getId()).orElse(null);
        if (oldUser == null)
            throw new RuntimeException("user not found");
        oldUser.setFirstname(user.getFirstname());
        oldUser.setLastname(user.getLastname());
        oldUser.setEmail(user.getEmail());
        if (!user.getPassword().equals(""))
            oldUser.setPassword(user.getPassword());
        oldUser.setPhone(user.getPhone());
        oldUser.setAddress(user.getAddress());
        oldUser.setLinkdin(user.getLinkdin());
        oldUser.setImage(user.getImage());
        oldUser = userRepo.save(oldUser);

        return oldUser;
    }

    @Override
    public User addUser(User user) {
        User newUser = new User();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setPhone(user.getPhone());
        newUser.setAddress(user.getAddress());
        newUser.setLinkdin(user.getLinkdin());
        newUser.setRole(user.getRole());
        newUser.setImage(user.getImage());
        newUser = userRepo.save(newUser);
        return newUser;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAllCoursesByTeacherId(Integer teacherId) {
        List<Course> courses = courseRepo.findByTeacherId(teacherId);
        return courses;
    }
    
}
