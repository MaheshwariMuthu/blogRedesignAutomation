package automationFramework;

import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;

import static stepDefinations.Hooks.configProperties;

public class Config {
	
	public static String fol_failedScreenshotPath =System.getProperty("user.dir")+"/Failed_Screenshots";
	public static String fol_passScreenshotPath =System.getProperty("user.dir")+"/Pass_Screenshots";
	public static String fol_jasonFilePath=System.getProperty("user.dir")+"/DataFiles/schneiderData.json";

	}
