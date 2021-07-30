package com.poms;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AirportResultsPage {
	@FindBy(id = "originLocationCode")
	public WebElement fromSelect;
	@FindBy(id = "destinationLocationCode")
	public WebElement toSelect;
	@FindBy(id = "departureDate")
	public WebElement departDateInput;
	@FindBy(id = "returnDateInput")
	public WebElement returnDateInput;
	@FindBy(id = "adults")
	public WebElement passInput;
	@FindBy(id = "maxPrice")
	public WebElement maxPriceInput;
	@FindBy(id = "button")
	public WebElement nextBtn;
	@FindBy(id = "inlineRadio1")
	public WebElement oneWayRadio;
	@FindBy(id = "inlineRadio2")
	public WebElement roundTripRadio;
	@FindBy(id = "travelClass")
	public WebElement travelClassSelect;
	@FindBy(id = "nonStop")
	public WebElement nonStopRadio;
	@FindBy(id = "connected")
	public WebElement connectedRadio;
	
//	Select dropdown = new Select(this.fromSelect);
//	Select dropdown2 = new Select(this.toSelect);
//	Select dropdown3 = new Select(this.travelClassSelect);
	
	public AirportResultsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	};
	
	public void clickOneWay() {
		this.oneWayRadio.click();
	};
	
	public void clickRoundTrip() {
		this.roundTripRadio.click();
	};
	
	public void selectDepartAirport() {
		//this.dropdown.getOptions().get(1).click();
		this.fromSelect.sendKeys("i");
	};
	
	public void selectLandingAirport() {
		//this.dropdown2.getOptions().get(1).click();
		this.toSelect.sendKeys("l");
	};
	
	public void selectDepartureDate(String departDate) {
		this.departDateInput.sendKeys(departDate);
	};
	
	public void selectReturnDate(String returnDate) {
		this.returnDateInput.sendKeys(returnDate);
	};
	
	public void selectNumOfPass(String numOfPass) {
		this.passInput.sendKeys(numOfPass);
	};
	
	public void selectTravelClass() {
		//this.dropdown3.getOptions().get(1).click();
		this.travelClassSelect.sendKeys("e");
	};
	
	public void clickNonStop() {
		this.nonStopRadio.click();
	};
	
	public void clickConnected() {
		this.connectedRadio.click();
	};
	
	public void inputMaxPrice(String maxPrice) {
		this.maxPriceInput.sendKeys(maxPrice);
	};
	
	public void clickNext() {
		this.nextBtn.click();
	};
	
}
