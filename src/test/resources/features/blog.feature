#Author: your.email@your.domain.com


Feature: Blogs feature file

  Background: User navigate to Homeserve Blogs
    Given User is on "Blogs" Home page

  @TC_0001 @blogGlobalValidation
  Scenario: Validate all the components in the Blog page global header and footer section
    # When User click on the Blog link
    Then User validates the links in "Header" section
    Then User validates the links in "Footer" section
    
  @TC_0002 @blogArticle
  Scenario: Validate all the details in the Blog article
    #When User click on the Blog link
    Then User read the data from "appliance-filters.txt" file
    Then User validates the article "title"
    Then User validates the article "author"
    Then User validates the article "publishDate"
    Then User validates the article "image"
    Then User validates the article content "headings"
    Then User validates the article content "paragraphs"
    Then User validates the article content "links"
    Then User validates the article content "Lists"
   
   @TC_0012 @blogArticle
   Scenario: Validate all the details in the Blog article
    #When User click on the Blog link
    Then User read the data from "appliance-filters.txt" file
    Then User validates the article content
        
   @TC_0003 @blogLightboxSignup
   Scenario: Validate whether able to signup to homeserve through lightbox
    #When User click on the Blog link
    Then User enter the "email" in "LightBox" section
    And Click on the "Signup" button in "LightBox"
    Then Validate the success message in "LightBox"
   
   @TC_0004 @blogFooterSignUp
   Scenario: Validate whether able to signup to homeserve through global footer
    #When User click on the Blog link
    Then User enter the "email" in "Footer" section
    And Click on the "Signup" button in "Footer"
    Then Validate the success message in "Footer"
    
   @TC_0005 @blogLeaveUsFeedback
   Scenario: Validate whether able to submit the feeback
    #When User click on the Blog link
    Then User enter the "comments" in "Feedback" section
    Then User enter the captcha
    And Click on the "Send feedback" button in "Feedback"
    Then Validate the success message in "Feedback"
    
   @TC_0006 @blogZipIn
   Scenario: Validate whether able to view the available plans after enter the zip code
    #When User click on the Blog link
    Then User enter the "zipcode" in "ZipIn" section
    And Click on the "Shop Now" button in "ZipIn"
    Then Validate the success message in "ZipIn"
    
   @TC_0007 @blogStickyZipIn
   Scenario: Validate whether able to view the available plans after enter the zip code in sticky Zip-in
    #When User click on the Blog link
    Then User enter the "zipcode" in "StickyZipIn" section
    And Click on the "Shop Now" button in "StickyZipIn"
    Then Validate the success message in "StickyZipIn"
    
   @TC_0008 @blogArticlePrint
   Scenario: Validate whether able to print the article content
    #When User click on the Blog link
    And Click on the "Print" button in "Article"
    Then User save the article content
    
   @TC_0009 @blogSearchArticle
   Scenario: Validate whether able to search the article
    #When User click on the Blog link
    And Click on the "Search" button in "Article"
    Then User enter the "search content" in "search" section
    Then User press "ENTER" key
    Then Validate the search result
    
   @TC_0011 @blogArticle @linkNavigation
   Scenario: Validate whether all the links navigate to the expected url
    #Then User validates the navigation of links in "Header" section
    #Then User validates the navigation of links in "HeaderTopNav" section
    Then User validates the navigation of links in "FooterSocial" section
    Then User validates the navigation of links in "FooterBottomLink" section
    Then User validates the navigation of links in "RightRail" section
    Then User validates the navigation of links in "FooterLink" section
    