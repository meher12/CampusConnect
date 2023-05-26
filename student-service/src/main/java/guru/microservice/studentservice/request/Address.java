package guru.microservice.studentservice.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private long id;
    private String personId;
    private String city;
    private String zipCode;


}
