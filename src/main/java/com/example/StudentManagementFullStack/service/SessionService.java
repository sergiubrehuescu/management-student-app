package com.example.StudentManagementFullStack.service;

import com.example.StudentManagementFullStack.exception.NotFoundException;
import com.example.StudentManagementFullStack.model.Session.Session;
import com.example.StudentManagementFullStack.repo.SessionRepository;
import com.example.StudentManagementFullStack.repo.StudentRepository;
import com.example.StudentManagementFullStack.model.Student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class SessionService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    SessionRepository sessionRepository;


    public Session addSession(Session session, Long id) {
        Student student = studentService.findStudentById(id);
        student.getSessionList().add(session);
        session.setStudent(student);
        sessionRepository.save(session);
        return session;
    }

    public Session removeSession(Long idSession) {
        Session session=findSessionById(idSession);
        sessionRepository.deleteById(idSession);
        return session;
    }

    public Session updateSession(Session session) {
        Session newSession = findSessionById(session.getIdSession());
        updateSessionInfo(session, newSession);
        return sessionRepository.save(newSession);
    }

    public Session findSessionById(Long idSession) {
        Optional<Session> session = sessionRepository.findById(idSession);
        if(session.isPresent()){
            return session.get();
        }
        throw new NotFoundException("Session with " + idSession + " is not found in the list of sessions");
    }



    public Session paySession(Long idSession) {
        Session session = findSessionById(idSession);
        session.setPaid(true);
        sessionRepository.save(session);
        return session;
    }

    public Session unpaySession(Long id) {
        Session session = findSessionById(id);
        session.setPaid(false);
        sessionRepository.save(session);
        return session;
    }

    public List<Session> addSessionsRecurent(Long studentId, LocalDate localDate, Session session) {
        Student student = studentService.findStudentById(studentId);

        for (int i = 0; i < DAYS.between(localDate, localDate.plusMonths(3)); i+=7) {
            System.out.println(localDate.plusDays(i));
            Session newSession = new Session(session.getIdSession(),session.getLanguageProgramming(),session.getDuration(),session.getPricePerHour(),session.isPaid(),localDate.plusDays(i),session.isRecurrent(),student);
            student.getSessionList().add(newSession);
        }
        studentRepository.save(student);
        return student.getSessionList();
    }

    private void updateSessionInfo(Session session, Session newSession) {
        newSession.setIdSession(session.getIdSession());
        newSession.setStudent(session.getStudent());
        newSession.setDuration(session.getDuration());
        newSession.setLanguageProgramming(session.getLanguageProgramming());
        newSession.setPaid(session.isPaid());
        newSession.setLocalDate(session.getLocalDate());
        newSession.setPricePerHour(session.getPricePerHour());
    }
}
