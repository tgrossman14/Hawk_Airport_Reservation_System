package frontcontroller;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import controllers.TestController;

//import controllers.UserController;
import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.core.security.Role;

/**
 * Dispatcher class to handle endpoints and call the respective controller method
 * */
public class Dispatcher {
	
	public Dispatcher(Javalin app) {
		// This route is for opening the localhost, the default page is the login page
		app.routes(() ->{
			path("/", () -> {
				get(UserController::defaultPage);
			});
		});
		// initial test route
		app.routes(() ->{
			path("/api/test", () -> {
				get(TestController::testCon);
			});
		});
		// Checks if the user is in the database
		app.routes(()->{
			path("/api/users",() ->{
				get(UserController::getAllUsers);
			});
		});
		// A test variation of the login functionality
		app.routes(()->{
			path("/api/check-user", ()->{
				post(UserController::checkUser);
			});
		});
		// The login functionality
		app.routes(()->{
			path("/api/login", ()->{
				post(UserController::userLogIn);
			});
		});
		// The logout functionality with the session
		app.routes(()->{
			path("/api/logout", ()->{
				get(UserController::logOut);
				post(UserController::logOut);
			});
		});
		// The test variation of registering the user
		app.routes(()->{
			path("/api/create-user", ()->{
				post(UserController::createUser);
			});
		});
		// The register functionality
		app.routes(()->{
			path("/api/register", ()->{
				post(UserController::registerUser);
			});
		});
		app.routes(()->{
			path("/api/dashboard", ()-> {
				get(UserController::userDashboard);
			});
		});
		
		// First enter latitude/longitude and radius
		app.routes(()-> {
			path("/api/airports", () -> {
				get(UserController::airportsNearest);
			});
		});
		// Select the airports, and other user input like departure date, return date if requested, etc
		app.routes(() -> {
			path("/api/airport-results", () -> {
				get(UserController::airportResults);
			});
		});
		// Load the search results for available flights
		app.routes(() -> {
			path("/api/search-results", () -> {
				get(UserController::searchResults);
			});
		});
		// Confirm the reservation before going to checkout
		app.routes(() -> {
			path("/api/price-results", () -> {
				get(UserController::priceResults);
			});
		});
		//JSON route for all reservations for a given user
		app.routes(() -> {
			path("/api/reservations.json", () -> {
				get(UserController::getAllReservationsByUserId);
				path(":id", () -> {
					get(UserController::getReservationById);
				});
			});
		});
		//Create new reservation
		app.routes(()->{
			path("/api/create-order", ()->{
				get(UserController::paymentInfo);
				post(UserController::createOrder);
			});
		});
		//Interactive seat map
		app.routes(() -> {
			path("/api/seatmap-display", ()-> {
				path(":id", ()->{
					get(UserController::seatmapDisplay);
				});
			});
		});
		//Booking seats for the first time
		app.routes(() -> {
			path("/api/book-seats", () -> {
				path(":id", () -> {
					post(UserController::bookSeats);
				});
			});
		});
		//JSON route to retrieve all the booked seats for a reservation ID
		app.routes(()->{
			path("/api/seats.json", ()-> {
				path(":id", ()-> {
					get(UserController::getAllSeatsByReservationId);
				});
			});
		});
		//Editing seats after having booked them
		app.routes(()-> {
			path("/api/edit-seats", ()-> {
				path(":id", ()-> {
					post(UserController::updateSeats);
				});
			});
		});
		//Deleting a reservation
		app.routes(()-> {
			path("/api/reservations", ()-> {
				path(":id", ()-> {
					post(UserController::deleteReservation);
				});
			});
		});
	}

}
