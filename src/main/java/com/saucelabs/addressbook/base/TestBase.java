package com.saucelabs.addressbook.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.saucelabs.addressbook.util.TestUtil;
import com.saucelabs.addressbook.util.WebEventListner;
/**
 * @author mtulugu
 *
 */
public class TestBase extends DriverInit {
	
	protected static final Logger logger = Logger.getLogger(TestBase.class.getName());
	
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListner eventListner;
	static String browser;
	
	protected static String email;
	protected static String password;
	public static String addressBookURL;

	
	static FirefoxOptions options = new FirefoxOptions();
	
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	
    public String username = System.getenv("SAUCE_USERNAME");

    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    
	public final String URL = "https://" + username + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub";

	
	public TestBase() {

		prop = new Properties();
		try {
			FileInputStream configFile = new FileInputStream (System.getProperty("user.dir") + "/src/main/java/com/saucelabs/addressbook/config/config.properties");
			prop.load(configFile);
			String log4jConfPath = System.getProperty("user.dir")+"/src/main/resources/log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
	
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
        // Read environment from property file
		addressBookURL = prop.getProperty("addressBookURL");

	}
	
	public  void launchBrowser() throws MalformedURLException{
		
		// Commented out from original TestBase which uses DriverInt
		//browser = prop.getProperty("browser");				
		//DriverInit instanceDriver = DriverInit.getInstance();		
		//driver = instanceDriver.openBrowserWithOptions(browser);
		
		
       
		// Added so that tests run on Sauce Platform
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setCapability("extendedDebugging", true);
        driver = new RemoteWebDriver(new java.net.URL(URL), chromeOptions);

		
		
		
		
		e_driver = new EventFiringWebDriver(driver);
		//Now create object of EventListnerHandler to register with EventFiringWebDriver
		eventListner = new WebEventListner();
		e_driver.register(eventListner);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(prop.getProperty("PAGE_LOAD_TIMEOUT")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("IMPLICIT_WAIT_TIMEOUT")), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		driver.get(addressBookURL);
		logger.info("Addressbook URL is launched");
	}
	
	public static void setupExtentReport(){
		
		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
			logger.info("Hostname can not be resolved");
		}
		
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html", true);
		extent.addSystemInfo("Host Name", hostname);
		extent.addSystemInfo("User Name", "Murali");
		extent.addSystemInfo("Environment", "Mac OS");		
	}
	
	public static void teardownExtentReport(){
		extent.flush();
		extent.close();		
	}
	

	public static void takeScreenshotOnTestFailure(ITestResult result) throws IOException{
		if(result.getStatus()==ITestResult.FAILURE){
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getName()); //to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getThrowable()); //to add error/exception in extent report
			
			String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //to add screenshot in extent report
			//extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //to add screencast/video in extent report
			//TODO: https://stackoverflow.com/questions/48546783/how-to-record-a-video-in-selenium-webdriver-and-java-api
		}
		else if(result.getStatus()==ITestResult.SKIP){
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS){
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}
				
		extent.endTest(extentTest); //ending test and ends the current test and prepare to create html report

	}
}
