package BlogRedesign;

import pageActions.CommonPageActions;
import pageLocators.ArticlePageObjects;
import pageLocators.BlogPageObjects;
import pageLocators.CommonPageLocators;
import stepDefinations.Hooks;

import static automationFramework.DataReader.blogProperties;
import static automationFramework.DataReader.geturl;
import static automationFramework.PageActions.clickElement;
import static automationFramework.StartDriver.driver;
import static automationFramework.Waits.*;
import static pageLocators.BlogPageObjects.*;
import static BlogRedesign.commonFunctions.articleContent;
import static BlogRedesign.commonFunctions.articleHeading;
import static BlogRedesign.commonFunctions.articleLink;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static stepDefinations.Hooks.configProperties;

import automationFramework.PageActions;


public class blogArticleFunctions {
	
	static PageActions pageActions = new PageActions();
	static CommonPageActions commonPageActions = new CommonPageActions();
	static CommonPageLocators commonPageLocators = new CommonPageLocators();
	static BlogPageObjects blogPageObjects = new BlogPageObjects();
	static csvFileReader csvReader = new csvFileReader();
	static ArticlePageObjects articlePageObjects = new ArticlePageObjects();
	public static Logger log = Logger.getLogger(blogArticleFunctions.class);
	public static int flagFail = 0;
	public static String outputReult = "";
	public static String output = "";
	public static String logResult = "";
	public static String migrateStatus = "";
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 15-Dec-2023
	 * Method Name	: verifyArticleHeading
	 * Description	: To validate the article heading in the body as expected
	 ---------------------------------------------------------------------------------------------------------*/
	
