package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 
 * @author Sarath Banala
 *
 */

public class Update_Config_Values {
	
	
	public static String get_Config_Val(String key)
    {
        String keyval = null;
        //Let's consider properties file is in project folder itself
        File file = new File(System.getProperty("user.dir") 
				+ "/src/test/resources/config.properties");

        //Creating properties object
        Properties prop = new Properties();
        //Creating InputStream object to read data
        FileInputStream objInput = null;
        try{
            objInput = new FileInputStream(file);
            //Reading properties key/values in file
            prop.load(objInput);
            keyval = prop.getProperty(key);
            objInput.close();
        }catch(Exception e){System.out.println(e.getMessage());}
        return keyval;
    }

	
	public static void set_config_values(String Key, String Value) { //

		{
	        File file = new File(System.getProperty("user.dir") 
					+ "/src/test/resources/config.properties");
	        Properties prop = new Properties();
	        FileInputStream objInput = null;
	        try {
	            objInput = new FileInputStream(file);
	            prop.load(objInput);
	            objInput.close();

	            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") 
						+ "/src/test/resources/config.properties");
	            prop.setProperty(Key, Value);
	            prop.store(out, null);
	            out.close();
	        } catch (Exception e) {System.out.println(e.getMessage());}
	    }
}
	
	public static void Update_Config_Prop(String ConfigType, String Configvalue) throws Exception {
		try {

			set_config_values(ConfigType, Configvalue);
			System.out.println("After updating properties file");
			System.out.println(ConfigType + " :" + get_Config_Val(ConfigType));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}