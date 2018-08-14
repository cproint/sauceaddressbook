package com.saucelabs.addressbook.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.addressbook.base.TestBase;
import com.saucelabs.addressbook.util.TestUtil;

/**
 * @author mtulugu
 *
 */
public class LoginPage extends TestBase {
	


	@FindBy(id = "session_email")
	@CacheLookup
	private WebElement emailTextBox;

	@FindBy(id = "session_password")
	@CacheLookup
	private WebElement passwordTextBox;

	@FindBy(xpath = "//input[@value='Sign in']")
	@CacheLookup
	private WebElement signInButton;

	@FindBy(css = "div.alert.alert-notice")
	@CacheLookup
	private WebElement invalidCredentialsErrorMessage;
		

	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions
	

	public String getLoginPageTitle(){
		return driver.getTitle();
	}
	
	public boolean isLoginEmailTextboxDisplayed(){
		return emailTextBox.isDisplayed();
	}
	
	public boolean isLoginEmailTextboxEnabled(){
		return emailTextBox.isEnabled();
	}
	
	public boolean isLoginEmailTextboxEmpty(){
		return emailTextBox.getText().isEmpty();
	}
		
	public boolean isLoginPasswordTextboxDisplayed(){
		return passwordTextBox.isDisplayed();
	}
	
	public boolean isLoginPasswordTextboxEnabled(){
		return passwordTextBox.isEnabled();
	}
	
	public boolean isLoginPasswordTextboxEmpty(){
		return passwordTextBox.getText().isEmpty();
	}
	
	public boolean isLoginButtonDisplayed(){
		return signInButton.isDisplayed();
	}
	
	public boolean isLoginButtonEnabled(){
		return signInButton.isEnabled();
	}
	
	public String getLoginButtonText(){
		return signInButton.getText();
	}
	
	public boolean isInvalidCredentialsErrorMessageDisplayed() {

		if (TestUtil.isElementPresent(driver, invalidCredentialsErrorMessage)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param email
	 * @param pwd
	 * @return
	 */
	public LogoutPage loginToAddressBook (String email, String pwd){
			emailTextBox.sendKeys(email);
			passwordTextBox.sendKeys(pwd);
			//loginButton.click();
			//Using JavascriptExecutor to click login button
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", signInButton);				
		return new LogoutPage();
	}
	

}
