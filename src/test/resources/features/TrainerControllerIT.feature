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

  Scenario: Update trainer profile with valid data
    Given The Client is a valid user for trainer operations
    When The Client Updates trainer profile with valid data using url "/api/v1/trainer/updateProfile"
    Then The Client Updates trainer profile with valid data receives a good response status 200
    And The Client Updates trainer profile with valid data receives a response message "Alice"

  Scenario: Update trainer profile with invalid data
    Given The Client is a valid user for trainer operations
    When The Client Updates trainer profile with invalid data using url "/api/v1/trainer/updateProfile"
    Then The Client Updates trainer profile with invalid data receives a bad response status 400
    And The Client Updates trainer profile with invalid data receives a bad response message "Trainer with username invalid_username not found"

  Scenario: Change status of trainer with valid username
    Given The Client is a valid user for trainer operations
    When The Client Changes status of trainer with valid username using url "/api/v1/trainer/changeStatus"
    Then The Client Changes status of trainer with valid username receives a good response status 200
    And The Client Changes status of trainer with valid username receives a response message "true"

  Scenario: Change status of trainer with invalid username
    Given The Client is a valid user for trainer operations
    When The Client Changes status of trainer with invalid username using url "/api/v1/trainer/changeStatus"
    Then The Client Changes status of trainer with invalid username receives a bad response status 400
    And The Client Changes status of trainer with invalid username receives a bad response message "Trainer with username invalid_username not found"

#  Scenario: Delete trainer with valid username
#    Given The Client is a valid user for trainer operations
#    When The Client Deletes trainer with valid username using url "/api/v1/trainer/delete"
#    Then The Client Deletes trainer with valid username receives a good response status 200

  Scenario: Delete trainer with invalid username
    Given The Client is a valid user for trainer operations
    When The Client Deletes trainer with invalid username using url "/api/v1/trainer/delete"
    Then The Client Deletes trainer with invalid username receives a bad response status 400