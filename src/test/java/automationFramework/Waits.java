package automationFramework;

import static automationFramework.StartDriver.driver;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import BlogRedesign.blogArticleFunctions;

public class Waits {
	public static StringBuffer VERIFICATION_ERRORS = new StringBuffer();
	public static Integer EXPLICIT_WAIT_280_SEC = 280;
	public static Integer EXPLICIT_WAIT_120_SEC = 120;
	public static Integer EXPLICIT_WAIT_60_SEC = 60;
	public static Integer EXPLICIT_WAIT_30_SEC = 30;
	public static Integer EXPLICIT_WAIT_10_SEC = 10;
	public static Integer EXPLICIT_WAIT_5_SEC = 5;
	public static JavascriptExecutor jsExecutor;

	public static Logger log = Logger.getLogger(PageActions.class);

	public Waits() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * Description: Wait till page load
	 * 
	 * @author aatish.slathia
	 */
	public static void waitTillPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_60_SEC));
		jsExecutor = (JavascriptExecutor) driver;
		// Wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = wd -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString().equals("complete");
		// Get JS is Ready
		boolean jsReady = (Boolean) jsExecutor.executeScript("return document.readyState").toString()
				.equals("complete");
		// Wait Javascript until it is Ready!
		if (!jsReady) {
//			System.out.println("JS in NOT Ready!");
			// Wait for Javascript to load
			wait.until(jsLoad);

			// Wait jQuery until it is Ready!
		//	wait.until(webDriver -> (Boolean) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0"));

		} else {
			sleep(10);
		}
	}

	/**
	 * @author aatish.slathia
	 * @description sleep
	 * @param seconds
	 */
	public static void sleep(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebElement waitUntilClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_60_SEC));
		wait.pollingEvery(Duration.ofSeconds(2));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static Boolean verifyWebElementVisible(WebElement element, int explicitWait) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("Element: ").append(element)
					.append(" is not present on page \n -Caugth exception: ").append(e.getMessage()).append("\n\n");
			return false;
		}
	}

	public static Boolean verifylocatorPresent(By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("Element: ").append(e.getMessage()).append("\n\n");
			return false;
		}
	}

	public static Boolean verifyWebElementVisibleWebElementBoolean(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_60_SEC));
		wait.pollingEvery(Duration.ofSeconds(20));
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;

		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("Element: ").append(element)
					.append(" is not present on page \n -Caught exception: ").append(e.getMessage()).append("\n\n");

			return false;
		}
	}

	public static Boolean verifyWebElementByVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_10_SEC));
		wait.pollingEvery(Duration.ofSeconds(1));
		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("By: ").append(by).append(" is not visible on page \n -Caugth exception: ")
					.append(e.getMessage()).append("\n\n");
			return false;
		}
	}

	public static Boolean waitUntilTitleDisplayed(String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_10_SEC));
		wait.pollingEvery(Duration.ofSeconds(1));
		try {
			wait.until(ExpectedConditions.titleIs(title));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			;
			return false;
		}
	}

	public static void verifyAlertPresent(int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		try {
			wait.until(ExpectedConditions.alertIsPresent());

		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("By: ").append(" is not visible on page \n -Caugth exception: ")
					.append(e.getMessage()).append("\n\n");

		}
	}

	public static Boolean verifyWebElementByInVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_10_SEC));
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("By: ").append(by).append(" is not present on page \n -Caught exception: ")
					.append(e.getMessage()).append("\n\n");
			return false;
		}
	}

	public static Boolean verifyWebElementInVisible(WebElement element, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		try {
			wait.until(ExpectedConditions.invisibilityOf(element));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("By: ").append(element).append(" is not present on page \n -Caught exception: ")
					.append(e.getMessage()).append("\n\n");
			return false;
		}
	}

	public static Boolean verifyPresenceOfElementByLocator(String xpathValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_10_SEC));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathValue)));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			VERIFICATION_ERRORS.append("By: ").append(" is not present on page \n -Caught exception: ")
					.append(e.getMessage()).append("\n\n");
			return false;
		}
	}

	public static boolean checkIfElementExists(WebDriver driver, By by) {
		boolean exist = false;
		exist = !driver.findElements(by).isEmpty();
		return exist;
	}

	public static void waitByNotVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_280_SEC));
		if (checkIfElementExists(driver, by)) {
			wait.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		}
	}

	public static boolean verifyWebElementPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_30_SEC));
		wait.pollingEvery(Duration.ofSeconds(1));
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (NoSuchElementException | NoSuchFrameException | NoSuchWindowException
				| ErrorHandler.UnknownServerException | TimeoutException e) {
			log.error("Element: " + element + " is not present on page. Exception: " + e.getMessage());
			//driver.navigate().refresh();
			VERIFICATION_ERRORS.append(
					"Element: " + element + " is not present on page \n -Caugth exception: " + e.getMessage() + "\n\n");
			return false;
		}
	}
	
	public static boolean verifyElementPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_30_SEC));
		wait.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			if (Waits.verifyWebElementVisibleWebElementBoolean(element)) {
				return true;
			} else {
				return false;
			}
	}

	public static void verifyElementPresent(String elementName, WebElement el) {
		if (Waits.verifyWebElementVisibleWebElementBoolean(el)) {
		} else {
			assertTrue(false, elementName + " is missing on page");
		}
	}

	public static void waitForElement(WebElement element, String elementName, int waitTime){
		//log.info("waiting for "+elementName+" element to load in UI");
		for(int i=0; i<=waitTime; i++){
			try{
				if(element.isDisplayed() || element.isEnabled()){
					System.out.println("Element "+elementName+" is displayed in UI");
					break;
				}else{
					try{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			} catch (WebDriverException e) {
				try{
					Thread.sleep(1000);
				} catch (InterruptedException j) {
				}
			}
			if(i==waitTime){
				System.out.println("No WebObject "+elementName+" Found in UI, waited for :: "+waitTime+ " seconds");
				Assert.fail("No WebObject "+elementName+" Found in UI, waited for :: "+waitTime+ " seconds");
			}
		}
	}
	public static boolean waitForElementavailblilityboolean(WebElement element, String elementName, int waitTime){
		boolean availbility = false;
		
		for(int i=0; i<=waitTime; i++){
			try{
				if(element.isDisplayed()){
				//if(element.isDisplayed() || element.isEnabled()){
					System.out.println("Element "+elementName+" is displayed in UI");
					availbility =true;
					break;
				}else{
					try{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			} catch (WebDriverException e) {
				try{
					Thread.sleep(1000);
				} catch (InterruptedException j) {
				}
			}
			if(i==waitTime){
				System.out.println("No WebObject "+elementName+" Found in UI, waited for :: "+waitTime+ " seconds");
				System.out.println("No WebObject "+elementName+" Found in UI, waited for :: "+waitTime+ " seconds");
//				Assert.fail("No WebObject "+elementName+" Found in UI, waited for :: "+waitTime+ " seconds");
			}
		}
		return availbility;
	}
	
	public static Waits getInstance() {
		Waits waits = null;
		if(waits == null) {
			waits = new Waits();
		}
		return waits;
	};
}
