package pageActions;

import static automationFramework.Constant.*;
import static automationFramework.Constant.PhoneNumber;
import static stepDefinations.Hooks.configProperties;
import static automationFramework.DataReader.geturl;
import static automationFramework.DynamicWebElements.*;
import static automationFramework.PageActions.*;
import static stepDefinations.Hooks.driver;
import static automationFramework.Waits.*;

import java.awt.*;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import automationFramework.LoggerHelper;
import automationFramework.PageActions;
import pageLocators.CommonPageLocators;
import stepDefinations.Hooks;


public class CommonPageActions {
    CommonPageLocators commonPageLocators = new CommonPageLocators();
    PageActions pageActions = new PageActions();
    public static Logger log = Logger.getLogger(LoggerHelper.class);

    public CommonPageActions() {
    }


    /**
     * Description: Navigate to the Site URL.
     *
     * @throws InterruptedException
     * @throws ParseException
     * @throws IOException
     */
    public void navigateToApplication()
            throws InterruptedException, IOException, ParseException {
        driver.get(geturl());
        waitTillPageLoad();
       // if(!Hooks.TC_ID.equals("TC_0003"))
        	//signupPopupClose();
//		closeCookiesBottom();
    }

    public void handlePopups() throws Exception {
        System.out.println("handleing Popups if any");   // //a[contains(text(),'Do Not Have Code')]
        waitTillPageLoad();
        if (configProperties.getProperty("server.site").equalsIgnoreCase("sanjose") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("wvwachoice") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("lasanitation")
        ) {

            WebElement entercodepopupClose = getWebElementByContainsText("Do Not Have Code");

            if (waitForElementavailblilityboolean(entercodepopupClose,"Enter Code popup",30)) {
                clickElement(entercodepopupClose, "Enter Code popup",false);
                waitTillPageLoad();
            }
        }


    }

    /**
     * Description: Entering Zip code and click on
     *
     * @throws InterruptedException
     */
    public void enterZipCodeAndSubmit(String zipCode, String EnterZipLocation) throws InterruptedException, AWTException {
        if (EnterZipLocation.equalsIgnoreCase("header")) {
            System.out.println(EnterZipLocation);
            clickElement(commonPageLocators.enterZipHeaderLink, "Enter Zip", false);
            //signupPopupClose();
            clickElement(commonPageLocators.enterZipHeaderLink, "Enter Zip", false);
            waitForElement(commonPageLocators.txtZipCodeHearder,"ZipCode input field",10);
            typeText(commonPageLocators.txtZipCodeHearder, zipCode, "ZipCode field");
            waitTillPageLoad();
            clickElement(commonPageLocators.view_Plan, "View Plans", false);
            waitTillPageLoad();
            waitTillPageLoad();
            //clickElement(commonPageLocators.viewAvailablePlans, "View available plans", false);
        } else if (EnterZipLocation.equalsIgnoreCase("LandingPage")) {
//			same code with different locators
            System.out.println(EnterZipLocation);
            clickElement(commonPageLocators.txtZipCode, "Enter Your ZIP Code", false);
            sleep(2);
            typeText(commonPageLocators.txtZipCode, zipCode, "Enter Your ZIP Code");
            waitTillPageLoad();
//            pageActions.pressTabKey();
            if (configProperties.getProperty("server.site").equalsIgnoreCase("slwofa")) {
                clickElement(commonPageLocators.view_Plan, "Shop Now", false);
            }
            if (configProperties.getProperty("server.site").equalsIgnoreCase("slwofc")) {
                clickElement(commonPageLocators.view_Plan, "Get a quote", false);
            }
            if (configProperties.getProperty("server.site").equalsIgnoreCase("homeserve")) {
                clickElement(commonPageLocators.view_Plan, "GO or View Plans", true);
                waitTillPageLoad();
                waitTillPageLoad();
                sleep(10);
                clickElement(commonPageLocators.viewAvailablePlans, "View available plans", false);
            }
            waitTillPageLoad();
            waitTillPageLoad();
        } else {
            throw new IllegalArgumentException("Invalid EnterZipLocation: " + EnterZipLocation);
        }
    }

