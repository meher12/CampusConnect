package guru.microservice.studentservice.controller;

import guru.microservice.studentservice.entity.Student;
import guru.microservice.studentservice.entity.StudentInfo;
import guru.microservice.studentservice.request.StudentRequest;
import guru.microservice.studentservice.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/students")
@RefreshScope
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/add")
    public ResponseEntity<Student> registerStudentC(@RequestBody StudentRequest studentRequest){
       Student student = studentService.registerStudent(studentRequest);
        logger.info("New Student registered {}", studentRequest);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentInfo> getDetailStudent(@PathVariable("studentId") Long studentId){
        StudentInfo studentInfo = studentService.getDetailStudent(studentId);
        return new ResponseEntity<>(studentInfo, HttpStatus.OK);
    }
}
