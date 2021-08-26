package com.example.StudentManagementFullStack.service;

import com.example.StudentManagementFullStack.exception.NotFoundException;
import com.example.StudentManagementFullStack.model.Student.StudentIdCard;
import com.example.StudentManagementFullStack.repo.StudentIdCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class StudentIdCardService {

    @Autowired
    private StudentIdCardRepository studentIdCardRepository;

    public StudentIdCard findStudentIdCard(Long studentId) {
        Optional<StudentIdCard> studentIdCard = studentIdCardRepository.findById(studentId);
        if(studentIdCard.isPresent()){
            return studentIdCard.get();
        }
        throw new NotFoundException("Couldn't find any card for the student with id " + studentId);
    }
}
