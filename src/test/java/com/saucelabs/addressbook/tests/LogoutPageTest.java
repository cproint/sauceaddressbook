package com.saucelabs.addressbook.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.saucelabs.addressbook.base.TestBase;
import com.saucelabs.addressbook.pages.LoginPage;
import com.saucelabs.addressbook.pages.LogoutPage;

/**
 * @author mtulugu
 *
 */
public class LogoutPageTest extends TestBase{
	
	LoginPage loginPage;
	LogoutPage logoutPage;
	
	public LogoutPageTest() {
		super();
	}
	
	@BeforeTest
	public void setExtent(){
		setupExtentReport();
	}
	
	@AfterTest
	public void endReport(){
		teardownExtentReport();	
	}
	
	@BeforeMethod
	public void setup(){	
	}
	

	private void setupForLogoutLink() throws MalformedURLException{		
		launchBrowser();
		loginPage = new LoginPage();
		loginPage.loginToAddressBook("user@example.com", "password");
		logoutPage = new LogoutPage();

	}
	
	@Test(priority = 1, groups = {"sanity"}, enabled = true)
	public void VerifyLogoutPageIsDisplayed() throws MalformedURLException {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());

		setupForLogoutLink();
		assertTrue(logoutPage.isLogoutPageDisplayed());
	}
	
	
	@Test(priority = 2, groups = {"sanity"}, enabled = true)
	public void VerifyLoginPageIsDisplayed() throws MalformedURLException {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());

		setupForLogoutLink();

		logoutPage.clickSigoutLink();
		//loginPage = new LoginPage();
		assertTrue(new LoginPage().isLoginButtonDisplayed());
	}
		
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		takeScreenshotOnTestFailure(result);
		driver.quit();	
	}
	

}
