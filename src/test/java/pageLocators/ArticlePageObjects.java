package pageLocators;

import static automationFramework.StartDriver.driver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BlogRedesign.blogArticleFunctions;

public class ArticlePageObjects {

	 public ArticlePageObjects() {
	        PageFactory.initElements(driver, this);
	 }
		
	//ZipIn section
		@FindBy(xpath = "//input[@id='home-zip-input']")
		public static WebElement inputZipCode;
		
		@FindBy(xpath = "//button[contains(@class,'SearchZip')]")
		public static WebElement buttonShopNow;
		
		@FindBy(xpath = "//h2[contains(@class,'zip-popup__title')]")
		public static WebElement zipInSuccess;
		
		
	//Sticky Zip-In
		@FindBy(xpath = "//div[contains(@class,'stickyZip')]/*//input[@id='home-zip-input']")
		public static WebElement inputStickyZipCode;
		
		@FindBy(xpath = "//div[contains(@class,'stickyZip')]/*//button")
		public static WebElement buttonStickySubmit;		
		
	//Feedback section
		@FindBy(xpath = "//form[@name='feedback-form']/textarea")
		public static WebElement textareaComments;
		
		@FindBy(xpath = "//button[@class='btn btn-primary']")
		public static WebElement btnSendFeedback;
		
		@FindBy(xpath = "//div[contains(@class,'Feedback_thankyou')]")
		public static WebElement feedbackSuccess;
		
		@FindBy(xpath = "//div[contains(@class,'BlogResearch')]")
		public static WebElement blogResearchWidget;
		
		@FindBy(xpath = "//div[contains(@class,'BlogResearch')]")
		public static WebElement headerHSLogo;
		
		//@FindBy(xpath = "//div[contains(@class,'templates_main')]")
		@FindBy(xpath = "//div[contains(@class,'templates_main')]/h2[3]/parent::div")
		public static WebElement articleContent;
		
	//Article Content
		@FindBy(xpath = "//div[contains(@class,' mx-auto')]//*/h1")
		public static WebElement ArticleTitle1;
		
		@FindBy(xpath = "//div[contains(@class,'templates_d_grid')]/div/p/span")
		public static WebElement ArticleAuthor1;
		
		@FindBy(xpath = "//div[contains(@class,'templates_d_grid')]/div/p[2]")
		public static WebElement ArticleCreatedDate1;
		
		@FindBy(xpath = "//div[contains(@class,' mx-auto')]/*//div[contains(@class,'mb-3')]/img")
		public static WebElement ArticleImage1;
		
		@FindBy(xpath = "//button[contains(@class,'templates_btn_print')]")
		public static WebElement buttonPrint;
				
	//Method to check the article content
		public WebElement articleContent(String count){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[" + count + "]/parent::div"));	
			return articleContent;
		}	
		
	//Method to check the article sub-heading1
		public WebElement articleHeading1(int count){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[" + count + "]"));	
			return articleContent;
		}
		
	//Method to check the article sub-headings
		public List<WebElement> articleHeading(){
			List<WebElement> articleContent = driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2"));	
			return articleContent;
		}
		
	//Method to check the article paragraphs
		public List<WebElement> articlePara(){
			List<WebElement> articleContent = driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/p"));	
			return articleContent;
		}
	
	//Method to check the article paragraphs
		public List<WebElement> articleParagraph(){
			List<WebElement> articleContent = driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::p"));	
			return articleContent;
		}
		
	//Method to check the article paragraphs
		public List<WebElement> articleParagraph2(int head){
			List<WebElement> articleContent = driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::p"));	
			return articleContent;
		}
		
	//Method to check the article paragraphs
		public List<WebElement> articleParagraph1(int head1, int head2){
			List<WebElement> articleContent = driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::p[preceding-sibling::h2["+head2+"]]"));	
			return articleContent;
		}
		
