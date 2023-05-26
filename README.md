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
@RefreshScope
public class ApiGateWayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI").addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/api/v1/students/**").uri("lb://student-service/"))
                .route(p -> p.path("/api/address/**").uri("lb://address-service/"))
                .route(p -> p.path("/student/**")
                        .filters(f -> f.rewritePath("/student/(?<segment>.*)", "/api/v1/students/${segment}"))
                        .uri("lb://student-service/"))
                .build();

        // p -> p.path("/api/v1/students/**" : Path to RestAPI).uri("lb://student-service/": name of service)

        // To create a specific filter path :
        /*
        .route(p -> p.path("/student/**")
        // this url student/(?<segment>.*) sreplace  /api/v1/students/${segment}
                .filters(f -> f.rewritePath("/student/(?<segment>.*)", "/api/v1/students/${segment}"))
                .uri("lb://student-service/"))
         */

    }
}
```
### Commented the following lines:
``` 
#spring.cloud.gateway.discovery.locator.enabled=true
#spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true
```

