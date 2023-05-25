package guru.microservice.studentservice.request;

public record StudentRequest(String firstName, String lastName, String email, String zipCode) {
}
