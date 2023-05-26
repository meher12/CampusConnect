package guru.microservice.studentservice.request;


import lombok.Data;

@Data
public class Address {

    private long id;
    private String personId;
    private String city;
    private String zipCode;


}
