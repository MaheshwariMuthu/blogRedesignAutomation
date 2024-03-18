package automationFramework;

import static automationFramework.StartDriver.driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DynamicWebElements {

	public static WebElement getWebElementByContainsClass(String value) {
		return driver.findElement(By.xpath("//*[contains(@class,'" + value + "')]"));
	}
	
	
	public static WebElement getWebElementByClass(String value) {
		return driver.findElement(By.xpath("//*[@class='" + value + "']"));
	}

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement getWebElementByID(String value) {
		return driver.findElement(By.xpath("//*[@id='" + value + "']"));
	}
	
	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement getWebElementByValue(String value) {
		return driver.findElement(By.xpath("//*[@value='" + value + "']"));
	}

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static  WebElement getWebElementByText(String value) {
		return driver.findElement(By.xpath("//*[text()='" + value + "']"));
	}

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement getWebElementByContainsText(String value) {
		return driver.findElement(By.xpath("//*[contains(text(),'" + value + "')]"));
	}
	
	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static  WebElement getClientNameFromClientDetailsTable(String value) {
		return driver.findElement(By.xpath("//td//*[text()='" + value + "']"));
	}
	

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement getWebElementByName(String value) {
		return driver.findElement(By.xpath("//*[@name='" + value + "']"));
	}

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement getWebElementByContainsID(String value) {
		return driver.findElement(By.xpath("//*[contains(@id,'" + value + "')]"));
	}
	
	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement dailyTsPageTextField(String value) {
		return driver.findElement(By.xpath("//input[@id='" + value + "']/parent::div"));
	}

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static String firstRowValesDailyTs(String value) {
		return driver.findElement(By.xpath("//tbody[@id='dasktop_view']/tr//td[" + value + "]")).getText();
	}

	/**
	 * Description:
	 *
	 * @return
	 */
	public static WebElement dailyTsDropdownList(String value1, int value2) {
		return driver.findElement(By.xpath("//input[@id='" + value1 + "']/parent::div/ul/li[" + value2 + "]"));
	}

	/**
	 * Description:
	 * 
	 * @param value
	 * @return
	 */
	public static By dailyTsDropdownListBy(String value) {
		return By.xpath("//input[@id='" + value + "']/parent::div/ul/li[1]");
	}
	
	/**
	 * Description:
	 *
	 * @return
	 */

	public static By loader() {
		return By.xpath("//ul[@style='display: block; overflow-y: scroll; height: 200px;']");
	}
	
	public static WebElement getWebElementByXpath(String value) {
		return driver.findElement(By.xpath("//a[text()='"+value+"']/following::button[1][text()='Add To Cart']"));
	}

}
