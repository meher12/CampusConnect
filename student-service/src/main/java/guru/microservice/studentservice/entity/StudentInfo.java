package guru.microservice.studentservice.entity;

import guru.microservice.studentservice.request.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentInfo {

    private Student student;
    private Address address;
}
