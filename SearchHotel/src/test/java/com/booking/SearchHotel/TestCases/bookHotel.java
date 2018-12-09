package com.booking.SearchHotel.TestCases;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
		driver.manage().window().maximize();
		String title = driver.getTitle();
		softAssert.assertTrue(title.contains("Booking.com:"), "Navigation to Booking.com Pass");

	}

	@Test
	public void bookAHotel() throws InterruptedException {
		HomePage home = new HomePage(driver);
		SearchResultPage sresult = new SearchResultPage(driver);
		HotelLandingPage hscreen = new HotelLandingPage(driver);
		UserDetailsPage details = new UserDetailsPage(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		/* Selection of Location */
		home.enterHotelString(GlobalVariable.placename);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		home.selectLocation(GlobalVariable.placename);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		/* Selection of number of rooms */
		home.clickRoomroomSelection();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		/*This section is to handle A/B testing as sometime room section is dropdown and sometime it is button*/
		if (home.isbutton_roomAvailable()) {
			home.selectRoom("4");
		} else {
			System.out.println("Enter else");
			home.buttonClickRoom();
		}
		// Setting Check in Date using JavaScript*/
		js.executeScript("arguments[0].setAttribute('value','02');", HomePage.input_checkin_month);
		js.executeScript("arguments[0].setAttribute('value','10');", HomePage.input_checkin_date);
		js.executeScript("arguments[0].setAttribute('value','2019');", HomePage.input_checkin_Year);
		
		// Setting Check out Date using JavaScript*/
		js.executeScript("arguments[0].setAttribute('value','02');", HomePage.input_checkout_month);
		js.executeScript("arguments[0].setAttribute('value','15');", HomePage.input_checkout_date);
		js.executeScript("arguments[0].setAttribute('value','2019');", HomePage.input_checkout_Year);

		home.clickSearch();
		String parentWindow = driver.getWindowHandle();
		String title_searchResult = driver.getTitle();
		softAssert.assertTrue(title_searchResult.contains(GlobalVariable.placename),"Mavigation to Hotel Main screen Passed");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		sresult.clickLink();

		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (!parentWindow.equalsIgnoreCase(window)) {
				driver.switchTo().window(window);
				System.out.println(driver.getTitle());
				break;
			}

		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		hscreen.selectDropdown("1");
		hscreen.clickbutton_reserve();
		Thread.sleep(5000);
		System.out.println(details.isTitleDropdownAvailable());
		
		if (!details.isTitleDropdownAvailable()) {
			details.selectTitle("mr");
		}
		details.clickradio_travelPurpose();
		details.enterfirstname("Lavnya");
		details.enterLastname("upadhyay");
		details.enterEmail("lavnyaup@gmail.com");
		details.enterConfirmEmail("lavnyaup@gmail.com");
		details.clickradio_mainGuest();
		details.clickFinalDetails();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@AfterClass
	public void cleaUp() {
		softAssert.assertAll();
		driver.close();
		driver.quit();
	}
}