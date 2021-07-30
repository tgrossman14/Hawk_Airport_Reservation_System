package com.poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HARSRegisterPage {
    @FindBy(id="firstName")
    public WebElement firstNameBox;
    @FindBy(id="lastName")
    public WebElement lastNameBox;
    @FindBy(id="email")
    public WebElement emailBox;
    @FindBy(id="password1")
    public WebElement passwordBox;
    @FindBy(id="register")
    public WebElement registerButton;
    @FindBy(className = "navbar-brand")
    public WebElement registerLink;

    public HARSRegisterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void registerUser(String firstName, String lastName, String email, String password){
        this.firstNameBox.sendKeys(firstName);
        this.lastNameBox.sendKeys(lastName);
        this.emailBox.sendKeys(email);
        this.passwordBox.sendKeys(password);
    }
    public void clickRegisterLink(){
        this.registerLink.click();
    }
    public void clickRegisterButton(){
        this.registerButton.click();
    }
}
