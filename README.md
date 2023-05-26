# Campus Connect

## API Gateway URL
To enable this URL, we should enable it like this in the `api-gateway` project:

``` 
spring.cloud.gateway.discovery.locator.enabled=true
```
API Gateway URL: http://localhost:8765/STUDENT-SERVICE/api/v1/students/602

## Change Service ID to Lowercase
To change the service ID to lowercase, add the following configuration:
```
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

API Gateway URL: http://localhost:8765/student-service/api/v1/students/602

## Exploring Routes with Spring Cloud Gateway
Add the following configuration class, `ApiGatewayConfiguration`, in the `api-gateway` project:

```java
@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get") // http://localhost:8765/get
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange-service/**").uri("lb://currency-exchange-service"))
                .route(p -> p.path("/currency-conversion-service/**").uri("lb://currency-conversion-service"))
                .route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion-service"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion-service"))
                .build();
    }
}
```
###Commented the following lines:
``` 
#spring.cloud.gateway.discovery.locator.enabled=true
#spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true
```

