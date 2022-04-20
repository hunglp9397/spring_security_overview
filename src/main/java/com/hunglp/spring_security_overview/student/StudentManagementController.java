package com.hunglp.spring_security_overview.student;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    ArrayList<Student> STUDENTS = new ArrayList<>() {
        {
            add(new Student(1, "Le Phi HUng"));
            add(new Student(2, "Le Viet Dung"));
        }
    };


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ADMINISTRATOR')")
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void addNewStudent(@RequestBody Student student){
        STUDENTS.add(new Student(student.getId(), student.getName()));
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        STUDENTS.removeIf(s -> (s.getId() == studentId));
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student){
        Optional<Student> existStudent =  STUDENTS.stream()
                .filter(s -> s.getId() == studentId).findFirst();

        existStudent.ifPresent(s -> s.setName(student.getName()));


    }



}
