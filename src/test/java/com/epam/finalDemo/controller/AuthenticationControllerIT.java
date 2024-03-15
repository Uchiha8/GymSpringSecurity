package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.AuthenticationRequest;
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

import static org.hamcrest.MatcherAssert.assertThat;

public class AuthenticationControllerIT {
    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;

    @Given("The Client is a valid user")
    public void validUser() {
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


    @When("The Client authenticates using url {string}")
    public void authenticateUsingUrl(String url) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        AuthenticationRequest request = new AuthenticationRequest(response.username(), response.password());
        HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<>(request, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Then("The Client while authenticates receives a response status {int}")
    public void receiveResponseStatus(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client while authenticates receives a response body")
    public void receiveResponseBody() {
        Assertions.assertNotNull(lastResponse.getBody());
    }

    @Given("The Client is an invalid user")
    public void invalidUser() {
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

}
