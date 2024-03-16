package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.dto.request.TraineeTrainingsRequest;
import com.epam.finalDemo.dto.request.UpdateTraineeProfileRequest;
import com.epam.finalDemo.dto.response.RegistrationResponse;
import com.epam.finalDemo.dto.response.TraineeProfileResponse;
import com.epam.finalDemo.dto.response.UpdateTraineeProfileResponse;
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


public class TraineeControllerIT {

    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;



    @Given("The Client is a valid user for trainee operations")
    public void the_client_valid_user_trainee_operations() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        TraineeRegistrationRequest trainee = TraineeRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .dateOfBirth(new Date())
                .build();

        HttpEntity<TraineeRegistrationRequest> requestEntity = new HttpEntity<>(trainee, headers);
        lastResponse = null;
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/trainee/register",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @When("The Client Saves trainee using url {string}")
    public void the_client_saves_valid_trainee(String url) {
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
    public void the_client_saves_valid_trainee_receives_status_code(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client receives a response body {string}")
    public void the_client_saves_valid_trainee_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client Saves invalid trainee using url {string}")
    public void the_client_saves_invalid_trainee(String url) {
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

    @Then("The Client Saves invalid trainee receives a bad response status {int}")
    public void the_client_saves_invalid_trainee_receives_status_code(int status) {
        assertThat("status code is " + status,
                lastResponse.getStatusCode().value() == status);
    }

    @And("The Client Saves invalid trainee receives a bad response message {string}")
    public void the_client_saves_invalid_trainee_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }


    @When("The Client Reads trainee profile with username using url {string}")
    public void the_client_reads_trainee_profile(String url) throws JsonProcessingException {
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

    @Then("The Client Reads trainee profile with valid username receives a good response status {int}")
    public void the_client_reads_trainee_profile_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Reads trainee profile with valid username receives a response message {string}")
    public void the_client_reads_trainee_profile_receives_response_body(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TraineeProfileResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), TraineeProfileResponse.class);
        Assertions.assertEquals(response.fistName(), body);
    }

    @When("The Client Reads trainee profile with invalid username using url {string}")
    public void the_client_reads_trainee_profile_with_invalid_username(String url) throws JsonProcessingException {
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

    @Then("The Client Reads trainee profile with invalid username receives a bad response status {int}")
    public void the_client_reads_trainee_profile_with_invalid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Reads trainee profile with invalid username receives a bad response message {string}")
    public void the_client_reads_trainee_profile_with_invalid_username_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client Updates trainee profile with valid data using url {string}")
    public void the_client_updates_trainee_profile_with_valid_data(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        UpdateTraineeProfileRequest request = UpdateTraineeProfileRequest.builder()
                .username(response.username())
                .firstName("Nozimjon")
                .lastName("Nabiev")
                .address("Chilonzor 10")
                .dateOfBirth(new Date())
                .isActive(true)
                .build();
        HttpEntity<UpdateTraineeProfileRequest> requestEntity = new HttpEntity<>(request, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.PUT,
                requestEntity,
                String.class);
    }

    @Then("The Client Updates trainee profile with valid data receives a good response status {int}")
    public void the_client_updates_trainee_profile_with_valid_data_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Updates trainee profile with valid data receives a response message {string}")
    public void the_client_updates_trainee_profile_with_valid_data_receives_response_body(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateTraineeProfileResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), UpdateTraineeProfileResponse.class);
        Assertions.assertEquals(response.firstName(), body);
    }

    @When("The Client Updates trainee profile with invalid data using url {string}")
    public void the_client_updates_trainee_profile_with_invalid_data(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        UpdateTraineeProfileRequest request = UpdateTraineeProfileRequest.builder()
                .username("invalid_username")
                .firstName("Nozimjon")
                .lastName("Nabiev")
                .address("Chilonzor 10")
                .dateOfBirth(new Date())
                .isActive(true)
                .build();
        HttpEntity<UpdateTraineeProfileRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client Updates trainee profile with invalid data receives a bad response status {int}")
    public void the_client_updates_trainee_profile_with_invalid_data_receives_bad_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Updates trainee profile with invalid data receives a bad response message {string}")
    public void the_client_updates_trainee_profile_with_invalid_data_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client Changes status of trainee with valid username using url {string}")
    public void the_client_changes_status_of_trainee_with_valid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        ChangeStatusRequest request = ChangeStatusRequest.builder()
                .username(response.username())
                .status(true)
                .build();
        HttpEntity<ChangeStatusRequest> requestEntity = new HttpEntity<>(request, headers);
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.PUT,
                requestEntity,
                String.class);
    }

    @Then("The Client Changes status of trainee with valid username receives a good response status {int}")
    public void the_client_changes_status_of_trainee_with_valid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Changes status of trainee with valid username receives a response message {string}")
    public void the_client_changes_status_of_trainee_with_valid_username_receives_response_body(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Boolean response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), Boolean.class);
        Assertions.assertEquals(response, Boolean.parseBoolean(body));
    }

    @When("The Client Changes status of trainee with invalid username using url {string}")
    public void the_client_changes_status_of_trainee_with_invalid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        ChangeStatusRequest request = ChangeStatusRequest.builder()
                .username("invalid_username")
                .status(true)
                .build();
        HttpEntity<ChangeStatusRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client Changes status of trainee with invalid username receives a bad response status {int}")
    public void the_client_changes_status_of_trainee_with_invalid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @And("The Client Changes status of trainee with invalid username receives a bad response message {string}")
    public void the_client_changes_status_of_trainee_with_invalid_username_receives_response_body(String body) {
        Assertions.assertTrue(Objects.requireNonNull(lastResponse.getBody()).toString().contains(body));
    }

    @When("The Client Deletes trainee with valid username using url {string}")
    public void the_client_deletes_trainee_with_valid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url + "?username=" + response.username(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                String.class);
    }

    @Then("The Client Deletes trainee with valid username receives a good response status {int}")
    public void the_client_deletes_trainee_with_valid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @When("The Client Deletes trainee with invalid username using url {string}")
    public void the_client_deletes_trainee_with_invalid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url + "?username=invalid_username",
                    HttpMethod.DELETE,
                    new HttpEntity<>(headers),
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client Deletes trainee with invalid username receives a bad response status {int}")
    public void the_client_deletes_trainee_with_invalid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @When("The Client Reads trainee training with invalid username using url {string}")
    public void the_client_reads_trainee_training_with_invalid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        TraineeTrainingsRequest request = TraineeTrainingsRequest.builder()
                .username(null)
                .build();
        HttpEntity<TraineeTrainingsRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url,
                    HttpMethod.GET,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client Reads trainee training with invalid username receives a bad response status {int}")
    public void the_client_reads_trainee_training_with_valid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }

    @When("The Client Cancels trainee training with invalid username using url {string}")
    public void the_client_cancels_trainee_training_with_invalid_username(String url) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationResponse response = objectMapper.readValue(Objects.requireNonNull(lastResponse.getBody()).toString(), RegistrationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.token());
        try {
            lastResponse = new RestTemplate().exchange(
                    "http://localhost:" + port + url + "?username=invalid_username&trainingName=invalid_training_name",
                    HttpMethod.PUT,
                    new HttpEntity<>(headers),
                    String.class);
        } catch (Exception e) {
            lastResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("The Client Cancels trainee training with invalid username receives a bad response status {int}")
    public void the_client_cancels_trainee_training_with_invalid_username_receives_status_code(int status) {
        Assertions.assertEquals(lastResponse.getStatusCode().value(), status);
    }


}
