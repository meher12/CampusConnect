package guru.microservice.addressservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerController.class);

    @RequestMapping(value = "/sample-api", method = RequestMethod.GET)
    //@Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    @RateLimiter(name="default")
    @Bulkhead(name="sample-api")
    public String sampleApi() {
        LOGGER.info("Sample api call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                String.class);
        return forEntity.getBody();
    }

    public String hardcodedResponse(Exception ex) {
        return "fallback-response";
    }
}
