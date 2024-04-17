package BlogRedesign;

import static automationFramework.StartDriver.driver;
import static automationFramework.Waits.verifyWebElementPresent;
import static stepDefinations.Hooks.configProperties;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import automationFramework.PageActions;
import pageLocators.BlogPageObjects;



public class commonFunctions {
	
	static PageActions pageActions = new PageActions();
	static BlogPageObjects blogPageObjects = new BlogPageObjects();
	static HashMap<String,String> articleContent = new HashMap<String,String>();
	static HashMap<String,String> articleHeading = new HashMap<String,String>();
	static HashMap<String,String> articleParagraph = new HashMap<String,String>();
	static HashMap<String,String> articleList = new HashMap<String,String>();
	static HashMap<String,String> articleLink = new HashMap<String,String>();
	public static Logger log = Logger.getLogger(blogArticleFunctions.class);
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 20-12-2023
	 * Method Name	: validateURL(String url)
	 * Description	: To validate the current driver URL displayed as expected
	 ---------------------------------------------------------------------------------------------------------*/
		public static void validateURL(String url) {
			
			String currURL = driver.getCurrentUrl();
			if(currURL.equals(url)) {
				System.out.println("'" + url + "' navigated is correct");
			}else {
				System.err.println("'" + url + "' navigated is not correct");
				Assert.fail("'" + url + "' navigated is not correct");
			}
			
		}
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 20-12-2023
	 * Method Name	: verifyLinkPresent(String linkName )
	 * Description	: To validate whether a particular link is present in the webpage
	 ---------------------------------------------------------------------------------------------------------*/
		public static void verifyLinkPresent(String linkName ) {
			try {
				WebElement element = null;
				switch(linkName) {
						case "Home":
							element = blogPageObjects.homeLink;
							break;
						default:
							System.out.println("Case is not found in repository for link '" + linkName + "'");
							Assert.fail("Case is not found in repository for link '" + linkName + "'");
							break;
					}
					if(verifyWebElementPresent(element)) {
						pageActions.setHighlight(element);
						System.out.println("'" + linkName + "' link is present in the Blog Page");
					}else {
						System.out.println("'" + linkName + "' link is not present in the Blog Page");
						Assert.fail("'" + linkName + "' link is not present in the Blog Page");
					}
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: boldString
	* Description	: To display the string in BOLD in log
	---------------------------------------------------------------------------------------------------------*/
		public static String boldString(String text) {
			 Font myFont = new Font(text, Font.BOLD,40);
			 return (char)27 + "[1m"+text+ (char)27 + "[0m";
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: readTextFile
	* Description	: To Parse the txt file and read by line and store it in variable for further processing
	---------------------------------------------------------------------------------------------------------*/
		public static String readTextFile(String fileName) throws IOException {
			// Open the file
			System.out.println(System.getProperty("user.dir")+configProperties.getProperty("articleFile")+fileName);
			FileInputStream fstream = new FileInputStream(System.getProperty("user.dir")+configProperties.getProperty("articleFile")+fileName);
			// Get the object of Data
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			String strLine, content = null;
			String beforeLine = "";
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {  // Print the content on the console
			  //System.out.println (strLine);
				if(strLine.contains("----")) {
					if(!beforeLine.contains("<img"))
						content = content + "<heading>\n";
				}else if(strLine.contains("<br>")) {
					String[] temp = strLine.split("<br>");
					strLine = temp[0] + "\n\n" + temp[1];
					content = content + strLine.trim()+"\n";
				}else if(strLine.contains("#### ")) {
					strLine = strLine.replaceAll("\\#### ", "<subHead4>");
					content = content + strLine.trim()+"\n";
				}else if(strLine.contains("### ")) {
					strLine = strLine.replaceAll("\\### ", "<subHead>");
					content = content + strLine.trim()+"\n";
				}else if(strLine.contains("## ")) {
					strLine = strLine.replaceAll("\\## ", "");
					content = content + strLine.trim()+"\n"+"<heading>"+"\n";
				}else if(strLine.contains("\\[")){
					strLine = strLine.replaceAll("\\[", "<link>");
					strLine = strLine.replaceAll("]", "<link>");
					content = content + strLine.trim()+"\n";
				}else if(strLine.contains("<!-- -->")){
					content = content;
				}else {
					content = content + strLine.trim()+"\n";
				}
				beforeLine = strLine;
			}
			//Close the input stream
			in.close();
			
			return content;
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: readArticleContent
	* Description	: Old - kept it just for reference
	---------------------------------------------------------------------------------------------------------*/
		public static void readArticleContent(String fileName) throws IOException, ParseException {
			String content = readTextFile(fileName);
			String[] dummy = content.split("---");
			String[] temp = dummy[1].split("\n");
			String[] data = null;
			String title = "", value = "";
			//FOr Reading basic details and store it in hashmap
			for(int i=1; i<temp.length; i++) {
				data = temp[i].split(":");
				title = data[0];
				System.out.println("title:"+title);
				if(!title.equals("eleventyNavigation")) {
					value = data[1].replaceAll("\"", "");
					if(title.equals("headline")) {
						value = value.replaceAll("\u00A0", " ");
						System.out.println("Headline:"+value);}
					if(data.length>1) {
						for(int j=2;j<data.length;j++) {
							value = value+":"+data[j].replaceAll("\"", "");
						}
					}
					articleContent.put(title, value.trim());
				}
			}
			//For reading Headings<h2> and store it in HashMap
			temp = dummy[2].split("<heading>");
			articleContent.put("HeadingCount", Integer.toString(temp.length-1));
			for(int i=0; i<temp.length-1; i++) {
				data = temp[i].split("\n");
				value = data[data.length-1];
				title="Heading"+(i+1);
				articleHeading.put(title, value.trim());
			}
			
			//For reading paragraphs and store it in HashMap
			temp = dummy[2].split("<heading>");
			int len = 0;
			int paraCount = 0;
			String spl = "* ";
			String spl1 = "**";
			//Outer Loop for getting headings
			for(int i=0; i<temp.length; i++) {
				data = temp[i].split("\n\n");
				if(i==0 || i==temp.length-1) {
					len = data.length;
				}else {
					len = data.length-1;
				}
				paraCount = paraCount+1;
				int pCount = 0, lCount = 0;
				int key = 0, list=0, subHead = 0;
				String temp_title = "",temp1 = "";
				int tem =0;
				//Inner Loop for getting paragraphs under each headings
				for(int j=1; j<len; j++) {
					if(i==0) {
						value = data[j-1]; // modified data[j-1]
					}else {	
						value = data[j];
					}
						int linkCount = 0;
							if(((articleContent.get(title)==null) && (articleHeading.get(title)==null)) || title.contains("List"+(i+1)) || title.contains("Sub_Heading")) {
								title = temp_title;
							}else {
								key = key+1;
								title="Paragraph"+(i+1)+"_"+(key);
								temp_title = title;
							}
						value = value.replaceAll("\u00A0", " ");
						//For storing Sub Headings <h3>
						if(value.contains("<subHead>")) {
							subHead = subHead+1;
							title = "Sub_Heading"+(i+1)+"_"+(subHead);
							value = value.replaceAll("<subHead>", "");
						}
						//For removing Bold string value
						if(value.contains(spl1)) {
							tem=value.indexOf("*");
							char tep = value.charAt(tem+1);
							if(tep=='*') {
								value = value.replace((value.substring(tem, tem+1)),"");
							}	
						}
						//For storing list values <ul><li>
						if(value.contains(spl)) {
							list = list+1;
							title = "List"+(i+1)+"_"+(list);
							value = value.replaceAll("\\*", "");
							String[] listTemp = value.split("\n");
							String listdummy = "";
							for(int a=0;a<listTemp.length;a++) {
								listdummy = listdummy+listTemp[a].trim()+"\n";
							}
							value = listdummy;
						}
						//For storing links <a href>
						if(value.contains("https:")) {
								String[] dump = value.split("\\[");
								for(int k=1;k<dump.length;k++) {
									String[] dump1 = dump[k].split("\\]");
									String linkTitle = "", linkVal = "";
									linkCount = linkCount +1;
									linkTitle = dump1[0];
									String[] dump2 = dump1[1].split("\\)");
									linkVal = (dump2[0].trim()).replaceAll("\\(", "");
									articleLink.put(title+"_link"+linkCount, linkTitle+"|"+linkVal);
									value = value.replaceAll("\\[", "");
									value = value.replaceAll("\\]", "");
									value = value.replaceAll("\\("+linkVal+"\\)", "");
								}
						}
						if(value.contains("_Manufacturer image_")) {
							value = value.replaceAll("_", "");
						}
						if(value.contains("_")) {
							value = value.replaceAll("_", "");
						}
						if((!(value.contains("<img"))) && (!(value==null)) && (!(value.equals("")))) {
							if(title.contains("Sub_Heading")) {
								articleHeading.put(title, value.trim());
							}else {
								articleContent.put(title, value.trim());
							}
							if(title.contains("Paragraph")) {
								pCount = pCount+1;
							}else if(title.contains("List")) {
								lCount = lCount+1;
							}
						articleContent.put(title+"_linkCount", Integer.toString(linkCount));
						articleContent.put("ParagraphCount"+(i+1), Integer.toString(pCount));
						articleContent.put("ListCount"+(i+1), Integer.toString(lCount));
						articleContent.put("SubHeading"+(i+1), Integer.toString(subHead));
						}
					}
				articleContent.put("ParagraphCount", Integer.toString(paraCount));
			}
			
			//For reading all the links and store it in HashMap
			
			
			
			for (Entry<String, String> entry : articleContent.entrySet()) {
			    String key = entry.getKey();
			    String value1 = entry.getValue();
			    System.out.println(key + ":" + value1);
			}
			System.out.println("Links:");
			for (Entry<String, String> entry : articleLink.entrySet()) {
			    String key = entry.getKey();
			    String value1 = entry.getValue();
			    System.out.println(key + ":" + value1);
			}
			
		/*	System.out.println("Headings:");
			for (Entry<String, String> entry : articleHeading.entrySet()) {
			    String key = entry.getKey();
			    String value1 = entry.getValue();
			    System.out.println(key + ":" + value1);
			} */
		}
		
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: writeCOnsoleOutput
	* Description	: To write the console output in logs
	---------------------------------------------------------------------------------------------------------*/
		public static void writeCOnsoleOutput() throws IOException {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
		    BufferedWriter out = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/src/test/resources/testdata/output/output.txt"));
		    try {
		        String inputLine = null;
		        do {
		            inputLine=in.readLine();
		            out.write(inputLine);
		            out.newLine();
		            System.out.print("Line1:"+inputLine);
		        } while (!inputLine.equalsIgnoreCase("eof"));
		        System.out.print("Write Successful");
		    } catch(IOException e1) {
		        System.out.println("Error during reading/writing");
		    } finally {
		        out.close();
		        in.close();
		    }
		}
		
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: validateURL(String url)
	* Description	: To read the meta description from md and store in hashmap for validation
	---------------------------------------------------------------------------------------------------------*/
		public static void readMetaContent(String fileName) throws IOException, ParseException {
			String content = readTextFile(fileName);
			//output = output+"\n|"+fileName.replaceAll("\\.txt", "")+"\t";
			articleContent.put("FileName",fileName.replaceAll("\\.txt", ""));
			String[] dummy = content.split("---");
			String[] temp = dummy[1].split("\n");
			String[] data = null;
			String title = "", value = "";
			//FOr Reading basic details and store it in hashmap
			for(int i=1; i<temp.length; i++) {
				data = temp[i].split(":");
				title = data[0];
				if(data.length>1) {
					if(!title.equals("eleventyNavigation")) {
						value = data[1].replaceAll("\"", "");
						if(data.length>1) {
							for(int j=2;j<data.length;j++) {
								value = value+":"+data[j].replaceAll("\"", "");
							}
						}
						if(title.equals("headline")) {
							value = value.replaceAll("﻿﻿﻿\u200B", "");
							value = value.replaceAll("\u00A0", " ");						
						}
						articleContent.put(title, value.trim());
					}
				}
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: readArticleContent1
	* Description	: To retreive and store the md file data in hash map with content seperated.
	---------------------------------------------------------------------------------------------------------*/
		public static void readArticleContent1(String fileName) throws IOException, ParseException {
			String content = readTextFile(fileName);
			//output = output+"\n|"+fileName.replaceAll("\\.txt", "")+"\t";
			articleContent.put("FileName",fileName.replaceAll("\\.txt", ""));
			String[] dummy = content.split("---");
			String[] temp = dummy[1].split("\n");
			String[] data = null;
			String title = "", value = "";
			String spl3 = "\\";
			String spl1 = "**";
			String spl5 = "*";
			int tempNum = 0;
			//FOr Reading basic details and store it in hashmap
			for(int i=1; i<temp.length; i++) {
				data = temp[i].split(":");
				title = data[0];
				if(data.length>1) {
					if(!title.equals("eleventyNavigation")) {
						value = data[1].replaceAll("\"", "");
						if(data.length>1) {
							for(int j=2;j<data.length;j++) {
								value = value+":"+data[j].replaceAll("\"", "");
							}
						}
						if(title.equals("headline")) {
							value = value.replaceAll("﻿﻿﻿\u200B", "");
							value = value.replaceAll("\u00A0", " ");						
						}
						articleContent.put(title, value.trim());
					}
				}
			}
			//For reading Headings<h2> and store it in HashMap
			temp = dummy[2].split("<heading>");
			articleContent.put("HeadingCount", Integer.toString(temp.length-1));
			if(!(temp.length==0)) {
				for(int i=0; i<temp.length-1; i++) {
					data = temp[i].split("\n");
					value = data[data.length-1];
					title="Heading"+(i+1);
					value = value.replaceAll("\u00A0", " ");
					if(value.contains("_")) {
						value = value.replaceAll("_", "");
					}
					if(value.contains(spl3))
							value = value.replace("\\.", ".");
					if(value.contains(spl1)) {
						tempNum=value.indexOf("*");
						char tep = value.charAt(tempNum+1);
						if(tep=='*') {
							value = value.replace((value.substring(tempNum, tempNum+1)),"");
						}	
					}
					articleHeading.put(title, value.trim());
				}
			}
			
			//For reading paragraphs and store it in HashMap
			temp = dummy[2].split("<heading>");
			int len = 0;
			int paraCount = 0;
			String spl = "* ";
			String spl2 = "- ";
			//Outer Loop for getting headings
			for(int i=0; i<temp.length; i++) {
				data = temp[i].split("\n\n");
				if(temp.length==1) {
					len = data.length+1;
				}else if(i==0 || i==temp.length-1) {
					len = data.length;
				}else {
					len = data.length-1;
				}
				paraCount = paraCount+1;
				int pCount = 0, lCount = 0, olCount = 0;
				int key = 0, list=0, subHead = 0, img = 0, video=0, subHead4=0;
				String temp_title = "",temp1 = "";
				int tem =0;
				int temp11 = 1;
				boolean flag = false;
	
				//Inner Loop for getting paragraphs under each headings
				for(int j=1; j<len; j++) {
					boolean flagVideo = false;
						if(i==0) {
							String tempValue = null;
							value = data[j-1]; // modified data[j-1]
							if(value.equals("")) {
								len = len-1;
								paraCount = paraCount-1;
								tempValue = value;
								value = data[temp11];
								value = value.replaceAll("\\n", "");
								flag = true;
							}
							if((flag == true) && (tempValue == null)) {
								temp11 = temp11+1;
								value = data[temp11];
							}
						}else {	
							value = data[j];
						}
						int linkCount = 0;
						key = key+1;
						title="Paragraph"+(i+1)+"_"+j;
						temp_title = title;
							
						value = value.replaceAll("\u00A0", " ");
						value = value.replaceAll("\u200B", "");
						
						//value = value.replaceAll("\\<br\\>.*frame\\>", "");
						
						//For removing \ link 1\.
						if(value.contains(spl3))
							value = value.replace("\\", "");
						
						//For replacing Hyphen
						if(value.contains("Hyphen"))
							value = value.replaceAll("<Hyphen>", "-------------------------------------------");
						
						//For removing Bold string value
						if(value.contains(spl1)) {
							tem=value.indexOf("*");
							char tep = value.charAt(tem+1);
							if(tep=='*') {
								value = value.replace((value.substring(tem, tem+1)),"");
							}	
						}
						
						//For storing list values <ul><li>
						if(value.length()>5) {
						if(value.substring(0,6).contains(spl)) {
							list = list+1;
							title = "List"+(i+1)+"_"+j;
							value = value.replaceAll("\\*", "");
							String[] listTemp = value.split("\n");
							String listdummy = "";
							for(int a=0;a<listTemp.length;a++) {
								listdummy = listdummy+listTemp[a].trim()+"\n";
							}
							value = listdummy;
						}}
						//For storing list values <ol>
						if(value.length()>4) {
							if(value.substring(0,4).contains(".  ")) {
								list = list+1;
								title = "OrdList"+(i+1)+"_"+j;
								int tempL = 0;
								String[] listTemp = value.split("\n");
								String listdummy = "";
								for(int a=0;a<listTemp.length;a++) {
									if(listTemp[a].substring(0,4).contains(".  ")) {
										tempL = listTemp[a].indexOf(".  ");
										System.out.println("Inside if");
								}else {
										System.out.println("Inside else");
										tempL = listTemp[a].indexOf(". ");
										listTemp[a] = listTemp[a].replaceFirst("\\. ", "");
									}
									System.out.println("VAlue:"+listTemp[a]);
									listTemp[a] = listTemp[a].replaceAll("\\.  ", "");
									listdummy = listdummy+(listTemp[a].trim().substring(tempL, listTemp[a].trim().length())).trim()+"\n";
								}
								value = listdummy;
							}
						}
						//For storing list values <ul><li>
						if(!(value.contains("<subHead>"))) {
							if(value.substring(0,2).equals(spl2)) {
								list = list+1;
								title = "List"+(i+1)+"_"+j;
								value = value.replaceAll("\\- ", "");
								String[] listTemp = value.split("\n");
								String listdummy = "";
								for(int a=0;a<listTemp.length;a++) {
									listdummy = listdummy+listTemp[a].trim()+"\n";
								}
								value = listdummy;
							}
						}
						
						//For removing Bold string value
						if(value.contains(spl5)) {
							value = value.replaceAll("\\*","");
						}
						
						//For storing Sub Headings <h3>
						if(value.contains("<subHead>")) {
							subHead = subHead+1;
							title = "Sub_Heading"+(i+1)+"_"+j;
							value = value.replaceAll("<subHead>", "");
						}
						
						//For storing Sub Headings <h4>
						if(value.contains("<subHead4>")) {
							subHead4 = subHead4+1;
							title = "Sub4_Heading"+(i+1)+"_"+j;
							value = value.replaceAll("<subHead4>", "");
						}
						
						//For storing image <img>
						if(value.contains("<img")) {
							img = img+1;
							title = "Image"+(i+1)+"_"+j;
							//value = value.replaceAll("<subHead>", "");
						}
						
						//For storing Video <iframe>
						if(value.contains("<iframe")) {
							video = video+1;
							title = "Video"+(i+1)+"_"+j;
							//value = value.replaceAll("<subHead>", "");
						}
						
						if(value.contains("&#39;")) {
							value = value.replaceAll("&#39;", "'");
						}
						
						if(value.contains("&quot;")) {
							value = value.replaceAll("&quot;", "\"");
						}
						
						if(value.contains("&amp;")) {
							value = value.replaceAll("&amp;", "&");
						}
						
						//For storing links <a href>
						if((value.contains("http"))||(value.contains("mailto"))||(value.contains("/en-us/"))) {
							String[] splitList = null;
							String[] dump = null;
							if(title.contains("List"))
							{
								splitList = value.split("\\n");
								articleContent.put(title+"_ListlinkCount", Integer.toString(splitList.length+1));
								for(int x=1; x<=splitList.length; x++) {
									int listLinkCount = 0;
									dump = splitList[x-1].split("\\[");
									for(int k=1;k<dump.length;k++) {
										String[] dump1 = dump[k].split("\\]");
										String linkTitle = "", linkVal = "";
										String tempLink = "";
										linkCount = linkCount +1;
										listLinkCount = listLinkCount+1;
										linkTitle = dump1[0];
										
										String[] dump2 = dump1[1].split("\\)");
										linkVal = (dump2[0].trim()).replaceAll("\\(", "");
										articleLink.put(title+"_list"+x+"_link_"+k, linkTitle+"|"+linkVal);
										String sp_char = "?";
										String sp_char1 = "=";
										String sp_char2 = "+";
										String sp_char3 = "~";
										String sp_char4 = "#";
										String sp_char5 = "$";
										System.out.println("linkVal:"+linkVal);
										if(linkVal.contains(sp_char) || linkVal.contains(sp_char2) || linkVal.contains(sp_char3) || linkVal.contains(sp_char4) || linkVal.contains(sp_char5)) {
											value = value.replaceAll("\\?", "");
											linkVal = linkVal.replaceAll("\\?", "");
											value = value.replaceAll("\\=", "");
											linkVal = linkVal.replaceAll("\\=", "");
											value = value.replaceAll("\\+", "");
											linkVal = linkVal.replaceAll("\\+", "");
											value = value.replaceAll("\\~", "");
											linkVal = linkVal.replaceAll("\\~", "");
											value = value.replaceAll("\\#", "");
											linkVal = linkVal.replaceAll("\\#", "");
											/*StringBuilder builder = new StringBuilder(value);
											builder.delete((value.indexOf(linkVal)), value.indexOf(linkVal)+linkVal.length());
											value = builder.toString();*/
											value = value.replaceAll("\\[", "");
											value = value.replaceAll("\\]", "");
											value = value.replaceAll("\\("+".*"+linkVal+"\\)", "");
											
										}else {
											value = value.replaceFirst(linkVal, linkVal+"1");
											linkVal= linkVal+"1";
											//System.out.println("linkVal:\\(http.*"+linkVal.substring((linkVal.length()-3), linkVal.length())+"\\)");
											value = value.replaceAll("\\[", "");
											value = value.replaceAll("\\]", "");
											value = value.replaceAll("\\(http.*"+linkVal.substring((linkVal.length()-3), linkVal.length())+"\\)", "");
											value = value.replaceAll("\\("+linkVal+"\\)", "");
										}
									}
									articleContent.put(title+"_linkCount"+x, Integer.toString(listLinkCount));
								}
							}else {
							
								dump = value.split("\\[");
								for(int k=1;k<dump.length;k++) {
									String[] dump1 = dump[k].split("\\]");
									String linkTitle = "", linkVal = "";
									String tempLink = "";
									linkCount = linkCount +1;
									linkTitle = dump1[0];
									
									String[] dump2 = dump1[1].split("\\)");
									linkVal = (dump2[0].trim()).replaceAll("\\(", "");
									articleLink.put(title+"_link"+linkCount, linkTitle+"|"+linkVal);
									String sp_char = "?";
									String sp_char1 = "=";
									String sp_char2 = "+";
									String sp_char3 = "~";
									String sp_char4 = "#";
									String sp_char5 = "$";
									if(linkVal.contains(sp_char) || linkVal.contains(sp_char2) || linkVal.contains(sp_char3) || linkVal.contains(sp_char4) || linkVal.contains(sp_char5)) {
										value = value.replaceAll("\\?", "");
										linkVal = linkVal.replaceAll("\\?", "");
										value = value.replaceAll("\\=", "");
										linkVal = linkVal.replaceAll("\\=", "");
										value = value.replaceAll("\\+", "");
										linkVal = linkVal.replaceAll("\\+", "");
										value = value.replaceAll("\\~", "");
										linkVal = linkVal.replaceAll("\\~", "");
										value = value.replaceAll("\\#", "");
										linkVal = linkVal.replaceAll("\\#", "");
										/*StringBuilder builder = new StringBuilder(value);
										builder.delete((value.indexOf(linkVal)), value.indexOf(linkVal)+linkVal.length());
										value = builder.toString();*/
										value = value.replaceAll("\\[", "");
										value = value.replaceAll("\\]", "");
										value = value.replaceAll("\\("+linkVal+"\\)", "");
										
									}else {
										value = value.replaceFirst("\\("+linkVal, "\\("+linkVal+"1");
										linkVal= linkVal+"1";
										value = value.replaceAll("\\[", "");
										value = value.replaceAll("\\]", "");
										value = value.replaceAll("\\(http.*"+linkVal.substring((linkVal.length()-3), linkVal.length())+"\\)", "");
										value = value.replaceAll("\\("+linkVal+"\\)", "");
									}
								}
								}
						}else if(value.contains("_Manufacturer image_")) {
							value = value.replaceAll("_", "");
						}
							if(value.contains("_")) {
								value = value.replaceAll("_", "");
							}
							
						if((!(value==null)) && (!(value.equals("")))) {
								if(title.contains("Sub_Heading") || title.contains("Sub4_Heading")) {
									articleHeading.put(title, value.trim());
								}else {
									articleContent.put(title, value.trim());
								}
								
								if(title.contains("Paragraph")) {
									pCount = pCount+1;
								}else if(title.contains("OrdList")) {
									olCount = olCount+1;
								}else if(title.contains("List")) {
									lCount = lCount+1;
								}else if(title.contains("Image")) {
									pCount = pCount+1;
								}else if(title.contains("Video")) {
									pCount = pCount+1;
								}
						articleContent.put(title+"_linkCount", Integer.toString(linkCount));
						articleContent.put("ParagraphCount"+(i+1), Integer.toString(pCount));
						articleContent.put("ListCount"+(i+1), Integer.toString(lCount));
						articleContent.put("OrdListCount"+(i+1), Integer.toString(olCount));
						articleContent.put("SubHeading"+(i+1), Integer.toString(subHead));
						articleContent.put("Image"+(i+1), Integer.toString(img));
						articleContent.put("Video"+(i+1), Integer.toString(video));
						articleContent.put("Sub4Heading"+(i+1), Integer.toString(subHead4));
						}
					}
				articleContent.put("ParagraphCount", Integer.toString(temp.length));
			}
			
			/*for (Entry<String, String> entry : articleContent.entrySet()) {
			    String key = entry.getKey();
			    String value1 = entry.getValue();
			    System.out.println(key + ":" + value1);
			}
			
			for (Entry<String, String> entry : articleLink.entrySet()) {
			    String key = entry.getKey();
			    String value1 = entry.getValue();
			    System.out.println(key + ":" + value1);
			}*/
			
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: clearHashMap
	* Description	: To clear all the hashMap for article to validate in loop
	---------------------------------------------------------------------------------------------------------*/
		public static void clearHashMap() {
			articleContent.clear();
			articleLink.clear();
			articleHeading.clear();
			articleList.clear();
			articleParagraph.clear();
		}	
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 20-12-2023
	* Method Name	: commonFunctions
	* Description	: Instance object method creation
	---------------------------------------------------------------------------------------------------------*/
		 public static commonFunctions getInstance() {
			 commonFunctions commfun = null;
				if(commfun == null) {
					commfun = new commonFunctions();
				}
				return commfun;
		};

}
