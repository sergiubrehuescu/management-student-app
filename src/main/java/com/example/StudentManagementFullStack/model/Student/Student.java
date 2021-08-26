package com.example.StudentManagementFullStack.model.Student;

import com.example.StudentManagementFullStack.enums.Gender;
import com.example.StudentManagementFullStack.enums.LanguageType;
import com.example.StudentManagementFullStack.model.LiveStream;
import com.example.StudentManagementFullStack.model.Session.Session;
import lombok.*;

//import javax.persistence.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Student") //for Hibernate
@Table(name="student")
public class Student {

    @Id
    @SequenceGenerator(name="student_sequence",sequenceName = "student_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_sequence")
    @Column(name="id",updatable = true)
    private Long id;
    @Column(name = "first_name",nullable = true,columnDefinition = "TEXT")
    @NotBlank
    private String firstName;
    @Column(name = "last_name",nullable = true,columnDefinition = "TEXT")
    @NotBlank
    private String lastName;
    @Column(name = "email",nullable = true,columnDefinition = "TEXT")
    @Email(message = "ENTER EMAIL VALID")
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dob;
    @Column(name ="age")
    private Integer age;
    @Column(name ="gender")
    @NotNull
    private Gender gender;
    @Column(name ="languageType")
    @NotNull
    LanguageType languageType;

    //@ElementCollection

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentIdCard studentIdCard;

    //Sa se propage sesiunea in baza de date,
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Session> sessionList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "enrolment",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "liveStream_id",
                    foreignKey = @ForeignKey(name = "enrolment_liveStream_id_fk"))
            )
    private List<LiveStream> liveStreamList = new ArrayList<>();

    public Integer getAge() {
        return (LocalDate.now().getYear()- this.dob.getYear());
    }

//    public void setAge(Integer age) {
//        this.age = (LocalDate.now().getYear()- this.dob.getYear());
//    }
}

