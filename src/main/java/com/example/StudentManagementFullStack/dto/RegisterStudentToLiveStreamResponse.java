package com.example.StudentManagementFullStack.dto;

import com.example.StudentManagementFullStack.model.LiveStream;
import com.example.StudentManagementFullStack.model.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentToLiveStreamResponse {

    private Student student;
    private LiveStream liveStream;
}
