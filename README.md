# Campus Connect

## Spring Cloud Config
To configure Spring Cloud Config, follow these steps in the config-server project:
 * Add the following dependencies to the pom.xml file:
   ```
    <!-- Config Server -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    ```
 * Add the @EnableConfigServer annotation to the main class:
    ```java
    @EnableConfigServer
    @SpringBootApplication
    public class ConfigServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(ConfigServerApplication.class, args);
        }
    }
    
    ```
 * Configure the Spring Cloud Config properties in the application.properties file:
 ``` 
 server.port=8888
 spring.cloud.config.server.git.uri=<your-git-repo-url>
 ```

 Make sure to replace <your-git-repo-url> with the URL of your Git repository where the configuration files are stored.

## Eureka Server
To set up the Eureka Server, follow these steps in the eureka-server project:
 * Add the following dependencies to the pom.xml file:
```xml
  <!-- Eureka Server -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
```
 * Add the @EnableEurekaServer annotation to the main class:
    ```
    @EnableEurekaServer
    @SpringBootApplication
    public class EurekaServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(EurekaServerApplication.class, args);
        }
    }
    
    ```
 * Configure the Eureka Server properties in the application.properties file:
    ```
    server.port=8761
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    
    ```

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
        // this url student/(?<segment>.*) replace  /api/v1/students/${segment}
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

### The new Custom Routes:
http://localhost:8765/student/602 replace http://localhost:8765/student-service/api/v1/students/602:

## Resilience4j:
1. Getting started with Circuit Breaker - Resilience4j
  * In address project add new dependencies:
  ```
  <dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId>
  </dependency>

  <dependency>
     <groupId>io.github.resilience4j</groupId>
     <artifactId>resilience4j-spring-boot2</artifactId>
  </dependency>
  ```

2. Playing with Resilience4j - Retry and Fallback Methods:
  - Can we return a fallback response if a service is down ?
  * CircuitBreakerController class:

     ```
        private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

        @GetMapping("/sample-api")
        @Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
        public String sampleApi() {
           logger.info("Sample api call received");
           ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                    String.class);
           return forEntity.getBody();
           
        }
        public String hardcodedResponse(Exception ex) {
           return "fallback-response";
        }
     ```
    In application.properties:
     ```
        resilience4j.retry.instances.sample-api.max-attempts=5
        resilience4j.retry.instances.sample-api.waitDuration=1s
        resilience4j.retry.instances.sample-api.enableExponentialBackoff=true
     ```
    In console we see 5 attempts:
     ```
        INFO 19676 --- [nio-8000-exec-1] n.g.m.c.CircuitBreakerController         : Sample api call received
        INFO 19676 --- [nio-8000-exec-1] n.g.m.c.CircuitBreakerController         : Sample api call received
        INFO 19676 --- [nio-8000-exec-1] n.g.m.c.CircuitBreakerController         : Sample api call received
        INFO 19676 --- [nio-8000-exec-1] n.g.m.c.CircuitBreakerController         : Sample api call received
        INFO 19676 --- [nio-8000-exec-1] n.g.m.c.CircuitBreakerController         : Sample api call received
     ```
    With "fallbackMethod" In browser (http://localhost:8281/sample-api) we see: "fallback-response" message instead of the default message error.

3. Playing with Circuit Breaker Features of Resilience4j:
   In terminal for auto send req:
     ```
        curl http://localhost:8281/sample-api
        watch curl http://localhost:8281/sample-api
        watch -n 0.1 curl http://localhost:8281/sample-api
     ```
   CircuitBreakerController class:
      ```
       //@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
         @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
      ```
  - FailureRateThreshold: Configures the failure rate threshold in percentage. \ When the failure rate is equal or greater than the threshold the CircuitBreaker transitions to open and starts short-circuiting calls. \
  - In application.properties file:
      ```
       resilience4j.circuitbreaker.instances.default.failure-rate-threshold=90
      ``` 
4. Exploring Rate Limiting and BulkHead Features of Resilience4j
  1.  Rate limiting is an imperative technique to prepare your API for scale and establish high availability and reliability of your service
  * @RateLimiter(name="default")
      ```
         #The number of permissions available during one limit refresh period
         #For example, you want to restrict the calling rate of some methods to be not higher than 2 req/ms.
         resilience4j.ratelimiter.instances.default.limit-for-period=2 

         #The period of a limit refresh. After each period the rate limiter sets its permissions count back to the limitForPeriod value
         resilience4j.ratelimiter.instances.default.limit-refresh-period=10s
      ```
  * In terminal for auto send req:
      ```
         curl http://localhost:8000/sample-api
         watch curl http://localhost:8000/sample-api
         watch -n 0.1 curl http://localhost:8000/sample-api
      ```
  * In browser we can see the result after two refresh we get a error because we configure the Rate limiting 'limit-for-period=2 ' and 'limit-for-period=2 '
  2.  bulkhead pattern that can be used to limit the number of concurrent execution: in this example 10 req
  * @Bulkhead(name="sample-api")
  * In application.properties file:
      ```
       #Max amount of parallel executions allowed by the bulkhead
       resilience4j.bulkhead.instances.sample-api.max-concurrent-calls=10
      ``` 
  * In browser we can see the result after 10 refresh

## Creating Docker Image for All Microservices :
  * To create a Docker image for each microservice, you can use a Dockerfile. Here's an example Dockerfile for the `address-service`:
    
    ```
     FROM openjdk:17
     ARG JAR_FILE=*.jar
     #RUN mvn clean install
     COPY /target/${JAR_FILE} address-service.jar
     ENTRYPOINT ["java", "-jar","address-service.jar"]
     EXPOSE 8281
     ``` 
You can create similar Dockerfiles for other microservices and customize them accordingly.
### Getting Started with Docker Compose
To use Docker Compose for running the microservices, you can configure the docker-compose.yml file.
Make sure to update the service names and ports according to your configuration.
 * The name of the service in docker-compose should have  the same name of the service name in the app
 * url in postman without apigateway and eureka server:
    - http://localhost:8181/api/v1/students/add
    - http://localhost:8181/api/v1/students/452
 * url in postman with apigateway and eureka server :
   - post http methode: http://localhost:8765/student/add
   - get http methode : http://localhost:8765/student/1


I hope this helps! Let me know if you need further assistance.



