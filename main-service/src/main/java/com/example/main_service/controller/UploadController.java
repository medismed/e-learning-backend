package com.example.main_service.controller;

import java.io.File;
import java.io.IOException;

import com.example.main_service.model.User;
import com.example.main_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main_service.model.Course;
import com.example.main_service.repo.CourseRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

@RestController
//@CrossOrigin(origins = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping("/upload")
public class UploadController {

    private static final String UPLOAD_DIR = "/Users/med/Desktop/e-learning/upload";
    private static final String IMAGES_DIR = UPLOAD_DIR + "/images";

    @Autowired
    private  UserRepo userRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ObjectMapper objectMapper; // For JSON deserialization


    @GetMapping("/hi")
    public String hi() {
        return "hi from the main-service";
    }

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/**")
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @RequestPart("file") MultipartFile file,
            @RequestPart("userId") String userId) {

        try {
            // Ensure the "images" directory exists
            File uploadDir = new File(UPLOAD_DIR, "images");
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Could not create upload directory.");
            }

            // Save the file to the "images" directory
            File uploadedFile = new File(uploadDir, file.getOriginalFilename());
            file.transferTo(uploadedFile);

            // Log the userId for debugging
            System.out.println("Received file for user ID: " + userId);
            System.out.println("the path is: " + uploadedFile.getAbsolutePath());

            User user = userRepo.findById(Integer.parseInt(userId)).get();
            user.setImage(uploadedFile.getAbsolutePath());
            userRepo.save(user);
            return ResponseEntity.ok(uploadedFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading image: " + e.getMessage());
        }
    }

    @GetMapping(value = "/get-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage(@RequestParam String link) {
        try {
            Path filePath = Paths.get(link).toAbsolutePath();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestParam String link) {
        try {

            Path filePath = Paths.get(link).toAbsolutePath();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Set headers and return the file as a response
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
