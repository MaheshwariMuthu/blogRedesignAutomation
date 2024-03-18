package Config;


import org.testng.annotations.Test;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;


/**
 * 
 * @author Sarath Banala
 *
 */
public class update_Config_Properties {
	
	@Test
	public static void Update_Config_Prop(String Value) throws Exception {
		try {
			Update_Config_Values.set_config_values("server.site", Value );
	        System.out.println("After updating properties file");
	        System.out.println("server.site: " + Update_Config_Values.get_Config_Val("server.site"));

		        
	}	 catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



}