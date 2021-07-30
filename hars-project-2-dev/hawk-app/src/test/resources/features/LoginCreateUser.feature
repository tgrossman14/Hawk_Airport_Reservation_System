# new feature
# Tags: optional

Feature: Login and Create User

  Background:
    Given a user is on the login page

  Scenario: A user enters the correct credentials
    When a user enters the correct email and password and click the login button
    Then the user is redirected to the dashboard

  Scenario: A user enters the incorrect credentials
    When a user enters the incorrect email and incorrect password
    Then the user is not redirected to the dashboard page

  Scenario: A user goes to the register page
    When a user enters the all of its information to the register page and click the submit button
    Then the user is redirected to booking a new flight

  Scenario: A user enters login and logout
    When a user enters correct the correct credentials and click the login button
    Then the user is redirected to the dashboard and click the logout button