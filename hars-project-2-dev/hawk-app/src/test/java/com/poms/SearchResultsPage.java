package com.poms;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SearchResultsPage {
	
	@FindBy(id = "0")
	public WebElement firstRadio; 
//	@FindBy(css="div.card:nth-child(1) > div:nth-child(1) > h4:nth-child(1) > input:nth-child(1)")
//	public WebElement firstRadio;
	@FindBy(id = "button")
	public WebElement nextBtn;
	
	
	public SearchResultsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	};
	
	public void clickFirstRadio() {
		this.firstRadio.click();
	};
	
	public void clickNext() {
		this.nextBtn.click();
	};
}
