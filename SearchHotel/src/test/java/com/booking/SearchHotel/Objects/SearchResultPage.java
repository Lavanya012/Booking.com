package com.booking.SearchHotel.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultPage {

	@FindBy(xpath = "//div[@id='hotellist_inner']/div[1]//h3//span[1]")
	WebElement link_hotelName;
	WebDriver driver;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickLink() {
		link_hotelName.click();
	}

}
