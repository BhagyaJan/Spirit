package org.test.help;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

public class AutoTestHelper {
	
	static WebDriver driver;
	static JavascriptExecutor js;
	static WebElement element;
	
	public static Actions acc;
	public static Robot r;
	
	static WebDriverWait wait;
	
	//returns an obj of Actions class
	public static Actions returnActionsObj()
	{
		acc = new Actions(driver);
		return acc;
	}
	
	
	//returns an object of Robot class
	public static Robot returnRobotObj() throws AWTException 
	{
		 return r = new Robot();
		
	}
	
	//returns an typecasted object of JavaScriptExecutor
	public static JavascriptExecutor returnJSObj()
	{
		return js = (JavascriptExecutor)driver;
	}
	
	public static WebElement handleFindElementXpath(WebDriver driver,String xpath) {
		
		if(wait==null)
		{
			wait = new WebDriverWait(driver, 10);
		}

		element =  driver.findElement(By.xpath(xpath));
		
		wait.until(ExpectedConditions.elementToBeClickable(element));
		
		return element;
	}
	
	
	public static void handleElementClickXpath(WebDriver driver,String xpath) {
		handleFindElementXpath(driver, xpath).click();
	}
	
	public static String handleElementInputXpath(WebDriver driver,String xpath,String input) {
		element = handleFindElementXpath(driver, xpath);
		element.sendKeys(input);
		return element.getAttribute("value");
	}
	
	public static String handleGetElementTextXpath(WebDriver driver,String xpath) {
		return handleFindElementXpath(driver, xpath).getText();
	}
	
	public static void handleSearchInputXpath(WebDriver driver,String xpath,String input) {
		handleFindElementXpath(driver, xpath).sendKeys(input,Keys.ENTER);
	}
	
	public static String handleDropDownByVisibleTextXpath(WebDriver driver,String xpath,String selectText) {
		
		element = handleFindElementXpath(driver, xpath);
		Select select =new Select(element);
		
		select.selectByVisibleText(selectText);
		
		return element.getAttribute("value");
	}
	
public static String handleDropDownByValueXpath(WebDriver driver,String xpath,String value) {
		
		element = handleFindElementXpath(driver, xpath);
		Select select =new Select(element);
		
		select.selectByValue(value);
		
		return element.getAttribute("value");
	}


public static WebElement handleElementScrollXpathJS(WebDriver driver,String xpath,boolean input) {
		
		
		element = handleFindElementXpath(driver, xpath);
		
		returnJSObj().executeScript("arguments[0].scrollIntoView('"+input +"');", element);
		return element;
	}

public static String handleElementInputUsingJS(WebDriver driver,String xpath,String input) {
	
	element = handleFindElementXpath(driver, xpath);
	
	returnJSObj().executeScript("arguments[0].setAttribute('value','"+input +"');", element);
	
	return (String) returnJSObj().executeScript("return arguments[0].getAttribute('value')",element);
}

public static String handleGetElementValueUsingJS(WebDriver driver,String xpath) {
	
	element = handleFindElementXpath(driver, xpath);
	
	return (String) returnJSObj().executeScript("return arguments[0].getAttribute('value')",element);
}


public static void handleElementClickUsingJS(WebDriver driver,String xpath) {
	
	element = handleFindElementXpath(driver, xpath);
	
	returnJSObj().executeScript("arguments[0].click();", element);
}


	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> setToArrayList(Set set) {
		
		List<String> list = new ArrayList<String>();
		list.addAll(set);
		return list;   
	}
	
	public static void handleWindow(WebDriver driver) {
		
		Set<String> allWindow1 = driver.getWindowHandles();
		//System.out.println(allWindow1);

		driver.switchTo().window(AutoTestHelper.setToArrayList(allWindow1).get(1));
	}
	public static void switchToParentWindow(WebDriver driver) {
		
		Set<String> allWindow1 = driver.getWindowHandles();
		//System.out.println(allWindow1);

		driver.switchTo().window(AutoTestHelper.setToArrayList(allWindow1).get(0));
	}
	
