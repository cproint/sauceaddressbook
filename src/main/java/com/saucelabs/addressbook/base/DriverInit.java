package com.saucelabs.addressbook.base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
/**
 * @author mtulugu
 *
 */
public class DriverInit {

	protected static final Logger logger = Logger.getLogger(DriverInit.class.getName());

	private static DriverInit instanceDriver = null;
	private static WebDriver driver;
	
	static FirefoxOptions options = new FirefoxOptions();

	@SuppressWarnings("unused")
	private void DriverInt() {
		String log4jConfPath = System.getProperty("user.dir")+"/src/main/resources/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}
	
	public static DriverInit getInstance(){
		
		if (instanceDriver == null)
			instanceDriver = new DriverInit();
		return instanceDriver;
		 
	}
	
	public WebDriver openBrowser(String browser){

		if (browser.equalsIgnoreCase("chrome") && System.getProperty("os.name").contains("Mac OS")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver");				
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/Drivers/geckodriver");
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
			driver = new FirefoxDriver(options); //works with Selenium 3.8.1 but not with 3.11.0

		} else if (browser.equalsIgnoreCase("headless") && System.getProperty("os.name").contains("Mac OS")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver");				
	        // Add options to Google Chrome. The window-size is important for responsive sites
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("headless");
	        options.addArguments("window-size=1200x600");
			driver = new ChromeDriver(options); 

		} else if (browser.equalsIgnoreCase("headless") && System.getProperty("os.name").contains("Linux")){
			System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
	        // Add options to Google Chrome. The window-size is important for responsive sites
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("headless");
	        options.addArguments("window-size=1200x600");
			driver = new ChromeDriver(options); 
			
		}
		  else {
			logger.info("Unsupported Browser: "+ browser);
		}
		
		
		return driver; 
	}
	

}
