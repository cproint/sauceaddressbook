package com.saucelabs.addressbook.retryanalyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
	
	int counter = 0;
	int retryLimit = 0; // chande this more than 1 to retry 
	
	public boolean retry(ITestResult result){
		
		if (counter < retryLimit){
			counter++;
			return true;
		}
		return false;
	}
	

	public String getResultStatusName(int status) {
		String resultName = null;
		if (status == 1)
			resultName = "SUCCESS";
		if (status == 2)
			resultName = "FAILURE";
		if (status == 3)
			resultName = "SKIP";
		return resultName;
}
}
