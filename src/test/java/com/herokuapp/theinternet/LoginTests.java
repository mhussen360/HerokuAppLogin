package com.herokuapp.theinternet;// Step 1: Create Package

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests { // Step 2: Create class (Test Case(s))

	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {

		// Steps to test login functionality
		System.out.println("Starting LoginTest");

		// Step 1: Create Driver (What is a driver)
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize(); // Full screen

		// Step 2: Open Page (Test Page)
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened succefully");

	}

	@Test(priority = 1, groups = { "positiveTest" })
	public void positiveLoginTest() {// login test Case

		// Step 3: Enter Username
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		// driver.findElement(By.id("username")).sendKeys("tomsmith");
		username.sendKeys("tomsmith");

		// Step 4: Enter Password
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));

		password.sendKeys("SuperSecretPassword!");

		// Step 5: Click Login Button
		driver.findElement(By.xpath("//button[@class='radius']")).click();

		// Verifications
		// Verify that expected url matches the current url
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "The Expected Url does Not match the Actual Url!!");

		// Verify that logout button is visible after logging in
		WebElement loginoutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(loginoutButton.isDisplayed(), "Logout Button is Not Visible");

		// Verify that successful message appears after logging in
		String ActualsuccessMessage = driver.findElement(By.xpath("//div[@id='flash']")).getText();
		Assert.assertTrue(ActualsuccessMessage.contains("You logged into a secure area!"),
				"Actual Successful message does Not contain Sucess message ");

		// Wait to see result
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2)
	public void negativeLoginTests(String uname, String pword, String expMessage) {// Invalid username test Case

		// Step 3: Enter Invalid Username
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		username.sendKeys(uname);

		// Step 4: Enter Password
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys(pword);

		// Step 5: Click Login Button
		driver.findElement(By.xpath("//button[@class='radius']")).click();

		// Verifications

		// Verify that Invalid username message appears after logging in String
		String usernameMessage = driver.findElement(By.xpath("/html//div[@id='flash']")).getText();
		Assert.assertTrue(usernameMessage.contains(expMessage), "Invalid Username message did Not Appear");

		// Wait to see result
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close Browser - Destroys driver instance
		driver.quit();
	}

}
