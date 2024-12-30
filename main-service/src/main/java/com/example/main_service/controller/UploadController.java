package com.example.main_service.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main_service.model.Course;
import com.example.main_service.repo.CourseRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/upload")
public class UploadController {

    private static final String UPLOAD_DIR = "/Users/med/Desktop/e-learning/upload";

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ObjectMapper objectMapper; // For JSON deserialization


    @GetMapping("/hi")
    public String hi() {
        return "hi from the main-service";
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFileAndCourse(
            @RequestPart("file") MultipartFile file,
            @RequestPart("course") String courseJson) {
        try {
            // Deserialize the course JSON into a Course object
            Course course = objectMapper.readValue(courseJson, Course.class);

            // Ensure upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Could not create upload directory.");
            }

            // Save file to the directory
            File uploadedFile = new File(uploadDir, file.getOriginalFilename());
            file.transferTo(uploadedFile);

            // Set the file path in the course and save the course to the database
            course.setLink(uploadedFile.getAbsolutePath());
            courseRepo.save(course);

            return ResponseEntity.ok("Course created and file uploaded successfully: " + uploadedFile.getAbsolutePath());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading file: " + e.getMessage());
        }
    }
}
