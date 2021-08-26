package com.example.StudentManagementFullStack.service;

import com.example.StudentManagementFullStack.dto.PastStudentStatisticsResponseDto;
import com.example.StudentManagementFullStack.dto.StatisticsOverAllDto;
import com.example.StudentManagementFullStack.dto.StudentStatisticsResponseDto;
import com.example.StudentManagementFullStack.dto.StudentsStatisticsResponseDto;
import com.example.StudentManagementFullStack.model.Session.Session;
import com.example.StudentManagementFullStack.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatisticsService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SessionRepository sessionRepository;

    public StudentStatisticsResponseDto studentStatistics(Long studentId) {
        AtomicInteger deptToday= new AtomicInteger(); //int nu poate fi folosit in forEach
        int deptMonth=0;
        int monthPay=0;
        int remainingPay=0;
        int payed=0;
        StudentStatisticsResponseDto studentStatisticsResponseDto = new StudentStatisticsResponseDto();
        List<Session> sessionDtoListDeptToday = studentService.deptToday(studentId); //double
        List<Session> sessionDtoListDeptMonth = studentService.deptMonth(studentId);
        List<Session> sessionDtoListMonthPay = studentService.monthPay(studentId);
        List<Session> sessionDtoRemainingPay = studentService.remainingPay(studentId);
        List<Session> sessionDtoListPayed = studentService.payed(studentId);

        sessionDtoListDeptToday.forEach(x-> deptToday.addAndGet(getPayedSession(x))); //nu poti folosi un int

        deptMonth += sessionDtoListDeptMonth.stream().mapToInt(this::getPayedSession).sum();

        for (Session sessionDto : sessionDtoListMonthPay) {
            monthPay += getPayedSession(sessionDto);
        }

        for (Session sessionDto : sessionDtoRemainingPay) {
            remainingPay += getPayedSession(sessionDto);
        }

        for (Session sessionDto : sessionDtoListPayed) {
            payed += getPayedSession(sessionDto);
        }

        setPayment(deptToday.get(), deptMonth, monthPay, remainingPay, payed, studentStatisticsResponseDto);
        return studentStatisticsResponseDto;
    }

    public PastStudentStatisticsResponseDto studentStatisticsMonthYear(Long studentId, Month month, Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        int monthPayS=0,remainingPayS=0,payedS=0;
        PastStudentStatisticsResponseDto pastStudentStatisticsResponseDto = new PastStudentStatisticsResponseDto();
        List<Session> sessionDtoListMonthPayS = studentService.monthPayS(studentId,month,year);
        List<Session> sessionDtoListRemainingPayS = studentService.remainingPayS(studentId,month,year);
        List<Session> sessionDtoListPayedS = studentService.payedS(sessionList,studentId,month,year);

        for (Session sessionDto : sessionDtoListMonthPayS) {
            monthPayS += getPayedSession(sessionDto);
        }
        for (Session sessionDto : sessionDtoListRemainingPayS) {
            remainingPayS += getPayedSession(sessionDto);
        }
        for (Session sessionDto : sessionDtoListPayedS) {
            payedS += getPayedSession(sessionDto);
        }

        setPaymentS(monthPayS, remainingPayS, payedS, pastStudentStatisticsResponseDto);
        return pastStudentStatisticsResponseDto;
    }

    public StudentsStatisticsResponseDto studentsStatistics() {
        List<Session> sessionList =sessionRepository.findAll();
        int debts=0,payed=0,income=0;
        StudentsStatisticsResponseDto studentsStatisticsResponseDto = new StudentsStatisticsResponseDto();
        List<Session> debtsTotal = studentService.studentsDebtsTotal(sessionList);
        List<Session> payedTotal = studentService.studentsPayedTotal(sessionList);
        List<Session> monthIncomeTotal = studentService.studentsMonthIncomeTotal(sessionList);

        for (Session sessionDto : debtsTotal) {
            debts += getPayedSession(sessionDto);
        }
        for (Session sessionDto : payedTotal) {
            payed += getPayedSession(sessionDto);
        }
        for (Session sessionDto : monthIncomeTotal) {
            income += getPayedSession(sessionDto);
        }
        setPaymentAllStudents(debts, payed, income, studentsStatisticsResponseDto);
        return studentsStatisticsResponseDto;
    }



    public StudentsStatisticsResponseDto studentsStatisticsMonthYear(Month month, Year year) {
        List<Session> sessionList =sessionRepository.findAll();
        int debts=0,payed=0,totalMonthIncome=0;
        StudentsStatisticsResponseDto studentsStatisticsResponseDto = new StudentsStatisticsResponseDto();
        List<Session> debtsTotalMonth = studentService.studentsDebtsTotaMonth(sessionList,month,year);
        List<Session> payedTotalMonth = studentService.studentsPayedTotalMonth(sessionList,month,year);
        List<Session> monthIncomeTotalMonth = studentService.studentsMonthIncomeTotalMonth(sessionList,month,year);

        for (Session sessionDto : debtsTotalMonth) {
            debts += getPayedSession(sessionDto);
        }
        for (Session sessionDto : payedTotalMonth) {
            payed += getPayedSession(sessionDto);
        }
        for (Session sessionDto : monthIncomeTotalMonth) {
            totalMonthIncome += getPayedSession(sessionDto);
        }
        setPaymentAllStudents(debts, payed, totalMonthIncome, studentsStatisticsResponseDto);
        return studentsStatisticsResponseDto;
    }

    public List<Session> studentsStatisticsMonthYearDashboardPaid(Month month, Year year) {
        List<Session> sessionList =sessionRepository.findAll();

        return studentService.studentsPayedTotalMonth(sessionList,month,year);
    }

    public List<Session> studentsStatisticsMonthYearDashboardUnpaid(Month month, Year year) {
        List<Session> sessionList =sessionRepository.findAll();

        return studentService.studentsMonthIncomeTotal(sessionList);
    }

    private int getPayedSession(Session sessionDto) {
        return sessionDto.getDuration() * sessionDto.getPricePerHour() / 60;
    }

    private void setPayment(int deptToday, int deptMonth, int monthPay, int remainingPay, int payed, StudentStatisticsResponseDto studentStatisticsResponseDto) {
        studentStatisticsResponseDto.setDeptToday(deptToday);
        studentStatisticsResponseDto.setDeptMonth(deptMonth);
        studentStatisticsResponseDto.setMonthPay(monthPay);
        studentStatisticsResponseDto.setRemainingPay(remainingPay);
        studentStatisticsResponseDto.setPayed(payed);
    }

    private void setPaymentS(int monthPayS, int remainingPayS, int payedS, PastStudentStatisticsResponseDto pastStudentStatisticsResponseDto) {
        pastStudentStatisticsResponseDto.setMonthPay(monthPayS);
        pastStudentStatisticsResponseDto.setRemainingPay(remainingPayS);
        pastStudentStatisticsResponseDto.setPayed(payedS);
    }

    private void setPaymentAllStudents(int debts, int payed, int totalMonthIncome, StudentsStatisticsResponseDto studentsStatisticsResponseDto) {
        studentsStatisticsResponseDto.setDebts(debts);
        studentsStatisticsResponseDto.setPayed(payed);
        studentsStatisticsResponseDto.setTotalMonthIncome(totalMonthIncome);
    }

    public StatisticsOverAllDto statisticsOverAll(Month monthName, Year year) {
        StatisticsOverAllDto a= new StatisticsOverAllDto();
        //sets the calendar to current date and time
        Calendar date = Calendar.getInstance();
        //sets the calendar with starting day of week
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //printing of first and last day of th week
        DateFormat dateformat = new SimpleDateFormat("EEE dd/MM/yyyy");
        System.out.println(dateformat.format(date.getTime()));
        for (int i = 0; i<7; i++)
        {
            date.add(Calendar.DATE, 1);
        }
        System.out.println(dateformat.format(date.getTime()));
        //System.out.println(date.add);
        return a;
    }



