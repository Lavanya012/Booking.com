package com.booking.SearchHotel.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class UserDetailsPage {
	
	WebDriver driver;
	
	@FindBy(id = "bp_travel_purpose_leasure")
	WebElement radio_travelPurpose;
	
	@FindBy(id ="booker_title")
	WebElement dropDown_title;
	
	@FindBy(id = "firstname")
	WebElement input_firstname;
	
	@FindBy(id = "lastname")
	WebElement input_lastname;
	
	@FindBy(id = "email")
	WebElement input_email;
	
	@FindBy(id = "email_confirm")
	WebElement input_emailConfirm;
	
	@FindBy(id = "notstayer_false")
	WebElement radio_mainGuest;
	
	@FindBy(xpath = "//div[@class='max-persons-details']/select")
	WebElement dropdown_Guest;
	
	@FindBy(xpath = "//input[contains(@id,'guest_name')]")
	WebElement input_GuestFullName;
	
	@FindBy(name = "book")
	WebElement button_FinalDetails;
	
	public UserDetailsPage(WebDriver driver)
	{
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickradio_travelPurpose()
	{
		radio_travelPurpose.click();
	}
	
	public void selectTitle(String title)
	{
		Select bookerTitle= new Select(dropDown_title);
		bookerTitle.selectByValue(title);
	}
	
	public void enterfirstname(String firstName)
	{
		input_firstname.sendKeys(firstName);
	}
	
	public void enterLastname(String lastName)
	{
		input_lastname.sendKeys(lastName);
	}
	
	public void enterEmail(String email)
	{
		input_email.sendKeys(email);
	}
	public void enterConfirmEmail(String confirmEmail)
	{
		input_emailConfirm.sendKeys(confirmEmail);
	}
	
	public void clickradio_mainGuest()
	{
		radio_mainGuest.click();
	}
	
	public void selectdropdown_Guest(String guestNum)
	{
		Select guest= new Select(dropdown_Guest);
		guest.selectByValue(guestNum);
	}
	
	public void enterGuestFullName(String guestFullName)
	{
		input_GuestFullName.sendKeys(guestFullName);
	}
	
	public void clickFinalDetails()
	{
		button_FinalDetails.click();
	}
	
	public  boolean isTitleDropdownAvailable()
	{
		
		boolean isTitleAvailable =driver.findElements(By.id("booker_title")).isEmpty();
		return isTitleAvailable;
		
	}

}
