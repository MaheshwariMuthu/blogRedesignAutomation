package pageLocators;

import static automationFramework.StartDriver.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BlogRedesign.blogArticleFunctions;

public class BlogPageObjects {

	 public BlogPageObjects() {
	        PageFactory.initElements(driver, this);
	 }
	 
	//Blog link in homePage
	 	@FindBy(xpath = "//button[@aria-label='toggle-email-popup']")
		public static WebElement blog_link;
	 
	//Home link in Blog page header
		@FindBy(xpath = "//div[@class='ms-auto navbar-nav']/a[contains(text(),'Home')]")
		public static WebElement homeLink;
		
		@FindBy(xpath = "//div[@class='header-navigation']/*//a[@class='search-btn']")
		public static WebElement btnSearch;
		
		@FindBy(xpath = "//div[contains(@class,'footer_bottom_content')][1]/p[1]/parent::div")
		public static WebElement disclaimerFooterContent;
		
		@FindBy(xpath = "//div[contains(@class,'footer_NewsLetter')]/div/h5")
		public static WebElement footerNewsletterHeading;
		
		//Footer SignUp Elements
		@FindBy(xpath = "//div[contains(@class,'footer_NewsLetter')]/div/p")
		public static WebElement footerNewsletterContent;
		
		@FindBy(xpath = "//div[contains(@class,'footer_NewsLetter')]/div/form/input")
		public static WebElement footerNewsletterInput;
		
		@FindBy(xpath = "//div[contains(@class,'footer_NewsLetter')]/div/form/button")
		public static WebElement footerNewsletterBtnSignUp;
		
		@FindBy(xpath = "//div[contains(@class,'ThnakyouMessage')]")
		public static WebElement footerNewsletterSuccess;
		
		@FindBy(xpath = "//div[contains(@class,'footer_footer_content')]/*//p")
		public static WebElement footerContent;
		
		//Lightbox signUp elements
		@FindBy(xpath = "//div[contains(@class,'modal-content first-content')]/*//input")
		public static WebElement lightboxInput;
		
		@FindBy(xpath = "//div[contains(@class,'modal-content first-content')]/*//button[contains(@class,'modal-sign btn')]")
		public static WebElement lightboxBtnSignUp;
		
		@FindBy(xpath = "//div[contains(@class,'modal-title')]")
		public static WebElement lightboxSuccessTitle;
		
		@FindBy(xpath = "//div[contains(@class,'modal-subtitle')]")
		public static WebElement lightboxSuccessSubTitle;

		@FindBy(xpath = "//div[contains(@class,'modal-text')]")
		public static WebElement lightboxSuccessText;
		
		
	//Method to check the header links availability
		public WebElement HeaderLink(String linkName){
			WebElement linkHeader = driver.findElement(By.xpath("//a[@class='link nav-links' and @title = '"+ linkName +"']"));	
			return linkHeader;
		}		
		
	//Method to check the footer links availability
		public WebElement FooterLink(String linkName){
			 WebElement linkFooter = driver.findElement(By.xpath("//div[contains(@class,'footer_gridRow')]/*//a[contains(text(),'" + linkName +"')]"));	
			 return linkFooter;
		}
		
		//Method to check the social links availability
		public WebElement RightRailLink(String linkName){
			 WebElement linkFooter = driver.findElement(By.xpath("//div[contains(@class,'app_social_link')]/*//a[contains(@href,'" + linkName +"')]"));	
			 return linkFooter;
		}
		
	//Method to check the footer links availability
		public WebElement FooterAppLogo(String linkName){
			WebElement linkFooter = driver.findElement(By.xpath("//img[contains(@src,'" + linkName +"')]"));	
			return linkFooter;
		}
		
	//Method to check the header top links availability
		public WebElement HeaderTopLink(String linkName){
			WebElement linkHeaderTop = driver.findElement(By.xpath("//div[@class='header-menu-items']/*//a[contains(text(),'"+ linkName +"')]"));	
			return linkHeaderTop;
		}
	
	//Method to check the header image links availability
		public WebElement HeaderImageLink(String linkName){
			WebElement imgLinkFooter = driver.findElement(By.xpath("//div[@class = 'wrapper ']/*//img[@alt='" + linkName +"']"));	
			return imgLinkFooter;
		}
		
	//Method to check the footer social links availability/*//
		public WebElement FooterSocialLink(String linkName){
			WebElement imgLinkFooter = driver.findElement(By.xpath("//div[contains(@class,'footer_social_links')]/*//a[contains(@href,'" + linkName + ".com')]"));	
			return imgLinkFooter;
		}
		
	//Method to check the footer social image links availability
		public WebElement FooterSocialImageLink(String linkName){
			WebElement imgLinkFooter = driver.findElement(By.xpath("//div[contains(@class,'footer_social_links')]/*//img[contains(@src,'" + linkName + "')]"));	
			return imgLinkFooter;
		}
		
		public WebElement FooterBottomLink(String linkName) {
			WebElement bottomLinkFooter = driver.findElement(By.xpath("//div[contains(@class,'footer_bottom_content')]/*//a[contains(text(),'"+ linkName + "')]"));	
			return bottomLinkFooter;
		}
		
	 public static BlogPageObjects getInstance() {
		 BlogPageObjects headerfunctions = null;
			if(headerfunctions == null) {
				headerfunctions = new BlogPageObjects();
			}
			return headerfunctions;
	};
	
}
