package com.example.main_service.service;

import com.example.main_service.model.Course;
import com.example.main_service.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<Course> getAllCourses();
    User updateUser(User user);
    User addUser(User user);
    User getUserById(Integer id);
    List<Course> getAllCoursesByTeacherId(Integer teacherId);
}
