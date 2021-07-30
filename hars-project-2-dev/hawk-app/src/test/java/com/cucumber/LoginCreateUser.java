package com.cucumber;

import com.poms.DashboardPage;
import com.poms.HARSLoginPage;
import com.poms.HARSRegisterPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class LoginCreateUser {
    WebDriver driver;
    @Before
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.firefox.driver", "drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("http://localhost:5000/");
    }
    @Given("a user is on the login page")
    public void a_user_is_on_the_login_page() {
        assert driver.getCurrentUrl().equals("http://localhost:5000/");
    }

    @When("a user enters the correct email and password and click the login button")
    public void a_user_enters_the_correct_email_and_password_and_click_the_login_button() {
        HARSLoginPage loginPage = new HARSLoginPage(driver);
        loginPage.login("bobjim@lakers.com", "bobjim");
    }

    @Then("the user is redirected to the dashboard")
    public void the_user_is_redirected_to_the_dashboard() throws InterruptedException {
        assert driver.getCurrentUrl().equals("http://localhost:5000/api/dashboard");
        Thread.sleep(2000);
    }

    @When("a user enters the all of its information to the register page and click the submit button")
    public void a_user_enters_the_all_of_its_information_to_the_register_page_and_click_the_submit_button() {
        HARSRegisterPage registerPage = new HARSRegisterPage(driver);
        registerPage.clickRegisterLink();
        assert driver.getCurrentUrl().equals("http://localhost:5000/new_user.html");
        registerPage.registerUser("Mike","Cameron","mc@lakers.com","mikecam");
        registerPage.clickRegisterButton();
    }

    @Then("the user is redirected to booking a new flight")
    public void the_user_is_redirected_to_the_login_page_and_login_with_the_right_credentials() {
        assert driver.getCurrentUrl().equals("http://localhost:5000/api/register");
    }

    @When("a user enters the incorrect email and incorrect password")
    public void a_user_enters_the_incorrect_email_and_incorrect_password() {
        HARSLoginPage loginPage = new HARSLoginPage(driver);
        loginPage.login("invalid@lakers.com", "invalid");
    }

    @Then("the user is not redirected to the dashboard page")
    public void the_user_is_not_redirected_to_the_dashboard_page() {
        assert driver.getCurrentUrl().equals("http://localhost:5000/");
    }
    @When("a user enters correct the correct credentials and click the login button")
    public void a_user_enters_correct_the_correct_credentials_and_click_the_login_button() {
        HARSLoginPage loginPage = new HARSLoginPage(driver);
        loginPage.login("bobjim@lakers.com", "bobjim");
    }

    @Then("the user is redirected to the dashboard and click the logout button")
    public void the_user_is_redirected_to_the_dashboard_and_click_the_logout_button() throws InterruptedException {
        assert driver.getCurrentUrl().equals("http://localhost:5000/api/dashboard");
        Thread.sleep(2000);
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickLogOut();
        assert driver.getCurrentUrl().equals("http://localhost:5000/");
    }
    @After
    public void teardown() {
        this.driver.quit();
    }
}