/*        public StatisticsOverAllDto statisticsOverAll(Month month, Year year) {
        List<Session> sessionList = sessionRepository.findAll();
        StatisticsOverAllDto statisticsOverAllDto = new StatisticsOverAllDto();
        ArrayList<WeekHours> weekHours = new ArrayList<WeekHours>();
        float monthHOurs = 0;
        LocalDate start = LocalDate.of(year.getValue(), month.getValue(), 1);
        LocalDate end = LocalDate.of(year.getValue(), month.getValue(), month.length(((year.getValue() % 4 == 0) && (year.getValue() % 100 != 0)) || (year.getValue() % 400 == 0)));
        Integer[] dayOfWeeks = {start.plusWeeks(0).getDayOfWeek().getValue(), start.plusWeeks(1).getDayOfWeek().getValue(), start.plusWeeks(2).getDayOfWeek().getValue(), start.plusWeeks(3).getDayOfWeek().getValue(),start.plusWeeks(4).getDayOfWeek().getValue()};
        System.out.println(start.getDayOfWeek());
        weekHours.add(new WeekHours(start.plusWeeks(0).toString()+ " " + start.plusDays(6), 0));
        weekHours.add(new WeekHours(start.plusWeeks(1).toString()+ " " + start.plusDays(13), 0));
        weekHours.add(new WeekHours(start.plusWeeks(2).toString()+ " " + start.plusDays(20), 0));
        weekHours.add(new WeekHours(start.plusWeeks(3).toString()+ " " + start.plusDays(27), 0));
        weekHours.add(new WeekHours(start.plusWeeks(4).toString()+ " " + end, 0));
        for (Session session : sessionList) {
            if (session.getLocalDate().getMonth().equals(month) && session.getLocalDate().getYear() == year.getValue()) {
                monthHOurs += (float) session.getDuration() / 60;
                for (int i = 0; i < dayOfWeeks.length-1; i++) {
                    if(session.getLocalDate().getDayOfMonth()>= dayOfWeeks[i] && session.getLocalDate().getDayOfMonth() < dayOfWeeks[++i]) {
                        weekHours.get(i).setHours(weekHours.get(i).getHours()+session.getPricePerHour()/60);
                    }
                }
            }
        }
        statisticsOverAllDto.setMonthHours(monthHOurs);
        statisticsOverAllDto.setWeekHours(weekHours);
        return statisticsOverAllDto;
    }*/

}
