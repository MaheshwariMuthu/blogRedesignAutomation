package automationFramework;

import static automationFramework.StartDriver.driver;
import static automationFramework.Waits.*;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageActions extends StartDriver {
	public static String mainID;
	Waits waits = new Waits();
	public static Logger log = Logger.getLogger(PageActions.class);
	
	/*
	 * Description: Verify the attribute value of an element
	 */
	public static void validateAttribute(WebElement element, String valueToBeCompared) {
		String actualvalue = element.getAttribute("title");
		log.info("Tool tip text: " + actualvalue);
		try {
			Assert.assertEquals(actualvalue, valueToBeCompared);
		} catch (Exception e) {
			log.info("its not working ");
		}
	}

	/*
	 * Description: Scroll to bottom of the page
	 */
	public static void scrollToBottomOfPage() {
		JavascriptExecutor executor = (JavascriptExecutor) StartDriver.driver;
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/*
	 * Description: Scroll to paticular element
	 */
	public static void executeJavaScriptScroll(WebElement element) throws InterruptedException {
		Waits.waitUntilClickable(element);
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		((JavascriptExecutor) StartDriver.driver).executeScript(scrollElementIntoMiddle, element);
	}

	/*
	 * Description: Java script function for click
	 */
	public static void executeJavaScriptClick(WebElement element) throws InterruptedException {
		WebDriverWait wait10sec = new WebDriverWait(StartDriver.driver,  Duration.ofSeconds(10));
		wait10sec.pollingEvery(Duration.ofSeconds(1));
		JavascriptExecutor executor = (JavascriptExecutor) StartDriver.driver;

		int attempts = 0;

		while (attempts < 20) {

			try {
				wait10sec.until(ExpectedConditions.elementToBeClickable(element));
				executor.executeScript("arguments[0].click();", element);
				break;
			} catch (StaleElementReferenceException e) {

			}
			attempts++;
		}

		if (attempts < 19) {

		} else {

		}
	}

	/**
	 * Description: Double click on element
	 */
	public static void doubleClick(WebElement element, String elementName, int waitTime) {
		waitForElement(element,elementName,waitTime);
		if (waitUntilClickable(element) != null) {
			try {
				Actions act = new Actions(driver);
				act.doubleClick(element).perform();
				log.info("Double click on: " + elementName);
			} catch (AssertionError e) {
				assertTrue(verifyWebElementPresent(element), "----> Element is not present");
			}
		} else {
			log.info("Unable to click on: " + elementName);
			Assert.fail("Element not found");
		}
	}

	public static void verify_text_on_page(WebElement element, String value) throws Exception {

		if((element.getText()).equalsIgnoreCase("value")){

			System.out.println("TextMatched");

		}else
			throw new Exception();
		}



	/**
	 * Description: Scroll to element and performing click
	 */
	public static void clickElement(WebElement element, String sElementName, boolean blnClickUsingMouseOver) throws InterruptedException {
		if (!blnClickUsingMouseOver) {
			try {
				waitForElement(element,sElementName,30);
				//log.info("Trying to click on " + sElementName + " element");
				setHighlight(element);
				sleep(3);
				waitUntilClickable(element);
				element.click();
				log.info("Clicked on " + sElementName);
			} catch (StaleElementReferenceException e) {
//			log.error(lesseePojo.getScenarioName().trim() + "- Element " + sElementName + " is not attached to the page document");
				e.printStackTrace();
				toBeFail("Element " + sElementName + " is not attached to the page document");
			} catch (NoSuchElementException e) {
//			log.error(lesseePojo.getScenarioName().trim() + "- Element " + sElementName + " was not found in DOM");
				e.printStackTrace();
				toBeFail("Element " + sElementName + " was not found in DOM");
			} catch (Exception e) {
//			log.error(lesseePojo.getScenarioName().trim() + "- Element " + sElementName + " was not clickable in time-" + optionWaitTime);
				e.printStackTrace();
				toBeFail("Element " + sElementName + " was not clickable in time");
			}
		}else {
			mouseHoverAndClick(element,sElementName);
		}
	}

	public static void setHighlight(WebElement element) {

			String attributevalue = "border:2px solid Crimson;";
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
	}

	public static void toBeFail(String sMessage) {
		log.error(sMessage);
		org.junit.Assert.fail(sMessage);
	}

	public static void clickElementWithJS(WebElement element, String elementName) throws InterruptedException {
		if (waitUntilClickable(element) != null) {
			try {
				assertTrue(verifyWebElementPresent(element), "----> Element is present");
				scrollToElement(element);
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView()", element);
				jsExecutor.executeScript(
						"arguments[0].setAttribute('style', 'border:2px solid red; background:yellow');", element);
				jsExecutor.executeScript("arguments[0].click();", element);
				element.click();
				log.info("Clicking on: " + elementName);
			} catch (AssertionError e) {
				assertTrue(verifyWebElementPresent(element), "----> Element is not present");
				scrollToElement(element);
				executeJavaScriptClick(element);
			}
		} else {
			log.info("Unable to click on: " + elementName);
			Assert.fail("Element not found");
		}
	}
	
	
	public static void clickOnDateFormCalenderWithClassName(String calenderClassName, String wantedDate) throws Exception
    {
        WebElement element=driver.findElement(By.xpath("//*[@class='"+calenderClassName+"']//td[text()='"+ wantedDate +"'][2]"));
        if (waitUntilClickable(element) != null) {
            try {
                assertTrue(verifyWebElementPresent(element), "----> Element is present");
                element.click();
                log.info("Clicking on: " + wantedDate);
            } catch (AssertionError e) {
                assertTrue(verifyWebElementPresent(element), "----> Element is not present");
                executeJavaScriptClick(element);
            }
        } else {
            log.info("Unable to click on: " + wantedDate);
            Assert.fail("Element not found");
        }
    }

	/**
	 * Description :Method to switch to Child Window/Tab
	 * 
	 * @author aatish.slathia
	 */
	public static synchronized void switchWindow() {
		mainID = driver.getWindowHandle();
		Set<String> allID = driver.getWindowHandles();
		for (String id : allID) {
			if (!id.equals(mainID)) {
				driver.switchTo().window(id);
			}
		}
	}

	/**
	 * Description : Method click on tab key and click on enter
	 * 
	 * @author aatish.slathia
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
	public void pressTabKeyThenEnter() throws AWTException, InterruptedException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		
	}

	public void pressTabKey() throws AWTException, InterruptedException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
	}

	/**
	 * Description :Method to switch to Main Window/Tab
	 * 
	 * @author aatish.slathia
	 */
	public static synchronized void switchToMainWindow() {
		driver.switchTo().window(mainID);
	}

	/**
	 * Description: Scroll to element particular element
	 */
	public static void scrollToElement(WebElement element) {

		try {
			Thread.sleep(3000L);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			int yPosition = element.getLocation().getY();
			js.executeScript("window.scroll (0, " + yPosition + ") ");       
			Thread.sleep(3000L);         
		} catch (Exception e) {
			System.out.println("Scroll till the failed");
		}
	}

	/*
	 * Description:Verifying Text
	 */
	public void verifyAlertText(WebElement element, String expectedText) {
		String actualText = element.getAttribute("innerHTML");
		System.out.println(actualText);
		Assert.assertTrue(actualText.contains(expectedText));
	}

	/**
	 * Description To Enter the Text to the Text filed
	 * 
	 * @author aatish.slathia
	 * @param element
	 * @param value
	 * @param elementName
	 */
	public static void typeText(WebElement element, String value, String elementName) {
		try {
			Waits.waitUntilClickable(element);
			assertTrue(verifyWebElementPresent(element), "----> Element is present");
			element.clear();
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].setAttribute('style', 'border:2px solid yellow');",
					element);
			element.sendKeys(value);
			log.info("Enter the "+ elementName + " <" + value + ">");
		} catch (AssertionError error) {
			log.info(" Unable to type " + value + " into " + elementName);
			Assert.fail("Unable to type " + elementName);
		} catch (Exception e) {
			log.info(" Unable to type " + value + " into " + elementName);
			Assert.fail("Unable to type " + elementName);
		}
	}


	public static String getFirstSelectedValueFromDropdown(WebElement element) {
		Select dropdown = new Select(element);
		String dropdownDefaultText = dropdown.getFirstSelectedOption().getText();
		return dropdownDefaultText;
	}

	public static int randomDate() {
		Random ran = new Random();
		return ran.nextInt(30) + 1;
	}

	public static void selectFromDropdownByVisibletext(WebElement element, String wantedOption) {
		assertTrue(verifyWebElementPresent(element));
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(wantedOption);
		}

		else {
			System.out.println("Dropdown is not visible");
		}

	}

	public String defaultValueOfDropdown(WebElement dropdownElement, String dropdownName, int waitTime) {
		waitForElement(dropdownElement, dropdownName, waitTime);
		Select select = new Select(dropdownElement);
		WebElement option = select.getFirstSelectedOption();
		String defaultItem = option.getText();

		return defaultItem;

	}

	public static void allDropdownOptions(String selectXpath, ArrayList<String> options) {
		int size = driver.findElements(By.xpath(selectXpath)).size();

		for (int i = 1; i <= size; i++) {
			options.add(driver.findElement(By.xpath(selectXpath + "[" + i + "]")).getText());
		}

	}

	public static boolean isClickable(WebElement el) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_10_SEC));
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void hoverOnElement(WebElement element,String elementName) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();

	}

	/*
	 * Description: Mouse hover on element and click on the element
	 */
	public static void mouseHoverAndClick(WebElement element, String elementName) {
		if (waitUntilClickable(element) != null) {
			try {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click().build().perform();
	} catch (AssertionError e) {
		assertTrue(verifyWebElementPresent(element), "----> Element is not present");
	}
} else {
		log.info("Unable to click on: " + elementName);
		Assert.fail("Element not found");
		}
	}

	public static void closeCurrentTabAndMoveToParentTab(String parentWin) {
		((JavascriptExecutor) driver).executeScript("window.close()");
		// driver.close();
		driver.switchTo().window(parentWin);
		driver.switchTo().defaultContent();
	}
	
	public static void navigateBack() {
		driver.navigate().back();
		waitTillPageLoad();
	}

	public static void selectFromDropdownByIndex(WebElement element, int index) {
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			Select dropdown = new Select(element);
			dropdown.selectByIndex(index);
		}
	}

	public static void selectFromDropdownByValue(WebElement element, String wantedOption) {
		if (verifyWebElementVisibleWebElementBoolean(element)) {
			Select dropdown = new Select(element);
			dropdown.selectByValue(wantedOption);
		}
	}

	public static void clearText(WebElement element) {
		String clearText = Keys.chord(Keys.CONTROL, "a");
		element.sendKeys(clearText);
		element.sendKeys(Keys.BACK_SPACE);
	}
	
	
	public static String getCurrentDate()
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
        return dateformat.format(new Date());
    }
	
	public static String getCurrentDate1()
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy");
        return dateformat.format(new Date());
    }
	
	public static void clickOnElementFormListWithClassName(String ListClassName, String wantedValue) throws Exception
    {
        WebElement element=driver.findElement(By.xpath("//[@class='"+ListClassName+"']//[text()='"+ wantedValue +"']"));
        if (waitUntilClickable(element) != null) {
            try {
                assertTrue(verifyWebElementPresent(element), "----> Element is present");
                element.click();
                log.info("Clicking on: " + wantedValue);
            } catch (AssertionError e) {
                assertTrue(verifyWebElementPresent(element), "----> Element is not present");
                executeJavaScriptClick(element);
            }
        } else {
            log.info("Unable to click on: " + wantedValue);
            Assert.fail("Element not found");
        }
    }
	
	public static String getOnlyCurrentDate()
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd");
        return dateformat.format(new Date());
    }

	/**
	 * Description :File upload by using the image path
	 * 
	 * 
	 * @param imagePath
	 */
	public static void uploadImage(String imagePath) {
		StringSelection stringSelection = new StringSelection(imagePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void IselementPresent(WebElement element) {
		
	}
	
    public void checkElementInViewport() {
    	
    	JavascriptExecutor js = (JavascriptExecutor) driver;  
    	WebElement element = driver.findElement(By.xpath("//h2[text()='Partner with us']"));
    	//Dimension bounding =  (Dimension) js.executeScript("return arguments[0].getBoundingClientRect();", viewport);

    	/*js.executeScript("var rect = arguments[0].getBoundingClientRect();"+
    			"var elemTop = rect.top;"+
    			" var elemBottom = rect.bottom;"+
    			"var isVisible = (elemTop >= 0) && (elemBottom <= Window.innerHeight);"+
    			"System.out.println(isVisible);",element);*/

    	boolean isVisible = (Boolean)js.executeScript("const checkIsVisible = (element) => {"+
            "var rect = element.getBoundingClientRect();"+
           " if(rect.bottom <= window.innerHeight){"+
           		"return true;"+
           " }else{"+
           "return false;"+
           " }"+
        "};",element);

    	
    	    // Only completely visible elements return true:
    	    
    	    // Partially visible elements return true:
    	    //isVisible = elemTop < window.innerHeight && elemBottom >= 0;
    	   // System.out.println(isVisible);

    	
    	/* WebDriver driver = ((RemoteWebElement)viewport).getWrappedDriver();

    	  boolean visible =  (Boolean)(js).executeScript(
    	      "var elem = arguments[0],                 " +
    	      "  box = elem.getBoundingClientRect(),    " +
    	      "  cx = box.left + box.width / 2,         " +
    	      "  cy = box.top + box.height / 2,         " +
    	      "  e = document.elementFromPoint(cx, cy); " +
    	      "for (; e; e = e.parentElement) {         " +
    	      "  if (e === elem)                        " +
    	      "    return true;                         " +
    	      "}                                        " +
    	      "return false;                            "
    	      , viewport);*/
    	  
    	  if (isVisible=true) {
      	    log.info("Element is in the viewport!");
      	} else {
      	    log.info("Element is NOT in the viewport!");
      	}
    	
    }

}
