package com.booking.SearchHotel.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HotelLandingPage {
	
	@FindBy(xpath = "//form[@id='hprt-form']//table//tr[1]/td[5]//select")
	WebElement dropDown_roomSelection;
	
	@FindBy(xpath = "//form[@id='hprt-form']//table//tr[1]/td[6]//button[@type='submit']")
	WebElement button_reserve;
	
	WebDriver driver;

	public HotelLandingPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void selectDropdown(String noOfroom)
	{
		Select roomselection= new Select(dropDown_roomSelection);
		roomselection.selectByValue(noOfroom);
	}
	
	public void clickbutton_reserve()
	{
		button_reserve.click();
	}
}
