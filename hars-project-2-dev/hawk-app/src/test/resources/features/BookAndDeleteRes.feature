Feature: Book and Delete a Reservation

Background:
  Given a user has logged inn

Scenario: A user searches for airports to fly from and to
  When a user clicks on the Book New Trip link
  And the user enters the departure city, state, and radius
  And the user enters the arrival city, state, and radius
  Then the user is redirected to the airport results page
  And available airports are displayed

Scenario: A user searches for one way flights with various search parameters
  Given a user is on the airport results page
  When the user selects a departure airport
  And the user selects a landing airport
  And the user selects a departure date
  And the user selects the number of passengers
  And the user selects the travel class
  And the user specifies connecting flights
  And the user selects the max price
  Then the user is redirected to the search results page
  And available flights matching the criteria are displayed

Scenario: A user searches for round trip flights with various search parameters
  Given a user is on the airport results page
  When the user selects a departure airport
  And the user selects a landing airport
  And the user selects a departure date
  And the user specifies round trips
  And the user selects a return date
  And the user selects the number of passengers
  And the user selects the travel class
  And the user specifies connecting flights
  And the user selects the max price
  Then the user is redirected to the search results page
  And available flights matching the criteria are displayed

Scenario: A user selects a reservation
  Given the user is on the search results page
  When the user selects the desired flight
  And the user clicks the submit button
  Then the user is redirected to the create order page

Scenario: A user confirms the reservation
  Given the user is on the create order page
  When the user enters payment information
  And selects their luggage
  And enters the passengers' names
  And clicks the button
  Then the user is redirectedd to the dashboard page
  And the new flight is booked

Scenario: A user deletes a reservation
  Given a user is on the dashboard page
  When the user clicks the delete button for a certain reservation
  Then the user is redirected back to the dashboard page
  And the reservation is deleted
