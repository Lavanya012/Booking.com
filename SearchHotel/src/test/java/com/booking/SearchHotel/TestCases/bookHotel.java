package com.booking.SearchHotel.TestCases;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.booking.SearchHotel.Objects.*;
import com.booking.SearchHotel.Utils.GlobalVariable;

public class bookHotel {

	WebDriver driver;
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void launchBrowser() {

		System.out.println("Open browser function");
		System.out.println(GlobalVariable.browser);
		switch (GlobalVariable.browser) {

		case "chrome":
			System.setProperty("webdriver.chrome.driver", GlobalVariable.ChromedriverPath);
			driver = new ChromeDriver();
			System.out.println("Chrome Launched");
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", GlobalVariable.FirefoxdriverPath);
			driver = new FirefoxDriver();
			System.out.println("Firefox Launched");
			break;
		}

		driver.get(GlobalVariable.url);
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String title = driver.getTitle();
		softAssert.assertTrue(title.contains("Booking.com:"), "Navigation to Booking.com Pass");

	}

	//The data is parameterized and picked from TestNg XML.
	@Test
	@Parameters({ "placeName", "fName", "lName", "email", "checkin_month", "checkin_date", "checkin_year",
			"checkout_month", "checkout_date", "checkout_year" })
	public void bookAHotel(String placeName, String fName, String lName, String email, String checkin_month,
			String checkin_date, String checkin_year, String checkout_month, String checkout_date, String checkout_year)
			throws InterruptedException {
		HomePage home = new HomePage(driver);
		SearchResultPage sresult = new SearchResultPage(driver);
		HotelLandingPage hscreen = new HotelLandingPage(driver);
		UserDetailsPage details = new UserDetailsPage(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		/* Selection of Location */
		home.enterHotelString(placeName);

		home.selectLocation(placeName);

		/* Selection of number of rooms */
		home.clickRoomroomSelection();

		/*
		 * This section is to handle A/B testing as sometime room section is
		 * dropdown and sometime it is button
		 */
		if (home.isbutton_roomAvailable()) {
			home.selectRoom("4");
		} else {
			System.out.println("Enter else");
			home.buttonClickRoom();
		}
		// Setting Check in Date using JavaScript*/
		js.executeScript("arguments[0].setAttribute('value','" + checkin_month + "');", HomePage.input_checkin_month);
		js.executeScript("arguments[0].setAttribute('value','" + checkin_date + "');", HomePage.input_checkin_date);
		js.executeScript("arguments[0].setAttribute('value','" + checkin_year + "');", HomePage.input_checkin_Year);

		// Setting Check out Date using JavaScript*/
		js.executeScript("arguments[0].setAttribute('value','" + checkout_month + "');", HomePage.input_checkout_month);
		js.executeScript("arguments[0].setAttribute('value','" + checkout_date + "');", HomePage.input_checkout_date);
		js.executeScript("arguments[0].setAttribute('value','" + checkout_year + "');", HomePage.input_checkout_Year);

		home.clickSearch();
		String parentWindow = driver.getWindowHandle();
		String title_searchResult = driver.getTitle();
		softAssert.assertTrue(title_searchResult.contains(placeName),
				"Navigation to Hotel Main screen Passed");

		sresult.clickLink();

		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (!parentWindow.equalsIgnoreCase(window)) {
				driver.switchTo().window(window);
				break;
			}

		}

		hscreen.selectDropdown("1");
		hscreen.clickbutton_reserve();
		String title_yourdetails = driver.getTitle();
		softAssert.assertTrue(title_yourdetails.contains("Your Details"),
				"Navigation to your details page is Passed");
		/*
		 * This if condition is to handle title dropdown sometime it does not
		 * appear
		 */
		if (!details.isTitleDropdownAvailable()) {
			details.selectTitle("mr");
		}
		details.clickradio_travelPurpose();
		details.enterfirstname(fName);
		details.enterLastname(lName);
		details.enterEmail(email);
		details.enterConfirmEmail(email);
		details.clickradio_mainGuest();
		details.clickFinalDetails();
		String title_finaldetails = driver.getTitle();
		softAssert.assertTrue(title_finaldetails.contains("Final Details"),
				"Navigation to Final details page is Passed");
	}

	@AfterClass
	public void cleaUp() {
		softAssert.assertAll();
		driver.close();
		driver.quit();
	}
}