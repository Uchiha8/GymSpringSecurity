package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.service.JwtService;
import com.epam.finalDemo.service.TraineeService;
import com.epam.finalDemo.service.TrainingService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;


public class TraineeControllerIT {

    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;


    @When("The Client Saves trainee using url {string}")
    public void saveTraineeUsingUrl(String url) {
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
                "http://localhost:" + port + url,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Then("The Client receives a response status {int}")
    public void receiveResponseStatus(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client receives a response body {string}")
    public void receiveResponseBody(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client Saves invalid trainee using url {string}")
    public void saveInvalidTraineeUsingUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        TraineeRegistrationRequest trainee = TraineeRegistrationRequest.builder()
                .firstName(null)
                .lastName("Doe")
                .address("123 Main St")
                .dateOfBirth(new Date())
                .build();

        HttpEntity<TraineeRegistrationRequest> requestEntity = new HttpEntity<>(trainee, headers);

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

    @Then("The Client receives a bad response status {int}")
    public void receiveBadResponseStatus(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client receives a bad response message {string}")
    public void receiveBadMessageBody(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }
}
