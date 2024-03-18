package automationFramework;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import BlogRedesign.blogFunctions;
import BlogRedesign.csvFileReader;
import stepDefinations.Hooks;
import static stepDefinations.Hooks.configProperties;
import static BlogRedesign.blogArticleFunctions.logResult;

public class DataReader {
	
	public static Properties blogProperties;
	public static Properties articleProperties;
	public static Logger log = Logger.getLogger(LoggerHelper.class);

	public static String geturl() throws IOException {
		String url=null;
		String env=configProperties.getProperty("server.env");
		String Site=configProperties.getProperty("server.site");
		if(Site!=null){
			switch (Site){
				case "Blogs":
					url = configProperties.getProperty("url");
					break;
				default:
					log.error("Invalid url entered");
					Assert.fail("Invalid url entered :: "+Site);
			}
		}
		log.info("Article Page URL: " + url + "\n");
		logResult = logResult + "Article Page URL: " + url + "\n\n";
		return url;
	}
	
	public static void getTestData(String fileName) throws IOException {
		
		try {
			csvFileReader.readCSV(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadPropertyFile(String fileName) throws FileNotFoundException, IOException {
		if(fileName.contains("blog")) {
			blogProperties = new Properties();
			blogProperties.load(new FileInputStream((new File(System.getProperty("user.dir") + configProperties.getProperty(fileName)))));
		}else if(fileName.contains("article")) {
			articleProperties = new Properties();
			articleProperties.load(new FileInputStream((new File(System.getProperty("user.dir") + configProperties.getProperty(fileName)))));
		}
	}
	
	 public static DataReader getInstance() {
		 DataReader dataReader = null;
			if(dataReader == null) {
				dataReader = new DataReader();
			}
			return dataReader;
	};
	
}
