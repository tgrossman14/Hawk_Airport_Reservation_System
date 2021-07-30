package com.poms;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HARSLoginPage {
	@FindBy(id="exampleInputEmail1")
	public WebElement emailBox;
	@FindBy(id="exampleInputPassword1")
	public WebElement passwordBox;
	@FindBy(css=".card-footer")
	public WebElement loginButton;
	
	public HARSLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void login(String email, String password) {
		this.emailBox.sendKeys(email);
		this.passwordBox.sendKeys(password);
		this.loginButton.click();
	}
}
