package com.example.StudentManagementFullStack.repo;

import com.example.StudentManagementFullStack.model.Student.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {

}
