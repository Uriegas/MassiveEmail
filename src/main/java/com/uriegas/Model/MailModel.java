package com.uriegas.Model;

import java.util.ArrayList;

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
	ObservableList<String> currentAdjuntos = FXCollections.observableArrayList();
	/**
	 * Returns the account list
	 * @return {@link ObservableList}<{@link Account}>
	 */
	public ObservableList<Account> getAccountList(){
		return accounts;
	}
	/**
	 * Find if the given account is the saved accounts
	 * @param account
	 * @return true if inside accounts, false otherwise 
	 */
	public boolean isInAccounts(Account account){
		for(Account a : accounts )
			if(a.equals(account))
				return true;
		return false;
	}
	/**
	 * Get adjuntos property
	 * @return
	 */
	public ObservableList<String> adjuntosProperty(){
		return this.currentAdjuntos;
	}
	/**
	 * Get adjuntos
	 * @return
	 */
	public ArrayList<String> getAdjuntos(){
		ArrayList<String> adjuntos = new ArrayList<String>();
		for(String a : this.currentAdjuntos)
			adjuntos.add(a);
		return adjuntos;
	}
}
