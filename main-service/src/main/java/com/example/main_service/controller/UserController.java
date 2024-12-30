package com.example.main_service.controller;

import java.util.Arrays;
import java.util.List;

import jakarta.ws.rs.QueryParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.main_service.model.Course;
import com.example.main_service.model.User;
import com.example.main_service.model.UserInput;
import com.example.main_service.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @QueryMapping
    public  List<User> getAllUsers(){
        System.out.println("shit we have been here ");
        return userService.getAllUsers();
    }

    @QueryMapping
    public List<Course> getAllCourses(){
        return userService.getAllCourses();
    }

    @QueryMapping
    public User getUserById(@Argument Integer id){
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<Course> getAllCoursesByTeacherId(@Argument Integer teacherId){
        
        System.out.println("before the userService the teacher id was "+ teacherId);
        return userService.getAllCoursesByTeacherId(teacherId);
    }

    @MutationMapping
    public User updateUser(@Argument UserInput user,@Argument Integer id){
        User persistUser = userService.getUserById(id);
        if (user.getPassword() == null)
            BeanUtils.copyProperties(user, persistUser,"password","role");    
        else 
            BeanUtils.copyProperties(user, persistUser,"role");
        return userService.updateUser(persistUser);
    }
    
    @MutationMapping
    public User addUser(@Argument UserInput user){
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return userService.addUser(newUser);
    }
    
}
