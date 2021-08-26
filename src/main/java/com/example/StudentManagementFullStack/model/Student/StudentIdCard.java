package com.example.StudentManagementFullStack.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="StudentIdCard")
@Table(name="student_id_card",uniqueConstraints = {@UniqueConstraint(name="student_id_card_number_unique",columnNames = "card_number")})
public class StudentIdCard {

    @Id
    @SequenceGenerator(name="student_card_id_sequence",sequenceName = "student_card_id_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_card_id_sequence")
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="card_number",nullable = false,length = 5)
    private String cardNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "studentIdCard",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Student student;

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
