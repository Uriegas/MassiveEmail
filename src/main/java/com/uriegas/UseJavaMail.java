package com.uriegas;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * JavaFX App
 */
class UseJavaMail{
	private static Session session;

	public static Session getSession(){return session;}

	public static void Login(Cuenta cuenta) {
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
				return new PasswordAuthentication(cuenta.getEmail(), cuenta.getContrasenia());
			}
		};

		session = Session.getDefaultInstance(props, auth);
		System.out.println("Session created");
	}

	/**
	 * Utility method to send simple HTML email
	 * @param mensaje
	 */
	public static void sendEmail(/*Session session,*/ Mensaje mensaje){
		try
		{

			Message msg = new MimeMessage(getSession());

			//set message headers
			msg.addHeader("X-Priority", "1");
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

			msg.setSubject(mensaje.getAsunto());

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mensaje.getDestinatario(), false));

	//------------------------------------------------------------------
			//AQUI EMPIEZA LO CHIDO
			Multipart mp = new MimeMultipart(); //Multipart permite crear un correo por partes

			//Inserto el cuerpo del mensaje
			BodyPart cuerpo = new MimeBodyPart();	//Creo una parte para el meter el cuerpo del mensaje
			cuerpo.setText(mensaje.getCuerpo()); //Asigno el texto a la instancia creada
			mp.addBodyPart(cuerpo);	//inserto la parte creada (cuerpo del mensaje)

			//Adjunto los archivos
			for(int i = 0; i < mensaje.getAdjuntos().size(); i++) {
				MimeBodyPart adjuntar = new MimeBodyPart(); //Creo otra parte para añadir los archivos adjuntos
				adjuntar.attachFile(mensaje.getAdjuntos().get(i));	//Aigno la ruta del archivo
				mp.addBodyPart(adjuntar);	//Inserto la parte creada(Archivo adjunto)
			}

			msg.setContent(mp); //Se rellena el mensaje con las partes creadas
	//------------------------------------------------------------------

			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}