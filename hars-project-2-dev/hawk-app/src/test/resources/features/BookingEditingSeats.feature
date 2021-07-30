Feature: Booking and Editing Seats

  Background:
    Given a user has logged in

  Scenario: A user wishes to book seats for a new reservation
    When the user clicks the seats link for that reservation
    Then the user is redirected to the seatmap display page
  
  Scenario: A user books seats for a new reservation
    Given the user is on the seatmap display page
    When the user clicks on their desired green available seats
    And the user clicks on the Book Seats button
    Then the user is redirected to the dashboard page

  Scenario: A user wishes to edit seats for a reservation in which they already booked seats
    When the user clicks the seats link for that reservation
    Then the user is redirected to the seatmap display page

  Scenario: A user edits seats for a reservation in which they already booked seats
    Given the user is on the seatmap display page
    When the user clicks on the new green available seats
    And the user clicks on the Edit Seats button
    Then the user is redirected to the dashboard page