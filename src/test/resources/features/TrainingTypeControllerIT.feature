Feature: Training type CRUD operations

#  Scenario: Read List of Training types with invalid data
#    Given The Client is a valid user for training type operations
#    When The Client requests for list of training types with invalid data using url "/api/v1/trainingType/all"
#    Then The Client while requesting for list of training types with invalid data receives a bad response status 400
#    And The Client while requesting for list of training types with invalid data receives a response body "No training types found"

  Scenario: Create a new training type
    Given The Client is a valid user for training type operations
    When The Client saves a training type using url "/api/v1/trainingType/register"
    Then The Client while saving a training type receives a ok response status 200
    And The Client while saving a training type receives a response body "Java"

  Scenario: Create a new training type with invalid data
    Given The Client is a valid user for training type operations
    When The Client saves a training type with invalid data using url "/api/v1/trainingType/register"
    Then The Client while saving a training type with invalid data receives a bad response status 400
    And The Client while saving a training type with invalid data receives a response body "Training type name is required"

  Scenario: Read List of Training types
    Given The Client is a valid user for training type operations
    When The Client requests for list of training types using url "/api/v1/trainingType/all"
    Then The Client while requesting for list of training types receives a ok response status 200
    And The Client while requesting for list of training types receives a response body "Java"


