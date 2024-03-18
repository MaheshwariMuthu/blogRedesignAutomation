package Email_Validator;



import javax.mail.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

import static automationFramework.Constant.BrowserorderNumberText;
import static stepDefinations.Hooks.configProperties;
import static pageActions.HSLandingPageActions.*;


public class Verify_Gmail {

    public static void Validate_Order_Confirmation_Email() throws Exception {

        check(configProperties.getProperty("host"), configProperties.getProperty("mailStoreType"),
                configProperties.getProperty("gmusername"), configProperties.getProperty("gmpassword"));

    }

    public static void check(String host, String storeType, String user,
                             String password) {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.imaps.host", host);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.starttls.enable", "true");
            properties.put("mail.imaps.ssl.trust", "*");
            properties.setProperty("mail.store.protocol", "imaps");

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();

            store.connect(host, 993, user+"@gmail.com", password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int n = messages.length, i = n - 1; i > n - 6; i--) {
                Message message = messages[i];
                if (message.getSubject().contains("Thank You For Your Order")) {
                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("ReceivedDate: " + message.getReceivedDate());
                    System.out.println("ContentType: " + message.getContentType());
                    System.out.println("currentDateandTime: " + currentDateandTime);
                    String sentTimestamp = currentDateandTime; // Replace with the actual sent timestamp
                    String receivedTimestamp = message.getReceivedDate().toString(); // Replace with the actual received timestamp

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));

                    try {
                        Date sentTime = dateFormat.parse(sentTimestamp);
                        Date receivedTime = dateFormat.parse(receivedTimestamp);

                        if (receivedTime.after(sentTime)) {
                            System.out.println("The received email is the latest one you sent.");
                        } else {
                            System.out.println("The received email is not the latest one you sent.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing timestamps: " + e.getMessage());
                    }

                    printPlainTextContent(message);
                }

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printPlainTextContent(Message message) throws Exception {
        Object content = message.getContent();

        if (content instanceof String) {
            String mimeType = message.getContentType();
            if (mimeType.startsWith("text/plain")) {
                System.out.println(content);
            } else if (mimeType.startsWith("TEXT/HTML")) {
                String plainText = htmlToPlainText((String) content);
                System.out.println("EmailBody: \n" + plainText);
                String pattern = "Order number: ([A-Z0-9]+)";

                // Create a Pattern object
                Pattern regex = Pattern.compile(pattern);

                // Create a Matcher object and apply the pattern to the email body
                Matcher matcher = regex.matcher(plainText);

                // Check if a match is found
                if (matcher.find()) {
                    // Extract the matched text
                    String EmailOrderNumber = matcher.group(0);
                    System.out.println( EmailOrderNumber);
                    String EmailTrimmedOrderNumber = EmailOrderNumber.replace("Order number: ", "");
                    System.out.println("OrderNumber from EMail= " +EmailTrimmedOrderNumber);
                    System.out.println("OrderNumber from Browser= " + BrowserorderNumberTexts);

                    if(EmailTrimmedOrderNumber.equalsIgnoreCase(BrowserorderNumberTexts)){
                        System.out.println("Order ID's Matched in Both UI and EMAIL");
                    }else{
                        System.out.println("Order ID's DID Matched in Both UI and EMAIL");
                       // throw new Exception();
                    }

                } else {
                    System.out.println("Order number not found in the email body.");
                }


            }
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    System.out.println(bodyPart.getContent());
                } else if (bodyPart.isMimeType("TEXT/HTML")) {
                    String plainText = htmlToPlainText((String) bodyPart.getContent());
                    //   System.out.println("EmailBody: \n" + plainText);
                    String pattern = "Order number: ([A-Z0-9]+)";

                    // Create a Pattern object
                    Pattern regex = Pattern.compile(pattern);

                    // Create a Matcher object and apply the pattern to the email body
                    Matcher matcher = regex.matcher(plainText);

                    // Check if a match is found
                    if (matcher.find()) {
                        // Extract the matched text
                        String orderNumber = matcher.group(0);
                        System.out.println( orderNumber);
                        String MyOrdereNumber = orderNumber.replace("Order number: ", "");
                        System.out.println("OrderNumber from EMail= " +MyOrdereNumber);

                        if(MyOrdereNumber.equalsIgnoreCase(BrowserorderNumberText)){
                            System.out.println("Order ID's Matched in Both UI and EMAIL");
                        }else{
                            System.out.println("Order ID's DID Matched in Both UI and EMAIL");
                            throw new Exception();
                        }

                    } else {
                        System.out.println("Order number not found in the email body.");
                    }

                }
            }
        }
    }

    private static String htmlToPlainText(String htmlContent) {
        return Jsoup.parse(htmlContent).text();
    }

}

