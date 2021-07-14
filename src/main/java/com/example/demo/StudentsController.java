package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentsController {

    @Autowired
    private StudentsRepository studentsRepository;

    @PostMapping("/students")
    public String insert(@RequestBody Students students){

        studentsRepository.save(students);

        return "執行資料庫Create操作";
    }
}
