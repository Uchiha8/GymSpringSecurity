package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.AuthenticationRequest;
import com.epam.finalDemo.dto.request.ChangeLoginRequest;
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

import static org.hamcrest.MatcherAssert.*;

public class AuthenticationControllerIT {
    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;

    @Given("The Client is a valid user")
    public void given_valid_user() {
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
    public void the_client_authenticates_with_valid_user(String url) throws JsonProcessingException {
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
    public void the_client_authenticates_receives_status_code(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client while authenticates receives a response body")
    public void the_client_authenticates_receives_response_body() {
        Assertions.assertNotNull(lastResponse.getBody());
    }

    @When("The Client authenticates invalid using url {string}")
    public void the_client_authenticates_with_invalid_user(String url) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        AuthenticationRequest request = new AuthenticationRequest(response.username() + "invalid_username", response.password());
        HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<>(request, headers);
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

    @Then("The Client while authenticates receives a bad response status {int}")
    public void the_client_authenticates_receives_bad_status_code(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client while authenticates receives a response body with message {string}")
    public void the_client_authenticates_receives_bad_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client changes password using url {string}")
    public void the_client_changes_password(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        ChangeLoginRequest request = new ChangeLoginRequest(response.username(), response.password(), "new_password");
        HttpEntity<ChangeLoginRequest> requestEntity = new HttpEntity<>(request, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.PUT,
                requestEntity,
                String.class);
    }

    @Then("The Client changes password receives a ok response status {int}")
    public void the_client_changes_password_receives_status_code(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client changes password receives a response body")
    public void the_client_changes_password_receives_response_body() {
        Assertions.assertNotNull(lastResponse.getBody());
    }

    @When("The Client changes password invalid using url {string}")
    public void the_client_changes_password_invalid(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        ChangeLoginRequest request = new ChangeLoginRequest(response.username() + "invalid_username", response.password(), "new_password");
        HttpEntity<ChangeLoginRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            lastResponse = null;
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client changes password invalid receives a bad response status {int}")
    public void the_client_changes_password_receives_bad_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client changes password invalid receives a response body with message {string}")
    public void the_client_changes_password_receives_bad_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }
}