    public void enterUserDetails(String Zipcode, String City) throws InterruptedException {

        String generateRandomAddress = RandomStringUtils.randomNumeric(4);
        String generateRandomEmail = RandomStringUtils.randomNumeric(3);

        Email = "maheswari.hstest+" + generateRandomEmail + "@gmail.com";
        Address = generateRandomAddress + " Chapmans Lane";
        PhoneNumber = "4409845223";
        ApartmentNumber = RandomStringUtils.randomNumeric(4);

        waitTillPageLoad();

        if (configProperties.getProperty("server.site").equalsIgnoreCase("Homeserve")) {
            verifyWebElementVisibleWebElementBoolean(getWebElementByID("email"));
            waitForElement(getWebElementByID("email"), "Email", 120);
            typeText(getWebElementByID("email"), Email, "Email");
            typeText(getWebElementByID("email-confirm"), Email, "Confirm Email");
            typeText(commonPageLocators.firstName, "AutoFirstName"+generateRandomEmail, "First name");
            typeText(getWebElementByID("last-name"), "AutoLastName"+generateRandomEmail, "Last name");
            typeText(commonPageLocators.addressLine, Address, "Address");
            typeText(getWebElementByID("address-line-2"), ApartmentNumber, "Address Second");
            typeText(getWebElementByID("home-phone"), PhoneNumber, "Home phone");
        } else {
            typeText(getWebElementByID("email"), Email, "Email");
            typeText(getWebElementByID("email-confirm"), Email, "Confirm Email");
            typeText(getWebElementByID("first-name"), "AutoFirstName"+generateRandomEmail, "First name");
            typeText(getWebElementByID("last-name"), "AutoLastName"+generateRandomEmail, "Last name");
            typeText(getWebElementByID("address-line-1"), Address, "Address");
            typeText(getWebElementByID("address-line-2"), ApartmentNumber, "Address Second");
            typeText(getWebElementByID("home-phone"), PhoneNumber, "Home Phone");
        }
        if (configProperties.getProperty("server.site").equalsIgnoreCase("ottawa") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("buffalowaternipcnew") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("firstenergy-fundle") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("kypower-tabs") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("lasanitation") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("sanjose") ||
                configProperties.getProperty("server.site").equalsIgnoreCase("wvwachoice")) {
            typeText(getWebElementByID("city"), City, "City");
            typeText(getWebElementByID("zip-code"), Zipcode, "Zip code");
        }


        try {
            WebElement State = driver.findElement(By.xpath("//*[@id=\"state\"]"));

            String state = State.getAttribute("value");
            System.out.println(state);

            if (configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan")
                    && state.equalsIgnoreCase("IN")) {
                // (313) 793-4983
                String MobileNumber = "4631" + RandomStringUtils.randomNumeric(3) + "340";
                typeText(getWebElementByID("home-phone"), MobileNumber, "Home phone");
            }
            Thread.sleep(4000);

        } catch (Exception e) {

        }
    }

    /**
     * Description: Closing cookies pop up.
     */
    public void closeCookiesBottom() throws InterruptedException {
        pageActions.clickElement(commonPageLocators.cookiesClose, "Close cookies", false);
    }
    

    /**
     * Description: Close Sign-UP popup close button
     *
     * @throws InterruptedException
     * @throws ParseException
     * @throws IOException
     */
    public void signupPopupClose() throws InterruptedException {
    	System.out.println("Closing SignUp popup page");
    	for (int i = 0; i<5; i++) {
    		if(waitForElementavailblilityboolean(commonPageLocators.lightboxCloseBtn,"Lightbox Popup",30)){
    			clickElement(commonPageLocators.lightboxCloseBtn, "Signup Popup", false);
    			waitTillPageLoad();
    			log.info("SignUp Popup closed");
    			break;
    		}
    	}
    }
    


}
