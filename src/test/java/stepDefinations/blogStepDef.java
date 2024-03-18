package stepDefinations;

import Config.update_Config_Properties;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageActions.CommonPageActions;

import static automationFramework.Waits.verifyWebElementPresent;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import BlogRedesign.blogArticleFunctions;
import BlogRedesign.blogArticleFunctions.*;
import BlogRedesign.blogFunctions;
import BlogRedesign.commonFunctions;
import BlogRedesign.csvFileReader;

import static BlogRedesign.csvFileReader.dataMapping;

public class blogStepDef {
	
	CommonPageActions commonPageActions = new CommonPageActions();
	
	@Given("User is on {string} Home page")
	public void userIsOnHomePage(String Site) throws Exception {
		commonPageActions.navigateToApplication();
	}
	
	@When("User click on the Blog link")
	public void User_click_on_the_Blog_link() throws Exception {

	}
	
	@Then("User validates the {string} link in the page")
	public void User_validates_the_link_in_the_page(String page) throws Exception {
		commonFunctions.verifyLinkPresent(page);
	}
	
	@Then("User validates the links in {string} section")
	public void User_validates_the_links_in_section(String input) throws Exception {
		blogFunctions.blogPageUIValidation(input);
	}
	
	@Then("User signup from {string}")
	public void User_signup_from(String input) throws Exception {
		//blogFunctions.signupHomeServe(input);
	}
	
	@Then("User validates the right panel of the blog page")
	public void User_validates_the_right_panel_of_the_blog_page() throws Exception {
		blogArticleFunctions.rightPanelSocialLinkCheck();
	}
	
	@And("User validates the image links in the header section")
	public void User_validates_the_image_links_in_the_header_section() throws Exception {
		
	}
	
	@And("User validate the feedback section")
	public void User_validate_the_feedback_section() throws Exception {
		blogArticleFunctions.validateFeedbackSection();
	}
	
	@Then("User validates the article {string}")
	public void User_validates_the_article_details(String key) throws Exception {
		blogArticleFunctions.verifyArticleDetails(key);
	}
	
	@Then("User validates the article content {string}")
	public void User_validates_the_article_content_details(String key) throws Exception {
		blogArticleFunctions.checkArticleContent(key);
	}
	
	@Then("User validates the article content")
	public void User_validates_the_article_content() throws Exception {
		blogArticleFunctions.checkArticleContentInOrder();
	}
	
	@Then("User validates all the article details")
	public void User_validates_all_the_article_details() throws Exception {
		blogArticleFunctions.checkAllArticleContent();
	}
	
	@Then("User read the data from {string} file")
	public void User_read_the_data_from_file(String fileName) throws Exception {
		commonFunctions.readArticleContent1(fileName);
	}

	@Then("User validates the article navigation link")
	public void User_validates_the_article_navigation_link() throws Exception {
		blogArticleFunctions.verifyArticleNavigationLink();
		
	}
	
	@Then("User validated the author and date of the article")
	public void User_validated_the_author_and_date_of_the_article() throws Exception {
		
	}
	
	@Then("User enter the {string} in {string} section")
	public void User_enter_the_email_in_section(String field, String section) {
		blogFunctions.enterText(field, section);
	}
	
	@Then("Click on the {string} button in {string}")
	public void Click_on_the_Sigup_button_in(String button, String section) throws InterruptedException {
		blogFunctions.clickButton(button, section);
	}
	
	@Then("Validate the success message in {string}")
	public void Validate_the_success_message_in(String section) throws Exception {
		blogFunctions.validateSuccessMessage(section);
	}
	
	@Then("User enter the captcha")
	public void User_enter_the_captcha() throws Exception {
		Thread.sleep(60000);
		blogArticleFunctions.clickCaptcha();
	}
	
	@Then("User validates the navigation of links in {string} section")
	public void User_validates_the_navigation_of_links_in_section (String section) throws Exception {
		blogFunctions.navigateToLinkValidation(section);
	}
	
	@Then("User save the article content")
	public void User_save_the_article_content () throws Exception {
		blogFunctions.printArticle();
	}
}
