package com.siri.practice.service;


import com.siri.practice.exception.ValidationException;
import com.siri.practice.model.Student;
import com.siri.practice.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        log.info("Trying to Register Student");
        validateStudent(student);
        Student saveStudent = studentRepository.save(student);
        log.info("Student Registered successfully");
        return saveStudent;
    }

    private void validateStudent(Student student) {
        //Validate student
        if (student == null) {
            throw new ValidationException("Invalid input, student can't be null");
        }
        if (student.getFirstName() == null) {
            throw new ValidationException("Invalid input, missing student firstname ");
        }
        if (student.getLastName() == null) {
            throw new ValidationException("Invalid input, missing student lastname");
        }
        if (student.getGender() == null) {
            throw new ValidationException("Invalid input, missing Student gender");
        }
        if (student.getCourse() == null) {
            throw new ValidationException("Student Course is mandatory");
        }
    }


    public Optional<Student> getStudentById(Long studentId) {
        log.info("Trying to get Student details with Student ID {}", studentId);
        Optional<Student> student = studentRepository.findById(studentId);
        log.info("student info found");
        return student;
    }

    public List<Student> getAllStudents() {
        log.info("Trying to get Student details");
        List<Student> students = studentRepository.findAll();

        if (!CollectionUtils.isEmpty(students)) {
            log.info("Students Size {}", students.size());
        }
        return students;
    }


    public Student updateStudent(Long studentId, Student newStudentData) {
        log.info("Trying to update Student ID {}", studentId);
        if (null == studentId || studentId.equals(0L)) {
            throw new ValidationException("Invalid input, missing student id can't be null or zero");
        }
        if (newStudentData == null) {
            throw new ValidationException("Student details can't be null");
        }
        Optional<Student> oldStudentData = studentRepository.findById(studentId);
        if (!oldStudentData.isPresent()) {
            throw new ValidationException("Student not found");
        }
        Student updatedStudent = oldStudentData.get();
        updatedStudent.setMail(newStudentData.getMail());
        studentRepository.save(updatedStudent);
        log.info("Student ID {} - updated successfully", studentId);

        return newStudentData;
    }

    public void deleteStudent(Long studentId) {
        log.info("Trying to delete with Student ID {}", studentId);
        studentRepository.deleteById(studentId);
        log.info("Student deleted successfully with StudentID: {}", studentId);
    }
}
