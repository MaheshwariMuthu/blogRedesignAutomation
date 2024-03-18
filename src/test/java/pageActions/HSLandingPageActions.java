package pageActions;

import automationFramework.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import automationFramework.PageActions;
import pageLocators.CommonPageLocators;
import static automationFramework.DataReader.*;
import static automationFramework.Constant.*;
import static automationFramework.DynamicWebElements.*;
import static stepDefinations.Hooks.configProperties;
import static automationFramework.PageActions.*;
import static automationFramework.Waits.*;
import static automationFramework.Waits.verifyWebElementVisibleWebElementBoolean;
import static automationFramework.Waits.waitTillPageLoad;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HSLandingPageActions {

    public static String currentDateandTime;
    public static Logger log = Logger.getLogger(HSLandingPageActions.class);
    CommonPageLocators commonPageLocators = new CommonPageLocators();
    Utils Utils = new Utils();


    CommonPageActions commonPageActions = new CommonPageActions();

    public static String BrowserorderNumberTexts;


    public HSLandingPageActions() {
    }


    public void enterZipcode(String zipCode) throws InterruptedException {
        clickElement(commonPageLocators.txtZipCode, "Enter Zipcode textbox", false);

    }

    /**
     * Description: Adding Product inside the cart and click on proceed to checkout.
     *
     * @throws InterruptedException
     */
    public void addProductToCartAndProceedToCheckout(boolean multipleItemsIntoCart) throws InterruptedException {
        int i = 0;

        if (commonPageLocators.addToCart.isEnabled()) {
            if (verifyWebElementVisibleWebElementBoolean(commonPageLocators.addToCart)) {
                scrollToElement(commonPageLocators.addToCart);
                clickElement(commonPageLocators.addToCart, "Add to cart", false);
                waitTillPageLoad();
                if (multipleItemsIntoCart && verifyWebElementVisibleWebElementBoolean(commonPageLocators.addToCart)) {
                    scrollToElement(commonPageLocators.addToCart);
                    clickElement(commonPageLocators.addToCart, "Add to cart", false);
                    waitTillPageLoad();
                }
                String CartPage = driver.getTitle();
                System.out.println(CartPage);

                if (CartPage.equalsIgnoreCase("Checkout Empty Cart")) {
                    Assert.fail("Your Cart is Empty");
                } else {
                    System.out.println("Product is added to Cart successfully");
                    sleep(3);
                    //somehow for 'aepindianamichigan' site, 'Process to Checkout' is NOT enabling on load, so creating it on fly
                    if (configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan")) {
                        WebElement entercodepopupClose = driver.findElement(By.xpath("(//a[contains(@class, 'add-to-cart')]/descendant::*[contains(text(),'Proceed To Checkout')])[1]"));                        if (waitForElementavailblilityboolean(entercodepopupClose,"proceed To Checkout",30)) {
                            clickElement(entercodepopupClose, "proceed To Checkout",false);
                           }
                    }else {
                        waitForElementavailblilityboolean(commonPageLocators.proceedToCheckout, "proceed To Checkout", 30);
//                    verifyWebElementVisibleWebElementBoolean(commonPageLocators.proceedToCheckout);
                        clickElement(commonPageLocators.proceedToCheckout, "Proceed To Checkout", false);
                    }

                    waitTillPageLoad();
                    System.out.println(configProperties.getProperty("server.site"));
                    if (configProperties.getProperty("server.site").equalsIgnoreCase("slwofc") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("kypower-tabs") ||
                            //	configProperties.getProperty("server.site").equalsIgnoreCase("firstenergy-fundle")||
                            configProperties.getProperty("server.site").equalsIgnoreCase("lasanitation") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("wvwachoice")
                    ) {
                        clickElement(getWebElementByText("Checkout"), "Proceed To Checkout", false);
                    }
//                    else {
//                        Assert.fail("Unable to Click on ADD to cart or Proceed To checkout buttons");
//                    }
                }
            }
        } else {
            Assert.fail("ERROR:  --- addToCart --- button Not displayed");
        }
        waitTillPageLoad();
    }


    /**
     * Description: Entering the Contact details inside the form.
     *
     * @throws InterruptedException
     * @throws ParseException
     * @throws IOException
     * @throws FileNotFoundException
     */


    public void enter_Zipcode_and_City_Details(String Zipcode, String City) {
        if (configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan")|| 
        	configProperties.getProperty("server.site").equalsIgnoreCase("apco-carousel")) {
            typeText(commonPageLocators.txtZipCode, Zipcode, "Zipcode");
            typeText(commonPageLocators.city_Details, City, "Zipcode");

        }

    }

    public void continueToPayment() throws Exception {
        clickElement(getWebElementByText("Continue to Payment Information"), "Continue to Payment Information", false);
        sleep(12);
        clickElement(getWebElementByText("Yes, use this address"), "Yes, use this address", false);
        sleep(10);
    }

    /**
     * Description:
     *
     * @throws InterruptedException
     */
    public void enterPaymentDetails() throws InterruptedException {
        clickElement(getWebElementByID("select2-checkout-form__method-container"), "Payment options", false);
        clickElement(commonPageLocators.creditCardOption, "CreditCard", false);
        Thread.sleep(12000);
        waitTillPageLoad();
        driver.switchTo().frame(commonPageLocators.creditCardNumberFrame);
        System.out.println("Enter Card Number");
        Thread.sleep(5000);
        typeText(commonPageLocators.creditCardNumberFrame, "4024007168458700", "Card number");
        typeText(getWebElementByID("micro-exp-date"), "122027", "Expiratioin Date");
        driver.switchTo().defaultContent();
        clickElement(commonPageLocators.completeSecureCheckout, "Checkout", false);
        waitTillPageLoad();
        Thread.sleep(8000);
    }

    public void enterAccountDetailsAndCompleteCheckout(String Value) throws Exception {

        typeText(commonPageLocators.enter_Account_Number, Value, "account number");
        clickElement(commonPageLocators.completeSecureCheckout, "complete secure checkout", false);
        waitTillPageLoad();

    }


    /**
     * Description: Verify order conformed successfully
     *
     * @throws InterruptedException
     */
    public void verifyOrderConformedSuccessfully() throws Exception {
        waitTillPageLoad();
        Thread.sleep(5000);
        try {
            if (commonPageLocators.AddressNotFound.getText().contains("Address not found!")) {
                clickElement(commonPageLocators.use_This_Address, "use the address", false);
                waitTillPageLoad();
                sleep(10);
                if (waitForElementavailblilityboolean(commonPageLocators.orderConfirmationTitle,"orderConfirmationTitle",30)) {
                    Assert.assertTrue("Order Confirmation not present ", verifyWebElementPresent(commonPageLocators.orderConfirmationTitle));
                    BrowserorderNumberText = commonPageLocators.orderNumber.getText();
                    String[] parts = BrowserorderNumberText.split("\\.");
                    String extractedValue = parts[1];
                    System.out.println("Extracted Value:" + extractedValue.trim());

                    BrowserorderNumberTexts = extractedValue.trim();
                    System.out.println("Order Number is: " + BrowserorderNumberTexts);
                    log.info("Order Number is: " + BrowserorderNumberTexts);
                    System.out.println("Sale completed.");
                    log.info("Sale completed.");

                } else if (verifyWebElementPresent(commonPageLocators.PaymentnotprocessedDialog)) {
                    Assert.fail("ERROR : ------Payment Not Processed:--------");
                }else {
                    Assert.fail("ERROR : ------Sale Not Completed OR UI Latency issue, check screenshots:--------");
                }
            } else {
//                waitForElement(getWebElementByValue("TVStreaming"), "TVStreamComm", 40);
//                clickElement(getWebElementByValue("TVStreaming"), "TVStreamComm", false);
//                clickElement(getWebElementByText("Finish"), "Finish", false);
//                waitTillPageLoad();
//                String feedBackMessage = getWebElementByClass("survey__message survey__message--subtitle").getText();
//                Thread.sleep(5000);
//                if (feedBackMessage.equals("Your feedback has been submitted successfully.")) {

                if (configProperties.getProperty("server.site").equalsIgnoreCase("Homeserve")) {
//                        clickElement(commonPageLocators.feedbackPopUpCloseBtn, "Feedback close btn", false);
                    Assert.assertTrue("Order Confirmation not present ", verifyWebElementPresent(commonPageLocators.orderConfirmationTitle));
                    WebElement ordertext = driver.findElement(By.xpath("//*[@id=\"site-main\"]/div[2]/div/div/div/div/div[2]/div/span[1]"));
                    scrollToElement(ordertext);
                    System.out.println(ordertext.getText());
                    BrowserorderNumberTexts = ordertext.getText();
                    System.out.println(BrowserorderNumberTexts);
                    String[] parts = BrowserorderNumberTexts.split(" ");
                    if (parts.length >= 3) {
                        String extractedValue = parts[2];
                        System.out.println("Extracted Value: " + extractedValue);
                        BrowserorderNumberTexts = extractedValue;
                    } else {
                        System.out.println("Error: Unable to extract order number.");
                    }

                    System.out.println("Order Number is : " + BrowserorderNumberTexts);
                } else {
                    clickElement(commonPageLocators.feedbackPopUpCloseBtn, "Feedback close btn", false);
                    Assert.assertTrue("Order Confirmation not present ", verifyWebElementPresent(commonPageLocators.orderConfirmationTitle));
                    BrowserorderNumberText = commonPageLocators.orderNumber.getText();
                    String[] parts = BrowserorderNumberText.split("\\.");
                    String extractedValue = parts[1];
                    System.out.println("Extracted Value:" + extractedValue.trim());
                    BrowserorderNumberTexts = extractedValue.trim();
                    System.out.println("Order Number is: " + BrowserorderNumberTexts);
                    log.info("Order Number is: " + BrowserorderNumberTexts);
                    Assert.assertTrue("Order Confirmation not present ", verifyWebElementPresent(getWebElementByText("Create Account")));
                    log.info("Homeserve sale completed.");
                }
//            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("ERROR : ------Sale Not Completed OR UI Latency issue, check screenshots:--------");
        }

    }

    public static void getCurrentTime() {
        // Create a SimpleDateFormat instance with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        // Set the time zone to IST (Indian Standard Time)
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        // Get the current date and time
        Date currentDate = new Date();

        // Format the current date and time
        currentDateandTime = sdf.format(currentDate);
        System.out.println("Current Date and Time : " + currentDateandTime);

        //return currentDateandTime;
    }


    /**
     * Description: Entering Zip code and click on
     *
     * @throws InterruptedException
     */
    public void enterZipCodeByLocationAndEnterZipCode(WebElement element, String zipCode) throws InterruptedException {
        // Check if element with XPath condition 1 exists
        if (verifyWebElementPresent(element)) {
            clickElement(commonPageLocators.enterZipHeaderLink, "Enter Zip", false);
            typeText(getWebElementByID("wrapout-zipcode"), zipCode, "ZipCode field");
            waitTillPageLoad();
            clickElement(getWebElementByClass("button button-blue zip-button"), "View Plans", false);
            waitTillPageLoad();
            verifyWebElementVisibleWebElementBoolean(getWebElementByText("View Plans On SLWA"));
        }
        // Check if element with XPath condition 2 exists
        else if (verifyWebElementPresent(element)) {
            typeText(getWebElementByID("home-zip-input"), zipCode, "Zipcode");
            clickElement(commonPageLocators.shopNowBannerBtn, "Shop now", false);
            waitTillPageLoad();
        } else {

        }
    }


    public void select_State(String StateValue) throws Exception {

        selectFromDropdownByVisibletext(commonPageLocators.select_States, StateValue);
        sleep(2);
        clickElement(commonPageLocators.state_Ok_Button, "OK", false);
        waitTillPageLoad();
        sleep(10);

    }
    
    public void addProductToCartAndProceedToCheckout(String plan, boolean multipleItemsIntoCart) throws InterruptedException {
        int i = 0;
        if (getWebElementByXpath(plan).isEnabled()) {
            if (verifyWebElementVisibleWebElementBoolean(getWebElementByXpath(plan))) {
                scrollToElement(getWebElementByXpath(plan));
                clickElement(getWebElementByXpath(plan), "Add to cart", false);
                waitTillPageLoad();
                if (multipleItemsIntoCart && verifyWebElementVisibleWebElementBoolean(commonPageLocators.addToCart)) {
                    scrollToElement(commonPageLocators.addToCart);
                    clickElement(commonPageLocators.addToCart, "Add to cart", false);
                    waitTillPageLoad();
                }
                String CartPage = driver.getTitle();
                System.out.println(CartPage);

                if (CartPage.equalsIgnoreCase("Checkout Empty Cart")) {
                    Assert.fail("Your Cart is Empty");
                } else {
                    System.out.println("Product is added to Cart successfully");
                    sleep(3);
                    //somehow for 'aepindianamichigan' site, 'Process to Checkout' is NOT enabling on load, so creating it on fly
                    if (configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan")) {
                        WebElement entercodepopupClose = driver.findElement(By.xpath("(//a[contains(@class, 'add-to-cart')]/descendant::*[contains(text(),'Proceed To Checkout')])[1]"));                        if (waitForElementavailblilityboolean(entercodepopupClose,"proceed To Checkout",30)) {
                            clickElement(entercodepopupClose, "proceed To Checkout",false);
                           }
                    }else {
                        waitForElementavailblilityboolean(commonPageLocators.proceedToCheckout, "proceed To Checkout", 30);
//                    verifyWebElementVisibleWebElementBoolean(commonPageLocators.proceedToCheckout);
                        clickElement(commonPageLocators.proceedToCheckout, "Proceed To Checkout", false);
                    }

                    waitTillPageLoad();
                    System.out.println(configProperties.getProperty("server.site"));
                    if (configProperties.getProperty("server.site").equalsIgnoreCase("slwofc") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("aepindianamichigan") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("kypower-tabs") ||
                            //	configProperties.getProperty("server.site").equalsIgnoreCase("firstenergy-fundle")||
                            configProperties.getProperty("server.site").equalsIgnoreCase("lasanitation") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("wvwachoice") ||
                            configProperties.getProperty("server.site").equalsIgnoreCase("Homeserve")
                    ) {
                        clickElement(getWebElementByText("Checkout"), "Checkout", false);
                    }
//                    else {
//                        Assert.fail("Unable to Click on ADD to cart or Proceed To checkout buttons");
//                    }
                }
            }
        } else {
            Assert.fail("ERROR:  --- addToCart --- button Not displayed");
        }
        waitTillPageLoad();
    }



}
