package automationFramework;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;

import stepDefinations.Hooks;

public class LoggerHelper {

	private static boolean root = false;
    static Logger log = Logger.getLogger(LoggerHelper.class);

	public static Logger getLogger(Class cls) {
		if (root) {
			return Logger.getLogger(cls);
		}
		PropertyConfigurator.configure("log4j.properties");
		root = true;
		return Logger.getLogger(cls);
	}
	

	    public static void main(String[] args) {
	        SimpleLayout layout = new SimpleLayout();
	        FileAppender appender = null;
	        try {
	            appender = new FileAppender(layout, System.getProperty("user.dir")+"/logs/"+Hooks.TC_ID+".logs",false);
	            log.addAppender(appender);
	            log.fatal("Fatal Message!");
	        } catch (Exception e) {
	            System.out.println("Exception: " + e);
	        } finally {
	            appender.close();
	        }
	    }
	
	
}
