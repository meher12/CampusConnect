package guru.microservice.addressservice.service;

import guru.microservice.addressservice.entity.Address;
import guru.microservice.addressservice.repository.AddressRepository;
import guru.microservice.addressservice.response.ResponseAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    // get address by zipCode
    public ResponseAddress registerAddressByZipCode(String personId, String zipCode) {

        // the optimized version:
        String city="";
        switch (zipCode) {
            case "0000":
                city = "Tunis";
                break;
            case "0001":
                city = "Ariana";
                break;
            case "0002":
                city = "Ben Arous";
                break;
            default:
                LOGGER.info("There is no address registered");
                //return new ResponseAddress(personId, zipCode);
        }

        Address address = Address.builder()
                .personId(personId)
                .city(city)
                .zipCode(zipCode)
                .build();
        addressRepository.saveAndFlush(address);
        LOGGER.info("Address is registered {} for student id {}", city + " -- " + zipCode, personId);

        return new ResponseAddress(personId, zipCode);

        // the old version:
       /* if (zipCode.equals("0000")) {
            Address address = Address.builder()
                    .personId(personId)
                    .city("Tunis")
                    .zipCode("0000")
                    .build();
            addressRepository.saveAndFlush(address);
            String studentId = address.getPersonId();
            String studentCity = address.getCity();
            String studentZipcode = address.getZipCode();
            LOGGER.info("Address is registered {} for student id {}", studentCity + " -- " + studentZipcode, studentId);
        } else if (zipCode.equals("0001")) {
            Address address =  Address.builder()
                    .personId(personId)
                    .city("Ariana")
                    .zipCode("0001")
                    .build();
            addressRepository.saveAndFlush(address);
            String studentId = address.getPersonId();
            String studentCity = address.getCity();
            String studentZipcode = address.getZipCode();
            LOGGER.info("Address is registered {} for student id {}", studentCity + " -- " + studentZipcode, studentId);
        } else if (zipCode.equals("0002")) {
            Address address =  Address.builder()
                    .personId(personId)
                    .city("Ben Arous")
                    .zipCode("0002")
                    .build();
            addressRepository.saveAndFlush(address);
            String studentId = address.getPersonId();
            String studentCity = address.getCity();
            String studentZipcode = address.getZipCode();
            LOGGER.info("Address is registered {} for student id {}", studentCity + " -- " + studentZipcode, studentId);
        } else {
            LOGGER.info("There is no address registered");
        }
        return new ResponseAddress(personId, zipCode);*/

    }

    public Address getAddressByPersonId(String personId) {
        return addressRepository.findBypersonId(personId)
                .orElseThrow(() -> new RuntimeException("there is no student has this Id: " + personId));
    }
}
