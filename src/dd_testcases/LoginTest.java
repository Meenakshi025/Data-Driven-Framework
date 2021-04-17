package dd_testcases;


import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dd_core.Page;
import dd_util.TestUtil;


public class LoginTest extends Page {

	
	public static String GlobalUsername;
	
	@BeforeTest
	public void isSkip(){
		
		if(!TestUtil.isExecutable("LoginTest")){
			
			throw new SkipException("Skipping the test as the Run mode is No");
			
		}
		
		
	}
	
	@Test(dataProvider="getData")
	public void doLogin(String username,String password) throws IOException{
		
		logs.debug("Inside Login Test");
		
		findElement("SignIn").click();
		findElement("username").sendKeys(username);
		findElement("password").sendKeys(password);
		findElement("login").click();
		TestUtil.CaptureScreenshot();
		GlobalUsername = username;
		
	
	}
	
	
	@DataProvider
	public static Object[][] getData(){
		
		return TestUtil.getData("LoginTest");
		
		
		
	}
	
	
	
	
}
