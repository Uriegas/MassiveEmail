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
	public StringProperty masterPasswordProperty(){
		return this.masterPassword;
	}
	public String getMasterPassword(){
		return this.masterPassword.get();
	}
	public void setMasterPassword(String value){
		this.masterPassword.set(value);
	}
	/**
	 * Validate that the parameter is the password of the system
	 * @param s
	 * @return true for successful and false for fail
	 */
	public boolean validateMasterPasswd(String s){
		if( this.getMasterPassword() == null || this.getMasterPassword().isEmpty() ){//There is no master passwd so set it
			setMasterPassword(s);
			return true;
		}
		if( this.getMasterPassword().equals(s) )
			return true;
		return false;
	}
}