	public static void verifyArticleHeading() {
		try {
			String article = csvFileReader.dataMapping.get("Heading");
			WebElement element = null;
			//element = articlePageObjects.ArticleTitle1(article);
			if(verifyWebElementPresent(element)) {
				pageActions.scrollToElement(element);
				pageActions.setHighlight(element);
				System.out.println("Article heading '" + article + "' is present in the Blog Page");
			}else {
				System.out.println("Article heading '" + article + "' is not present in the Blog Page");
				Assert.fail("Article heading '" + article + "' is not present in the Blog Page");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 15-Dec-2023
	 * Method Name	: verifyArticleNavigationLink
	 * Description	: To validate the article navigated URL as expected
	 ---------------------------------------------------------------------------------------------------------*/
	
	public static void verifyArticleNavigationLink() {
		try {
			String url = csvFileReader.dataMapping.get("URL");
			commonFunctions.validateURL(url);
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 25-01-2023
	 * Method Name	: verifyArticleDetails
	 * Description	: To validate the article title, created By and date details in the body as expected
	 ---------------------------------------------------------------------------------------------------------*/
	
	public static void verifyArticleDetails(String key) throws ParseException {
			String value = "";
			WebElement element = null;
			switch(key) {
				case "title":
					value = articleContent.get("headline");
					element = articlePageObjects.ArticleTitle();
					break;
				case "author":
					value = articleContent.get("author");
					element = articlePageObjects.ArticleAuthor();
					break;
				case "publishDate":
					value = articleContent.get("publishDate");
					SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
					Date d = dat.parse(value);
					SimpleDateFormat dat1 = new SimpleDateFormat("MMMM dd, yyyy");
					value = (dat1.format(d));
					element = articlePageObjects.ArticleCreatedDate();
					break;
				case "image":
					value = articleContent.get("heroImage");
					//value = value.replaceAll("Blog", "webimage");
					//value = value.replaceAll(".jpg", "");
					//value = value.replaceAll(".png", "");
					element = articlePageObjects.ArticleImage();
					break;
				default:
					log.info("Case not defined for the key:"+key);
					break;
			}
				if(element!=null) {
					pageActions.scrollToElement(element);
					pageActions.setHighlight(element);
					if(key.equals("image")) {
						if(element.getAttribute("src").contains(value)) {
							log.info("Article " + key + " <" + element.getAttribute("src") + "> MATCHED\n");
							logResult =logResult + "Article " + key + " <" + element.getAttribute("src") + "> MATCHED\n\n";
							output = output+"|"+"Pass\t|";
						}else {
							log.error("Article " + key + " <" + element.getAttribute("src") + "> NOT MATCHED");
							logResult = logResult + "Article " + key + " <" + element.getAttribute("src") + "> MATCHED\n\n";
							output = output+"|"+"Fail\t|";
							flagFail = 1;
						}
					}else {
						if((element.getText().replaceAll("\u00A0", " ").trim()).equals(value)) {
							log.info("Article " + key + " <" + element.getText() + "> MATCHED");
							logResult =logResult + "Article " + key + " <" + element.getText() + "> MATCHED\n\n";
							output = output+"|"+"Pass\t";
						}else {
							log.error("Article " + key + " <" + element.getText() + "> NOT MATCHED");
							logResult =logResult + "Article " + key + " <" + element.getText() + "> NOT MATCHED\n\n";
							output = output+"|"+"Fail\t";
							flagFail = 1;
						}
					}
				}else {
					if(key.equals("title")) {
						migrateStatus = "FAIL";
					}
					log.error("Article " + key + "NOT MATCHED FOUND");
					logResult =logResult + "Article " + key + "NOT MATCHED FOUND\n\n";
					output = output+"|"+"Fail\t\n";
					flagFail = 1;
				}
				
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 10-01-2024
	 * Method Name	: articleContentCheck
	 * Description	: 
	 ---------------------------------------------------------------------------------------------------------*/
	public static void articleContentCheck() {
		
		WebElement element = null;
		/*List<WebElement> contentHead = driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2"));
		WebElement element = blogPageObjects.articleContent(Integer.toString(contentHead.size()));
		//element = blogPageObjects.articleContent;
		System.out.println(element.getText());*/
		element = articlePageObjects.articleContent;
		System.out.println(element.getText());
		//div[contains(@class,'templates_main')]/h2[3]/parent::div
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 22-12-2023
	 * Method Name	: validateFeedbackSection
	 * Description	: To submit the feedback and check the response
	 ---------------------------------------------------------------------------------------------------------*/
	
	public static void validateFeedbackSection() {
		
		WebElement element = null;
		//element = blogPageObjects.divfeedback;
		if(verifyWebElementPresent(element)) {
			pageActions.scrollToElement(element);
			pageActions.setHighlight(element);
			try {
			//	element = articlePageObjects.chkboxIamNotARobot;
				pageActions.setHighlight(element);
				pageActions.clickElement(element, "I am not a Robot checkbox", false);
				//element = articlePageObjects.windowImageSelect;
				if(verifyWebElementPresent(element)) {
					pageActions.setHighlight(element);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			element = articlePageObjects.btnSendFeedback;
			pageActions.setHighlight(element);
			System.out.println("Validate feedback section in the article page is validated successfully");
		}else {
			System.out.println("Validate feedback section in the article page is not validated");
			Assert.fail("Validate feedback section in the article page is not validated");
		}
		
	}
	
		
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 05-01-2024
	 * Method Name	: rightPanelSocialLinkCheck
	 * Description	: To validate teh social links in blog article page right panel.
	 ---------------------------------------------------------------------------------------------------------*/
	
	public static void rightPanelSocialLinkCheck() {
		
		String links = blogProperties.getProperty("rightPanelSocialLink");
		String[] temp_split = links.split("\\|");
		String linkName = "";
		WebElement element = null;
		try {
			for(int i=0; i < temp_split.length ; i++) {
				linkName = temp_split[i];
				element = articlePageObjects.rightPanelSocialLink(linkName);
				if(verifyWebElementPresent(element)) {
					pageActions.setHighlight(element);
					System.out.println("'" + commonFunctions.boldString(linkName) + "' logo is present in the Blog Page Right panel");
				}else {
					System.err.println("'" + commonFunctions.boldString(linkName) + "' logo is not present in the Blog Page Right panel");
				}
			}
			pageActions.scrollToElement(element);
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 23-01-2024
	 * Method Name	: enterCaptcha
	 * Description	: To enter the captcha in feedback section
	 ---------------------------------------------------------------------------------------------------------*/
	public static void clickCaptcha() {
		By element = null;
		try {
			Thread.sleep(10000);;
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
	                By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));
	       
	        wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[@class='recaptcha-checkbox-border']"))).click();
	        log.info("Clicked the captcha successfully");
		}catch(Exception e) {
			log.error("Error in clicking the captcha: "+e);
			Assert.fail("Error in clicking the captch");
		}
	}
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 29-01-2024
	 * Method Name	: checkArticleContent
	 * Description	: To check the content of the article
	 ---------------------------------------------------------------------------------------------------------*/
	public static void checkArticleContent(String type) {
		
		List<WebElement> element = null;
		WebElement ele = null;
		String value = "";
		String data = "";
		int paraCOunt = 0;
		int count = 0;
		switch(type) {
			case "headings":
				element = articlePageObjects.articleHeading();
				for (int i=0; i<element.size(); i++) {
					value = element.get(i).getText();
					data = articleHeading.get("Heading"+(i+1));
					if(value.equals(data)) {
						log.info("Heading"+(i+1)+" Matched\n"+data+"\n"+value);
					}else {
						log.info("Heading"+(i+1)+" not Matched\n"+data+"\n"+value);
						flagFail = 1;
					}
				}
				
				count = Integer.parseInt(articleContent.get("ParagraphCount"));
				if(count>0) {
					int subCOunt =0;
					for(int i=1; i<=count; i++) {
							subCOunt = Integer.parseInt(articleContent.get("SubHeading"+(i)));
							if(subCOunt>0) {
								for(int k=1;k<=subCOunt; k++) {
									if(i==1) {
										ele = articlePageObjects.articleSubHead(k);
									}else if(i==count){
										ele = articlePageObjects.articleSubHead2(i-1,k);
									}else {
										ele = articlePageObjects.articleSubHead1(i,i-1,k);
									}
									data = articleHeading.get("Sub_Heading"+i+"_"+(k));
									value = ele.getText();
									if(((value.trim().replaceAll("\n", ""))).equals(data)) {
										log.info("Sub_Heading"+i+"_"+(k)+" Matched\n"+data+"\n"+value);
									}else {
										log.info("Sub_Heading"+i+"_"+(k)+" not Matched\n"+data+"\n"+value);
										flagFail = 1;
									}
								}
							}
					}
				}
				break;
				
			case "paragraphs":
				count = Integer.parseInt(articleContent.get("ParagraphCount"));
				System.out.println("ParagraphCount:"+count);
				for(int i=1; i<=count; i++) {
					if(i==1) {
						element = articlePageObjects.articleParagraph();
					}else if(i==count){
						element = articlePageObjects.articleParagraph2(i-1);
					}else {
						element = articlePageObjects.articleParagraph1(i,i-1);
					}
					paraCOunt = Integer.parseInt(articleContent.get("ParagraphCount"+(i)));
					for (int j=0; j<element.size(); j++) {
						value = element.get(j).getText();
						data = articleContent.get("Paragraph"+i+"_"+(j+1));
						if(((value.trim().replaceAll("\n", ""))).equals(data)) {
							log.info("Paragraph"+i+"_"+(j+1)+" Matched\n"+data+"\n"+value);
						}else {
							log.error("Paragraph"+i+"_"+(j+1)+" not Matched\n"+data+"\n"+value);
							flagFail = 1; 
						}
					}
				}
				break;
				
			case "links":
				count = Integer.parseInt(articleContent.get("ParagraphCount"));
				int linkCOunt =0;
				String[] link = null;
				// To validate links in Paragraphs
				for(int i=1; i<=count; i++) {
					paraCOunt = Integer.parseInt(articleContent.get("ParagraphCount"+(i)));
					for (int j=0; j<paraCOunt; j++) {
						linkCOunt = Integer.parseInt(articleContent.get("Paragraph"+i+"_"+(j+1)+"_linkCount"));
						if(linkCOunt>0) {
							for(int k=1;k<=linkCOunt; k++) {
								if(i==1) {
									ele = articlePageObjects.articleLink(((paraCOunt-(j+1))+1) ,k);
								}else if(i==count){
									ele = articlePageObjects.articleLink2((paraCOunt-j),i-1,k);
								}else {
									ele = articlePageObjects.articleLink1((paraCOunt-j),i,i-1,k);
								}
								data = articleLink.get("Paragraph"+i+"_"+(j+1)+"_link"+k);
								link = data.split("\\|");
								value = ele.getAttribute("href");
								if((value.trim()).equals(link[1])) {
									log.info("Paragraph"+i+"_"+(j+1)+"_link"+k+" Matched\n"+data+"\n"+ele.getText()+"|"+value);
								}else {
									log.info("Paragraph"+i+"_"+(j+1)+"_link"+k+" not Matched\n"+data+"\n"+ele.getText()+"|"+value);
									flagFail = 1;
								}
							}
						}
					}
				}
				// To validate links in List
				for(int i=1; i<=count; i++) {
					paraCOunt = Integer.parseInt(articleContent.get("ListCount"+(i)));
					for (int j=0; j<paraCOunt; j++) {
						linkCOunt = Integer.parseInt(articleContent.get("List"+i+"_"+(j+1)+"_linkCount"));
						if(linkCOunt>0) {
							for(int k=1;k<=linkCOunt; k++) {
								if(i==1) {
									ele = articlePageObjects.articleULink(k,1);
								}else if(i==count){
									ele = articlePageObjects.articleULink2(i-1,k,1);
								}else {
									ele = articlePageObjects.articleULink1(i,i-1,k,1);
								}
								//data = articleLink.get("List"+i+"_"+(j+1)+"_link"+k,1);
								link = data.split("\\|");
								value = ele.getAttribute("href");
								if((value.trim()).equals(link[1])) {
									log.info("List"+i+"_"+(j+1)+"_link"+k +" Matched\n"+data+"\n"+ele.getText()+"|"+value);
								}else {
									log.info("List"+i+"_"+(j+1)+"_link"+k +" not Matched\n"+data+"\n"+ele.getText()+"|"+value);
									flagFail = 1;
								}
							}
						}
					}
				}
					
				// To validate links in Sub Headings
				for(int i=1; i<=count; i++) {
					paraCOunt = Integer.parseInt(articleContent.get("SubHeading"+(i)));
					for (int j=0; j<paraCOunt; j++) {
						linkCOunt = Integer.parseInt(articleContent.get("Sub_Heading"+i+"_"+(j+1)+"_linkCount"));
						if(linkCOunt>0) {
							for(int k=1;k<=linkCOunt; k++) {
								if(i==1) {
									ele = articlePageObjects.articleHeadLink((j+1));
								}else if(i==count){
									ele = articlePageObjects.articleHeadLink2(i-1,(j+1));
								}else {
									ele = articlePageObjects.articleHeadLink1(i,i-1,(j+1));
								}
								data = articleLink.get("Sub_Heading"+i+"_"+(j+1)+"_link"+k);
								link = data.split("\\|");
								pageActions.setHighlight(ele);
								value = ele.getAttribute("href");
								if((value.trim()).equals(link[1])) {
									log.info("Sub_Heading"+i+"_"+(j+1)+"_link"+k +" Matched\n"+data+"\n"+ele.getText()+"|"+value);
								}else {
									log.info("Sub_Heading"+i+"_"+(j+1)+"_link"+k +" not Matched\n"+data+"\n"+ele.getText()+"|"+value);
									flagFail = 1;
								}
							}
						}
					}
				}	
				break;
			case "Lists":
				count = Integer.parseInt(articleContent.get("ParagraphCount"));
				int listCOunt =0;
				// To validate links in Paragraphs
				for(int i=1; i<=count; i++) {
					listCOunt = Integer.parseInt(articleContent.get("ListCount"+(i)));
					if(listCOunt>0) {
						for(int k=1;k<=listCOunt; k++) {
							if(i==1) {
								ele = articlePageObjects.articleList(k);
							}else if(i==count){
								ele = articlePageObjects.articleList2(i-1,k);
							}else {
								ele = articlePageObjects.articleList1(i,i-1,k);
							}
							data = articleContent.get("List"+i+"_"+(k));
							value = ele.getText();
							if(((value.trim())).equals(data)) {
								log.info("List"+i+"_"+(k)+" Matched\n"+data+"\n"+value);
							}else {
								log.info("List"+i+"_"+(k)+" not Matched\n"+data+"\n"+value);
								flagFail = 1;
							}
						}
					}
				}
				break;
			case "content":
				element = articlePageObjects.articleHeading();
				ele = articlePageObjects.articleContent(Integer.toString(element.size()));
				System.out.println(ele.getText());
				break;
			default:
				break;
		}
		if(flagFail>0) {
			output = output+"|"+"Fail\t";
		}else {
			output = output+"|"+"Pass\t";
		}
		
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 06-02-2024
	 * Method Name	: checkArticleContentInOrder
	 * Description	: To check the content of the article
	 ---------------------------------------------------------------------------------------------------------*/
	public static void checkArticleContentInOrder() throws ParseException {
		
		flagFail = 0;
		checkArticleBaseData();
		
		if(!migrateStatus.equals("FAIL")) {
			List<WebElement> element = null;
			WebElement ele = null;
			
			String prevTitle = "";
	
			
			int headingCount = Integer.parseInt(articleContent.get("ParagraphCount"))-1;
			int blockCount = Integer.parseInt(articleContent.get("ParagraphCount"));
			int paraCount = 0;
			int listCount = 0;
			int linkCount = 0;
			int ordlistCount = 0;
			int subHeadingCount = 0;
			int subHeading4Count = 0;
			int imageCount = 0;
			int videoCount = 0;
			int count = 0;
			int headK = 0;
			
			//For storing pass and fail count
			int headPassResult = 0, headFailResult = 0, headResult = 0;
			int paraPassResult = 0, paraFailResult = 0, paraResult = 0;
			int listPassResult = 0, listFailResult = 0, listResult = 0;
			int linkPassResult = 0, linkFailResult = 0, linkResult = 0;
			int imagePassResult = 0, imageFailResult = 0, imageResult =0;
			int videoPassResult = 0, videoFailResult = 0, videoResult =0;
			
			String resultLog = "";
			
			String appValue = "";
			String fileValue = "";
			
			String parakey = "";
			String paraValue = "";
			
			String listKey = "";
			String listValue = "";
			
			String subHeadKey = "";
			String subHeadValue = "";
			
			String headKey = "";
			String headValue = "";
			
			String linkKey = "";
			String linkValue = "";
			
			String imgKey = "";
			String imgValue = "";
			
			String ordListKey = "";
			String ordListValue = "";
			
			String videoKey = "";
			String videoValue = "";
			
			String subHead4Key = "";
			String subHead4Value = "";
			
			String type = "";
			//Loop for checking the blocks
			for (int i=1; i<=blockCount; i++) {
				paraCount = Integer.parseInt(articleContent.get("ParagraphCount"+i));
				listCount = Integer.parseInt(articleContent.get("ListCount"+i));
				subHeadingCount = Integer.parseInt(articleContent.get("SubHeading"+i));
				imageCount = Integer.parseInt(articleContent.get("Image"+i));
				ordlistCount = Integer.parseInt(articleContent.get("OrdListCount"+i));
				videoCount = Integer.parseInt(articleContent.get("Video"+i));
				subHeading4Count = Integer.parseInt(articleContent.get("Sub4Heading"+i));
				linkCount = 0;
				int listK = 1;
				int ordlistK = 1;
				int paraK = 0;
				int subHeadK = 1;
				int subHead4K = 1;
				int paralinkK = 1;
				int subHeadLinkK = 1;
				int imageK = 1;
				int videoK = 1;
				count = paraCount+listCount+subHeadingCount+ordlistCount;
				
				//For validating paragraphs under each block
				for (int j=0; j<count; j++) {
					
					parakey = "Paragraph"+i+"_"+(j+1);
					paraValue = articleContent.get(parakey);
					
					listKey = "List"+i+"_"+(j+1);
					listValue = articleContent.get(listKey);
					
					subHeadKey = "Sub_Heading"+i+"_"+(j+1);
					subHeadValue = articleHeading.get(subHeadKey);
					
					imgKey = "Image"+i+"_"+(j+1);
					imgValue = articleContent.get(imgKey);
					
					ordListKey = "OrdList"+i+"_"+(j+1);
					ordListValue = articleContent.get(ordListKey);
					
					videoKey = "Video"+i+"_"+(j+1);
					videoValue = articleContent.get(videoKey);
					
					subHead4Key = "Sub4_Heading"+i+"_"+(j+1);
					subHead4Value = articleHeading.get(subHead4Key);
	
					if(!(paraValue==null)) {
						
						prevTitle = parakey;
						
						if(headingCount!=0) {
							if(i==1) {
								element = articlePageObjects.articleParagraph();
							}else if(i==blockCount){
								element = articlePageObjects.articleParagraph2(i-1);
							}else {
								element = articlePageObjects.articleParagraph1(i,i-1);
							}
						}else {
							element = articlePageObjects.articlePara();
						}
						type = "Paragraph ";
						fileValue = paraValue;
						appValue = element.get(paraK).getText();
						appValue = appValue.trim().replaceAll("\\n", "");
						paraK = paraK+1;
						linkCount = Integer.parseInt(articleContent.get(parakey+"_linkCount"));
						if(linkCount>0) {
							
							String[] link = null;
							String value = "";
							String temp = "";
							for(int k=1;k<=linkCount; k++) {
								if(headingCount!=0) {
									if(i==1) {
										ele = articlePageObjects.articleLink(((paraCount+1)-paraK),k);
									}else if(i==blockCount){
										ele = articlePageObjects.articleLink2(paraK,i-1,k);
									}else {
										ele = articlePageObjects.articleLink1(((paraCount+1)-paraK),i,i-1,k);
									}
								}else {
									ele = articlePageObjects.articleLinkwithoutHead(paraK,k);
								}
								linkKey = parakey+"_link"+k;
								linkValue = articleLink.get(linkKey);
								link = linkValue.split("\\|");
								temp = linkValue;
								linkValue = link[1];
								if(!linkValue.contains("http")) {
									if(!linkValue.contains("mailto"))
										linkValue = configProperties.getProperty("linkURL") + linkValue;
								}
								value = ele.getAttribute("href");
								
								if(((value.trim())).contains(linkValue)) {
									if(configProperties.getProperty("report").equals("simple")) {
										log.info("Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED\n");
										logResult =logResult + "Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED\n\n";}
									else if(configProperties.getProperty("report").equals("detail")) {
										log.info("Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n");
										logResult =logResult + "Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n\n";}
									else {
										if(j==0) {
											log.info("Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n");
											logResult =logResult + "Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n\n";}
										else {
											log.info("Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED\n");
											logResult =logResult + "Block"+i+"_Paragraph"+(j+1)+"_link "+k+" MATCHED\n\n";}
									}
									linkPassResult = linkPassResult+1;
									//log.info("Link"+i+"_"+(j+1)+"_link"+k+" MATCHED\n\n"+temp+"\n");
									//log.info(keyValue+"\n"+appValue);
								}else {
									log.info("Block"+i+"_Paragraph"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n");
									logResult = logResult + "Block"+i+"_Paragraph"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n\n";
									flagFail = 1; 
									linkFailResult = linkFailResult+1;
								}
							}
							paralinkK = paralinkK + 1;
							
						}
						
					}else if(!(listValue==null)) {
						
						prevTitle = listKey;
						
						if(headingCount!=0) {
							if(i==1) {
								ele = articlePageObjects.articleList((listCount+1)-listK);
							}else if(i==blockCount){
								ele = articlePageObjects.articleList2(i-1,listK);
							}else {
								ele = articlePageObjects.articleList1(i,i-1,(listCount+1)-listK);
							}
						}else {
							ele = articlePageObjects.articleListWithoutHead(listK);
						}
						listK = listK+1;
						type = "List ";
						fileValue = listValue;
						appValue = ele.getText().replaceAll("\\t", "");
						
						linkCount = Integer.parseInt(articleContent.get(listKey+"_linkCount"));
						
						if(linkCount>0) {
							
							String[] link = null; 
							String value = "";
							String temp = "";
							int listLinkCount = 0;
							listLinkCount = Integer.parseInt(articleContent.get(listKey+"_ListlinkCount"))-1;
							for(int k=1;k<=listLinkCount; k++) {
								linkCount = Integer.parseInt(articleContent.get(listKey+"_linkCount"+k));
								if(linkCount>0) {
									for(int s=1; s<=linkCount; s++) {
										if(headingCount!=0) {
											if(i==1) {
												ele = articlePageObjects.articleULink(k,s);
											}else if(i==blockCount){
												ele = articlePageObjects.articleULink2(i-1,k,s);
											}else {
												ele = articlePageObjects.articleULink1(i,i-1,k,s);
											}
										}else {
											if(linkCount == 1) {
												ele = articlePageObjects.articleULinkWithoutHead(listK-1,k,s);
											}else {
												ele = articlePageObjects.articleULinkWithoutHead1(listK-1,k,s);
											}
										}
										linkKey = listKey+"_list"+k+"_link_"+s;
										linkValue = articleLink.get(linkKey);
										link = linkValue.split("\\|");
										temp = linkValue;
										linkValue = link[1];
										if(!linkValue.contains("http")) {
											if(!linkValue.contains("mailto"))
												linkValue = configProperties.getProperty("linkURL") + linkValue;
										}
										value = ele.getAttribute("href");
										
										if(((value.trim())).contains(linkValue)) {
											if(configProperties.getProperty("report").equals("simple")) {
												log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n");
												logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n";}
											else if(configProperties.getProperty("report").equals("detail")) {
												log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n");
												logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n\n";}
											else {
												if(j==0) {
													log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n");
													logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n\n";}
												else {
													log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n");
													logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n";}
											}
											linkPassResult = linkPassResult+1;
											//log.info("Link"+i+"_"+(j+1)+"_link"+k+" MATCHED\n\n"+temp+"\n");
											//log.info(keyValue+"\n"+appValue);
										}else {
											log.info("Block"+i+"_List"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n");
											logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n\n";
											flagFail = 1; 
											linkFailResult = linkFailResult+1;
										}
									}
								}
							}
						}
	
					}else if(!(subHeadValue==null)) {
						
						prevTitle = subHeadKey;
						
						if(headingCount!=0) {
							if(i==1) {
								ele = articlePageObjects.articleSubHead((subHeadingCount+1)-subHeadK);
							}else if(i==blockCount){
								ele = articlePageObjects.articleSubHead2(i-1,subHeadK);
							}else {
								ele = articlePageObjects.articleSubHead1(i,i-1,(subHeadingCount+1)-subHeadK);
							}
						}else {
							ele = articlePageObjects.articleSubHead3(subHeadK);
						}
						
						subHeadK =subHeadK+1;
						type = "Sub Heading ";
						fileValue = subHeadValue;
						appValue = ele.getText();
							linkCount = Integer.parseInt(articleContent.get(subHeadKey+"_linkCount"));
							
							if(linkCount>0) {
								
								String[] link = null; 
								String value = "";
								String temp = "";
								for(int k=1;k<=linkCount; k++) {
									if(i==1) {
										ele = articlePageObjects.articleHeadLink((subHeadingCount+1)-subHeadK);
									}else if(i==blockCount){
										ele = articlePageObjects.articleHeadLink2(i-1,subHeadLinkK);
									}else {
										ele = articlePageObjects.articleHeadLink1(i,i-1,subHeadLinkK);
									}
									//div[contains(@class,'templates_main')]/h2[3]/preceding-sibling::h2[2]/following-sibling::h3[4]/a
									System.out.println("subHeadLinkK:"+subHeadLinkK);
									subHeadLinkK = subHeadLinkK+1;
									linkKey = subHeadKey+"_link"+k;
									linkValue = articleLink.get(linkKey);
									link = linkValue.split("\\|");
									temp = linkValue;
									linkValue = link[1];
									if(!linkValue.contains("http")) {
										if(!linkValue.contains("mailto"))
											linkValue = configProperties.getProperty("linkURL") + linkValue;
									}
									value = ele.getAttribute("href");
									
									if(((value.trim())).contains(linkValue)) {
										if(configProperties.getProperty("report").equals("simple")) {
											log.info("Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED\n");
											logResult =logResult + "Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED\n\n";}
										else if(configProperties.getProperty("report").equals("detail")) {
											log.info("Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n");
											logResult =logResult + "Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n\n";}
										else {
											if(j==0) {
												log.info("Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n");
												logResult =logResult + "Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n\n";}
											else {
												log.info("Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED\n");
												logResult =logResult + "Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" MATCHED\n\n";}
										}
										linkPassResult = linkPassResult+1;
										//log.info("Link"+i+"_"+(j+1)+"_link"+k+" MATCHED\n\n"+temp+"\n");
										//log.info(keyValue+"\n"+appValue);
									}else {
										log.info("Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n");
										logResult =logResult + "Block"+i+"_Sub_Heading"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n\n";
										flagFail = 1; 
										linkFailResult = linkFailResult+1;
									}
								}
								
						}else {
							subHeadLinkK = subHeadLinkK+1;
						}
						
					}else if(!(subHead4Value==null)) {
						
						if(i==1) {
							ele = articlePageObjects.articleSub4Head(subHead4K);
						}else if(i==blockCount){
							ele = articlePageObjects.articleSub4Head2(i-1,subHead4K);
						}else {
							ele = articlePageObjects.articleSub4Head1(i,i-1,(subHeading4Count+1)-subHead4K);
						}
						subHead4K =subHead4K+1;
						type = "Sub Heading ";
						fileValue = subHead4Value;
						appValue = ele.getText();
						
					}else if(!(imgValue==null)) {
						
						System.out.println("paraCount"+((paraCount+1)));
						if(headingCount!=0) {
							if(i==1) {
								ele = articlePageObjects.articleImage((count-j));
								paraK = paraK+1;
							}else if(i==blockCount){
								paraK = paraK+1;
								ele = articlePageObjects.articleImage2(paraK,i-1);
							}else {
								paraK = paraK+1;
								ele = articlePageObjects.articleImage1(((paraCount+1)-paraK),i,i-1);
							}
						}else {
							paraK = paraK+1;
							ele = articlePageObjects.articleImage3(paraK);
						}
						imageK =imageK+1;
						type = "Image ";
						fileValue = imgValue;
						if(!(ele==null)) {
							appValue = ele.getAttribute("src").replaceAll("_", "");
						}else {
							appValue = "Image NOT FOUND";
						}
						
					}else if(!(videoValue==null)) {
						
						paraK = paraK+1;
						
						if(headingCount!=0) {
							if(prevTitle.contains("List")) {
								ele = articlePageObjects.articleListVideo(listK-1,i-1);
							}else {
								if(i==1) {
									ele = articlePageObjects.articleVideo((count-j));
								}else if(i==blockCount){
									ele = articlePageObjects.articleVideo2(paraK,i-1);
								}else {
									ele = articlePageObjects.articleVideo1(((paraCount+1)-paraK),i,i-1);
								}
							}
						}else {
							if(prevTitle.contains("List")) {
								ele = articlePageObjects.articleListVideo1(listK-1);
							}else {
								ele = articlePageObjects.articleVideo3(paraK);
							}
						}
						
						videoK =videoK+1;
						type = "Video ";
						fileValue = videoValue.replaceAll("www.", "");
						if(!(ele==null)) {
							appValue = ele.getAttribute("src").replaceAll("_", "");
						}else {
							appValue = "Video NOT FOUND";
						}
						
					}else if(!(ordListValue==null)) {
						
						
						if(headingCount!=0) {
							if(i==1) {
								ele = articlePageObjects.articleOrdList(ordlistK);
							}else if(i==blockCount){
								ele = articlePageObjects.articleOrdList2(i-1,ordlistK);
							}else {
								ele = articlePageObjects.articleOrdList1(i,i-1,(ordlistCount+1)-ordlistK);
							}
						}else {
							ele = articlePageObjects.articleOrdListWithoutHead(ordlistK);
						}
						
						ordlistK = ordlistK+1;
						type = "List ";
						fileValue = ordListValue;
						appValue = ele.getText().replaceAll("\\t", "");
						linkCount = Integer.parseInt(articleContent.get(ordListKey+"_linkCount"));
						
						if(linkCount>0) {
							
							String[] link = null; 
							String value = "";
							String temp = "";
							for(int k=1;k<=linkCount; k++) {
								if(i==1) {
									ele = articlePageObjects.articleOLink(k);
								}else if(i==blockCount){
									ele = articlePageObjects.articleOLink2(i-1,k);
								}else {
									ele = articlePageObjects.articleOLink1(i,i-1,k);
								}
								linkKey = ordListKey+"_link"+k;
								linkValue = articleLink.get(linkKey);
								link = linkValue.split("\\|");
								temp = linkValue;
								linkValue = link[1];
								if(!linkValue.contains("http")) {
									if(!linkValue.contains("mailto"))
										linkValue = configProperties.getProperty("linkURL") + linkValue;
								}
								value = ele.getAttribute("href");
								
								if(((value.trim())).contains(linkValue)) {
									if(configProperties.getProperty("report").equals("simple")) {
										log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n");
										logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n";}
									else if(configProperties.getProperty("report").equals("detail")) {
										log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n");
										logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n\t"+ele.getText()+"|"+value+"\n\n";}
									else {
										if(j==0) {
											log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n");
											logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED : "+ele.getText()+"|"+value+"\n\n";}
										else {
											log.info("Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n");
											logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" MATCHED\n\n";}
									}
									linkPassResult = linkPassResult+1;
									//log.info("Link"+i+"_"+(j+1)+"_link"+k+" MATCHED\n\n"+temp+"\n");
									//log.info(keyValue+"\n"+appValue);
								}else {
									log.info("Block"+i+"_List"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n");
									logResult =logResult + "Block"+i+"_List"+(j+1)+"_link "+k+" NOT MATCHED\n\n\t"+temp+"\n\n\t"+ele.getText()+"|"+value+"\n\n";
									flagFail = 1; 
									linkFailResult = linkFailResult+1;
								}
							}
						}
	
					}
					
					if(imgValue==null && videoValue==null) {
						if(appValue.replaceAll("_", "").equals(fileValue)) {
							if(configProperties.getProperty("report").equals("simple")) {
								log.info("Block"+i+"_"+type+(j+1)+" MATCHED\n");
								logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED\n\n";}
							else if(configProperties.getProperty("report").equals("detail")) {
								log.info("Block"+i+"_"+type+(j+1)+" MATCHED\n\n\t"+appValue+"\n");
								logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED\n\n\t"+appValue+"\n\n";}
							else {
								if(j==0) {
									log.info("Block"+i+"_"+type+(j+1)+" MATCHED : "+appValue+"\n");
									logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED : "+appValue+"\n\n";}
								else {
									log.info("Block"+i+"_"+type+(j+1)+" MATCHED\n");
									logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED\n\n";}
							}
		
							switch(type){
								case "Paragraph ":
									paraPassResult = paraPassResult+1;
									break;
								case "List ":
									listPassResult = listPassResult+1;
									break;
								case "Sub Heading ":
									headPassResult = headPassResult+1;
									break;
							}
							//log.info(keyValue+"\n"+appValue);
						}else {
							log.info("Block"+i+"_"+type+(j+1)+" NOT MATCHED\n\n\t"+fileValue+"\n\n\t"+appValue+"\n\n");
							logResult = logResult + "Block"+i+"_"+type+(j+1)+" NOT MATCHED\n\n\t"+fileValue+"\n\n\t"+appValue+"\n\n";
							flagFail = 1; 
							switch(type){
							case "Paragraph ":
								paraFailResult = paraFailResult+1;
								break;
							case "List ":
								listFailResult = listFailResult+1;
								break;
							case "Sub Heading ":
								headFailResult = headFailResult+1;
								break;
							}
						}
					}else {
						if(!(appValue.contains("NOT FOUND"))) {
							if(fileValue.contains(appValue)) {
								if(configProperties.getProperty("report").equals("simple")) {
									log.info("Block"+i+"_"+type+(j+1)+" MATCHED\n");
									logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED\n\n";}
								else if(configProperties.getProperty("report").equals("detail")) {
									log.info("Block"+i+"_"+type+(j+1)+" MATCHED\n\n\t"+appValue+"\n");
									logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED\n\n\t"+appValue+"\n\n";}
								else {
									if(j==0) {
										log.info("Block"+i+"_"+type+(j+1)+" MATCHED : "+appValue+"\n");
										logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED : "+appValue+"\n\n";}
									else {
										log.info("Block"+i+"_"+type+(j+1)+" MATCHED\n");
										logResult = logResult + "Block"+i+"_"+type+(j+1)+" MATCHED\n\n";}
								}
								switch(type){
									case "Image ":
										imagePassResult = imagePassResult+1;
										break;
									case "Video ":
										videoPassResult = videoPassResult+1;
										break;
								}
							}else {
								log.info("Block"+i+"_"+type+(j+1)+" NOT MATCHED\n\n\t"+fileValue+"\n\n\t"+appValue+"\n\n");
								logResult = logResult + "Block"+i+"_"+type+(j+1)+" NOT MATCHED\n\n\t"+fileValue+"\n\n\t"+appValue+"\n\n";
								flagFail = 1; 
								switch(type){
									case "Image ":
										imageFailResult = imageFailResult+1;
										break;
									case "Video ":
										videoFailResult = videoFailResult+1;
										break;
								}
							}
						}else {
							log.info("Block"+i+"_"+type+(j+1)+" NOT MATCHED\n\n\t"+fileValue+"\n\n\t"+appValue+"\n\n");
							logResult = logResult + "Block"+i+"_"+type+(j+1)+" NOT MATCHED\n\n\t"+fileValue+"\n\n\t"+appValue+"\n\n";
							flagFail = 1; 
							switch(type){
								case "Image ":
									imageFailResult = imageFailResult+1;
									break;
								case "Video ":
									videoFailResult = videoFailResult+1;
									break;
							}
						}
					}
					
				} // For Loop End - j
				//For validating Headings
				if(!(i==blockCount)) {
					headKey = "Heading"+i;
					headValue = articleHeading.get(headKey);
					element = articlePageObjects.articleHeading();	
					appValue = element.get(headK).getText();
					headK = headK + 1;
					if(appValue.equals(headValue)) {
						log.info("Heading "+i+": < "+headValue+" > MATCHED\n");
						logResult = logResult + "Heading "+i+": < "+headValue+" > MATCHED\n\n";
						headPassResult = headPassResult+1;
					}else {
						log.info("Heading "+i+" NOT MATCHED\n\n"+headValue+"\n"+appValue+"\n");
						logResult = logResult + "Heading "+i+" NOT MATCHED\n\n\t"+headValue+"\n\n\t"+appValue+"\n\n";
						flagFail = 1;
						headFailResult = headFailResult+1;
					}
				}
				
			} // For loop End - i
			//csvFileReader.createAndwriteToFile(logResult, articleContent.get("FileName"));
			outputReult = outputReult + "\t|Pass: "+headPassResult+" Fail: "+headFailResult+"\t|Pass: "+paraPassResult+" Fail: "+paraFailResult+
					"\t|Pass: "+linkPassResult+" Fail: "+linkFailResult+"\t|Pass: "+listPassResult+" Fail: "+listFailResult + 
					"\t|Pass: "+imagePassResult+" Fail: "+imageFailResult + "\t|Pass: "+videoPassResult+" Fail: "+videoFailResult + "\t|\n";
			
		}else {
			outputReult = outputReult + "\t|Not Migrated\t|\n";
		}
	}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 09-02-2024
	 * Method Name	: checkAllArticleContent
	 * Description	: To check the content of the article
	 ---------------------------------------------------------------------------------------------------------*/
	public static void checkAllArticleContent() throws IOException, ParseException, InterruptedException {
		int i=1;
		outputReult = outputReult+"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		outputReult = outputReult+"|ArticleName\t\t\t\t|Heading\t\t|Paragraph\t\t|Link\t\t\t|List\t\t\t|Image\t\t\t|Video\t\t\t|\n";
		outputReult = outputReult+"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		output = output+"--------------------------------------------------------------------------\n";
		output = output+"|ArticleName\t\t\t\t|Title\t|Author\t|Date\t|Image\t|\n";
		output = output+"--------------------------------------------------------------------------";
		for (Entry<String, String> entry : csvFileReader.dataMapping.entrySet()) {
		    String key = entry.getKey();
		    String temp = entry.getValue();
		    String[] split = temp.split("\\|");
		    String file = split[1];

		    //For getting category to frame url
		    String category = split[0];
		    
		    String fileName = category + "/" + file +".txt";
		    
		    //read the article content from md file and store it in HashMap
		    commonFunctions.readArticleContent1(fileName);
		    
		    //Storing the framed url
		    String url = configProperties.getProperty("articleUrl")+category+"/"+file;
			log.info("Article "+i+": "+file+"\n");
			
			//Navigate to the particular article page url
		    navigateToArticlePage(url);
		    
		    //validate the particular article contents
		    if(file.length()>=10 && file.length()<15) {
		    	outputReult = outputReult+"|"+ file + "\t\t\t";
		    	output = output+"\n|"+ file + "\t\t\t\t";
		    } else if(file.length()>=15 && file.length()<=20) {
		    	outputReult = outputReult+"|"+ file + "\t\t";
		    	output = output+"\n|"+ file + "\t\t\t";
			}else if(file.length()>20 && file.length()<=25) {
		    	outputReult = outputReult+"|"+ file + "\t\t";
		    	output = output+"\n|"+ file + "\t\t\t";
			}else if(file.length()<10) {
		    	outputReult = outputReult+"|"+ file + "\t\t\t";
		    	output = output+"\n|"+ file + "\t\t\t\t";
			}else if(file.length()>25 && file.length()<=39) {
		    	outputReult = outputReult+"|"+ file;
		    	output = output+"\n|"+ file + "\t";
			}else if(file.length()>=40 && file.length()<=50) {
		    	outputReult = outputReult+"|"+ file;
		    	output = output+"\n|"+ file;
			}
		    
			blogArticleFunctions.checkArticleContentInOrder();
			commonFunctions.clearHashMap();
			i= i+1;
			//outputReult = outputReult+"-----------------------------------------------------------------------------------------------------------------\n";
			if(migrateStatus.equals("FAIL")) {
				csvFileReader.createAndwriteToFile(logResult, "NOTMIGRATED_"+file);
			}else {
				if(flagFail == 1)
					csvFileReader.createAndwriteToFile(logResult, "FAIL_"+file);
				else
					csvFileReader.createAndwriteToFile(logResult, "PASS_"+file);
			}
			logResult = ""; 
			migrateStatus = "";
		}
		outputReult = outputReult+"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
		output = output+"\n--------------------------------------------------------------------------\n";
		log.info(output+"\n"+outputReult);
	}
	
	public static void checkArticleBaseData() throws ParseException {
		verifyArticleDetails("title");
		if(!migrateStatus.equals("FAIL")) {
			verifyArticleDetails("author");
			verifyArticleDetails("publishDate");
			verifyArticleDetails("image");
		}
	}
	
	   public static void navigateToArticlePage(String url)
	            throws InterruptedException, IOException, ParseException {
	        driver.get(url);
	        log.info("Article Page URL:  "+url+"\n");
	        logResult = logResult + "Article Page URL:  "+url+"\n\n";
	        waitTillPageLoad();
	       //if(!Hooks.TC_ID.equals("TC_0003"))
	    	  // commonPageActions.signupPopupClose();
		   //closeCookiesBottom();
	    }
	
	
	public static blogArticleFunctions getInstance() {
		blogArticleFunctions blogfunctions = null;
		if(blogfunctions == null) {
			blogfunctions = new blogArticleFunctions();
		}
		return blogfunctions;
	};

}
