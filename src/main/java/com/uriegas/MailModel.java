package com.uriegas;

import javafx.beans.*;
import javafx.beans.property.*;
import javafx.collections.*;
/**
 * Data model for the mail app
 */
public class MailModel {
	/**
	 * Master password for the app
	 */
	StringProperty masterPassword = new SimpleStringProperty();
	/**
	 * Array of accounts
	 */
	ObservableList<Account> accounts = FXCollections.observableArrayList(account ->
		new Observable[]{account.emailProperty(), account.contraseniaProperty()});
	/**
	 * Array of mails
	 */
	ObservableList<Mail> mails = FXCollections.observableArrayList(mail ->
		new Observable[]{mail.destinatarioProperty(), mail.cuerpoProperty(), mail.asuntoProperty(), mail.adjuntosProperty()});
	
}
