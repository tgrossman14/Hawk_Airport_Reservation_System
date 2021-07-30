package com.poms;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeatmapPage {
	@FindBy(css="#Flight-0 > div:nth-child(3) > div:nth-child(4)")
	public WebElement chosenSeatFlightZero;
	@FindBy(css="#map-section > nav:nth-child(1) > a:nth-child(2)")
	public WebElement secondTab;
	@FindBy(css="#Flight-1 > div:nth-child(3) > div:nth-child(4)")
	public WebElement chosenSeatFlightOne;
	@FindBy(id="book-seats-button")
	public WebElement bookSeatsButton;
	@FindBy(css="#Flight-0 > div:nth-child(3) > div:nth-child(5)")
	public WebElement editedSeat;
	@FindBy(id="edit-seats-button")
	public WebElement editSeatsButton;
	
	public SeatmapPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void clickSeats() {
		this.chosenSeatFlightZero.click();
		this.secondTab.click();
		this.chosenSeatFlightOne.click();
	}
	
	public void clickBookSeatsButton() {
		this.bookSeatsButton.click();
	}
	
	public void clickNewSeat() {
		this.editedSeat.click();
	}
	
	public void clickEditSeatsButton() {
		this.editSeatsButton.click();
	}
	
}
