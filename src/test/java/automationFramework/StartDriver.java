package automationFramework;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class StartDriver {
	public static WebDriver driver;

	/*
	 * Description: Initialize Driver
	 */
	public static void initializeWeb_Driver()
			throws IOException, ParseException, org.json.simple.parser.ParseException, InterruptedException {
//		String browser = DataReader.getParameterString("browser", "environment");
//		if ((browser.equalsIgnoreCase("chrome"))) {
//			WebDriverManager.chromedriver().setup();
//			
//			
//			ChromeOptions options = new ChromeOptions();
//			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
//			chromeOptionsMap.put("plugins.plugins_disabled", new String[] {
//			        "Chrome PDF Viewer"
//			});
//			chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
//			options.setExperimentalOption("prefs", chromeOptionsMap);
//
//
//			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
////			options.addArguments("-incognito");
//			options.addArguments("--remote-allow-origins=*");
//			options.addArguments("start-maximized");
//			options.setExperimentalOption("excludeSwitches",
//				    Arrays.asList("disable-popup-blocking"));
////			options.setExperimentalOption("prefs", chromePrefs);
//			
//			DesiredCapabilities caps = new DesiredCapabilities();
//			caps.setAcceptInsecureCerts(true);
//			driver = new ChromeDriver(options);
//			
//		}
//
//		else if ((browser.equalsIgnoreCase("firefox"))) {
//			WebDriverManager.firefoxdriver().setup();
//			DesiredCapabilities caps = new DesiredCapabilities();
//			caps.setAcceptInsecureCerts(true);
//			FirefoxOptions options = new FirefoxOptions();
//			options.addArguments("--start-maximized");
//			driver = new FirefoxDriver(options);
//		}

	}


	public static void closeDriver() {

		//driver.quit();
	}
}