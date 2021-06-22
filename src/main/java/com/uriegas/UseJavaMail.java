package com.uriegas;
import java.util.*;
import javax.mail.*;

/**
 * JavaFX App
 */
class UseJavaMail{
	private static String fromEmail;
	private static String password;
	private static Session session;

	public static void setFromEmail(String fEmail) {
		fromEmail = fEmail;
	}
	public static void setPassword(String pass) {
		password = pass;
	}

	public static Session getSession(){return session;}

	public static String getFromEmail(){return fromEmail;}
	public static String getPassword(){return password;}

	public static void Login() {
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
				return new PasswordAuthentication(getFromEmail(), getPassword());
			}
		};

		session = Session.getDefaultInstance(props, auth);
		System.out.println("Correo: " + fromEmail);
		System.out.println("Session created");
		//EmailUtil.sendEmail(session, toEmail,"SSLEmail Testing Subject", "SSLEmail Testing Body");
	}
}