# HARS-Project-2

_Project Description_

The Hawk Airport Reservation System (HARS) is an application that allows users to make electronic reservations for airline tickets. It eases the process of viewing vacant seats, getting flight details, and viewing arrival and departure times. With HARS, users can pick different airlines, view their past tickets, book new tickets, and check their flight status all within one application.

_Technologies Used_

    Java
    Jenkins
    EC2
    DevOps
    Agile-Scrum
    Javalin
    JDBC
    Maven
    JavaScript
    HTML
    CSS
    PostgreSQL
    AWS S3
    Selenium

_Third Party APIs_

    Amadeus Self-Service
    Google Geocoding

_Maven Dependencies_

    Javalin
    SLF4J
    Jackson Databind
    TestNG
    PostgreSQL
    Thymeleaf
    Mockito
    JSON
    Selenium
    Cucumber
    Log4j

_Features_

List of features ready

    Login/Logout for existing customers
    Register Account for new customers
    Dashboard page where users can view past and upcoming reservations
    Book New Trip feature where users can choose the airport to arrive to, number of passengers, departure date, class, and max price
    Search Results page to display the available reservations that match the user criteria
    Price Results page for users to input passenger information, luggage size, and purchase their reservation with banking details
    Seatmap Display page to allow users to choose their seats on each of their flights, or edit seats if they had already chosen some
    Delete Reservation feature allows users to delete a reservation at any time before the departure date

To-do list:

    Improve the styling on the Seatmap page
    Add more verification for user input

_Getting Started_

Open Git Bash and enter the following commands:

git clone https://gitlab.com/ray.amparo1995/hars-project-2.git

git checkout origin qa

git pull

_Usage_

After pulling, simply run Main.java and go to http:/localhost:5000, and enter the user credentials, or create an account if no such account is available. Upon successful login, you are redirected to the Dashboard page and can view all your reservations. In order to book a new reservation, click on the Book New Trip link in the navigation bar, and follow the four steps in order (takeoff and arrival destinations, search criteria, search results, confirm reservation). You will be sent back to the Dashboard page and the new reservation will appear. To delete a reservation, click on that reservation's Delete Reservation button.

To choose your seats on a newly booked reservation, on the Dashboard page click the reservation's link to open up the Seatmap Display. Once the page loads, choose your desired seat(s) from the available green boxes, and click the Book seats button when you are finished. To edit seats, click the same link on the Dashboard page for that reservation, and the previously selected seats are shown in blue. Simply click on the new seat(s) and click the Edit seats button.
