package com.example.StudentManagementFullStack.repo;

import com.example.StudentManagementFullStack.model.Session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
}
