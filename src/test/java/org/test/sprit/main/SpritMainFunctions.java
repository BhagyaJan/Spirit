package org.test.sprit.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.help.AutoTestHelper;

public class SpritMainFunctions extends AutoTestHelper {

	static WebDriver driver;
	static Robot bot;
	
	@BeforeClass
	public static void launchBrowser() throws IOException, InterruptedException {
	    driver = getChromeDriver();
		driver.get("https://www.spirit.com/");
		Thread.sleep(5000);
		screenshot(driver, "Browser Launched");
	}
	
	@AfterClass
	public static void quiteBrowser() {
		driver.quit();
	}
	
	@Before
	public void startTime() {
		new Date().getTime();
	}
	
	@After
	public void endTime() {
		new Date().getTime();
	}
	
	@Test
	public void main() throws InterruptedException, AWTException, IOException
	{		
		
		try
		{
			
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
			//Select Trip
			handleElementClickXpath(driver, "(//span[contains(text(),'One Way')])[1]");			 
			
			handleElementClickXpath(driver, "//input[contains(@id,'flight-OriginStationCode')]");
			
			handleElementClickXpath(driver, "(//button[contains(text(),'LAX')])[1]");
					    		    
		    handleElementInputXpath(driver, "//input[contains(@id,'flight-DestinationStationCode')]", "New York");
		    
		    handleElementClickXpath(driver, "(//button[contains(text(),'LGA')])[1]");
								
			handleElementClickXpath(driver, "//input[contains(@id,'flight-Date')]");
			
			handleElementClickXpath(driver, "(//td[@class='ng-star-inserted']//span[not(contains(@class,'disabled'))])[1]");
			
			screenshot(driver, "Input Entered on Search Flght Screen");
			
			handleElementClickXpath(driver, "//button[contains(.,'Search Flights')]");
			
			Thread.sleep(5000);
			
			screenshot(driver, "Available Flights screen launched");
			
			//handleElementClickXpath(driver, "(//div[contains(@class,'flex-grow-1 flex-basis-0 d-flex justify-content-center align-items-center ng-star-inserted')])[2]");	
			handleElementClickXpath(driver, "(//app-input[contains(@class,'ng-star-inserted')])[1]");	
			Thread.sleep(2000);
			screenshot(driver, "Flight Seleced");
			handleElementClickXpath(driver, "//button[contains(text(),'Continue')]");
			Thread.sleep(5000);
			screenshot(driver, "Book screen Launched");
			handleElementClickXpath(driver, "//button[contains(text(),'Book It')]");
			Thread.sleep(5000);
			screenshot(driver, "Passenger Details screen Launched");
			List<String> input= excelReadRow("D:\\Selienum Excels\\Spirit Input.xlsx", "Test1", 4, 2);
			
			handleDropDownByVisibleTextXpath(driver, "//select[contains(@name,'title')]",input.get(0));
			handleElementInputXpath(driver, "//input[contains(@name,'firstName')]", input.get(1));
			handleElementInputXpath(driver, "//input[contains(@name,'lastName')]", input.get(2));
			handleElementInputXpath(driver, "//input[contains(@name,'dateOfBirth')]", input.get(3));
			handleElementClickXpath(driver, "//label[contains(@for,'check-usePrimaryAsContactName')]");
			handleElementInputXpath(driver, "//input[contains(@name,'address')]", input.get(4));
			handleElementInputXpath(driver, "//input[contains(@name,'city')]", input.get(5));
			
			handleDropDownByVisibleTextXpath(driver, "//select[contains(@name,'provinceState')]",input.get(6));
			handleElementInputXpath(driver, "//input[contains(@name,'postalCode')]", input.get(7));
			handleDropDownByVisibleTextXpath(driver, "//select[contains(@name,'countryCode')]",input.get(8));
			handleElementInputXpath(driver, "//input[contains(@name,'contactEmailPrimary')]", input.get(9));
			handleElementInputXpath(driver, "//input[contains(@name,'contactEmailConfirm')]", input.get(10));
			handleDropDownByVisibleTextXpath(driver, "//select[contains(@name,'phoneCountryType')]",input.get(11));
			handleElementInputXpath(driver, "//input[contains(@name,'phoneNumber')]", input.get(12));
			
			handleElementClickXpath(driver, "//button[contains(text(),'continue')]");
			Thread.sleep(5000);
			handleElementClickXpath(driver, "(//div/i[contains(@class,'icon-add-circle')])[1]");
			handleElementClickXpath(driver, "(//button[contains(text(),'Continue')])[2]");
			Thread.sleep(5000);
			handleElementClickXpath(driver, "(//div[contains(@class,'seat-row-seat standard ng-star-inserted')])[1]");
			handleElementClickXpath(driver, "//button[contains(text(),'Continue')]");
			Thread.sleep(5000);
			handleDropDownByValueXpath(driver, "//select[contains(@name,'checkInSelect')]", "ONLINE");
			handleElementClickXpath(driver, "//button[contains(text(),'Continue')]");
			Thread.sleep(5000);
			screenshot(driver, "Payment Screen Launched");
			
			List <String> spritoutput = new ArrayList<String>();

			spritoutput.add(handleGetElementTextXpath(driver, "(//p[contains(@class,'custom-padding-summary mb-1')])[2]"));

			spritoutput.add(handleGetElementTextXpath(driver, "(//p[contains(@class,'custom-padding-summary mb-1')])[1]"));

			spritoutput.add(handleGetElementTextXpath(driver, "(//p[contains(@class,'custom-padding-summary mb-1')])[3]"));
			
			
			
			excelWriteRow("D:\\Selienum Excels\\Spirit Output.xlsx", "sheet1", 3, 2, spritoutput);
		}
		finally {
			Thread.sleep(5000);
		}
	}
	
	

}
