package com.epam.finalDemo.controller;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.dto.request.TrainerRegistrationRequest;
import com.epam.finalDemo.dto.response.RegistrationResponse;
import com.epam.finalDemo.dto.response.TrainerProfileResponse;
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

public class TrainerControllerIT {
    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;

    @Given("predefined dataset")
    public void predefined_dataset() throws JsonProcessingException {
        HttpHeaders headers2 = new HttpHeaders();
        headers2.set("Content-Type", "application/json");
        TraineeRegistrationRequest trainee = TraineeRegistrationRequest.builder()
                .firstName("Ali")
                .lastName("Najimov")
                .address("Yunusobod")
                .dateOfBirth(new Date())
                .build();
        HttpEntity<TraineeRegistrationRequest> requestEntity2 = new HttpEntity<>(trainee, headers2);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/trainee/register",
                HttpMethod.POST,
                requestEntity2,
                String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        TrainingType trainingType = TrainingType.builder()
                .name("Java")
                .build();
        HttpEntity<TrainingType> requestEntity = new HttpEntity<>(trainingType, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/trainingType/register",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }


    @Given("The Client is a valid user for trainer operations")
    public void the_client_valid_user_trainer_operations() {
        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("Content-Type", "application/json");
        TrainerRegistrationRequest trainer = TrainerRegistrationRequest.builder()
                .firstName("Natig")
                .lastName("Kurbanov")
                .trainingType("Java")
                .build();
        HttpEntity<TrainerRegistrationRequest> requestEntity1 = new HttpEntity<>(trainer, headers1);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/trainer/register",
                HttpMethod.POST,
                requestEntity1,
                String.class);
    }

    @When("The Client Saves trainer using url {string}")
    public void the_client_saves_valid_trainer(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        TrainerRegistrationRequest trainer = TrainerRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .trainingType("Java")
                .build();
        HttpEntity<TrainerRegistrationRequest> requestEntity = new HttpEntity<>(trainer, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Then("The Client saves trainer receives a response status {int}")
    public void the_client_saves_valid_trainer_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client saves trainer receives a response body {string}")
    public void the_client_saves_valid_trainer_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client Saves invalid trainer using url {string}")
    public void the_client_saves_invalid_trainer(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        TrainerRegistrationRequest trainer = TrainerRegistrationRequest.builder()
                .firstName(null)
                .lastName("Doe")
                .trainingType("Java")
                .build();

        HttpEntity<TrainerRegistrationRequest> requestEntity = new HttpEntity<>(trainer, headers);

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

    @Then("The Client Saves invalid trainer receives a bad response status {int}")
    public void the_client_saves_invalid_trainer_receives_bad_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Saves invalid trainer receives a bad response message {string}")
    public void the_client_saves_invalid_trainer_receives_bad_response_message(String message) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(message));
    }

    @When("The Client Reads trainer profile with username using url {string}")
    public void the_client_reads_trainer_profile(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url + "?username=" + response.username(),
                HttpMethod.GET,
                requestEntity,
                String.class);
    }

    @Then("The Client Reads trainer profile with valid username receives a good response status {int}")
    public void the_client_reads_trainer_profile_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Reads trainer profile with valid username receives a response message {string}")
    public void the_client_reads_trainer_profile_receives_response_body(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TrainerProfileResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), TrainerProfileResponse.class);
        Assertions.assertEquals(response.fistName(), body);
    }

    @When("The Client Reads trainer profile with invalid username using url {string}")
    public void the_client_reads_trainer_profile_with_invalid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url + "?username=invalid_username",
                    HttpMethod.GET,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client Reads trainer profile with invalid username receives a bad response status {int}")
    public void the_client_reads_trainer_profile_with_invalid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Reads trainer profile with invalid username receives a bad response message {string}")
    public void the_client_reads_trainer_profile_with_invalid_username_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }
}
