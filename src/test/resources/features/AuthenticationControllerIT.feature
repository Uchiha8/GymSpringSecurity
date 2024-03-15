Feature: Authentication and Authorization

  Scenario: User logs in with valid credentials
    Given The Client is a valid user
    When The Client authenticates using url "/api/v1/auth/authenticate"
    Then The Client while authenticates receives a response status 200

  Scenario: Valid user changes password
    Given The Client is a valid user
    When The Client requests to change password using url "/changePassword" with new password "newPassword123"
    Then The Client while changing password receives a response status 200
    And The Client while changing password receives a response body

  Scenario: Invalid user attempts to change password
    Given The Client is an invalid user
    When The Client requests to change password using url "/changePassword" with new password "newPassword123"
    Then The Client while changing password receives a response status 400