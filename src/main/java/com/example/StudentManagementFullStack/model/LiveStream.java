package com.example.StudentManagementFullStack.model;

import com.example.StudentManagementFullStack.enums.LanguageType;
import com.example.StudentManagementFullStack.model.Student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "liveStream")
@Table(name = "livestreams")
public class LiveStream {

    @Id
    @SequenceGenerator(name="liveStream_sequence",sequenceName = "liveStream_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "liveStream_sequence")
    @Column(name="id",updatable = false)
    private Long id;
    @Column(name="location",nullable = false , columnDefinition = "TEXT")
    private String location;
    @Column(name = "created_at",nullable = false)
    private LocalDate createdAt=LocalDate.now();
    @Column(name = "expire_register_date",nullable = false)
    private LocalDate expireRegisterDate;
    //todo topics in the list of the student , cannot registrr to event if is not part of his own list
    //todo with entity
    @Column(name = "language_type",nullable = false)
    private LanguageType languageType;
    @Column(name="topic_live_stream",nullable = false , columnDefinition = "TEXT")
    private String topicLiveStream;

    @JsonIgnore
    @ManyToMany(mappedBy = "liveStreamList")
    private List<Student> studentList = new ArrayList<>();

    @Override
    public String toString() {
        return "LiveStream{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", createdAt=" + createdAt +
                ", expireRegisterDate=" + expireRegisterDate +
                ", languageType=" + languageType +
                ", topicLiveStream='" + topicLiveStream + '\'' +
                ", studentList=" + studentList +
                '}';
    }
}
