package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.apache.logging.log4j.*;

import io.javalin.http.Context;
import models.Reservation;
import models.Traveller;
import models.User;
import services.ReservationService;
import services.ReservationServiceImpl;
import services.TravellersService;
import services.TravellersServiceImpl;
import services.UserService;
import services.UserServiceImpl;

/**
 * UserController class to define the static methods that are called in Dispatcher
 * Has static field variables for each Service
 * */
public class UserController {
    static UserService userService = new UserServiceImpl();
    static ReservationService reservationService = new ReservationServiceImpl();
    static TravellersService travellersService = new TravellersServiceImpl();
    
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(UserController.class);

    public static void getAllUsers(Context context){
        context.json(userService.getAllUsers());
    }

    public static void createUser(Context context) {
        User user = context.bodyAsClass(User.class);
        userService.createUser(user);
        context.result("A new user has been created!");
    }

    public static void checkUser(Context context){
        User user = context.bodyAsClass(User.class);
        context.json(userService.checkUser(user));
    }
    /**
     * Method to log a user in
     * Renders the dashboard page upon successful login
     * Catches IllegalArgumentException if the user entered invalid credentials
     * Redirects the user back to the login page
     * */
    public static void userLogIn(Context context){
        try {
			User user = userService.userLogIn(context.formParam("email"), context.formParam("password"));
			context.sessionAttribute("currentUser", user);
			context.json(user);
			LOGGER.info("Logged in as " + user.getFirstName() + " " +user.getLastName());
			context.redirect("/api/dashboard");
		} catch (IllegalArgumentException e) {
			LOGGER.error("User entered false credentials");
			context.redirect("/");
		}
    }
    public static void registerUser(Context context){
        userService.registerUser(context.formParam("firstName"),
                context.formParam("lastName"),
                context.formParam("email"),
                context.formParam("password"));
        context.render("public/index.html");
        LOGGER.info("New user made");
    }
    /**
     * Method to render the default page
     * If a user is not logged in (sessionAttribute "currentUser" doesn't exist), render the page
     * Else redirect the user to the dashboard page
     * */
    public static void defaultPage(Context context){
        if (context.sessionAttribute("currentUser") == null) {
			context.render("public/login.html");
		} else {
			LOGGER.error("Already logged in");
			context.redirect("/api/dashboard");
		}
    }
    /**
     * Method to render the dashboard page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the dashboard page
     * Else redirect the user to the login page
     * */
    public static void userDashboard(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		LOGGER.info("Dashboard page loaded");
    		context.render("public/dashboard.html");
    	} else {
    		LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render the index page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the index page
     * Else redirect the user to the login page
     * */
    public static void airportsNearest(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		LOGGER.info("Airports Nearest page loaded");
    		User user = context.sessionAttribute("currentUser");
    		context.render("public/index.html");
    	} else {
    		//Redirect to login if the user isn't logged in
    		LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render the airport results page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the airport results page
     * Else redirect the user to the login page
     * */
    public static void airportResults(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		LOGGER.info("Airport Results page loaded");
    		context.render("public/airport_results.html");
    	} else {
    		//Redirect to login if the user isn't logged in
    		LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render the search results page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the search results page
     * Else redirect the user to the login page
     * 
     * Must validate each form parameter, if any parameter is invalid, redirect to the airports page
     * */
    public static void searchResults(Context context) {
    	// Check the user is logged in
    	if (context.sessionAttribute("currentUser") != null) {
    		//Validate the query params
    		Date date=new Date();
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    		String departureDate = context.queryParam("departureDate");
    		String returnDate = context.queryParam("returnDate");
    		String adults = context.queryParam("adults");
    		String maxPrice = context.queryParam("maxPrice");
    		dateFormat.setLenient(false);
    		try {
    			dateFormat.parse(departureDate.trim());
    			if (returnDate != null) {
    				dateFormat.parse(returnDate.trim());
    			}
            } catch (ParseException pe) {
            	LOGGER.warn("Invalid date format");
            	context.redirect("/api/airports");
            } catch (NullPointerException npe) {
            	LOGGER.warn("Invalid date format");
            	context.redirect("/api/airports");
            }
    		try {
				if (Integer.valueOf(adults) < 1 || Integer.valueOf(maxPrice) < 1) {
					LOGGER.warn("Integer parameters must be greater than 0");
					context.redirect("/api/airports");
				}
			} catch (NumberFormatException e) {
				LOGGER.warn("Integer parameters must be integers");
				context.redirect("/api/airports");
			}
    		LOGGER.info("Airport Results page loaded");
			context.render("public/search_results.html");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render the price results page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the price results page
     * Else redirect the user to the login page
     * */
    public static void priceResults(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		LOGGER.info("Price Results page loaded");
			context.render("public/price_results.html");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render the create order page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the create order page
     * Else redirect the user to the login page
     * */
    public static void paymentInfo(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		LOGGER.info("Payment info page loaded");
			context.render("public/create_order.html");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to create a new reservation
     * If a user is logged in (sessionAttribute "currentUser" exists), create the reservation
     * Else redirect the user to the login page
     * 
     * Method gets the 'names-json' form parameter, and userId from currentUser, and the JSON cookie from the cookie named "flight"
     * Calls the reservationService's static method to create the reservation, passes in the arguments
     * */
    public static void createOrder(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
			User user = context.sessionAttribute("currentUser");
			String names = context.formParam("names-json");
			int userId = user.getId();
			String jsonCookie = context.cookie("flight");
			context.removeCookie("flight");
			reservationService.createReservation(userId, jsonCookie, names);
			LOGGER.info("New order created");
			context.redirect("/");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render JSON data for all reservations of a given user ID
     * If a user is logged in (sessionAttribute "currentUser" exists), render the JSON data
     * Else redirect the user to the login page
     * 
     * Get user ID from the "currentUser" session attribute
     * Call the getAllReservations service method
     * context.json the results
     * */
    public static void getAllReservationsByUserId(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
			User user = context.sessionAttribute("currentUser");
			int userId = user.getId();
			List<Reservation> reservations = reservationService.getAllReservations(userId);
			LOGGER.info("All reservations retrieved for User #" + userId);
			context.json(reservations);
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render JSON data for the reservation by reservation ID
     * If a user is logged in (sessionAttribute "currentUser" exists), render the JSON data
     * Else redirect the user to the login page
     * 
     * Get reservation ID from the path parameter
     * Call the getlReservation service method
     * context.json the results
     * */
    public static void getReservationById(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
			User user = context.sessionAttribute("currentUser");
			Integer reservationId = Integer.parseInt(context.pathParam("id"));
			Reservation reservation = reservationService.getReservation(reservationId);
			LOGGER.info("Reservation #" + reservationId + " retrieved");
			context.json(reservation);
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render the seatmap display page
     * If a user is logged in (sessionAttribute "currentUser" exists), render the seatmap display page
     * Else redirect the user to the login page
     * */
    public static void seatmapDisplay(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		LOGGER.info("Seatmap displayed");
			context.render("public/seatmap_display.html");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to book seats on a reservation
     * If a user is logged in (sessionAttribute "currentUser" exists), book the seats
     * Else redirect the user to the login page
     * 
     * Get reservation id from the path parameter
     * context.formParamMap() for retrieving all form parameters (this is dynamic so can't hardcode names)
     * For each form parameter, call the bookSeat service method
     * Redirect to the dashboard
     * */
    public static void bookSeats(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		Integer reservationId = Integer.parseInt(context.pathParam("id"));
    		Map<String, List<String>> map = context.formParamMap();
    		for (Map.Entry<String, List<String>> pair : map.entrySet()) {
    			String key = pair.getKey();
    			String planeSeat = pair.getValue().get(0);
    			String carrierCode = key.split("-")[0];
    			Integer flightNumber = Integer.parseInt(key.split("-")[1]);
    			String luggage = key.split("-")[3];
    			String cabin = key.split("-")[4];
    			travellersService.bookSeat(reservationId, planeSeat, luggage, cabin, "2011-01-01 00:00:00", carrierCode, flightNumber);
    		}
    		LOGGER.info("All selected seats booked.");
    		context.redirect("/api/dashboard");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Update seats on a previously booked reservation
     * If a user is logged in (sessionAttribute "currentUser" exists), edit the seats
     * Else redirect the user to the login page
     * 
     * context.formParamMap() for retrieving all form parameters (this is dynamic so can't hardcode names)
     * For each form parameter, call the updateSeat service method
     * Redirect to the dashboard
     * */
    public static void updateSeats(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		Integer reservationId = Integer.parseInt(context.pathParam("id"));
    		Map<String, List<String>> map = context.formParamMap();
    		for (Map.Entry<String, List<String>> pair : map.entrySet()) {
    			String key = pair.getKey();
    			String planeSeat = pair.getValue().get(0);
    			Integer travellerId = Integer.parseInt(key.split("-")[3]);
    			travellersService.updateSeat(travellerId, planeSeat);
    		}
    		LOGGER.info("All selected seats edited.");
    		context.redirect("/api/dashboard");
		} else {
    		//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to render JSON data for all booked seats for a given reservation ID
     * If a user is logged in (sessionAttribute "currentUser" exists), render the JSON data
     * Else redirect the user to the login page
     * 
     * Gets the reservation ID from the path parameter
     * Calls the getAllSeats service method
     * context.json the results
     * */
    public static void getAllSeatsByReservationId(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		Integer reservationId = Integer.parseInt(context.pathParam("id"));
    		List<Traveller> seats = travellersService.getAllSeats(reservationId);
    		LOGGER.info("All seats seats retrieved for Reservation #" + reservationId);
    		context.json(seats);
    	} else {
    		LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }
    /**
     * Method to delete a reservation by given reservation ID
     * If a user is logged in (sessionAttribute "currentUser" exists), delete the reservation
     * Else redirect the user to the login page
     * 
     * Get the reservation ID from the path parameter
     * Calls the deleteReservation service method
     * context.json the results
     * */
    public static void deleteReservation(Context context) {
    	if (context.sessionAttribute("currentUser") != null) {
    		Integer reservationId = Integer.parseInt(context.pathParam("id"));
    		reservationService.deleteReservation(reservationId);
    		LOGGER.info("Reservation #" + reservationId + " deleted");
    		context.redirect("/api/dashboard");
    	} else {
    		LOGGER.error("User isn't logged in.");
    		context.redirect("/");
    	}
    }

    /**
     * Method to log the user out
     * If a user is logged in (sessionAttribute "currentUser" exists), set the currentUser session attribute to null
     * Else redirect the user to the login page
     * */
    public static void logOut(Context context){
        if (context.sessionAttribute("currentUser") != null) {
			context.sessionAttribute("currentUser", null);
			LOGGER.info("User has logged out.");
			context.redirect("/");
		} else {
			//Redirect to login if the user isn't logged in
			LOGGER.error("User isn't logged in.");
    		context.redirect("/");
		}
    }

}