	public static Alert isAlertPresent(WebDriver driver) 
	{ 
		Alert a = null;
	    try 
	    { 
	    	
	        a = driver.switchTo().alert(); 
	        //System.out.println(a.getText());
	        return a; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return a; 
	    }   // catch 
	}   // isAlertPresent()

	public static void handleSimpleAlert(WebDriver driver) 
	{
		Alert a=isAlertPresent(driver);
		
		if(a!=null)
		{
			a.accept();	
		}
	}
	public static void handleConfirmAlert(WebDriver driver,int input) 
	{
		Alert a=isAlertPresent(driver);
		
		if(a!=null)
		{
			if(input==1)
				a.accept();	
			else
				a.dismiss();
		}
	}
	public static void handlePromptAlert(WebDriver driver,int input,String text) 
	{
		Alert a=isAlertPresent(driver);
		
		if(a!=null)
		{
			if(input==1)
			{
				a.sendKeys(text);
				a.accept();	
			}
			else
				a.dismiss();
		}
	}

	public static void handleFrames(WebDriver driver, String matchString) {
		
		int size = driver.findElements(By.tagName("iframe")).size();
		
		System.out.println("size : " + size);
		
		for(int i=0; i<size; i++){
			
			driver.switchTo().frame(i);
			List<WebElement> elementFrames = driver.findElements(By.xpath("" + matchString + ""));
			System.out.println(elementFrames.size());
			if(elementFrames.size()==1)
			{
					System.out.println("Frame Index : " + i);
					break;
			}
			driver.switchTo().defaultContent();
			}
		
	}
	
	public static WebDriver getFirefoxDriver()
	{
		System.setProperty("webdriver.gecko.driver", "D:\\BrowserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		maximize(driver);
		return driver;
	}
	
	public static WebDriver getChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\BrowserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		maximize(driver);
		return driver;
	}
	
	public static WebDriver launchIE()
	{
		System.setProperty("webdriver.ie.driver", "D:\\BrowserDrivers\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		maximize(driver);
		return driver;
	}
	
	//maximize
	public static void maximize(WebDriver driver)
	{
		driver.manage().window().maximize();
	}
	
	public static void handleCookies(WebDriver driver) {
		
	    for(Cookie ck : driver.manage().getCookies())							
	    {			
	      if(!(ck.getName().equals("shippingCountry")|| ck.getName().equals("currency")))
	        driver.manage().deleteCookie(ck);      
	    }
	    
	    for(Cookie ck : driver.manage().getCookies())							
	    {			
	        System.out.println(ck.getName() + "==========" + ck.getValue()) ;
	        
	    }
	    System.out.println( "===================================" ) ;
	}
	

	public static void print(String s) {
		System.out.println(s);
	}
	
	public static void screenshot(WebDriver driver,String fileName) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		Files.copy(src, new File("D:\\Screenshot\\"+fileName +".png"));
	}
	
	public static Sheet excelGetSheet(String filePath, String sheetName) throws IOException
	{
		FileInputStream fis = new FileInputStream(filePath);
		Workbook w = new XSSFWorkbook(fis);
		
		return w.getSheet(sheetName);
	}
	
	public static String excelReadSingleCell(String filePath, String sheetName, int rowNum, int cellNum) throws IOException
	{
		String cellValue=null;
		
		Sheet s= excelGetSheet(filePath,sheetName);
		
		Row r = s.getRow(rowNum);
		Cell c = r.getCell(cellNum);
		
		int cellTypeNum = c.getCellType();
		
		if(cellTypeNum==1)
		{
			cellValue = c.getStringCellValue();
		}
		
		else if(cellTypeNum == 0)
		{
			if(DateUtil.isCellDateFormatted(c))
			{
				Date dateValue = c.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
				cellValue = sdf.format(dateValue);
			}
			
			else
			{
				double d = c.getNumericCellValue();
				long l = (long)d;
				cellValue = String.valueOf(l);
			}
		}
		
		return cellValue;
	}
	
	public static String getCellValue(Cell c) 
	{
		String cellValue=null;
		
		int cellTypeNum = c.getCellType();
		
		if(cellTypeNum==1)
		{
			cellValue = c.getStringCellValue();
		}
		
		else if(cellTypeNum == 0)
		{
			if(DateUtil.isCellDateFormatted(c))
			{
				Date dateValue = c.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
				cellValue = sdf.format(dateValue);
			}
			
			else
			{
				double d = c.getNumericCellValue();
				long l = (long)d;
				cellValue = String.valueOf(l);
			}
		}
		
		return cellValue;
	}

