package pageLocators;

import static automationFramework.StartDriver.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CommonPageLocators {
    public CommonPageLocators() {
        PageFactory.initElements(driver, this);
    }

    //	@FindBy(xpath = "//a[@class='link js-header-enter-zip']/descendant::span")
//	public WebElement enterZipHeaderLink;
    @FindAll({
        @FindBy(xpath = "//button[@aria-label='toggle-email-popup']")
        //	@FindBy(xpath = "//a[@class='link js-header-enter-zip']/descendant::span");
})
public WebElement email_capture_close_button;
    @FindAll({
            @FindBy(xpath = "//div[@class='component generic-error-lightbox light-box js-generic-error-lb']")
            //	@FindBy(xpath = "//a[@class='link js-header-enter-zip']/descendant::span");
    })
    public WebElement PaymentnotprocessedDialog;
    @FindAll({
            @FindBy(xpath = "//a[@class='link js-header-enter-zip']/descendant::span"),
            @FindBy(xpath = "//a[@for=\"trigger-zip-form\"]/span"), //div[@class='field zip-bar']/input[@name='zipcode']
    })
    public WebElement enterZipHeaderLink;

    @FindAll({
            @FindBy(xpath = "//div[@class='field zip-bar']/input[@placeholder='Enter Your ZIP Code']"),
    })
    public WebElement txtZipCodeHearder;

    @FindAll({
            @FindBy(xpath = "/html/body/main/section/div[2]/div/div/div/div[2]/div/form/fieldset/div/div/div[2]/div/input[1]"),
            @FindBy(xpath = "//*[@id=\"zip-code\"]"),

    })
    public WebElement txtZipCode;

    @FindBy(xpath = "//*[@id=\"city\"]")
    public WebElement city_Details;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div[2]/a")
    public WebElement use_This_Address;
    //
    @FindAll({
            @FindBy(xpath = "//*[text()='Address not found!']"),
            @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/h2"),

    })
    public WebElement AddressNotFound;
    @FindAll({
    		//@FindBy(xpath = "//a[text()='Tech Protection Essential']/following::button[1][text()='Add To Cart']"),
    		@FindBy(xpath = "//a[text()='PLAN_NAME']/following::button[1][text()='Add To Cart']"),
            @FindBy(xpath = "/html/body/main/div[4]/div/div/div/div[2]/div/div[3]/div[3]/a/span[1]"),
            @FindBy(xpath = "/html/body/main/div[2]/div/div/div/div[1]/div/div[3]/div[2]/a/span[1]"),
            @FindBy(xpath = "//button[text()='Add To Cart']"),
            @FindBy(xpath = "//*[@class='button js-add-to-cart']"),
            @FindBy(xpath = "(//span[text()='Add To Cart'])[1]"),
    })
    public WebElement addToCart;

    @FindAll({

            @FindBy(xpath = "/html/body/div[2]/div/div[1]/div"),
            @FindBy(xpath = "/html/body/div[2]/div/div[1]")
    })
    public WebElement Payment_not_Processed_Alert;


    @FindBy(xpath = "//*[@id=\"code-input\"]")
    public WebElement entercodepopup;

    @FindBy(xpath = "//*[@id='mtw-code-form']/fieldset/div/a")
    public WebElement entercodepopupClose;

    @FindBy(xpath = "//*[@id=\"onetrust-close-btn-container\"]/button")
    public WebElement btnCookiesClose;

    @FindBy(xpath = "//*[@id=\"installmentsPerYear\"]")
    public WebElement Select_Bill_Frequency;


    @FindBy(xpath = "//*[@id=\"payment_details_lower\"]/input")
    public WebElement Add_To_Cart_Button;

    @FindAll({
            @FindBy(xpath = "/html/body/main/div[3]/div/div/div[2]/div/div/div/form/div[7]/a/div"),
            @FindBy(xpath = "/html/body/div[1]/div/div[1]/aside/div[2]/a"),
    })
    public WebElement Proceed_To_Cart_Button;


    @FindAll({
            @FindBy(xpath = "//*[@class='checkmark']"),

    })
    public List<WebElement> select_Plan;

    @FindAll({
            @FindBy(xpath = "//*[@id=\"firstName\"]"),
            @FindBy(xpath = "//*[@id=\"first-name\"]")

    })
    public WebElement firstName;

    @FindAll({

            @FindBy(xpath = "//*[@id=\"AddressLine1\"]"),
            @FindBy(xpath = "//*[@id=\"address-line-1\"]")

    })
    public WebElement addressLine;


    @FindAll({
            @FindBy(xpath = "//*[@id=\"address-line-2\"]"),
            @FindBy(xpath = "//*[@id=\"AddressLine1\"]")

    })
    public WebElement apartmentDetails;

    @FindAll({
            @FindBy(xpath = "//*[@id=\"card_expiry_month\"]"),
            @FindBy(xpath = "//*[@id='Expiry']"),

    })
    public WebElement cardExpiryMonth;


    @FindAll({
            @FindBy(xpath = "//*[@id=\"card_expiry_year\"]")

    })
    public WebElement cardExpiryYear;
    @FindBy(xpath = "//*[@id=\"payment_details_lower\"]/input")
    public WebElement nextButton;

    @FindAll({
            @FindBy(xpath = "//div[3]/div[2]/div[2]/p/button"),
            @FindBy(xpath = "//*[@class='c491-add-new-payment__btn']"),
            @FindBy(xpath = "//*[@id=\"select2-checkout-form__method-container\"]"),

    })
    public WebElement choosePaymentTYpe;
    //div[4]/div/div[1]/fieldset/div/div[1]/input

    @FindAll({
            @FindBy(xpath = "//fieldset/div[4]/div/div[1]/fieldset/div/div[1]/input"),
            @FindBy(xpath = "//*[@id=\"card_type_001\"]"),
            @FindBy(xpath = "//*[text()='Credit or Debit Card']"),
    })
    public WebElement cardType;
    @FindAll({
            @FindBy(xpath = "/html/body/form/input[2]"),
            @FindBy(xpath = "//*[@id=\"CardNumber\"]"),
            @FindBy(xpath = "//*[@id=\"card_number\"]"),
            @FindBy(xpath = "//input[@id='number']"),
            @FindBy(xpath = "//*[@id=\"number\"]"),

    })
    public WebElement card_Number;

    @FindBy(xpath = "//*[@class='button js-post-message']")
    public WebElement Complete_secure_CheckOut;

    @FindBy(xpath = "//*[@id=\"States\"]")
    public WebElement select_States;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div[1]/form/fieldset/div[2]/button")
    public WebElement state_Ok_Button;


    @FindAll({
            @FindBy(xpath = "//*[@id=\"Expiry\"]"),

    })
    public WebElement month_Year;


    @FindAll({
           @FindBy(xpath = "//*[@id=\"ContentDiv\"]/input[2]"),

    })
    public WebElement add_Card;


    @FindAll({
            @FindBy(xpath = "//*[@class='button zip-button js-show-ps red-theme']"),
            @FindBy(xpath = "//*[contains(text(),'Get a quote')]"),
            //@FindBy(xpath = "//button[@type='submit']/descendant::span[text()='GO']"),
            @FindBy(xpath = "//div[@class='field zip-bar']/input[@name='zipcode']/following::button[@type='submit']/descendant::span[text()='View Plans']"),
           

    })
    public WebElement view_Plan;


    @FindAll({
            @FindBy(xpath = "/html/body/div[2]/div/div[2]/main/div/h2"),

    })
    public WebElement other_Customers_Purchased;


    @FindAll({
            @FindBy(xpath = "/html/body/div"),


    })
    public WebElement success_Message;


    /* Proceed to checkout */
    @FindAll({
    		@FindBy(xpath = "//a[contains(@id,'btn-proceed-to-checkout')]"),
            @FindBy(xpath = "/html/body/main/div[2]/div/div/div/div[1]/div/div[3]/div[2]/a/span[2]"),
            @FindBy(xpath = "/html/body/main/div[4]/div/div/div/div[2]/div/div[3]/div[3]/a/span[2]"),
            @FindBy(xpath = "/html/body/main/div[4]/div/div/div/div[1]/div/div[3]/div[3]/a/span[2]"),
            @FindBy(xpath = "/html/body/div[2]/div/div[1]/aside/div[2]/a"),
            @FindBy(xpath = "/html/body/main/div[3]/div[1]/div[1]/div/div[2]/div[2]/a/span[2]"),
            @FindBy(xpath = "*//aside/div[2]/a"),
            @FindBy(xpath = "//div[3]/div[2]/a/span[2]"),
            @FindBy(xpath = "//*[@class='btn btn--red product-tile__cta-button']"),
            @FindBy(xpath = "//div[@class='cart__submit-container']/descendant::a[contains(text(),'Proceed To Checkout')]"),
            @FindBy(xpath = "/html/body/div[1]/div/div[1]/aside/div[2]/a"),
            @FindBy(xpath = "/html/body/main/div[3]/div/div/div[2]/div/div/div/form/div[9]/a/div"),
    })
    public WebElement proceedToCheckout;

    //

    @FindBy(xpath = "//*[@id=\"account-number\"]")
    public WebElement enter_Account_Number;


    @FindBy(xpath = "//div[2]/fieldset/div/div[2]/div/label")
    public WebElement select_Go_PaperLess;

    /* Credit Card option X-path */
    @FindAll({
            @FindBy(xpath = "//li/descendant::*[contains(text(),'Credit or Debit Card')]"),
            @FindBy(css = "#select2-checkout-form__method-result-oqsm-Credit\\ or\\ Debit\\ Card"),
    })
    public WebElement creditCardOption;
    @FindAll({
            @FindBy(xpath = "//li/descendant::*[contains(text(),'Checking Account')]"),
            @FindBy(xpath = "//span[@class='selection']"),
    })
    public WebElement checkingAccountOption;


    /* Credit Card option X-path */
    @FindAll({
            @FindBy(xpath = "/html/body/main/div/div/div/div[3]/form/div/div/div[2]/div[3]/section/div/div[2]/div/button"),
            @FindBy(xpath = "/html/body/main/div/div/div/div[3]/form/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/button"),
            @FindBy(xpath = "(//div[@class='checkout-form__container']//button[text()='Complete Secure Checkout'])[1]"),
            @FindBy(xpath = "(/html/body/main/div/div/div/div[3]/form/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/button"),
            @FindBy(xpath = "//*[@id=\"checking-form\"]/div[3]/button"),
    })
    public WebElement completeSecureCheckout;

    /* Card number X-path */


    /* Credit card number X-path */
    @FindAll({
            @FindBy(xpath = "(//iframe)[1]"),
            @FindBy(xpath = "(//iframe)[2]"),
            @FindBy(xpath = "//*[@id=\"micro-number-container\"]/iframe"),

    })
    public WebElement creditCardNumberFrame;

    /* Credit card number X-path */
    @FindBy(xpath = "(//span[text()='Shop Now'])[1]")
    public WebElement shopNowBannerBtn;

    /* View available palns X-path */
    @FindAll({
    @FindBy(xpath = "//form/button[@type='submit']/descendant::span[text()='View Plans']"),
    @FindBy(xpath = "//li[@class='provider-selection__provider']/descendant::a"),
    })
    public WebElement viewAvailablePlans;

    /* View palns X-path */

//    @FindAll({
//            @FindBy(xpath = "//button[@class='button zip-button js-show-ps']/descendant::span[text()='View Plans']"),
//            @FindBy(xpath = "//button[@type='submit']/descendant::span[text()='View Plans']"),
//    })
//    public WebElement viewPlan;

    /* Close button for Feedback pop X-path */
    @FindBy(xpath = "//button[text()='Close']")
    public WebElement feedbackPopUpCloseBtn;

    /* Title X-path */
    @FindBy(xpath = "//h1[contains(text(),'Order')]")
    public WebElement orderConfirmationTitle;

    /* Order number X-path */
    @FindAll({
            @FindBy(xpath = "//span[@class='order-confirm__text']/descendant::span"),
            @FindBy(xpath = "*//p/strong"),
            @FindBy(xpath = "/html/body/main/div/div/div/div/div/div/div[1]/div[4]/p/strong"),
            @FindBy(xpath = "//*[@id=\"site-main\"]/div[2]/div/div/div/div/div[2]/div/span[1]"),

    })
    public WebElement orderNumber;

//	/* First name checkout X-path */
//	@FindBy(xpath = "//input[@placeholder='First Name']")
//	public WebElement firstName;

    /* Close cookies X-path */
    @FindAll({
            @FindBy(xpath = "//div[@id='onetrust-close-btn-container']/descendant::button"),
            @FindBy(xpath = "/html/body/main/div[3]/div/button"),
            @FindBy(xpath = "/html/body/main/div[6]/div/button"),
            @FindBy(xpath = "//button[contains(@class,'footer-banner')]")
    })
    public WebElement cookiesClose;
    
    /*Sign-Up Close BUtton*/
    @FindAll({
    		@FindBy(xpath = "//button[contains(@class, \"email-capture-popup__close-button\")]"),
    })
    public WebElement signUpClose;
    
    @FindAll({
		 @FindBy(xpath = "//div[@class='ms-auto navbar-nav']/a[contains(text(),'Home')]"),
		 //@FindBy(linkText = "Home")
	 })
	 public static WebElement homeLink;
    
    @FindAll({
    	@FindBy(xpath = "//button[contains(@class,'modal-cross btn')]"),
    })
    public static WebElement lightboxCloseBtn;
    
}
