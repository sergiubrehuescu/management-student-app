package com.example.StudentManagementFullStack.controller;

import com.example.StudentManagementFullStack.model.Session.Session;
import com.example.StudentManagementFullStack.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) { this.sessionService = sessionService; }

    @PostMapping(path="addToStudent/{id}")
    public Session addSessionToStudent(@PathVariable Long id ,@RequestBody Session session){
        return sessionService.addSession(session,id);
    }

    @GetMapping(path = "getSession/{id}")
    public Session getSession(@PathVariable Long id){
        return sessionService.findSessionById(id);
    }

    @DeleteMapping(path="remove/{idSession}")
    public Session removeSession(@PathVariable Long idSession){
        return sessionService.removeSession(idSession);
    }
    @PutMapping("update")
    public Session updateSession(@RequestBody @Valid Session session) {
        return sessionService.updateSession(session);
    }

    @PutMapping("pay/{id}")
    public Session paySession(@PathVariable @Valid Long id){
        return sessionService.paySession(id);
    }

    @PutMapping("unpay/{id}")
    public Session unpaySession(@PathVariable @Valid Long id){
        return sessionService.unpaySession(id);
    }

    @PutMapping("addRecurent/{studentId}/{localDate}")
    public List<Session> addSessionsRecurent(@PathVariable Long studentId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, @RequestBody Session session){
        return sessionService.addSessionsRecurent(studentId,localDate,session);
    }
}