		//Method to check the article links
		public WebElement articleImage(int p1){
			if(driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::p["+p1+"]/img")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::p["+p1+"]/img"));	
				return articleContent;
			}
		}
				
	//Method to check the article links
		public WebElement articleImage2(int p1, int head){
			if(driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::p["+p1+"]/img")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::p["+p1+"]/img"));	
				return articleContent;
			}
		}
				
	//Method to check the article links
		public WebElement articleImage1(int p1, int head1,int head2){
			
			if(driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::p[preceding-sibling::h2["+head2+"]]["+p1+"]/img")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::p[preceding-sibling::h2["+head2+"]]["+p1+"]/img"));	
				return articleContent;
			}
		}
		
		//Method to check the article links
		public WebElement articleImage3(int p1){
			if(driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/p["+p1+"]/img")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/p["+p1+"]/img"));	
				return articleContent;
			}
		}
		
		//Method to check the article links
		public WebElement articleVideo(int p1){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::p["+p1+"]/div/iframe"));	
			return articleContent;
		}
				
	//Method to check the article links
		public WebElement articleVideo2(int p1, int head){
			if(driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::p["+p1+"]/div/iframe")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::p["+p1+"]/div/iframe"));	
				return articleContent;
			}
		}
		
		//Method to check the article links
				public WebElement articleVideo3(int p1){
					if(driver.findElements(By.xpath("//div[contains(@class,'templates_main')]/p["+p1+"]/div/iframe")).isEmpty()) {
						return null;
					}else {
						WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/p["+p1+"]/div/iframe"));	
						return articleContent;
					}
				}
				
	//Method to check the article links
		public WebElement articleVideo1(int p1, int head1,int head2){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::p[preceding-sibling::h2["+head2+"]]["+p1+"]/div/iframe"));	
			return articleContent;
		}
		
	//Method to check the article links
		public WebElement articleLinkwithoutHead(int p1,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/p["+p1+"]/a["+link+"]"));	
			return articleContent;
		}
				
	//Method to check the article links
		public WebElement articleLink(int p1,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::p["+p1+"]/a["+link+"]"));	
			return articleContent;
		}
				
	//Method to check the article links
		public WebElement articleLink2(int p1, int head,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::p["+p1+"]/a["+link+"]"));	
			return articleContent;
		}
				
	//Method to check the article links
		public WebElement articleLink1(int p1, int head1,int head2,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::p[preceding-sibling::h2["+head2+"]]["+p1+"]/a["+link+"]"));	
			return articleContent;
		}
		
	//Method to check the article links
		public WebElement articleHeadLink(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::h3["+link+"]/a"));	
			return articleContent;
		}
						
	//Method to check the article links
		public WebElement articleHeadLink2(int head, int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::h3["+link+"]/a"));	
			return articleContent;
		}
						
	//Method to check the article links
		public WebElement articleHeadLink1(int head1, int head2, int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::h2["+head2+"]/following-sibling::h3["+link+"]/a"));	
			return articleContent;
		}
		

		//Method to check the article links
		public WebElement articleULinkWithoutHead(int list, int link, int l){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/ul["+list+"]/li["+link+"]/p/a["+l+"]"));	
			return articleContent;
		}
		
		//Method to check the article links
		public WebElement articleULinkWithoutHead1(int list, int link, int l){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/ul["+list+"]/li["+link+"]/p/a["+l+"]"));	
			return articleContent;
		}
		
		//Method to check the article links
		public WebElement articleULink(int list,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::ul/li["+list+"]/p/a["+link+"]"));	
			return articleContent;
		}
				
	//Method to check the article links
		public WebElement articleULink2(int head,int list,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::ul/li["+list+"]/p/a["+link+"]"));	
			return articleContent;
		}
				
	//Method to check the article links
		public WebElement articleULink1(int head1, int head2,int list,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::ul[preceding-sibling::h2["+head2+"]]/li["+list+"]/p/a["+link+"]"));	
			return articleContent;
		}
		
	//Method to check the article sub heading <h3>
		public WebElement articleSubHead(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]//preceding-sibling::h3["+link+"]"));	
			return articleContent;
		}
						
	//Method to check the article sub heading <h3>
		public WebElement articleSubHead2(int head,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]//following-sibling::h3["+link+"]"));	
			return articleContent;
		}
						
	//Method to check the article sub heading <h3>
		public WebElement articleSubHead1(int head1, int head2,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::h3[preceding-sibling::h2["+head2+"]]["+link+"]"));	
			return articleContent;
		}
		
	//Method to check the article sub heading <h3>
		public WebElement articleSubHead3(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h3["+link+"]"));	
			return articleContent;
		}
		
	//Method to check the article sub heading <h4>
		public WebElement articleSub4Head(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]//following-sibling::h4["+link+"]"));	
			return articleContent;
		}
						
	//Method to check the article sub heading <h4>
		public WebElement articleSub4Head2(int head,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]//following-sibling::h4["+link+"]"));	
			return articleContent;
		}
						
	//Method to check the article sub heading <h4>
		public WebElement articleSub4Head1(int head1, int head2,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::h4[preceding-sibling::h2["+head2+"]]["+link+"]"));	
			return articleContent;
		}	
		
	//Method to check the article links
		public WebElement articleListWithoutHead(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/ul["+link+"]"));	
			return articleContent;
		}
		
	//Method to check the article links
		public WebElement articleList(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]//preceding-sibling::ul["+link+"]"));	
			return articleContent;
		}
								
	//Method to check the article links
		public WebElement articleList2(int head,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]//following-sibling::ul["+link+"]"));	
			return articleContent;
		}
								
	//Method to check the article links
		public WebElement articleList1(int head1, int head2,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::ul[preceding-sibling::h2["+head2+"]]["+link+"]"));	
			return articleContent;
		}
		
		//Method to check the article links
		public WebElement articleOrdList(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]//preceding-sibling::ol["+link+"]"));	
			return articleContent;
		}
								
	//Method to check the article links
		public WebElement articleOrdList2(int head,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]//following-sibling::ol["+link+"]"));	
			return articleContent;
		}
								
	//Method to check the article links
		public WebElement articleOrdList1(int head1, int head2,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::ol[preceding-sibling::h2["+head2+"]]["+link+"]"));	
			return articleContent;
		}
		
		//Method to check the article links
		public WebElement articleOrdListWithoutHead(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/ol["+link+"]"));	
			return articleContent;
		}
		
		//Method to check the article ol links
		public WebElement articleOLink(int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2[1]/preceding-sibling::ol/li["+link+"]/p/a"));	
			return articleContent;
		}
				
	//Method to check the article ol links
		public WebElement articleOLink2(int head,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head+"]/following-sibling::ol/li["+link+"]/p/a"));	
			return articleContent;
		}
				
	//Method to check the article ol links
		public WebElement articleOLink1(int head1, int head2,int link){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/preceding-sibling::ol[preceding-sibling::h2["+head2+"]]/li["+link+"]/p/a"));	
			return articleContent;
		}
		
	//Method to check the article U Links
		public WebElement articleULinks(){
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/ul"));	
			return articleContent;
		}
		
	//Method to get video inside list
		public WebElement articleListVideo(int list, int head1) {
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/h2["+head1+"]/following-sibling::ul["+list+"]/li/p/div/iframe"));	
			return articleContent;
		}
		
	//Method to get video inside list
		public WebElement articleListVideo1(int list) {
			WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,'templates_main')]/ul["+list+"]/li/p/div/iframe"));	
			return articleContent;
		}
					
	//Article body
	//Method to check the article heading in the body
		public WebElement ArticleTitle(){
			if(driver.findElements(By.xpath("//div[contains(@class,' mx-auto')]//*/h1")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,' mx-auto')]//*/h1"));	
				return articleContent;
			}
		}
		
	//Method to check the article author in the body
		public WebElement ArticleAuthor(){
			WebElement elename = driver.findElement(By.xpath("//div[contains(@class,'templates_d_grid')]/div/p/span"));
			return elename;
		}
		
	//Method to check the article created date in the body
		public WebElement ArticleCreatedDate(){
			WebElement elename = driver.findElement(By.xpath("//div[contains(@class,'templates_d_grid')]/div/p[2]"));
			return elename;
		}
		
	//Method to check the article created date in the body.
		public WebElement ArticleImage(){
			if(driver.findElements(By.xpath("//div[contains(@class,' mx-auto')]/*//div[contains(@class,'mb-3')]/img")).isEmpty()) {
				return null;
			}else {
				WebElement articleContent = driver.findElement(By.xpath("//div[contains(@class,' mx-auto')]/*//div[contains(@class,'mb-3')]/img"));	
				return articleContent;
			}
		}
		
	//Method to check the footer social image links availability
		public WebElement rightPanelSocialLink(String linkName){
			WebElement socialLinkRightPanel = driver.findElement(By.xpath("//img[contains(@src,'" + linkName + "')]"));	
			return socialLinkRightPanel;
		}

	 public static BlogPageObjects getInstance() {
		 BlogPageObjects headerfunctions = null;
			if(headerfunctions == null) {
				headerfunctions = new BlogPageObjects();
			}
			return headerfunctions;
	};
	
}
