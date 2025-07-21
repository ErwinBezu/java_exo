package com.example.demo.exo3.controller;

import com.example.demo.exo3.model.Student;
import com.example.demo.exo3.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("totalStudents", studentService.getTotalStudents());
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String register(Student student){
        studentService.addStudent(student);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getStudent(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable UUID id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "detail";
        } else {
            return "redirect:/list";
        }
    }

    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String searchEtudiant(@RequestParam String nom, Model model) {
        List<Student> students = studentService.searchStudentsByLastName(nom);
        model.addAttribute("students", students);
        model.addAttribute("searchTerm", nom.trim());
        return "result-search";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable UUID id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "edit";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable UUID id, Student student, Model model) {
        try {
            student.setId(id);
            studentService.updateStudent(student);
            return "redirect:/detail/" + id;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            return "edit";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return "redirect:/list";
    }
}
