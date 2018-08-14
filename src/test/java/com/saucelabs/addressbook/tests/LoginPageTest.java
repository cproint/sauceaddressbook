package com.saucelabs.addressbook.tests;

import com.saucelabs.addressbook.base.TestBase;
import com.saucelabs.addressbook.pages.LoginPage;
import com.saucelabs.addressbook.pages.LogoutPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 * @author mtulugu
 *
 */
public class LoginPageTest extends TestBase {
	
	//private static final Logger logger = Logger.getLogger(LoginPageTest.class.getName());
	
	LoginPage loginPage;
	LogoutPage logoutPage;

	public LoginPageTest() {
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
	
	@DataProvider(name = "invalidCredentials")
	public static Object[][] invalidLoginCredentials() {
	    return new Object[][] {
	    							{"abc@ams.com", "sasasasassasas"},
	    							{"abc@ams.com", ""},
	    							{"abc@ams.com", " "},
	    							{"abc@ams.com", "sasasasa"},
	    							{"", ""},
	    							{" ", " "}
	    						  };
	}
	
	@BeforeMethod
	public void setup(){
	  
		//logger.info("Before calling launchBrowser()");
		launchBrowser();
		loginPage = new LoginPage();
		
		
	}

	//Use the below Retry Logic only if few Test Cases are to specifically re-run 
	//in case of failure. This is not a good approach. better to retest failed testcases
	//at Test Suite Level (Run Time)
	//@Test(priority = 1, retryAnalyzer = com.amsarmada.qa.automation.retryanalyzer.RetryAnalyzer.class)
	@Test(priority = 1, groups = {"sanity"}, enabled = true)
	public void verifyLoginPageTitle() {
		// Tried with this.getClass().getEnclosingMethod().getName()... Not working...shows NP Exception  
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());
		assertEquals("Address Book - Sign In",loginPage.getLoginPageTitle(),"Login Page Title Not Matched");
		
	}
	
	@Test(priority = 2, groups = {"sanity"}, enabled = true)
	public void verifyLoginPageEmailTextBox() {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());
		assertTrue(loginPage.isLoginEmailTextboxDisplayed());
		assertTrue(loginPage.isLoginEmailTextboxEnabled());
		assertTrue(loginPage.isLoginEmailTextboxEmpty());
	}
	
	@Test(priority = 3, groups = {"sanity"}, enabled = true)
	public void verifyLoginPagePasswordTextBox() {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());
		assertTrue(loginPage.isLoginPasswordTextboxDisplayed());
		assertTrue(loginPage.isLoginPasswordTextboxEnabled());
		assertTrue(loginPage.isLoginPasswordTextboxEmpty());
	}
	
	@Test(priority = 4, groups = {"sanity"}, enabled = false)
	public void verifyLoginButton() {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());
		assertTrue(loginPage.isLoginButtonDisplayed());
		assertTrue(loginPage.isLoginButtonEnabled());
		assertTrue(loginPage.isLoginPasswordTextboxEmpty());
		assertEquals("Sign in", loginPage.getLoginButtonText());
	}
		
	
	@Test(priority = 5, groups = {"regression"}, enabled = true, dataProvider = "invalidCredentials")
	public void verifyLoginToAddressBookWithInvalidCredentials(String email, String password) {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());
		try {
			logoutPage = loginPage.loginToAddressBook(email, password);
			assertTrue(loginPage.isInvalidCredentialsErrorMessageDisplayed());
		}
		catch(Exception exception) {
			logger.info("Should not be able to login with invalid credentials: " + exception.getMessage());
			Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);	
		}
	}
	
	@Test(priority = 7, groups = {"sanity", "regression"}, enabled = false)
	public void verifyLoginToAddressBookWithValidCredentials() {
		class Local{};
		extentTest = extent.startTest(Local.class.getEnclosingMethod().getName());
		//homePage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		//assertTrue(homePage.isHomePageImageDisplayed());		
	}
	

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		takeScreenshotOnTestFailure(result);
		driver.quit();	
	}
	

}
