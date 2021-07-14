package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostMapping("/student")
    public String insert(@RequestBody Student student){
        String sql = "INSERT INTO student (name) VALUE (:studentName)";

        Map<String,Object> map = new HashMap<>();
        map.put("studentName",student.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int id = keyHolder.getKey().intValue();
        System.out.println("mysql 自動產生 id為 : "+id);
        return "執行INSERT sql";
    }

    @PostMapping("students/batch")
    public String inserList(@RequestBody List<Student> studentList){

        String sql = "INSERT INTO student (name) VALUE (:studentName)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("studentName",student.getName());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);

        return "執行批次INSERT sql";
    }

    @PutMapping("/student/{studentId}")
    public String update(@RequestBody Student student){
        String sql = "UPDATE student SET name=:studentName WHERE id=:studentId";

        Map<String,Object> map=new HashMap<>();
        map.put("studentName",student.getName());

        namedParameterJdbcTemplate.update(sql,map);

        return "執行UPDATE sql";
    }

    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId){
        String sql = "DELETE FROM student WHERE id=:studentId";

        Map<String,Object> map=new HashMap<>();
        map.put("studentId",studentId);

        namedParameterJdbcTemplate.update(sql,map);
        return "執行DELETE sql";
    }

    @RequestMapping("/students/select")
    public List<Student> select(){
        String sql = "SELECT id , name FROM student";
        Map<String,Object> map=new HashMap<>();

        StudentRowMapper studentRowMapper = new StudentRowMapper();

        List<Student> list = namedParameterJdbcTemplate.query(sql,map,studentRowMapper);

        return list;
    }

}
