package com.siri.practice.service;


import com.siri.practice.exception.ValidationException;
import com.siri.practice.model.Student;
import com.siri.practice.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    public void testCreateStudentWhenMissingGender() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email");
        student.setCourse("ECE");
        student.setLastName("Kotari");
        student.setFirstName("Sirisha");
        Throwable exception = assertThrows(ValidationException.class,
                () -> {
                    studentService.createStudent(student);
                });
        assertTrue(exception.getMessage().contains("Invalid input, missing Student gender"));
    }

    @Test
    public void testCreateStudentWhenMissingNames() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email");
        student.setFirstName("Sirisha");
        Throwable exception = assertThrows(ValidationException.class,
                () -> {
                    studentService.createStudent(student);
                });
        assertTrue(exception.getMessage().contains("Invalid input, missing student lastname"));

        Student student1 = new Student();
        student1.setStudentId(123L);
        student1.setMail("email");
        student1.setLastName("Sirisha");
        Throwable exception1 = assertThrows(ValidationException.class,
                () -> {
                    studentService.createStudent(student1);
                });
        assertTrue(exception1.getMessage().contains("Invalid input, missing student firstname"));
    }


    @Test
    public void testCreateStudentWhenMissingCourse() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email");
        student.setGender("ECE");
        student.setLastName("Kotari");
        student.setFirstName("Sirisha");
        Throwable exception = assertThrows(ValidationException.class,
                () -> {
                    studentService.createStudent(student);
                });
        assertTrue(exception.getMessage().contains("Student Course is mandatory"));
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email");
        student.setGender("ECE");
        student.setCourse("ECE");
        student.setLastName("Kotari");
        student.setFirstName("Sirisha");
        when(studentRepository.save(student)).thenReturn(student);
        Student dbStudent = studentService.createStudent(student);
        assertEquals(dbStudent.getFirstName(), student.getFirstName());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email");
        student.setGender("ECE");
        student.setCourse("ECE");
        student.setLastName("Kotari");
        student.setFirstName("Sirisha");
        when(studentRepository.findById(123l)).thenReturn(Optional.of(student));
        Optional<Student> student1 = studentService.getStudentById(123l);
        assertNotNull(student1);
        assertTrue(student1.isPresent());
        assertEquals(student1.get().getFirstName(), student.getFirstName());

    }


    @Test
    public void testUpdatedStudentMissingStudentId() {

        Throwable exception = assertThrows(ValidationException.class,
                () -> {
                    studentService.updateStudent(0L, null);
                });
        assertTrue(exception.getMessage().contains("Invalid input, missing student id can't be null or zero"));
        Throwable exception1 = assertThrows(ValidationException.class,
                () -> {
                    studentService.updateStudent(null, null);
                });
        assertTrue(exception1.getMessage().contains("Invalid input, missing student id can't be null or zero"));

    }

    @Test
    public void testUpdatedStudentMissingStudentDetails() {

        Throwable exception = assertThrows(ValidationException.class,
                () -> {
                    studentService.updateStudent(1L, null);
                });
        assertTrue(exception.getMessage().contains("Student details can't be null"));

    }

    @Test
    public void testUpdatedStudentNotFound() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email");
        student.setGender("ECE");
        student.setCourse("ECE");
        student.setLastName("Kotari");
        student.setFirstName("Sirisha");
        Throwable exception = assertThrows(ValidationException.class,
                () -> {
                    studentService.updateStudent(1L, student);
                });
        assertTrue(exception.getMessage().contains("Student not found"));

    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setStudentId(123L);
        student.setMail("email@yahoo.com");
        student.setGender("ECE");
        student.setCourse("ECE");
        student.setLastName("Kotari");
        student.setFirstName("Sirisha");

        Student dbStudent = new Student();
        dbStudent.setStudentId(123L);
        dbStudent.setMail("email@gmail.com");
        dbStudent.setGender("ECE");
        dbStudent.setCourse("ECE");
        dbStudent.setLastName("Kotari");
        dbStudent.setFirstName("Sirisha");

        when(studentRepository.findById(123l)).thenReturn(Optional.of(dbStudent));
        Student updatedStudent = studentService.updateStudent(123L, student);
        assertEquals(updatedStudent.getMail(), student.getMail());
    }

}
