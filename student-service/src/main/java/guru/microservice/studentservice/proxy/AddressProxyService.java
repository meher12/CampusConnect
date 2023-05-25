package guru.microservice.studentservice.proxy;

import guru.microservice.studentservice.request.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="address-service" , url="localhost:8281/api/address")
public interface AddressProxyService {

    @GetMapping("/api/address/{personId}/{zipCode}")
    public Address registerAddress(@PathVariable("personId") String personId, @PathVariable("zipCode") String zipCode);

    @GetMapping("/api/address/{personId}")
    public Address getAddressBypersonId(@PathVariable("personId") String personI);
}