package com.example.StudentManagementFullStack.service;


import com.example.StudentManagementFullStack.dto.RegisterStudentToLiveStreamRequest;
import com.example.StudentManagementFullStack.dto.RegisterStudentToLiveStreamResponse;
import com.example.StudentManagementFullStack.exception.NotFoundException;
import com.example.StudentManagementFullStack.model.LiveStream;
import com.example.StudentManagementFullStack.model.Student.Student;

import com.example.StudentManagementFullStack.repo.LiveStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LiveStreamService {

    @Autowired
    LiveStreamRepository liveStreamRepository;

    @Autowired
    private StudentService studentService;

    public LiveStream createLiveStream(LiveStream liveStreamRequest) {
        LiveStream liveStream = new LiveStream();
        liveStream.setLocation(liveStreamRequest.getLocation());
        liveStream.setTopicLiveStream(liveStreamRequest.getTopicLiveStream());
        liveStream.setExpireRegisterDate(liveStreamRequest.getExpireRegisterDate());
        liveStream.setLanguageType(liveStreamRequest.getLanguageType());
        liveStreamRepository.save(liveStream);
        return liveStream;
    }

    private LiveStream findLiveStreamById(Long id) {
        Optional<LiveStream> liveStream = liveStreamRepository.findById(id);
        if(liveStream.isPresent()){
            return liveStream.get();
        }
        throw new NotFoundException("LiveStream with id " + id + " was not found");
 }


    public RegisterStudentToLiveStreamResponse registerToLiveStream(RegisterStudentToLiveStreamRequest registerStudentToLiveStreamRequest) {
        LiveStream liveStream =findLiveStreamById(registerStudentToLiveStreamRequest.getLiveStreamId());
        Student student = studentService.findStudentById(registerStudentToLiveStreamRequest.getStudentId());
        checkRegisterLiveStream(liveStream, student);

        return new RegisterStudentToLiveStreamResponse(student,liveStream);
    }

    private void checkRegisterLiveStream(LiveStream liveStream, Student student) {
        if(liveStream.getExpireRegisterDate().isAfter(LocalDate.now())){
            if((!liveStream.getStudentList().contains(student) && liveStream.getExpireRegisterDate().isAfter(LocalDate.now()))){
                liveStream.getStudentList().add(student);
                student.getLiveStreamList().add(liveStream);
                liveStreamRepository.save(liveStream);
            }
            else throw new NotFoundException("Student with id " + student.getId() + " is already part of the LiveStream");
        }
        else throw new NotFoundException("Livestream with id " + liveStream.getId() + " has already expired date and cannot get register");
    }

    public LiveStream deleteLiveStream(Long id) {
        LiveStream liveStream = findLiveStreamById(id);
        List<Student> students = liveStream.getStudentList();

        students.stream().forEach(student -> student.getLiveStreamList().remove(liveStream));
        liveStreamRepository.delete(liveStream);
        return liveStream;
    }

    public LiveStream updateLiveStream(LiveStream liveStream) {
        LiveStream newLiveStream = findLiveStreamById(liveStream.getId());
        setUpdateLiveStream(liveStream, newLiveStream);
        return liveStreamRepository.save(newLiveStream);
    }

    private void setUpdateLiveStream(LiveStream liveStream, LiveStream newLiveStream) {
        newLiveStream.setLanguageType(liveStream.getLanguageType());
        newLiveStream.setCreatedAt(liveStream.getCreatedAt());
        newLiveStream.setLocation(liveStream.getLocation());
        newLiveStream.setTopicLiveStream(liveStream.getTopicLiveStream());
        newLiveStream.setExpireRegisterDate(liveStream.getExpireRegisterDate());
    }

    public LiveStream getLiveStream(Long id) {
        return  findLiveStreamById(id);
    }

    public RegisterStudentToLiveStreamResponse unregisterToLiveStream(RegisterStudentToLiveStreamRequest registerStudentToLiveStreamRequestDto) {
        LiveStream liveStream =findLiveStreamById(registerStudentToLiveStreamRequestDto.getLiveStreamId());
        Student student = studentService.findStudentById(registerStudentToLiveStreamRequestDto.getStudentId());
        checkUnregisterLiveStream(liveStream, student);

        return new RegisterStudentToLiveStreamResponse(student,liveStream);
    }

    private void checkUnregisterLiveStream(LiveStream liveStream, Student student) {
        if(liveStream.getStudentList().contains(student)){
            liveStream.getStudentList().remove(student);
            student.getLiveStreamList().remove(liveStream);
            liveStreamRepository.save(liveStream);
        }
        else throw new NotFoundException("Student with id" + student.getId() + "is not part of the LiveStream anyway");
    }

}
