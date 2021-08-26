package com.example.StudentManagementFullStack.controller.Student;

import com.example.StudentManagementFullStack.exception.ApiRequestException;
import com.example.StudentManagementFullStack.exception.NotFoundException;
import com.example.StudentManagementFullStack.service.StudentService;
import com.example.StudentManagementFullStack.model.Student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(path="student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("listStudentsAll")
    public List<Student> getStudents() {
        //throw new NotFoundException("stud " + "id"  + " not found");
        return studentService.getStudents();
    }

    @GetMapping("getLanguageType")
    public List<String> getLanguageType() {
        return studentService.getLanguageType();
    }

    @GetMapping("getStudent/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }
    @GetMapping("getStudent/{id}/exception")
    public Student getCustomerException(@PathVariable Long id) {
        throw new ApiRequestException("api req AAA" + id);
    }

    @PostMapping("add")
    public Student addStudent(@Valid  @RequestBody Student student){
        return studentService.addStudent(student);
    }

    @DeleteMapping(path= "remove/{studentId}")
    public void removeStudent(@PathVariable("studentId") Long studentId){
        studentService.removeStudent(studentId);
    }

    @PutMapping(path= "update")
    public Student updateStudent(@RequestBody @Valid Student student) {
        return studentService.updateStudent(student);
    }
}
