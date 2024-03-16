Feature: Authentication and Authorization

  Scenario: User logs in with valid credentials
    Given The Client is a valid user
    When The Client authenticates using url "/api/v1/auth/authenticate"
    Then The Client while authenticates receives a response status 200

  Scenario: User logs in with invalid credentials
    Given The Client is a valid user
    When The Client authenticates invalid using url "/api/v1/auth/authenticate"
    Then The Client while authenticates receives a bad response status 400
    And The Client while authenticates receives a response body with message "Invalid username/password"

  Scenario: User changes password with valid credentials
    Given The Client is a valid user
    When The Client changes password using url "/api/v1/auth/changePassword"
    Then The Client changes password receives a ok response status 200
    And The Client changes password receives a response body

  Scenario: User changes password with invalid credentials
    Given The Client is a valid user
    When The Client changes password invalid using url "/api/v1/auth/changePassword"
    Then The Client changes password invalid receives a bad response status 400
    And The Client changes password invalid receives a response body with message "User not found"
