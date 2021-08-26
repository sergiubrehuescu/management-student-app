package com.example.StudentManagementFullStack.repo;

import com.example.StudentManagementFullStack.model.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//package com.example.StudentManagementFullStack.repo;
//
//import com.example.StudentManagementFullStack.model.Student.Student;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//

//@Transactional(readOnly = true) // acid
    @Repository
    public interface StudentRepository extends JpaRepository<Student,Long> {
//
//    //@Query("SELECT s FROM Student s WHERE s.email =?1")
//    //Spring Data JPA will generate a QUERY that will find a student with a given email
//    //We have an optional because the email is unique in our database and not a List.
//    @Query("SELECT s from Student s WHERE s.email = ?1") //1 is because i have one one argument
//    //Best practice to have query in every method
    Optional<Student> findStudentByEmail(String email);
//    @Query("SELECT s from Student s WHERE s.firstName = ?1 and s.lastName = ?2")
//    List<Student> findStudentsByFirstNameEqualsAndLastNameEquals(String firstName, String lastName);
//
//    @Query(value = "SELECT * FROM student  WHERE first_name = ?1 AND last_name = ?2",nativeQuery = true)
//    List<Student> findStudentsByFirstNameEqualsAndLastNameEqualsNative(String firstName, String lastName);
//
//    @Query(value = "SELECT * FROM student  WHERE first_name = :firstName AND last_name = :lastName",nativeQuery = true)
//    List<Student> findStudentsByFirstNameEqualsAndLastNameEqualsNative2(@Param("firstName") String firstName,@Param("lastName") String lastName);
//
//
//   // @Transactional //use when you modify data ,
//
//    @Modifying // this tell to SPRING DATA that the query doens't have to map anything from the database into Entities
//    //and instead we are just modifying some data in our table
//    @Query("DELETE FROM Student u WHERE u.id = ?1")
//    int deleteStudentById(Long id);
//
}
