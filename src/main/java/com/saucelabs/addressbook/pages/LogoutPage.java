package com.saucelabs.addressbook.pages;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.addressbook.base.TestBase;
import com.saucelabs.addressbook.util.TestUtil;
/**
 * @author mtulugu
 *
 */
public class LogoutPage extends TestBase{
	
	@FindBy(xpath = "//a[@data-test='home']")
	private WebElement homeLink;
	
	@FindBy(xpath = "//a[@data-test='addresses']")
	private WebElement addressesLink;

	@FindBy(xpath = "//a[@data-test='sign-out']")
	private WebElement signOutLink;
	
	public LogoutPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions

	public boolean isLogoutPageDisplayed() {

		if (TestUtil.isElementPresent(driver, homeLink) 
				&& TestUtil.isElementPresent(driver, addressesLink) 
				&& TestUtil.isElementPresent(driver, signOutLink)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param email
	 * @param pwd
	 * @return
	 */
	public LoginPage clickSigoutLink (){
			//Using JavascriptExecutor to click login button
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", signOutLink);				
		return new LoginPage();
	}

}
