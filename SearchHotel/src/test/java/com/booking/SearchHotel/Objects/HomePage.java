package com.booking.SearchHotel.Objects;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

	WebDriver driver;

	JavascriptExecutor js = (JavascriptExecutor) driver;

	public HomePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "ss")
	WebElement input_searchBox;

	@FindBys(@FindBy(xpath = "//ul[@role='listbox']/descendant::li[@role='option']"))
	List<WebElement> list_hotels;

	@FindBy(xpath = "//input[@name='checkin_month']")
	public static WebElement input_checkin_month;

	@FindBy(xpath = "//input[@name='checkin_monthday']")
	public static WebElement input_checkin_date;

	@FindBy(xpath = "//input[@name='checkin_year']")
	public static WebElement input_checkin_Year;

	@FindBy(xpath = "//input[@name='checkout_month']")
	public static WebElement input_checkout_month;

	@FindBy(xpath = "//input[@name='checkout_monthday']")
	public static WebElement input_checkout_date;

	@FindBy(xpath = "//input[@name='checkout_year']")
	public static WebElement input_checkout_Year;

	@FindBy(id = "xp__guests__toggle")
	WebElement input_roomSelection;

	@FindBy(id = "no_rooms")
	WebElement dropdown_rooms;

	@FindBy(id = "group_adults")
	WebElement dropdown_adults;

	@FindBy(xpath = "//input[@id='no_rooms']/following-sibling::button[@data-bui-ref='input-stepper-add-button']")
	WebElement button_room;

	@FindBy(xpath = "//button[@data-sb-id='main']")
	WebElement button_search;

	public void enterHotelString(String placeSortName) {
		input_searchBox.sendKeys(placeSortName);

	}

	public void selectLocation(String placeName) {
		System.out.println("Enter in location");

		for (WebElement element : list_hotels) {
			System.out.println("Enter in list");
			System.out.println(element);
			if (element.getAttribute("innerText").contains(placeName)) {
				element.click();

				break;
			}

		}
	}

	public void setCheckinDate(int cinMonth, int cinDay, int cinYear) {
		js.executeScript("arguments[0].setAttribute('value','" + cinMonth + "');", input_checkin_month);
		js.executeScript("arguments[0].setAttribute('value','" + cinDay + "');", input_checkin_date);
		js.executeScript("arguments[0].setAttribute('value','" + cinYear + "');", input_checkin_Year);
	}

	public void setCheckoutMonth(int coutMonth, int coutDay, int coutYear) {
		js.executeScript("arguments[0].setAttribute('value','" + coutMonth + "');", input_checkout_month);
		js.executeScript("arguments[0].setAttribute('value','" + coutDay + "');", input_checkout_date);
		js.executeScript("arguments[0].setAttribute('value','" + coutYear + "');", input_checkout_Year);
	}

	public void clickRoomroomSelection() {
		input_roomSelection.click();
	}

	public void selectRoom(String room) {
		Select rooms = new Select(dropdown_rooms);
		rooms.selectByValue(room);
	}

	public void selectPerson(String person) {
		Select noOfperson = new Select(dropdown_adults);
		noOfperson.selectByValue(person);
	}

	public void clickSearch() {
		button_search.click();
	}

	public void buttonClickRoom() {
		button_room.click();
	}

	public boolean isbutton_roomAvailable() {

		boolean isAvailable = driver
				.findElements(By
						.xpath("//input[@id='no_rooms']/following-sibling::button[@data-bui-ref='input-stepper-add-button']"))
				.isEmpty();
		return isAvailable;

	}
}
