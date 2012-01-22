package org.wise4.mail;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.mail.Message.RecipientType;

/**
 * A simple email sender class, used to test if the host server can send email using GMail's SMTP server.
 *
 * Partially followed steps on this page: http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/ "Gmail to SSL" section
 * and other websites.
 *
 * Config settings (ie username,password,toEmailAddress,etc) must be stored in src/main/resources/mail.properties.
 * 
 * Usage 
 * You will need maven and git.
 * 1. Check out the project using git.
 * 2. Copy src/main/resources/sample_mail.properties to src/main/resources/mail.properties and edit the file.
 *
 * Option 1
 * 1. Run mvn test.  This will in effect run the JUnit tests in src/main/test/SendmailTest.java
 *
 * Option 2
 * 1. Import this project in Eclipse (in Eclipse, "File"->"Import..."->"Existing Projects into Workspace")
 * 2. Run Sendmail.java as Java Application. (Right click on Sendmail.java->"Run As"->"Java Application"
 *
 * @author hirokiterashima
 */
public class Sendmail {

	/**
	 * Send mail with specified params
	 * @param from who this is from
	 * @param to who this is for
	 * @param subject subject of the mail
	 * @param text body of the mail
	 */
	public static void send(final Properties props){
		
    	String from = props.getProperty("testmail.from");
		String to = props.getProperty("testmail.to");
		String subject = props.getProperty("testmail.subject");
		String body = props.getProperty("testmail.body");
		
		Session mailSession = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
			}
		});
		Message simpleMessage = new MimeMessage(mailSession);
		
		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		try {
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
		} catch (AddressException e) {
			e.printStackTrace();
		}
		
		try {
			simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setSubject(subject);
			simpleMessage.setText(body);
						
			Transport.send(simpleMessage);			
		} catch (MessagingException e) {
			System.err.println("Error sending mail");
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/main/resources/mail.properties"));
		} catch (FileNotFoundException e1) {
			System.err.println("Properties file not found");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.err.println("Error loading properties file");
			e1.printStackTrace();
		}
		
		Sendmail.send(props);		
	}
}