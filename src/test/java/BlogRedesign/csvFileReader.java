package BlogRedesign;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static stepDefinations.Hooks.configProperties;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;

import pageLocators.BlogPageObjects;


public class csvFileReader {
	
	public static HashMap<String,String> dataMapping = new HashMap<String,String>();
	public static HashMap<String,Integer> headerMapping = new HashMap<String,Integer>();
	
	/*------------------------------------------------------------------------------------------------------
	 * Author		: Maheswari Muthu
	 * Date			: 07-Feb-2023
	 * Method Name	: readCSV(String TestID)
	 * Description	: To retrieve data from the CSV data file based on the test case ID and store it in HashMap(dataMapping)
	 ---------------------------------------------------------------------------------------------------------*/
		public static void readCSV(String fileName) throws IOException {
	        
			//Create the CSVFormat object
	       CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
	
	       //initialize the CSVParser object
	       String path  = System.getProperty("user.dir")+configProperties.getProperty("inputFile")+fileName+".csv";
	       CSVParser parser = new CSVParser(new FileReader(path), format);
	       
	  		int i=1;
	       switch(fileName) {
	       		case "blogMigration":
		            for(CSVRecord record : parser){
		         	   if((record.get("ExecutionFlag")).equals("Y"))
		         		   dataMapping.put("Article"+i, record.get("Category") + "|" + record.get("ArticleName"));
		         		   i=i+1;
		            }
		            break;
	       		case "linkNavigation":
	                for(CSVRecord record : parser){
	             		   dataMapping.put(record.get("Section")+"_"+i , record.get("linkName") + "|" + record.get("expectedURL"));
	             		   i=i+1;
	                }
	                break;
	       }
	       
	       //close the parser
	       parser.close();      
	       
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 07-Feb-2023
	* Method Name	: writeCSV(String TestID)
	* Description	: To write data to the CSV data file 
	---------------------------------------------------------------------------------------------------------*/
		public static void writeCSV(String fileName, String Article, String status) throws IOException {
	        
			//Create the CSVFormat object
	       CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
	
	       //initialize the CSVParser object
	       String path  = System.getProperty("user.dir")+configProperties.getProperty("inputFile")+fileName+".csv";
	       CSVParser parser = new CSVParser(new FileReader(path), format);
	       
	       
	       FileWriter outputfile = new FileWriter(path); 
	       
	       // create CSVWriter object filewriter object as parameter 
	       CSVWriter writer = new CSVWriter(outputfile); 
	 
	       // adding header to csv 
	       String[] header = { "Name", "Class", "Marks" }; 
	       writer.writeNext(header); 
	 
	       // add data to csv 
	       String[] data1 = { "Aman", "10", "620" }; 
	       writer.writeNext(data1); 
	       String[] data2 = { "Suraj", "10", "630" }; 
	       writer.writeNext(data2); 
	 
	       // closing writer connection 
	       writer.close(); 
	       
	  		int i=1;
	  		for(CSVRecord record : parser){
	      	   if((record.get("ArticleName")).equals(Article))
	      		   dataMapping.put("Article"+i, record.get("Category") + "|" + record.get("ArticleName"));
	      		   i=i+1;
	         }
	  		
		}
		
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 07-Feb-2023
	* Method Name	: createAndwriteToFile
	* Description	: To write data to the txt file. output file0
	---------------------------------------------------------------------------------------------------------*/
		public static void createAndwriteToFile(String outputData, String fileName) {
			try{
	            // Create new file
	
				String reportFileName = createfileNamewithDate(fileName,"txt");
				
	            String path=System.getProperty("user.dir")+configProperties.getProperty("outputFile")+"\\"+reportFileName;
	            System.out.println(path);
	            File file = new File(path);
	
	            // If file doesn't exists, then create it
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	
	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	
	            // Write in file
	            bw.write(outputData);
	
	            // Close connection
	            bw.close();
	        }
	        catch(Exception e){
	            System.out.println(e);
	        }
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 07-Feb-2023
	* Method Name	: createfileNamewithDate
	* Description	: To update a filename with the current date and timestamp.
	---------------------------------------------------------------------------------------------------------*/
		public static String createfileNamewithDate(String fileName,String fileType) {
			Date currentTime = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
	
	
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = simpleDateFormat.format(currentTime);
			if(fileType.equals("pdf")) {
				return fileName+"_$1$.pdf".replace("$1$", sdf.format(currentTime));
			}else {
				return fileName+"_$1$.txt".replace("$1$", sdf.format(currentTime));
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 07-Feb-2023
	* Method Name	: writeExcel
	* Description	: To update a test result to excel file
	---------------------------------------------------------------------------------------------------------*/
		public static void writeExcel(String articleName, String category, String status) {
			File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/outputData.xlsx");
			FileInputStream input;
			try {
				input = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(input);
				XSSFSheet sheet = workbook.getSheet("blogMigration");
				XSSFRow row = sheet.getRow(0);
				int rowCount = sheet.getLastRowNum();
				int columnCount = row.getLastCellNum();
				System.out.println("rowCount:"+rowCount);
				String tcID = "", article = "", categoryValue = "";
				for(int i=1; i<rowCount; i++) {
					//columnName = sheet.getRow(0).getCell(i).getStringCellValue();
					categoryValue = sheet.getRow(i).getCell(2).getStringCellValue();
					if(categoryValue.equals(category)) {
						article = sheet.getRow(i).getCell(3).getStringCellValue();
						if(article.equals(articleName)) {
							XSSFCell cell = sheet.getRow(i).getCell(12);
							cell.setCellValue(status);
							break;
						}
					}
				}
				input.close();
				FileOutputStream output = new FileOutputStream(file);
				workbook.write(output);
				workbook.close();
				output.close();
			} catch (FileNotFoundException e) {
				System.out.println("Error in writing data in excel" +e);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error in writing data in excel" +e);
				e.printStackTrace();
			}catch (Exception e) {
				System.out.println("Error in writing data in excel" +e);
				e.printStackTrace();
			}
		}
	
	/*------------------------------------------------------------------------------------------------------
	* Author		: Maheswari Muthu
	* Date			: 07-Feb-2023
	* Method Name	: csvFileReader
	* Description	: Instance object creation
	---------------------------------------------------------------------------------------------------------*/
		 public static csvFileReader getInstance() {
			 csvFileReader csvReader = null;
				if(csvReader == null) {
					csvReader = new csvFileReader();
				}
				return csvReader;
		};
	
}
