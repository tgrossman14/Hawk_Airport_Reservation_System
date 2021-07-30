package com.poms;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage {

	@FindBy(id = "nameCard")
	public WebElement nameOnCardInput;
	@FindBy(id = "numberCard")
	public WebElement numOnCardInput;
	@FindBy(id = "expCard")
	public WebElement expOnCardInput;
	@FindBy(id = "cvnCard")
	public WebElement cvnOnCardInput;
	@FindBy(id = "S")
	public WebElement sLuggageRadio;
	@FindBy(id = "M")
	public WebElement mLuggageRadio;
	@FindBy(id = "L")
	public WebElement lLuggageRadio;
	@FindBy(id = "XL")
	public WebElement xlLuggageRadio;
	@FindBy(id = "passenger-0-first-name")
	public WebElement firstNamePassInput;
	@FindBy(id = "passenger-0-last-name")
	public WebElement lastNamePassInput;
	@FindBy(id = "confirmBtn")
	public WebElement confirmBtn;
	
	public CheckoutPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void insertPaymentInfo(String name, String ccNum, String expDate, String cvn) {
		this.nameOnCardInput.sendKeys(name);
		this.numOnCardInput.sendKeys(ccNum);
		this.expOnCardInput.sendKeys(expDate);
		this.cvnOnCardInput.sendKeys(cvn);
	}
	
	public void selectMedium() {
		this.mLuggageRadio.click();
	}
	
	public void insertPassName(String firstName, String lastName) {
		this.firstNamePassInput.sendKeys(firstName);
		this.lastNamePassInput.sendKeys(lastName);
	}
	
	public void clickConfirm() {
		this.confirmBtn.click();
	}
}
