package com.example.StudentManagementFullStack.controller.Student;

import com.example.StudentManagementFullStack.model.Student.StudentIdCard;
import com.example.StudentManagementFullStack.service.StudentIdCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class StudentIdCardController {

    @Autowired
    private StudentIdCardService studentIdCardService;

    @GetMapping(path = "studentIdCard/{studentId}")
    public StudentIdCard findStudentIdCard(@PathVariable Long studentId){
        StudentIdCard studentIdCard = studentIdCardService.findStudentIdCard(studentId);
        return studentIdCard;
    }
}
