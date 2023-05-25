package guru.microservice.addressservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {


    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "address_id_sequence")
    @SequenceGenerator(name = "address_id_sequence", sequenceName = "address_id_sequence")
    @Id
    private Long id;
    private String personId;
    private String city;
    private String zipCode;

    public Address(String personId, String city, String zipCode) {
        this.personId = personId;
        this.city = city;
        this.zipCode = zipCode;
    }
}


