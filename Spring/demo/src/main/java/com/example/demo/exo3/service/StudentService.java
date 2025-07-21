package com.example.demo.exo3.service;

import com.example.demo.exo3.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public StudentService() {
        initStudents();
    }

    private void initStudents() {
        students.add(Student.builder()
                .id(UUID.randomUUID())
                .firstName("Squall")
                .lastName("Leonhart")
                .email("squall.leonhart@balamb-garden.university")
                .age(17)
                .build());
        students.add(Student.builder()
                .id(UUID.randomUUID())
                .firstName("Selphie")
                .lastName("Tilmitt")
                .email("selphie.tilmitt@balamb-garden.university")
                .age(17)
                .build());
        students.add(Student.builder()
                .id(UUID.randomUUID())
                .firstName("Zell")
                .lastName("Dincht")
                .email("zell.dincht@balamb-garden.university")
                .age(17)
                .build());
        students.add(Student.builder()
                .id(UUID.randomUUID())
                .firstName("Seifer")
                .lastName("Almasy")
                .email("seifer.almasy@balamb-garden.university")
                .age(17)
                .build());
        students.add(Student.builder()
                .id(UUID.randomUUID())
                .firstName("Irvine")
                .lastName("Kinneas")
                .email("irvine.kinneas@galbadia.university")
                .age(17)
                .build());
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(UUID id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Student addStudent(Student student) {
        boolean emailExists = students.stream()
                .anyMatch(s -> s.getEmail().equals(student.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
        }

        student.setId(UUID.randomUUID());
        students.add(student);
        return student;
    }

    public List<Student> searchStudentsByLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return students.stream()
                .filter(student -> student.getLastName().toLowerCase()
                        .contains(lastName.toLowerCase().trim()))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByLastName(String lastName) {
        return students.stream()
                .filter(s -> s.getLastName().equals(lastName))
                .toList();
    }

    public boolean deleteStudent(UUID id) {
        return students.removeIf(student -> student.getId().equals(id));
    }

    public int getTotalStudents() {
        return students.size();
    }
}
