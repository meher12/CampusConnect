package guru.microservice.studentservice.service;

import guru.microservice.studentservice.entity.Student;
import guru.microservice.studentservice.entity.StudentInfo;
import guru.microservice.studentservice.repository.StudentRepository;
import guru.microservice.studentservice.request.Address;
import guru.microservice.studentservice.request.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Student registerStudent(StudentRequest studentRequest) {

        Student student = Student.builder()
                .firstName(studentRequest.firstName())
                .lastName(studentRequest.lastName())
                .email(studentRequest.email())
                .zipCode(studentRequest.zipCode())
                .build();

        studentRepository.saveAndFlush(student);
        Map<String, String> uriVariable = new HashMap<>();
        uriVariable.put("personId", String.valueOf(student.getId()));
        uriVariable.put("zipCode", student.getZipCode());

        //RestTemplate
        restTemplate.getForEntity("http://localhost:8281/api/v1/address/add/{personId}/{zipCode}", Address.class, uriVariable);
        return student;

    }

    public StudentInfo getDetailStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id" + studentId));
        Address address =  restTemplate.getForObject("http://localhost:8281/api/v1/address/{personId}", Address.class, String.valueOf(student.getId()));
        return new StudentInfo(student,address);
    }
}
