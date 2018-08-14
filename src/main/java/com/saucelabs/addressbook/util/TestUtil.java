package com.saucelabs.addressbook.util;


import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.saucelabs.addressbook.base.TestBase;

/**
 * @author mtulugu
 *
 */
public class TestUtil extends TestBase {
	



	WebElement element;

	// Start - These are Selenium UI based utility methods
	public void switchToFrame(String frameName){
		driver.switchTo().frame(frameName);
	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		
		}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;

	}
	
	public static boolean isElementPresent(WebDriver webdriver, WebElement webelement) {
		boolean exists = false;

		webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

		try {
			webelement.getTagName();
			exists = true;
		} catch (NoSuchElementException e) {
			logger.info(e.getMessage());
		}

		webdriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

		return exists;
	}
	
	public static boolean isElementPresentByLocator(String locator){
		
		if(driver.findElements(By.cssSelector(locator)).size() > 0){
				return true;
			}else{
				return false;
		}
	}
	
	//This is Explicit wait using locator (Used by non-Page Object Model)
	public void waitForElement(String locator) {
		element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));

	}

	    
		public void clickBrowserBackButton() {

			driver.navigate().back();

		}
		
		

		public void implicitWaitForElement() {
			try {

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} catch (Exception e) {

			}

		}

		public static void fluentWaitForElement(WebElement element) {
			
			try {

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						   .withTimeout(10, TimeUnit.SECONDS)
						   .pollingEvery(10, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class);
				
						wait.until(ExpectedConditions.visibilityOf(element));
						logger.info("Element found: " + element);
						
						} catch (Exception e) {
							logger.info("Element not found: " + element);
			}

		}

		//This is Explicit wait using WebElement (Used in Page Object Model)
		public static void explicitWaitForElement(WebElement element) {
			element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(element));

		}
		
		public void moveMouse() {

			Actions builder = new Actions(driver);
			Action moveMouseOver = builder.moveByOffset(50, 50).build();
			moveMouseOver.perform();
		}	
	    
}

