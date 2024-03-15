Feature: Trainee Controller CRUD operations

  Scenario: Create a new trainee controller
    When The Client Saves trainee using url "/api/v1/trainee/register"
    Then The Client receives a response status 200
    And The Client receives a response body "john.doe"

  Scenario: Create a new invalid trainee controller
    When The Client Saves invalid trainee using url "/api/v1/trainee/register"
    Then The Client receives a bad response status 400
    And The Client receives a bad response message "First name is required"

#  Scenario: Retrieve trainee profile
#    When The Client Retrieves trainee profile using username "john_doe"
#    Then The Client from fetching trainee receives a response status 200
#    And The Client from fetching receives a response body containing "John"
