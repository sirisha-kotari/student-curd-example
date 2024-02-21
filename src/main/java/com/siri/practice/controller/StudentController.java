package com.siri.practice.controller;

import com.siri.practice.model.Student;
import com.siri.practice.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Student student) {
        log.info("Student details {}", student);
        studentService.createStudent(student);
        return new ResponseEntity<>("Student registered successfully!...", HttpStatus.OK);
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();

    }

    @GetMapping("/student/{studentId}")
    public Optional<Student> getStudentByID(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/student/{studentId}")
    private ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>("Student deleted successfully!...", HttpStatus.OK);
    }


    @PatchMapping("/update/{studentId}")
    private ResponseEntity<String> update(@PathVariable("studentId") Long studentId, @RequestBody Student newStudentData) {

        studentService.updateStudent(studentId, newStudentData);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("Student Updated successfully", HttpStatus.OK);

        return responseEntity;

    }

}
