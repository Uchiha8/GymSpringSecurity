Feature: Trainer Controller CRUD operations

  Scenario: Create a new trainer controller
    Given predefined dataset
    When The Client Saves trainer using url "/api/v1/trainer/register"
    Then The Client saves trainer receives a response status 200
    And The Client saves trainer receives a response body "john.doe"

  Scenario: Create a new invalid trainer controller
    When The Client Saves invalid trainer using url "/api/v1/trainer/register"
    Then The Client Saves invalid trainer receives a bad response status 400
    And The Client Saves invalid trainer receives a bad response message "First name is required"

#  read trainer with valid username
  Scenario: Read trainer profile with username
    Given The Client is a valid user for trainer operations
    When The Client Reads trainer profile with username using url "/api/v1/trainer/profile"
    Then The Client Reads trainer profile with valid username receives a good response status 200
    And The Client Reads trainer profile with valid username receives a response message "Natig"

  Scenario: Read trainer profile with invalid username
    Given The Client is a valid user for trainer operations
    When The Client Reads trainer profile with invalid username using url "/api/v1/trainer/profile"
    Then The Client Reads trainer profile with invalid username receives a bad response status 400
    And The Client Reads trainer profile with invalid username receives a bad response message "Trainer with username invalid_username not found"