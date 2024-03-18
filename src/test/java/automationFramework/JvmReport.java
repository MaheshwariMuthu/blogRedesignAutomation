package automationFramework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;




public class JvmReport
{
	public static File loc;
public static void generateJVMReport(String JsonFile)
{
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HH:mm:ss");
	String strDate =null;
	Date curDate = new Date();
	strDate = sdf.format(curDate);
	strDate=strDate.replaceAll(":", ".");
	loc=new File(System.getProperty("user.dir") + "\\target\\JVM\\"+ strDate+"_"+"HomserveDigital"+"_");
	//File loc=new File(System.getProperty("user.dir") + "\\target\\JVM\\"+"HomserveDigital"+"");
	//String a=browserName;
	Configuration con=new Configuration(loc,"HomeServe Project");
	//con.addClassifications("Browser Name",a);
//	con.addClassifications("Sprint", "1");
//	con.addClassifications("OS", System.getProperty("os.name"));
	List<String> JsonFiles=new ArrayList<String>();
	JsonFiles.add(JsonFile);
	ReportBuilder builder=new ReportBuilder(JsonFiles,con);
	builder.generateReports();
	
	/*if((loc.exists()))
	{
		File[] f1=loc.listFiles();
		for(int i=0;i<f1.length;i++)
		{
		if(f1[i].toString().contains("cucumber-html-reports"))
		{
			File m1=new File(f1[i].toString());
			File[] f2=m1.listFiles();
			for(int j=0;i<f2.length;j++)
			{
			if(f2[j].toString().contains("overview-features"))
			{
			f2[j].renameTo(new File(System.getProperty("user.dir") +"\\target\\JVM\\"+RunCuke.strDate+"_"+Base.featureName+"_"+Base.get_testData(Base.browser_data,"Browser Name")+"\\cucumber-html-reports\\"+Base.featureName+".html"));
			break;
			}
			}
		}
		}*/
	}
		
}
