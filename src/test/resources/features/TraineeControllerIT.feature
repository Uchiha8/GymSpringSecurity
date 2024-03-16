Feature: Trainee Controller CRUD operations

  Scenario: Create a new trainee controller
    When The Client Saves trainee using url "/api/v1/trainee/register"
    Then The Client receives a response status 200
    And The Client receives a response body "john.doe"

  Scenario: Create a new invalid trainee controller
    When The Client Saves invalid trainee using url "/api/v1/trainee/register"
    Then The Client Saves invalid trainee receives a bad response status 400
    And The Client Saves invalid trainee receives a bad response message "First name is required"

  Scenario: Read trainee profile with username
    Given The Client is a valid user for trainee operations
    When The Client Reads trainee profile with username using url "/api/v1/trainee/profile"
    Then The Client Reads trainee profile with valid username receives a good response status 200
    And The Client Reads trainee profile with valid username receives a response message "John"

  Scenario: Read trainee profile with invalid username
    Given The Client is a valid user for trainee operations
    When The Client Reads trainee profile with invalid username using url "/api/v1/trainee/profile"
    Then The Client Reads trainee profile with invalid username receives a bad response status 400
    And The Client Reads trainee profile with invalid username receives a bad response message "Trainee with username invalid_username not found"

  Scenario: Update trainee profile with valid data
    Given The Client is a valid user for trainee operations
    When The Client Updates trainee profile with valid data using url "/api/v1/trainee/updateProfile"
    Then The Client Updates trainee profile with valid data receives a good response status 200
    And The Client Updates trainee profile with valid data receives a response message "Nozimjon"

  Scenario: Update trainee profile with invalid data
    Given The Client is a valid user for trainee operations
    When The Client Updates trainee profile with invalid data using url "/api/v1/trainee/updateProfile"
    Then The Client Updates trainee profile with invalid data receives a bad response status 400
    And The Client Updates trainee profile with invalid data receives a bad response message "Trainee with username invalid_username not found"

  Scenario: Change status of trainee with valid username
    Given The Client is a valid user for trainee operations
    When The Client Changes status of trainee with valid username using url "/api/v1/trainee/changeStatus"
    Then The Client Changes status of trainee with valid username receives a good response status 200
    And The Client Changes status of trainee with valid username receives a response message "true"

  Scenario: Change status of trainee with invalid username
    Given The Client is a valid user for trainee operations
    When The Client Changes status of trainee with invalid username using url "/api/v1/trainee/changeStatus"
    Then The Client Changes status of trainee with invalid username receives a bad response status 400
    And The Client Changes status of trainee with invalid username receives a bad response message "Trainee with username invalid_username not found"

  Scenario: Delete trainee with valid username
    Given The Client is a valid user for trainee operations
    When The Client Deletes trainee with valid username using url "/api/v1/trainee/delete"
    Then The Client Deletes trainee with valid username receives a good response status 200

  Scenario: Delete trainee with invalid username
    Given The Client is a valid user for trainee operations
    When The Client Deletes trainee with invalid username using url "/api/v1/trainee/delete"
    Then The Client Deletes trainee with invalid username receives a bad response status 400

  Scenario: Read trainee training with invalid username
    Given The Client is a valid user for trainee operations
    When The Client Reads trainee training with invalid username using url "/api/v1/trainee/trainings"
    Then The Client Reads trainee training with invalid username receives a bad response status 400

  Scenario: Cancel trainee training with invalid username
    Given The Client is a valid user for trainee operations
    When The Client Cancels trainee training with invalid username using url "/api/v1/trainee/cancelTraining"
    Then The Client Cancels trainee training with invalid username receives a bad response status 400