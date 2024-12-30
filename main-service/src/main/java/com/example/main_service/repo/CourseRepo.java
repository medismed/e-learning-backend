package com.example.main_service.repo;

import com.example.main_service.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    List<Course> findByTeacherId(Integer teacherId);
}
