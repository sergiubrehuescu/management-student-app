package com.example.StudentManagementFullStack.controller;

import com.example.StudentManagementFullStack.dto.PastStudentStatisticsResponseDto;
import com.example.StudentManagementFullStack.dto.StatisticsOverAllDto;
import com.example.StudentManagementFullStack.dto.StudentStatisticsResponseDto;
import com.example.StudentManagementFullStack.dto.StudentsStatisticsResponseDto;
import com.example.StudentManagementFullStack.model.Session.Session;
import com.example.StudentManagementFullStack.service.StatisticsService;
import com.example.StudentManagementFullStack.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("getNotPaidSession/{studentId}")
    public List<Session> getNotPaidSession(@PathVariable Long studentId){
        return  studentService.deptToday(studentId);
    }

    @GetMapping("getNotPaidSessions/{studentId}/{month}/{year}")
    public List<Session> getNotPaidSessionsMonthYear(@PathVariable Long studentId, @PathVariable Month month, @PathVariable Year year){
        return  studentService.getNotPaidSessionsMonthYear(studentId,month,year);
    }

    @GetMapping("getPaidSessions/{studentId}")
    public List<Session> getPaidSessions(@PathVariable Long studentId){
        return  studentService.getPaidSessions(studentId);
    }

    @GetMapping("getPaidSessions/{studentId}/{month}/{year}")
    public List<Session> getPaidSessionsMonthYear(@PathVariable Long studentId,@PathVariable Month month,@PathVariable Year year){
        return  studentService.getPaidSessionsMonthYear(studentId,month,year);
    }

    @GetMapping("student/{studentId}")
    public StudentStatisticsResponseDto studentStatistics(@PathVariable Long studentId){
        return statisticsService.studentStatistics(studentId);
    }

    @GetMapping("student/{studentId}/{month}/{year}")
    public PastStudentStatisticsResponseDto studentStatisticsMonth(@PathVariable Long studentId, @PathVariable Month month, @PathVariable Year year){
        return statisticsService.studentStatisticsMonthYear(studentId,month,year);
    }

    @GetMapping("month")
    public StudentsStatisticsResponseDto studentsStatistics(){
        return statisticsService.studentsStatistics();
    }

    @GetMapping("month/{monthName}/{year}")
    public StudentsStatisticsResponseDto studentsStatistics(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.studentsStatisticsMonthYear(monthName,year);
    }

    @GetMapping("monthDashboardPaid/{monthName}/{year}")
    public List<Session> studentsStatisticsDashboardPaid(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.studentsStatisticsMonthYearDashboardPaid(monthName,year);
    }

    @GetMapping("monthDashboardUnpaid/{monthName}/{year}")
    public List<Session> studentsStatisticsDashboardUnpaid(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.studentsStatisticsMonthYearDashboardUnpaid(monthName,year);
    }
    @GetMapping("hours/{monthName}/{year}")
    public StatisticsOverAllDto statisticsOverAll(@PathVariable Month monthName, @PathVariable Year year){
        return statisticsService.statisticsOverAll(monthName,year);
    }
}
