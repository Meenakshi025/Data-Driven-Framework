package dd_core;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import dd_util.DbManager;
import dd_util.TestUtil;
import dd_util.Xls_Reader;

public class Page {
	
	
	public static WebDriver driver;
	public static Properties Config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Xls_Reader excel = new Xls_Reader(System.getProperty("user.dir")+"\\src\\dd_properties\\testdata.xlsx");
	public static Logger logs = Logger.getLogger("devpinoyLogger");
	
	@BeforeSuite
	public void init() throws IOException, AddressException, SQLException, ClassNotFoundException, MessagingException{
		
		if(driver==null){
		
		fis = new FileInputStream(System.getProperty("user.dir")+"/src/dd_properties/Config.properties");
		Config.load(fis);
		logs.debug("Loaded the Config property file");
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\dd_properties\\OR.properties");
		OR.load(fis);
		logs.debug("loaded the OR property file");
		
		if(Config.getProperty("browser").equals("firefox")){
			
			driver = new FirefoxDriver();
			logs.debug("Loaded Firefox");
			
		}else if(Config.getProperty("browser").equals("ie")){
			
			System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			
		}else if(Config.getProperty("browser").equals("chrome")){
			
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			
		}
		
		driver.get(Config.getProperty("testsiteurl"));
		driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		DbManager.setMysqlDbConnection();
		}
		
		
	}
	
	public static WebElement findElement(String key) throws IOException{
		
		try{
		
			
			return driver.findElement(By.xpath(OR.getProperty(key)));
		
		
		}catch(Throwable t){
			
			
			TestUtil.CaptureScreenshot();
			return null;
			
		}
		
		
	}
	
	
	
	@AfterSuite
	public void QuitDriver(){
		
		//send mail
		//driver.quit();
		
	}

}
