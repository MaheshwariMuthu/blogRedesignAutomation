package BlogRedesign;

import static automationFramework.Waits.verifyWebElementPresent;
import static automationFramework.Waits.waitTillPageLoad;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Robot;
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
	 * Method Name	: enterEmail
	 * Description	: Method to enter the email address for signUp
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
	 * Method Name	: enterEmail
	 * Description	: Method to click the signUp button for signUp
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
				element = articlePageObjects.buttonPrint;
				break;
			default:
				break;
		}
		if(verifyWebElementPresent(element)) {
			pageActions.scrollToElement(element);
			pageActions.clickElement(element, button, false);
			waitTillPageLoad();
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
		
		pdfScreenshot(csvFileReader.createfileNamewithDate(section));
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
	
	public static void printArticle() throws AWTException {
		
	/*	pageActions.switchWindow();
		
		WebElement iframeElement = driver.findElement(By.id("pdf-viewer"));
		


		// Switch to the iframe window
		driver.switchTo().frame(iframeElement);
		WebElement iframeButton = driver.findElement(By.id("myiframebutton"));
		iframeButton.click();
		
		waitTillPageLoad();
		
		pageActions.switchToMainWindow();*/
		
		/*Robot r = new Robot();

		r.keyPress(KeyEvent.VK_ESCAPE);

		r.keyRelease(KeyEvent.VK_ESCAPE);*/
		
		//driver.switchTo().window(driver.getWindowHandles().stream().filter(handle -> !handle.equals(driver.getWindowHandle())).findAny().get());
		//pageActions.switchWindow();
		
	/*	Set<String> windowHandles = driver.getWindowHandles();
		if (!windowHandles.isEmpty()) {
		    driver.switchTo().window((String) windowHandles.toArray()[windowHandles.size() - 1]);
		}else {
			System.out.println("Empty");
		}
		//Now work with the dialog as with an ordinary page:  
		//driver.findElement(By.className("cancel")).click();
		//WebElement printPreviewApp = driver.findElement(By.xpath("//print-preview-app"));
		//WebElement printPreviewAppContent = (WebElement) ((RemoteWebDriver) driver).executeScript("return arguments[0].shadowRoot", printPreviewApp);
		
		//WebElement printPreviewHeader = printPreviewAppContent.findElement(By.cssSelector("print-preview-header"));
		//WebElement printPreviewHeaderContent = (WebElement) ((RemoteWebDriver) driver).executeScript("return arguments[0].shadowRoot", printPreviewHeader);
		
		driver.findElement(By.cssSelector("cr-button[class*=cancel]")).click();*/
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfWindowsToBe(2));
		driver.switchTo().window(driver.getWindowHandles().stream().skip(1).findFirst().get());
		WebElement printPreviewApp = driver.findElement(By.tagName("print-preview-app"));
		WebElement printPreviewAppConten = null;
		try {
			printPreviewAppConten = expandShadowRoot(printPreviewApp, driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement printPreviewSidebar = printPreviewAppConten.findElement(By.tagName("print-preview-sidebar"));
		WebElement printPreviewSidebarContent = expandShadowRoot(printPreviewSidebar, driver);
		WebElement printPreviewHeader = printPreviewSidebarContent.findElement(By.tagName("print-preview-header"));
		WebElement printPreviewHeaderContent = expandShadowRoot(printPreviewHeader, driver);
		printPreviewHeaderContent.findElement(By.cssSelector("cr-button[class*=cancel]")).click();
		
		
		//endTime = time.time() + 180
			    // switch to print preview window
			 /*   driver.switchTo().window(driver.getWindowHandles());
			            // get the cancel button
			    WebElement printPreviewAppContent = (WebElement) ((RemoteWebDriver) driver).executeScript(
			                "return document.querySelector('print-preview-app').shadowRoot.querySelector('#sidebar').shadowRoot.querySelector('print-preview-header#header').shadowRoot.querySelector('paper-button.cancel-button')")
			            if cancelButton:
			                // click on cancel
			                cancelButton.click()
			                // switch back to main window
			                driver.switch_to.window(driver.window_handles[0])
			                return True
			        time.sleep(1)
			        if time.time() > endTime:
			            driver.switch_to.window(driver.window_handles[0])
			            break
		*/
	}
	
	private static WebElement expandShadowRoot(WebElement parent, WebDriver driver) {
	    return (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", parent);
	}
	
	
}
