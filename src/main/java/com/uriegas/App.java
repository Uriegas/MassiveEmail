package com.uriegas;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * JavaFX App
 */
public class App{
    public static void main(String[] args) {
//      //Declare recipient's & sender's e-mail id.
//      String destmailid = "1930526@upv.edu.com";
//      String sendrmailid = "jesusuriegas18@gmail.com";	  
//     //Mention user name and password as per your configuration
//      final String uname = "jesusuriegas18@gmail.com";
//      final String pwd = "y#91LC*FB2djSU4y%%a%fq61";

        final String fromEmail = "1930526@upv.edu.mx"; //requires valid gmail id
		final String password = ""; // correct password for gmail id
		final String toEmail = "jesusuriegas18@gmail.com"; // can be any email id 
		
		System.out.println("SSLEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		props.put("mail.smtp.port", "465"); //SMTP Port
		
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getDefaultInstance(props, auth);
		System.out.println("Session created");
	        EmailUtil.sendEmail(session, toEmail,"SSLEmail Testing Subject", "SSLEmail Testing Body");
   }
}