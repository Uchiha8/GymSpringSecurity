package com.epam.finalDemo.controller;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.dto.response.RegistrationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

public class TrainingTypeControllerIT {
    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;

    @Given("The Client is a valid user for training type operations")
    public void the_client_valid_user_training_type_operations() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        TraineeRegistrationRequest trainee = TraineeRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .dateOfBirth(new Date())
                .build();

        HttpEntity<TraineeRegistrationRequest> requestEntity = new HttpEntity<>(trainee, headers);

        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/trainee/register",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @When("The Client saves a training type using url {string}")
    public void the_client_saves_training_type(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        TrainingType request = TrainingType.builder()
                .name("Java")
                .build();
        HttpEntity<TrainingType> requestEntity = new HttpEntity<>(request, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Then("The Client while saving a training type receives a ok response status {int}")
    public void the_client_saves_training_type_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client while saving a training type receives a response body {string}")
    public void the_client_saves_training_type_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client saves a training type with invalid data using url {string}")
    public void the_client_saves_training_type_with_invalid_data(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        TrainingType request = TrainingType.builder()
                .name(null)
                .build();
        HttpEntity<TrainingType> requestEntity = new HttpEntity<>(request, headers);
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client while saving a training type with invalid data receives a bad response status {int}")
    public void the_client_saves_training_type_with_invalid_data_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client while saving a training type with invalid data receives a response body {string}")
    public void the_client_saves_training_type_with_invalid_data_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client requests for list of training types using url {string}")
    public void the_client_gets_all_training_types(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
    }

    @Then("The Client while requesting for list of training types receives a ok response status {int}")
    public void the_client_gets_all_training_types_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client while requesting for list of training types receives a response body {string}")
    public void the_client_gets_all_training_types_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

//    @When("The Client requests for list of training types with invalid data using url {string}")
//    public void the_client_gets_all_training_types_with_invalid_data(String url) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        headers.set("Authorization", "Bearer " + response.token());
//        try {
//            lastResponse = new RestTemplate().exchange(
//                    "http://localhost:" + port + url,
//                    HttpMethod.GET,
//                    new HttpEntity<>(headers),
//                    String.class);
//        } catch (Exception e) {
//            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @Then("The Client while requesting for list of training types with invalid data receives a bad response status {int}")
//    public void the_client_gets_all_training_types_with_invalid_data_receives_status_code(int status) {
//        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
//    }
//
//    @And("The Client while requesting for list of training types with invalid data receives a response body {string}")
//    public void the_client_gets_all_training_types_with_invalid_data_receives_response_body(String body) {
//        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
//    }
}
