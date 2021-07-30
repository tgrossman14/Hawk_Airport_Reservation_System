package com.poms;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class DestinationsPage {
	
	@FindBy(id = "departureCity")
	public WebElement departCityInput;
	@FindBy(id = "departureState")
	public WebElement departStateSelect;
	@FindBy(id = "radius")
	public WebElement departRadiusInput;
	@FindBy(id = "arrivalCity")
	public WebElement arrivalCityInput;
	@FindBy(id = "arrivalState")
	public WebElement arrivalStateSelect;
	@FindBy(id = "destination-radius")
	public WebElement destinationRadiusInput;
	@FindBy(id = "searchBtn")
	public WebElement searchBtn;
	
	public DestinationsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void insertDepartKeys(String departCity, String departState, String departRadius) {
		this.departCityInput.sendKeys(departCity);
		this.departStateSelect.sendKeys(departState);
		this.departRadiusInput.sendKeys(departRadius);
	}
	
	public void insertArrivalKeys(String arrivalCity, String arrivalState, String arrivalRadius) {
		this.arrivalCityInput.sendKeys(arrivalCity);
		this.arrivalStateSelect.sendKeys(arrivalState);
		this.destinationRadiusInput.sendKeys(arrivalRadius);
	}
	
	public void clickSearch() {
		this.searchBtn.click();
	}
}
