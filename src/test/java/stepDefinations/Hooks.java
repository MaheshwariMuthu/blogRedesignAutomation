package stepDefinations;

import automationFramework.DataReader;
import automationFramework.LoggerHelper;
import automationFramework.StartDriver;
import automationFramework.Utils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import BlogRedesign.blogArticleFunctions;
import BlogRedesign.blogFunctions;
import BlogRedesign.commonFunctions;
import BlogRedesign.csvFileReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static BlogRedesign.blogArticleFunctions.logResult;


public class Hooks extends StartDriver {

    public static File extentFile = null;
    public static String strDate = null;
    public static String fileName;
    public static boolean flag = false;
    public static String start_date;
    public static Properties configProperties;
    public static String TC_ID;
    public static Scenario scenario;
    public static Logger log = Logger.getLogger(LoggerHelper.class);


    @Before
    public void beforeScenario(Scenario scenario) throws FileNotFoundException, IOException, ParseException, InterruptedException {

        configProperties = new Properties();
        configProperties.load(new FileInputStream((new File(System.getProperty("user.dir") + "/src/test/resources/testdata/Properties/config.properties"))));
        String browser = configProperties.getProperty("browser");
        DataReader.loadPropertyFile("blogProperties");
        DataReader.loadPropertyFile("articleProperties");
        Collection<String> tags = scenario.getSourceTagNames();
        TC_ID = (tags.stream().findFirst().get()).substring(1);
        if(TC_ID.equals("TC_0010") ||TC_ID.equals("TC_0015") ) {
        	DataReader.getTestData("blogMigration");
        }else if(TC_ID.equals("TC_0011")) {
        	DataReader.getTestData("linkNavigation");
    	}
        
        log.info(TC_ID + " - "+ scenario.getName()+ "\n");
        log.info("Browser Used: "+ browser + "\n");
        logResult = logResult + "Browser Used: "+ browser + "\n\n";
        
        if ((browser.equalsIgnoreCase("chrome"))) {
   	      	
            //	WebDriverManager.chromedriver().setup();
        	ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("-incognito");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("start-maximized");
            options.addArguments("--force-device-scale-factor=0.8");
//			options.setExperimentalOption("excludeSwitches",
//				    Arrays.asList("disable-popup-blocking"));
            options.addArguments("--disable-popup-blocking");
            options.setBrowserVersion("117");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setAcceptInsecureCerts(true);
            caps.acceptInsecureCerts();
            //	caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            driver = new ChromeDriver(options);
            Thread.sleep(2000);
            driver.manage().deleteAllCookies();

        } else if ((browser.equalsIgnoreCase("firefox"))) {
//			WebDriverManager.firefoxdriver().setup();
         /*  DesiredCapabilities caps = new DesiredCapabilities();
            caps.setAcceptInsecureCerts(true);
            FirefoxOptions options = new FirefoxOptions();
            driver = new FirefoxDriver(options);*/
            
        	System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + configProperties.getProperty("firefoxDriver"));
        	driver = new FirefoxDriver(); //Creating an object of FirefoxDriver
        	
        /*	FirefoxProfile prof = new FirefoxProfile();
            FirefoxOptions options = new FirefoxOptions();

            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + configProperties.getProperty("firefoxDriver"));
           
            prof.setPreference("browser.download.folderList", 2);
             prof.setPreference("browser.download.dir","C:\\Users\\train\\OneDrive\\Desktop");
           // prof.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            options.setProfile(prof);
            driver = new FirefoxDriver(options);*/
            
        	driver.manage().deleteAllCookies();
        	driver.manage().window().maximize();
        	
        }else if ((browser.equalsIgnoreCase("edge"))) {
        	System.setProperty("webdriver.edge.driver",System.getProperty("user.dir") + configProperties.getProperty("edgeDriver"));
                // Instantiate a ChromeDriver class.
                driver = new EdgeDriver();
                // Maximize the browser
                driver.manage().window().maximize();
        }
    }


    @AfterStep
    public static void AddScreenshot(Scenario scenario) throws IOException {
            byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(imageBytes, "image/png", "image");
            if(TC_ID.equals("TC_0010")) {
            	csvFileReader.createAndwriteToFile(blogArticleFunctions.output+"\n\n"+blogArticleFunctions.outputReult, "ExecutionLog");
            }
            if(TC_ID.equals("TC_0015")){
            	csvFileReader.createAndwriteToFile(blogArticleFunctions.output, "ExecutionLog");
            }
            
    }

    /**
     * Description: Taking screenshot for pass and failed scenario- -creating folder
     * for both as well pass and failed scenario
     *
     * @param scenario
     * @throws IOException
     * @author aatish.slathia
     */
    @After
    public static void saveScreenShotForFailedAndPassScenario(Scenario scenario) throws IOException, InterruptedException {
        String ImagePath;
        //Thread.sleep(10000);
  
        if (scenario.isFailed()) {
            try {

                ImagePath = "./Failed_Screenshots/" + Utils.getStringWithTimeStamp(scenario.getName()) + ".png";
                File screenshot_with_scenario_name = ScreenshotWithURL();
                byte[] fileContent = FileUtils.readFileToByteArray(screenshot_with_scenario_name);
                scenario.attach(fileContent, "image/png", "image");
                FileUtils.copyFile(screenshot_with_scenario_name, new File(ImagePath));
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        } else {
            try {

				ImagePath = "./Pass_Screenshots/" + Utils.getStringWithTimeStamp(scenario.getName()) + ".png";
                File screenshot_with_scenario_name  = ScreenshotWithURL();
                byte[] fileContent = FileUtils.readFileToByteArray(screenshot_with_scenario_name);
                scenario.attach(fileContent, "image/png", "image");
				FileUtils.copyFile(screenshot_with_scenario_name, new File(ImagePath));
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
                log.info("---------------------------------------------------------------------------------------------------------------------------");
        //commonFunctions.writeCOnsoleOutput();
        driver.manage().deleteAllCookies();
        driver.close();
        //driver.quit();
    }

    public static void takeScreenshot(String featurename) {

        try {
            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            File screenshot_with_scenario_name = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot_with_scenario_name,
                    new File("./Failed_Screenshots/" + Utils.getStringWithTimeStamp(featurename) + ".png"));
        } catch (WebDriverException | IOException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }
    
    public static File ScreenshotWithURL() {

        // Take screenshot
        BufferedImage screenshot = null;
        try {
            screenshot = ImageIO.read(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));

            // Create a new image with space for URL at the bottom
            BufferedImage finalImage = new BufferedImage(screenshot.getWidth(), screenshot.getHeight() + 50, BufferedImage.TYPE_INT_ARGB);

            Graphics g = finalImage.getGraphics();
            g.drawImage(screenshot, 0, 0, null);
            g.setColor(Color.RED);
            g.setFont(new Font("SansSerif", Font.BOLD, 20));

            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeStr = now.format(formatter);

            g.drawString(driver.getCurrentUrl(), 10, 35); // Add URL at the top

            // Draw date and time at the bottom right
            g.drawString(dateTimeStr, screenshot.getWidth() - 220, screenshot.getHeight() - 10); // Adjust position and width as per font size

            // Saving to a temp file or specific location
            File output = new File("screenshotWithUrl.png");
            ImageIO.write(finalImage, "png", output);

            System.out.println("After test - ScreenshotWithURL");

            return output;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
