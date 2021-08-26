package com.example.StudentManagementFullStack.model.Session;

import com.example.StudentManagementFullStack.model.Student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Session")
@Table(name = "sessions")
public class Session {

    @Id
    @SequenceGenerator(name="session_sequence",sequenceName = "session_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "session_sequence")
    @Column(name="id",updatable = false)
    private Long idSession;
    @Column(name = "language_programming",nullable = false,columnDefinition = "TEXT")
    private String languageProgramming;
    @Column(name = "duration",nullable = false,columnDefinition = "TEXT")
    private Integer duration;
    @Column(name = "price_per_hour")
    private Integer pricePerHour;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "local_date",nullable = false,columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDate localDate;
    @Column(name = "recurrent")
    private boolean recurrent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false,referencedColumnName = "id",foreignKey = @ForeignKey(name = "student_session_fk"))
    private Student student;

    @Override
    public String toString() {
        return "Session{" +
                "idSession=" + idSession +
                ", languageProgramming='" + languageProgramming + '\'' +
                ", duration=" + duration +
                ", pricePerHour=" + pricePerHour +
                ", paid=" + paid +
                ", localDate=" + localDate +
                ", recurrent=" + recurrent +
                ", student=" + student +
                '}';
    }
}
