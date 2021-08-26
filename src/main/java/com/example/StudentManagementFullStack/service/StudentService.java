package com.example.StudentManagementFullStack.service;

//import com.example.StudentManagementFullStack.repo.StudentIdCardRepository;

//import com.example.StudentManagementFullStack.model.Student.Student;
import com.example.StudentManagementFullStack.enums.LanguageType;
import com.example.StudentManagementFullStack.exception.NotFoundException;
import com.example.StudentManagementFullStack.model.Session.Session;
import com.example.StudentManagementFullStack.model.Student.Student;
import com.example.StudentManagementFullStack.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

//    private final StudentIdCardRepository studentIdCardRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        //this.studentIdCardRepository = studentIdCardRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent())
            throw new IllegalStateException("Email taken");
        studentRepository.save(student);
        return student;
    }

    public void removeStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new NotFoundException("Student with " + studentId + " doesn't exist");
        }
        studentRepository.deleteById(studentId);
    }

    // use the setter of the entity to see if can update or not
    //we don't have to implement any jpql query.
//    @Transactional
//    public void updateStudent(Long studentId, String name, String email) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new IllegalStateException(
//                        "student with id " + studentId + " dones't exsit"));

//        if(name != null && name.length() > 0 && !Object.equals(student.getName(),name)){
//            student.setName(name);
//        }
//
//        if(email != null & email.length() > 0 && !Object.equals(student.getEmail(),email)){
//            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
//            if(studentOptional.isPresent()){
//                throw new IllegalStateException("email take");
//            }
//            student.setEmail(email);
//        }


    public Student findStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        }
        throw new NotFoundException("Student with " + id  + " not found");
    }

    public Student updateStudent(Student student) {
        Student newStudent = findStudentById(student.getId());
        updateStudentInfo(student,newStudent);
        return studentRepository.save(newStudent);
    }

    private void updateStudentInfo(Student student, Student newStudent) {
        newStudent.setStudentIdCard(student.getStudentIdCard());
        newStudent.setEmail(student.getEmail());
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
    }

    public List<Session> getPaidSessions(Long studentId){
        Student student=findStudentById(studentId);
        List<Session> sessionList = student.getSessionList();
        List<Session> paidSessions = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return paidSessions;
    }

    public List<Session> getNotPaidSessionsMonthYear(Long studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> notPaidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return notPaidSessionsMonthYear;
    }

    public List<Session> getPaidSessionsMonthYear(Long studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> paidSessionsMonthYear = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear() == year.getValue())
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return paidSessionsMonthYear;
    }
    //todo ?????????????
    public List<Session> deptToday(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> deptToday = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().getYear() == LocalDate.now().getYear())
                .filter(session -> !session.isPaid())
                //.map(mapToDto) // .map(session -> sessionMapper.mapToDto(session))
                .collect(Collectors.toList());
        return  deptToday;
    }

    public List<Session> deptMonth(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> deptMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().getYear() == LocalDate.now().getYear())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return deptMonth;
    }

    public List<Session> monthPay(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> monthPay = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().getYear() == LocalDate.now().getYear())
                .collect(Collectors.toList());
        return monthPay;
    }

    public List<Session> remainingPay(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> remainingPay = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().getYear() == LocalDate.now().getYear())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return remainingPay;
    }

    public List<Session> payed(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> payed = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().getYear() == LocalDate.now().getYear())
                .filter(Session::isPaid)
                .collect(Collectors.toList());
        return payed;
    }

    public List<Session> remainingPayS(Long studentId, Month month, Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> remainingPayS = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return remainingPayS;
    }

    public List<Session> monthPayS(Long studentId, Month month,Year year) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Student wit id " + studentId + "was not found"));
        List<Session> sessionList = student.getSessionList();
        List<Session> monthPayS = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return monthPayS;
    }

    public List<Session> payedS(List<Session> sessionList,Long studentId, Month month, Year year) {
        List<Session> payedS = sessionList.stream()
                .filter(session -> session.getStudent().getId().equals(studentId))
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return payedS;
    }

    public List<Session> studentsDebtsTotal(List<Session> sessionList) {
        List<Session> debtsTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return debtsTotal;
    }

    public List<Session> studentsPayedTotal(List<Session> sessionList) {
        List<Session> payedTotal = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return payedTotal;
    }

    public List<Session> studentsMonthIncomeTotal(List<Session> sessionList) {
        List<Session> totalMonthIncome = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(LocalDate.now().getMonth()))
                .filter(session -> session.getLocalDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return totalMonthIncome;
    }

    public List<Session> studentsDebtsTotaMonth(List<Session> sessionList,Month month,Year year) {
        List<Session> debtsTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> !session.isPaid())
                .collect(Collectors.toList());
        return debtsTotalMonth;
    }

    public List<Session> studentsPayedTotalMonth(List<Session> sessionList,Month month,Year year) {
        List<Session> payedTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .filter(session -> session.isPaid())
                .collect(Collectors.toList());
        return payedTotalMonth;
    }

    public List<Session> studentsMonthIncomeTotalMonth(List<Session> sessionList,Month month,Year year) {
        List<Session> monthIncomeTotalMonth = sessionList.stream()
                .filter(session -> session.getLocalDate().getMonth().equals(month))
                .filter(session -> session.getLocalDate().getYear()==year.getValue())
                .collect(Collectors.toList());
        return monthIncomeTotalMonth;
    }

    public List<String> getLanguageType() {
        List<String> listLanguageType = Stream.of(LanguageType.values())
                .map(LanguageType::name)
                .collect(Collectors.toList());

        return listLanguageType;
    }


//    public Student getStudent(Long id) {
//        return getStudents().stream()
//                .filter(student -> student.getId().equals(id)).findFirst()
//                .orElseThrow(() -> new NotFoundException("Student with " + id  + " not found"));
//    }
}


