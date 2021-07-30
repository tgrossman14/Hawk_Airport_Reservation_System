package com.cucumber;

import static org.junit.Assert.assertArrayEquals;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.poms.AirportResultsPage;
import com.poms.CheckoutPage;
import com.poms.DestinationsPage;
import com.poms.HARSLoginPage;
import com.poms.SearchResultsPage;
import com.poms.DashboardPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookAndDeleteRes {
	WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.firefox.driver", "drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:5000/");
	};
	
	@Given("a user has logged inn")
	public void a_user_has_logged_in() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/");
		HARSLoginPage loginPage = new HARSLoginPage(driver);
		loginPage.login("bobjim@lakers.com", "bobjim");
	};
	
	@When("a user clicks on the Book New Trip link")
	public void a_user_click_book_new_trip() {
		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.clickBookTripLink();
	};
	
	@When("the user enters the departure city, state, and radius")
	public void a_user_enter_depart_info() {
		DestinationsPage dp = new DestinationsPage(driver);
		dp.insertDepartKeys("Houston", "Texas", "30");
	};
	
	@When("the user enters the arrival city, state, and radius")
	public void a_user_enters_arrival_info() {
		DestinationsPage dp = new DestinationsPage(driver);
		dp.insertArrivalKeys("Los Angeles", "California", "30");
	};
	
	@Then("the user is redirected to the airport results page")
	public void a_user_clicks_search() {
		DestinationsPage dp = new DestinationsPage(driver);
		dp.clickSearch();
	};
	
	@Then("available airports are displayed")
	public void user_is_redirected() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/airport-results?departureCity=Houston&departureState=TX&radius=30&arrivalCity=Los+Angeles&arrivalState=CA&destination-radius=30");
	};
	
	@Given("a user is on the airport results page")
	public void user_is_on_results_page() {
		driver.get("http://localhost:5000/api/airport-results?departureCity=Houston&departureState=TX&radius=30&arrivalCity=Los+Angeles&arrivalState=CA&destination-radius=30");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		assert driver.getCurrentUrl().contains("/api/airport-results");
	};
	
	@When("the user selects a departure airport")
	public void user_selects_depart_airport() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.selectDepartAirport();
	};
	
	@When("the user selects a landing airport")
	public void user_selects_arrival_airport() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.selectLandingAirport();
	};
	
	@When("the user selects a departure date")
	public void user_selects_departure_date() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.selectDepartureDate("07/27/2021");
	};
	
	// Round trip scenarios
	@When("the user specifies round trips")
	public void user_specifies_round_trip() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.clickRoundTrip();
	};
	
	@When("the user selects a return date")
	public void user_selects_return_date() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.selectReturnDate("07/30/2021");
	};
	
	@When("the user selects the number of passengers")
	public void user_selects_num_of_pass() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.selectNumOfPass("1");
	};
	
	@When("the user selects the travel class")
	public void user_selects_travel_class() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.selectTravelClass();
	};
	
	@When("the user specifies connecting flights")
	public void user_selects_conn_flight() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.clickConnected();
	};
	
	@When("the user selects the max price")
	public void user_selects_max_price() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.inputMaxPrice("400");
	};
	
	@Then("the user is redirected to the search results page")
	public void user_redirected_results_page() {
		AirportResultsPage arp = new  AirportResultsPage(driver);
		arp.clickNext();
	};
	
	@Then("available flights matching the criteria are displayed")
	public void user_has_flights_displayed() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/search-results?originLocationCode=IAH&destinationLocationCode=LAX&departureDate=2021-07-27&returnDate=2021-07-30&adults=2&nonStop=false&travelClass=ECONOMY&maxPrice=400");
	};
	
	@Given("the user is on the search results page")
	public void user_on_search_results_page() {
		driver.get("http://localhost:5000/api/search-results?originLocationCode=IAH&destinationLocationCode=LAX&departureDate=2021-07-27&returnDate=2021-07-30&adults=2&nonStop=false&travelClass=ECONOMY&maxPrice=400");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	};
	
	@When("the user selects the desired flight")
	public void user_selects_flight() {
		SearchResultsPage srp = new SearchResultsPage(driver);
		srp.clickFirstRadio();
	};
	
	@When("the user clicks the submit button")
	public void user_clicks_submit() {
		SearchResultsPage srp = new SearchResultsPage(driver);
		srp.clickNext();
	};
	
	@Then("the user is redirected to the create order page")
	public void user_redirected_to_checkout() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/price-results?flightSelection=0");
	};
	
	@Given("the user is on the create order page")
	public void user_on_checkout() {
		driver.get("http://localhost:5000/api/search-results?originLocationCode=IAH&destinationLocationCode=LAX&departureDate=2021-07-27&returnDate=2021-07-30&adults=2&nonStop=false&travelClass=ECONOMY&maxPrice=400");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		SearchResultsPage srp = new SearchResultsPage(driver);
		srp.clickFirstRadio();
		srp.clickNext();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		assert driver.getCurrentUrl().contains("/api/price-results");
	};
	
	@When("the user enters payment information")
	public void user_enter_payment_info() {
		CheckoutPage cp = new CheckoutPage(driver);
		cp.insertPaymentInfo("John Doe", "1111222233334444", "05/23", "123");
	};
	
	@When("selects their luggage")
	public void user_selects_luggage() {
		CheckoutPage cp = new CheckoutPage(driver);
		cp.selectMedium();
	};
	
	@When("enters the passengers' names")
	public void user_enters_name() {
		CheckoutPage cp = new CheckoutPage(driver);
		cp.insertPassName("John", "Doe");
	};
	
	@When("clicks the button")
	public void user_clicks_confirm() {
		CheckoutPage cp = new CheckoutPage(driver);
		cp.clickConfirm();
	};
	
	@Then("the user is redirectedd to the dashboard page")
	public void user_redirected_to_dashboard() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/dashbord");
	};
	
	@Then("the new flight is booked")
	public void user_flight_booked() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	};
	
	@Given("a user is on the dashboard page")
	public void a_user_is_on_the_dashboard_page() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/dashboard");
		assert driver.getCurrentUrl().equals("http://localhost:5001/api/dashboard");
	};
	
	@When("the user clicks the delete button for a certain reservation")
	public void the_user_clicks_the_delete_button_for_a_certain_reservation() {
		DashboardPage dp = new DashboardPage(driver);
		dp.clickDeleteButton();
	};
	
	@Then("the user is redirected back to the dashboard page")
	public void user_redirected_back_to_the_dashboard() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/dashbord");
		assert driver.getCurrentUrl().equals("http://localhost:5001/api/dashbord");
	};
	
	@Then("the reservation is deleted")
	public void the_reservation_is_deleted() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	};
	
	@After
	public void teardown() {
		this.driver.quit();
	}
}
