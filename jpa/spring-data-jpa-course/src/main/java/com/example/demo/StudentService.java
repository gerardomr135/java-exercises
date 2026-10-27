package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final CountryRepository countryRepository;
    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository, CountryRepository countryRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.countryRepository = countryRepository;
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public void addSubjectByStudentId(Long id, Subject subject) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            return;
        }

        student.get().getSubjects().add(subject);
    }

    public void insertion() {
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("Math"),
                new Subject("History"),
                new Subject("Physics"),
                new Subject("Chemistry")
        ));
        subjectRepository.saveAll(subjects);

        Student storedStudent = save(new Student(
                "Gerardo",
                "Morales Ramos",
                "email@email.com",
                20,
                "Hello, world!",
                new ArrayList<>()
        ));

        countryRepository.save(new Country("Mexico"));
    }

    public void test(){
        var optStudent = studentRepository.findByEmail("email@email.com");
        if (optStudent.isEmpty()) {
            return;
        }

        var student = optStudent.get();

        var optSubject = subjectRepository.findByName("Math");
        if(optSubject.isEmpty()){return;}

        var subject = optSubject.get();

        student.getSubjects().add(subject);
        optSubject.get().getStudents().add(student);
    }
}
