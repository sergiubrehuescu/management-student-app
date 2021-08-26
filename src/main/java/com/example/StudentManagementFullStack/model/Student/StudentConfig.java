//package com.example.StudentManagementFullStack.model.Student;
//
//import com.example.StudentManagementFullStack.repo.StudentIdCardRepository;
//import com.example.StudentManagementFullStack.repo.StudentRepository;
//import com.github.javafaker.Faker;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
//@Configuration
//public class StudentConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
//                                        StudentIdCardRepository studentIdCardRepository){
//        return args -> {
//            System.out.println("A");
//
//            Faker faker = new Faker();
//            String firstName="AAABBB";
//            String lastName=faker.name().lastName();
//            String email = String.format("%s.%s@gmail.com",firstName,lastName);
//            Student student = new Student(firstName,lastName,email, LocalDate.now());
//            StudentIdCard studentIdCard = new StudentIdCard(1L,"12345",student);
//            studentRepository.save(student);
//            studentIdCardRepository.save(studentIdCard);
//            studentRepository.findById(1L)
//                    .ifPresent(System.out::println);
//
//         Student s1= new Student(1L,"A","Sergiu","sergiu@ggg", LocalDate.of(2000, Month.JANUARY,22));
//         Student s2= new Student(2L,"B","Sergiu","sergiu@ggag", LocalDate.of(2000, Month.JANUARY,22));
//
//         studentRepository.saveAll(List.of(s1,s2));
//
//            Optional<Student> studentByEmail = studentRepository.findStudentByEmail("sergi2u@gmail.com");
//            studentByEmail
//                 .ifPresentOrElse(System.out::println,
//                         () -> System.out.println("studen not fff"));
//         studentRepository.findStudentsByFirstNameEqualsAndLastNameEquals("A","Sergiu")
//                 .forEach(student -> System.out.println(student.toString()));
//
//            System.out.println(studentRepository.deleteStudentById(4L));
//            System.out.println("DELLLLL");
//
//            generateFakeStudents(studentRepository);
//            sortingStudents(studentRepository);
//            pagination(studentRepository);
//            Faker faker = new Faker();
//            String firstName="AAABBB";
//            String lastName=faker.name().lastName();
//            String email = String.format("%s.%s@gmail.com",firstName,lastName);
//            Student student = new Student(firstName,lastName,email,LocalDate.now());
//            StudentIdCard studentIdCard = new StudentIdCard("12345", student);
//
//            studentIdCardRepository.save(studentIdCard);
//
//            studentRepository.findById(1L)
//                    .ifPresent(System.out::println);
//
//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);
//        };
//    }
//
//    private void pagination(StudentRepository studentRepository) {
//        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("firstName").ascending());
//
//        Page<Student> page = studentRepository.findAll(pageRequest);
//        //List<Student> listStudentsPage = page.get().collect(Collectors.toList());
//        page.get().forEach(student -> System.out.println(student.getEmail()));
//        System.out.println(page);
//        //System.out.println(listStudentsPage);
//    }
//
//    private void sortingStudents(StudentRepository studentRepository) {
//        //Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
//        Sort sort = Sort.by( "firstName").ascending();
//        studentRepository.findAll(sort)
//                .forEach(student -> System.out.println(student.getFirstName()));
//    }
//
//    private void generateFakeStudents(StudentRepository studentRepository) {
//        Faker faker = new Faker();
//        for (int i = 0; i < 20; i++) {
//            String firstName=faker.name().firstName();
//            String lastName=faker.name().lastName();
//            String email = String.format("%s.%s@gmail.com",firstName,lastName);
//            Student student = new Student();
//            studentRepository.save(student);
//        }
//    }
//}
