package com.cucumber;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.poms.HARSLoginPage;
import com.poms.DashboardPage;
import com.poms.SeatmapPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookingEditingSeats {
	WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.firefox.driver", "drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("http://localhost:5000/");
	}
	
	@Given("a user has logged in")
	public void a_user_has_logged_in() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/");
		HARSLoginPage loginPage = new HARSLoginPage(driver);
		loginPage.login("bobjim@lakers.com", "bobjim");
	}
	
	@When("the user clicks the seats link for that reservation")
	public void the_user_clicks_the_seats_link_for_that_reservation() {
		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.clickSeatmapLink();
	}
	
	@Then("the user is redirected to the seatmap display page")
	public void the_user_is_redirected_to_the_seatmap_display_page() {
		assert driver.getCurrentUrl().contains("/api/seatmap-display");
	}
	
	@Given("the user is on the seatmap display page")
	public void the_user_is_on_the_seatmap_display_page() {
		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.clickSeatmapLink();
	}
	
	@When("the user clicks on their desired green available seats")
	public void the_user_clicks_on_their_desired_green_available_seats() {
		SeatmapPage seatmap = new SeatmapPage(driver);
		seatmap.clickSeats();
	}
	
	@When("the user clicks on the Book Seats button")
	public void the_user_clicks_on_the_book_seats_button() {
		SeatmapPage seatmap = new SeatmapPage(driver);
		seatmap.clickBookSeatsButton();
	}
	
	@Then("the user is redirected to the dashboard page")
	public void the_user_is_redirected_to_the_dashboard_page() {
		assert driver.getCurrentUrl().equals("http://localhost:5000/api/dashboard");
	}
	
	@When("the user clicks on the new green available seats")
	public void the_user_clicks_on_the_new_green_available_seats() {
		SeatmapPage seatmap = new SeatmapPage(driver);
		seatmap.clickNewSeat();
	}
	
	@When("the user clicks on the Edit Seats button")
	public void the_user_clicks_on_the_edit_seats_button() {
		SeatmapPage seatmap = new SeatmapPage(driver);
		seatmap.clickEditSeatsButton();
	}
	
	@After
	public void teardown() {
		this.driver.quit();
	}
}