	//Excel read one full row
	public static List<String> excelReadRow(String filePath, String sheetName, int rowNum,int colStartNum) throws IOException
	{
		List<String> strList = new ArrayList<String>();
		
		Sheet s= excelGetSheet(filePath,sheetName);
		
		Row r = s.getRow(rowNum);
		
		int numberOfCells = r.getPhysicalNumberOfCells();
		
		numberOfCells = numberOfCells + colStartNum;
		
		for(int i =colStartNum ; i<numberOfCells; i++)
		{
			strList.add(getCellValue(r.getCell(i)));
		}
		return strList;
	}
	
	
	//read all elements in an excel
	public static List<String> excelReadAll(String filePath, String sheetName) throws IOException
	{
		List<String> strList = new ArrayList<String>();
		
		Sheet sheet= excelGetSheet(filePath,sheetName);
		
		Iterator<Row> itr = sheet.rowIterator();
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		
		while (itr.hasNext())                 
		{  
		Row row = itr.next();  
		Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
			while (cellIterator.hasNext())   
			{  
			Cell cell = cellIterator.next();  
				switch (cell.getCellType())               
				{  
				case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
					strList.add(cell.getStringCellValue());  
				break;  
				case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
					   if (DateUtil.isCellDateFormatted(cell)) {
						   strList.add(df.format(cell.getDateCellValue()));
			           } else {
			        	   strList.add(String.valueOf(cell.getNumericCellValue()));
			           }  
			   
				break;  
				default:  
				}  
			}	
		}
		return strList;
	}
	
	
	//write values in a row
	public static void excelWriteRow(String filePath, String sheetName, int rowNum, int colStartNum,List<String> cellValues) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(filePath);
		Workbook w = new XSSFWorkbook();		
		Sheet s = w.createSheet(sheetName);
		Row r = s.createRow(rowNum);
		int cellCount = cellValues.size() + colStartNum;
		
		for(int i =colStartNum,j=0; i<cellCount; i++,j++) 
		{
			Cell c = r.createCell(i);
			c.setCellValue(cellValues.get(j));
		}
		
		w.write(fos);
		
	}
	
	//mouseover action
	public static void mouseOver(WebElement e)
	{	
		
		returnActionsObj().moveToElement(e).perform();
	}
	
	//drag and drop
	public static void dragAndDrop(WebElement from, WebElement to)
	{
		returnActionsObj().dragAndDrop(from, to).perform();
	}
	
	
	//press Down Key n number of times
	public static void pressDownKey(int n) throws AWTException
	{
		
		for(int i=0; i<n; i++) 
		{
			returnRobotObj().keyPress(KeyEvent.VK_DOWN);
			returnRobotObj().keyRelease(KeyEvent.VK_DOWN);
		}
	}
	
	//press up key n number of times
	public static void pressUpKey(int n) throws AWTException
	{
		
		for(int i=0; i<n; i++) 
		{
			returnRobotObj().keyPress(KeyEvent.VK_UP);
			returnRobotObj().keyRelease(KeyEvent.VK_UP);
		}
	}
	
	//press tab key n number of times 
	
	public static void pressTab(int n) throws AWTException
	{
		
		for(int i=0; i<n; i++) 
		{
			returnRobotObj().keyPress(KeyEvent.VK_TAB);
			returnRobotObj().keyRelease(KeyEvent.VK_TAB);
		}
	}
	
	
	//press enter key n number of times
	public static void pressEnter() throws AWTException
	{

			returnRobotObj().keyPress(KeyEvent.VK_ENTER);
			returnRobotObj().keyRelease(KeyEvent.VK_ENTER);
	}
	
	//right click an element
	public static void rightClick(WebElement e)
	{
		returnActionsObj().contextClick(e).perform();;
	}
	
	
	//double click an element
	public static void doubleClick(WebElement e)
	{
		returnActionsObj().doubleClick(e).perform();
	}
	
}
