package com.uriegas.Model;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * JavaFX App
 */
public class UseJavaMail{
	private static Session session;

	public static Session getSession(){return session;}
	/**
	 * Establish a connection ({@link Session}) to the SMTP server
	 * TODO: Add OAUTH2 authentication: https://stackoverflow.com/q/34307661/13991148
	 * https://medium.com/@ihorsokolyk/two-factor-authentication-with-java-and-google-authenticator-9d7ea15ffee6
	 * @param cuenta {@link Account}
	 * @throws AuthenticationFailedException
	 * @throws MessagingException
	 */
	public static void Login(Account cuenta) throws AuthenticationFailedException, MessagingException {
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
		//-->Validate session
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", 465, cuenta.getEmail(), cuenta.getContrasenia());
		transport.close();
		//<--Validate session
		System.out.println("Session created");
	}

	/**
	 * Utility method to send simple HTML email
	 * @param mensaje
	 */
	public static void sendEmail(/*Session session,*/ Mail mensaje){
		try
		{

			Message msg = new MimeMessage(getSession());

			//set message headers
			msg.addHeader("X-Priority", "1");
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(session.getProperty("mail.from"), session.getProperty("mail.from.alias")));

			msg.setReplyTo(msg.getReplyTo());

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

			System.out.println("Email sent successfully!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} 
}