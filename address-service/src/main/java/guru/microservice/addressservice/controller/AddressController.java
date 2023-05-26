package guru.microservice.addressservice.controller;

import guru.microservice.addressservice.entity.Address;
import guru.microservice.addressservice.response.ResponseAddress;
import guru.microservice.addressservice.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/address")
@RefreshScope
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private  Address address;

    public static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    @GetMapping("/add/{personId}/{zipCode}")
    public ResponseEntity<ResponseAddress> registerAddress(@PathVariable("personId") String personId, @PathVariable("zipCode") String zipCode) {

        ResponseAddress    address = addressService.registerAddressByZipCode(personId, zipCode);
        LOGGER.info(" Address Controller Class: :::: personId: {},zipcode: {} ",personId , zipCode);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping("{personId}")
    public ResponseEntity<Address> getAddressByPersonId(@PathVariable String personId) {
        Address address = addressService.getAddressByPersonId(personId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }


}
