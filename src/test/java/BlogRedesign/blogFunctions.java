package BlogRedesign;

import static automationFramework.Waits.verifyWebElementPresent;
import static automationFramework.Waits.waitTillPageLoad;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v117.network.Network;
import org.openqa.selenium.devtools.v117.network.model.Request;
import org.openqa.selenium.devtools.v117.network.model.RequestId;
import org.openqa.selenium.devtools.v117.network.model.Response;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import automationFramework.LoggerHelper;
import automationFramework.PageActions;
import pageActions.CommonPageActions;
import pageLocators.ArticlePageObjects;
import pageLocators.BlogPageObjects;
import pageLocators.CommonPageLocators;
import stepDefinations.Hooks;

import static stepDefinations.Hooks.configProperties;
import static automationFramework.DataReader.blogProperties;
import static automationFramework.StartDriver.driver;
import static automationFramework.DataReader.articleProperties;
import static stepDefinations.Hooks.driver;

public class blogFunctions {
	
	static PageActions pageActions = new PageActions();
	static CommonPageLocators commonPageLocators = new CommonPageLocators();
	static BlogPageObjects blogPageObjects = new BlogPageObjects();
	static ArticlePageObjects articlePageObjects = new ArticlePageObjects();
	public static Logger log = Logger.getLogger(LoggerHelper.class);
	static CommonPageActions commonPageActions = new CommonPageActions();
	static Document document = null;
	static PdfWriter writer = null;
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 10-01-2024
	 * Method Name	: footerNewLetterCheck(String)
	 * Description	: Method to validate the footer signUp UI webElements
	 ---------------------------------------------------------------------------------------------------------*/
		public static void footerNewsLetterCheck(String content) {
			WebElement element = null;
			String value = "", eleText = "";
			switch(content) {
				case "Heading":
					value = blogProperties.getProperty("footerNewsletterHeading");
					element = blogPageObjects.footerNewsletterHeading;
					eleText = element.getText();
					checkContent(element, value, eleText, "NewsLetter " + content);
					break;
				case "Content":
					value = blogProperties.getProperty("footerNewsletterContent");
					element = blogPageObjects.footerNewsletterContent;
					eleText = element.getText();
					checkContent(element, value, eleText, "NewsLetter " + content);
					break;
				case "SignUpInput":
					element = blogPageObjects.footerNewsletterInput;
					checkWebElementPresent(element, "Email Address", "Input field",  "Newsletter");
					value = "Enter email address";
					eleText = element.getAttribute("placeholder");
					checkContent(element, value, eleText, "NewsLetter " + content);
					break;
				case "SignUpButton":
					element = blogPageObjects.footerNewsletterBtnSignUp;
					checkWebElementPresent(element, "Sign Up", "Button",  "Newsletter");
					value = "Sign up!";
					eleText = element.getText();
					checkContent(element, value, eleText, "NewsLetter " + content);
					break;
				default:
					break;
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 10-01-2024
	 * Method Name	: articleContentCheck
	 * Description	: 
	 ---------------------------------------------------------------------------------------------------------*/
		public static void blogPageUIValidation(String section) {
			switch(section) {
				case "Header":
					blogPageComponentCheck("MainHeader");
					blogPageComponentCheck("HeaderTopNav");
					blogPageComponentCheck("HSLogo");
					blogPageComponentCheck("SearchButton");
					break;
				case "Footer":
					blogPageComponentCheck("FooterLink");
					blogPageComponentCheck("FooterSocial");
					blogPageComponentCheck("FooterBottomLink");
					blogPageComponentCheck("AboutBlog");
					blogPageComponentCheck("Disclaimer");
					blogPageComponentCheck("NewsLetter");
					break;
				default:
					break;
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 10-01-2024
	 * Method Name	: articleContentCheck
	 * Description	: To validate the global header and footer component UI validation
	 ---------------------------------------------------------------------------------------------------------*/
		public static void blogPageComponentCheck(String section) {
			WebElement element = null;
			String value = "", eleText = "";
			String links = "";
			String[] temp_split = null;
			String linkName = "";
			switch(section) {
				case "MainHeader":
					links = blogProperties.getProperty("headerLink");
					temp_split = links.split("\\|");
					for(int i=0; i < temp_split.length ; i++) {
						linkName = temp_split[i];
						element = blogPageObjects.HeaderLink(linkName);
						checkWebElementPresent(element, linkName, "Link",  section);
					}
					break;
				case "HeaderTopNav":
					links = blogProperties.getProperty("headerTopLink");
					temp_split = links.split("\\|");
					for(int i=0; i < temp_split.length ; i++) {
						linkName = temp_split[i];
						element = blogPageObjects.HeaderTopLink(linkName);
						checkWebElementPresent(element, linkName, "Link",  section);
					}
					break;
				case "FooterLink":
					links = blogProperties.getProperty("footerLink");
					temp_split = links.split("\\|");
					for(int i=0; i < temp_split.length ; i++) {
						linkName = temp_split[i];
						element = blogPageObjects.FooterLink(linkName);
						checkWebElementPresent(element, linkName, "Link", "Footer");
					}
					break;
				case "HSLogo":
					linkName = "HS";
					element = blogPageObjects.HeaderImageLink("Logo");
					checkWebElementPresent(element, linkName, "Logo", "Header");
					break;
				case "SearchButton":
					element = blogPageObjects.btnSearch;
					checkWebElementPresent(element, "Search", "Button", "Header");
					break;
				case "FooterSocial":
					links = blogProperties.getProperty("footerSocialLink");
					temp_split = links.split("\\|");
					for(int i=0; i < temp_split.length ; i++) {
						linkName = temp_split[i];
						if(linkName.contains("trust")) {
							element = blogPageObjects.FooterSocialImageLink(linkName);
						}else {
							element = blogPageObjects.FooterSocialLink(linkName);
						}
						checkWebElementPresent(element, linkName, "Social Link", "Footer");
					}
					break;
				case "FooterBottomLink":
					links = blogProperties.getProperty("footerBottomLink");
					temp_split = links.split("\\|");
					for(int i=0; i < temp_split.length ; i++) {
						linkName = temp_split[i];
						element = blogPageObjects.FooterBottomLink(linkName);
						checkWebElementPresent(element, linkName, "Link", "Footer Bottom");
					}
					break;
				case "Disclaimer":
					value = blogProperties.getProperty("disclaimerContent");
					element = blogPageObjects.disclaimerFooterContent;
					eleText = element.getText();
					checkContent(element, value, eleText, section);
					break;
				case "AboutBlog":
					value = blogProperties.getProperty("aboutBlogContent");
					element = blogPageObjects.footerContent;
					eleText = element.getText();
					checkContent(element, value, eleText, section);
					break;
				case "NewsLetter":
					blogFunctions.footerNewsLetterCheck("Heading");
					blogFunctions.footerNewsLetterCheck("Content");
					blogFunctions.footerNewsLetterCheck("SignUpInput");
					blogFunctions.footerNewsLetterCheck("SignUpButton");
					break;
				default:
					break;	
			}
			log.info(section + " links validated successfully");
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 10-01-2024
	 * Method Name	: articleContentCheck
	 * Description	: To check webELement present in the application
	 ---------------------------------------------------------------------------------------------------------*/
		public static void checkWebElementPresent(WebElement element, String link, String type, String section) {
			if(verifyWebElementPresent(element)) {
				pageActions.setHighlight(element);
				log.info("'" +link + "' " +type+ " is present in the Blog Page '" + section + "' section");
			}else {
				log.error("'" + link + "' " +type+ " is not present in the Blog Page '" + section + "' section");
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 10-01-2024
	 * Method Name	: checkContent
	 * Description	: To validate the expected text displayed in the WebElement text.
	 ---------------------------------------------------------------------------------------------------------*/
		public static void checkContent(WebElement element, String value, String content, String section) {
			if(content.contains(value)) {
				pageActions.setHighlight(element);
				log.info(section+ " text validated successfully\n" + content);
			}else {
				log.error(section+ " text is not matched with the configuration.\n" + content );
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 18-01-2024
	 * Method Name	: validateSuccessMessage
	 * Description	: Method to validate the success Message 
	 ---------------------------------------------------------------------------------------------------------*/
		public static void validateSuccessMessage(String input) throws Exception {
			WebElement element = null;
			String successMessage = "", message="", logMessage="";
			switch(input) {
				case "Footer":
						element = blogPageObjects.footerNewsletterSuccess;
						message = element.getText();
						successMessage = articleProperties.getProperty("signupSuccess");
						logMessage = "signed up through footer";
					break;
				case "LightBox":
						element = blogPageObjects.lightboxSuccessSubTitle;
						message = element.getText()+"\n"+blogPageObjects.lightboxSuccessText.getText();
						successMessage = articleProperties.getProperty("lightboxSuccess");
						logMessage = " signed up through lightbox";
					break;
				case "Feedback":
					element = articlePageObjects.feedbackSuccess;
					message = element.getText();
					successMessage = articleProperties.getProperty("feedbackSuccess");
					logMessage = "submitted feeback";
					break;
				case "ZipIn":
				case "StickyZipIn":
					element = articlePageObjects.zipInSuccess;
					message = element.getText();
					successMessage = articleProperties.getProperty("zipInSuccess");
					logMessage = "viewed plans for the zipcode";
					break;
				default:
					break;
			}
			if(verifyWebElementPresent(element)) {
				if(input.equals("Lightbox")) {
					commonPageActions.signupPopupClose();
				}
				if((message).equalsIgnoreCase(successMessage)){
					log.info("User successfully "+ logMessage);
	
				}else {
					log.error("User not "+ logMessage);
				}
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 19-01-2024
	 * Method Name	: enterText
	 * Description	: Method to enter the text in specified field based on the parameter passed
	 ---------------------------------------------------------------------------------------------------------*/
		public static void enterText(String field, String section) {
			WebElement element = null;
			String value = "";
			switch(section) {
				case "Footer":
					element = blogPageObjects.footerNewsletterInput;
					value = articleProperties.getProperty("FooterEmail");
					break;
				case "LightBox":
					element = blogPageObjects.lightboxInput;
					value = articleProperties.getProperty("LightboxEmail");
					break;
				case "Feedback":
					element = articlePageObjects.textareaComments;
					value = articleProperties.getProperty("comments");
					break;
				case "ZipIn":
					element = articlePageObjects.inputZipCode;
					value = articleProperties.getProperty("zipCode");
					break;
				case "StickyZipIn":
					element = blogPageObjects.footerContent;
					pageActions.scrollToElement(element);
					element = articlePageObjects.inputStickyZipCode;
					value = articleProperties.getProperty("zipCode");
					break;
				case "search":
					element = articlePageObjects.inputSearch;
					value = articleProperties.getProperty("search");
					break;
				default:
					break;
			}
			if(verifyWebElementPresent(element)) {
				pageActions.scrollToElement(element);
				pageActions.typeText(element,value , field);
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 19-01-2024
	 * Method Name	: clickButton
	 * Description	: Method to click the  button based on parameter passed.
	 ---------------------------------------------------------------------------------------------------------*/
		public static void clickButton(String button, String section) throws InterruptedException {
			WebElement element = null;
			switch(section) {
				case "Footer":
					element = blogPageObjects.footerNewsletterBtnSignUp;
					if(!configProperties.getProperty("browser").equals("firefox"))
						logNetworkContent();
					break;
				case "LightBox":
					element = blogPageObjects.lightboxBtnSignUp;
					if(!configProperties.getProperty("browser").equals("firefox"))
						logNetworkContent();
					break;
				case "Feedback":
					element = articlePageObjects.btnSendFeedback;
					Thread.sleep(30000);
					pageActions.setHighlight(element);
					break;
				case "ZipIn":
					element = articlePageObjects.buttonShopNow;
					break;
				case "StickyZipIn":
					element = articlePageObjects.buttonStickySubmit;
					break;
				case "Article":
					if(button.equals("Print"))
						element = articlePageObjects.buttonPrint;
					else if(button.equals("Search"))
						element = articlePageObjects.buttonSearch;
					break;
				default:
					break;
			}
			if(verifyWebElementPresent(element)) {
				pageActions.scrollToElement(element);
				if(!button.equals("Search")) {
					pageActions.clickElement(element, button, false);
				}else {
					pageActions.mouseHoverAndClick(element, button);}
				if(!button.equals("Print")) {
					waitTillPageLoad();
				}else {
					Thread.sleep(3000);
				}
			}
		}
		
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 19-01-2024
	 * Method Name	: logNetworkContent
	 * Description	: Method to get the API call status after submission
	 ---------------------------------------------------------------------------------------------------------*/
		public static void logNetworkContent() {
			
			DevTools devTools = ((HasDevTools) driver).getDevTools();
			devTools.createSession();
			devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
			devTools.addListener(Network.requestWillBeSent(), request ->
			{
				request.getRequest();
			});
	
				
				devTools.addListener(Network.responseReceived(), response ->
				{
					Response res = response.getResponse(); 
					//regId[0] = response.getRequestId();
					
					if(res.getUrl().contains(articleProperties.getProperty("webbulaCall"))) {
						if(res.getStatus()==200)
							log.info("Webbula Call passed with status <"+res.getStatus()+">: "+res.getUrl());
						else
							log.error("Webbula Call failed with status <"+res.getStatus()+">: "+res.getUrl());
					}
					if(res.getUrl().contains(articleProperties.getProperty("sfmcCall"))) {
						if(res.getStatus()==200)
							log.info("SFMC Call passed with status <" + res.getStatus() + ">: "+res.getUrl());
						else
							log.error("SFMC Call failed with status <" + res.getStatus() + ">: "+res.getUrl());
					}
					
				});
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 12-02-2024
	 * Method Name	: navigateToLinkValidation
	 * Description	: Method to check the links in various section is navigating to the expected url.
	 ---------------------------------------------------------------------------------------------------------*/
		public static void navigateToLinkValidation(String section) throws IOException, DocumentException {
			
			pdfScreenshot(csvFileReader.createfileNamewithDate(section,"txt"));
			for (Entry<String, String> entry : csvFileReader.dataMapping.entrySet()) {
			    String key = entry.getKey();
			    String temp = entry.getValue();
			    String[] split = temp.split("\\|");
			    
			    String linkName = split[0];
			    String url = split[1];
			    
			    WebElement element = null;
	
			    if(section.equals(key.replaceAll("\\_.*", ""))) {
					switch(section) {
						case "Header":
							element = blogPageObjects.HeaderLink(linkName);
							break;
						case "HeaderTopNav":
							element = blogPageObjects.HeaderTopLink(linkName);
							break;
						case "FooterBottomLink":
							element = blogPageObjects.FooterBottomLink(linkName);
							break;
						case "FooterSocial":
							if(linkName.contains("trust")) {
								element = blogPageObjects.FooterSocialImageLink(linkName);
							}else {
								element = blogPageObjects.FooterSocialLink(linkName);
							}
							break;
						case "FooterLink":
							element = blogPageObjects.FooterLink(linkName);
							break;
						case "RightRail":
							element = blogPageObjects.RightRailLink(linkName);
							break;
						default:
							break;
					}
				    navigateToLink(element, section, linkName, url);
				}
			}
			document.close();
			writer.close();
		
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 12-02-2024
	 * Method Name	: navigateToLink
	 * Description	: Method to check the links is navigating to the expected url.
	 ---------------------------------------------------------------------------------------------------------*/
		public static void navigateToLink(WebElement element, String section, String linkName, String url) throws IOException, DocumentException {
			try {
				String pageURL = "";
				pageActions.scrollToElement(element);
				pageActions.clickElement(element, linkName, false);
				waitTillPageLoad();
				switch(section) {
					case "HeaderTopNav":
					case "FooterBottomLink":
					case "RightRail":
						pageActions.switchWindow();
						waitTillPageLoad();
						pageURL = driver.getCurrentUrl();
						addImageInPDF("Link :"+linkName +" - URL: "+pageURL);
						driver.close();
						pageActions.switchToMainWindow();
						break;
					case "Header":
					case "FooterSocial":
					case "FooterLink":
						pageURL = driver.getCurrentUrl();
						addImageInPDF("Link :"+linkName +" - URL: "+pageURL);
						//pageActions.checkElementInViewport();
						pageActions.navigateBack();
						break;
				}
				if(url.equals(pageURL)) {
					log.info(linkName + " link NAVIGATED to the expected URL: <"+pageURL+">\n");
				}else {
					log.info(linkName + " link NOT NAVIGATED to the expected URL: <"+pageURL+">\n");
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 12-02-2024
	* Method Name	: pdfScreenshot
	* Description	: Method to write screenshot to pdf
	---------------------------------------------------------------------------------------------------------*/
		public static void pdfScreenshot(String filename) throws DocumentException, MalformedURLException, IOException {
			
			document = new Document();
			String output = System.getProperty("user.dir") + configProperties.getProperty("screenshot") + "//" + filename + ".pdf";
			FileOutputStream fos = new FileOutputStream(output);
	
			// Instantiate the PDF writer
			writer = PdfWriter.getInstance(document, fos);
	
			// open the pdf for writing
			writer.open();
			document.open();
	
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 12-02-2024
	* Method Name	: addImageInPDF
	* Description	: Method to add images0 to pdf
	---------------------------------------------------------------------------------------------------------*/
		public static void addImageInPDF(String title) throws MalformedURLException, IOException, DocumentException {
			byte[] input = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			// process content into image
			Image im = Image.getInstance(input);
	
			//set the size of the image
			im.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight()/2);
	
			// add the captured image to PDF
			document.add(new Paragraph(" "+title+" "));
			document.add(im);
			
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 12-02-2024
	* Method Name	: pressKey
	* Description	: press a key using keyboard
	---------------------------------------------------------------------------------------------------------*/
		public static void pressKey(String key) throws AWTException, InterruptedException {
			switch(key) {
				case "ENTER":
					pageActions.pressEnterKey();
					break;
				default:
					break;
			}
			waitTillPageLoad();
			
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 12-02-2024
	* Method Name	: checkSearchResult
	* Description	: Method to validate the search result in BLog Search functionality
	---------------------------------------------------------------------------------------------------------*/
		public static void checkSearchResult() throws InterruptedException {
			
			WebElement element = articlePageObjects.searchResult;
			String result = "";
			String split_result[] = null;
			//element = articlePageObjects.ArticleTitle1(article);
			if(verifyWebElementPresent(element)) {
				result = element.getText();
				split_result = result.split(" ");
				if(result.contains("0")) {
					log.info("Search Result not found for the keyword: "+split_result[3]);
				}else {
					log.info(split_result[0]+" Result found for the keyword: "+split_result[3]);
				}
			}
			
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 12-02-2024
	* Method Name	: printArticle
	* Description	: Method to validate the print functionality the article content
	---------------------------------------------------------------------------------------------------------*/
		public static void printArticle() throws AWTException, InterruptedException {
			
		//	WebElement iframeElement = driver.findElement(By.id("sidebar"));
		//	driver.switchTo().frame(iframeElement);
			Robot r = new Robot();
			r.delay(5000);
			System.out.println("Before TAB");
			r.keyPress(KeyEvent.VK_UP);
			r.keyRelease(KeyEvent.VK_UP);
			r.keyPress(KeyEvent.VK_UP);
			r.keyRelease(KeyEvent.VK_UP);
			r.keyPress(KeyEvent.VK_UP);
			r.keyRelease(KeyEvent.VK_UP);
			r.delay(3000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			r.delay(3000);
			String file = System.getProperty("user.dir") + "\\logs\\" + csvFileReader.createfileNamewithDate("article","pdf");
			StringSelection str = new StringSelection(file);
			Toolkit .getDefaultToolkit().getSystemClipboard().setContents(str, null);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			r.delay(2000);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			r.delay(3000);
			System.out.println("After ENTER");
			//r.keyRelease(KeyEvent.VK_ESCAPE);
		}
	
}
