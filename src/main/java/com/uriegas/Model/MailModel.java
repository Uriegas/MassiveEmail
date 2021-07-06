package com.uriegas.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
/**
 * Data model for the mail app, the serialization is implementated overloading the write and read methods
 * the attributes are labeled as transient to not mess up with java serialization
 */
public class MailModel implements Serializable {
	/**
	 * Master password for the app
	 */
	private transient StringProperty masterPassword = new SimpleStringProperty();
	/**
	 * Array of accounts
	 */
	private transient ObservableList<Account> accounts = FXCollections.observableArrayList(account ->
		new Observable[]{account.emailProperty(), account.contraseniaProperty()});
	/**
	 * Array of mails, not serialized
	 */
	private transient ObservableList<Mail> mails = FXCollections.observableArrayList(mail ->
		new Observable[]{mail.destinatarioProperty(), mail.cuerpoProperty(), mail.asuntoProperty(), mail.adjuntosProperty()});
	/**
	 * Current list of adjuntos files
	 */
	private transient ObservableList<String> currentAdjuntos = FXCollections.observableArrayList();
	/**
	 * Current table view
	 */
	private transient ExcelTable excelTable = new ExcelTable();
	/**
	 * Last 5 opened excel files
	 */
	private transient ObservableList<String> lastViewedExcels = FXCollections.observableArrayList();

	//-->accounts methods
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
	//<--accounts methods

	//-->mails methods
	public ObservableList<Mail> mailsProperty(){
		return this.mails;
	}
	public ArrayList<Mail> getMails(){
		ArrayList<Mail> tmp = new ArrayList<Mail>();
		for( Mail m : mails )
			tmp.add(m);
		return tmp;
	}
	public void addMail(Mail m){
		this.mails.add(m);
	}
	public void addMails(ArrayList<Mail> mails){
		this.mails.addAll(mails);
	}
	//<--mails methods

	//-->adjuntos methods
	public ObservableList<String> adjuntosProperty(){
		return this.currentAdjuntos;
	}
	public ArrayList<String> getAdjuntos(){
		ArrayList<String> adjuntos = new ArrayList<String>();
		for(String a : this.currentAdjuntos)
			adjuntos.add(a);
		return adjuntos;
	}
	public void setAdjuntos(ArrayList<String> s){
		this.adjuntosProperty().addAll(s);
	}
	public void setAdjuntos(String s){
		this.adjuntosProperty().add(s);
	}
	//<--adjuntos methods

	//-->masterPassword methods 
	public StringProperty masterPasswordProperty(){
		return this.masterPassword;
	}
	public String getMasterPassword(){
		return this.masterPassword.get();
	}
	public void setMasterPassword(String value){
		this.masterPassword.set(value);
	}
	//<--masterPassword methods 

	//-->excelTable methods 
	public ExcelTable excelTableProperty(){
		return this.excelTable;
	}
	// public String getExcelTable(){
	// 	for(ObservableList<String> row : this.excelTable.getItems())

	// }
	/**
	 * Converts a given table into a TableView.
	 * TODO: Eror handling
	 * @param table
	 */
	public void setExcelTable(ArrayList<List<String>> table){
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();//Excel table in javafx arraylist's
        List<String> headers = table.get(0);//Headers of the table

        for(int i = 1; i < table.size(); i++)
            data.add(FXCollections.observableArrayList(table.get(i)));

        this.excelTable.setItems(data);

        //Create the table columns, set the cell value factory and add the column to the tableview.
        for (int i = 0; i < table.get(0).size(); i++) {
            final int curCol = i;
            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    headers.get(curCol)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            this.excelTable.getColumns().add(column);
        }
		this.excelTable.setHeaders((ArrayList<String>)headers);
	}
	//<--excelTable methods 

	//-->lastViewedExcels methods 
	public ObservableList<String> lastViewedExcelsProperty(){
		return this.lastViewedExcels;
	}
	public ArrayList<String> getLastViewedExcels(){
		ArrayList<String> tmp = new ArrayList<String>();
		for(String a : this.lastViewedExcels)
			tmp.add(a);
		return tmp;
	}
	public void setLastViewedExcels(String value){
		if(this.lastViewedExcels.isEmpty())
			this.lastViewedExcels.add(value);
		else
			for( String s : this.lastViewedExcels )
				if(!s.equals(value))
					this.lastViewedExcels.add(value);
	}
	public void setLastViewedExcels(ArrayList<String> values){
		this.lastViewedExcels.addAll(values);
	}
	//<--lastViewedExcels methods 

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
	/**
	 * Serialize this object, expect for the mails
	 * Doesn't save mails and adjuntos
	 * TODO: In following versions we can add an option to save messages and dues serialize them
	 * @param s
	 * @throws Exception
	 */
    private void writeObject(ObjectOutputStream s) throws Exception {
        // s.defaultWriteObject();
		s.writeUTF(getMasterPassword());
		s.writeObject(new ArrayList<Account>(accounts));
		s.writeObject(new ArrayList<String>(lastViewedExcels));
		s.writeObject(new ArrayList<Mail>(mails));
    }
	/**
	 * Serialize this object, expect for the mails
	 * TODO: In following versions we can add an option to save messages and dues serialize them
	 * TODO implement accounts encryption
	 * @param s
	 * @throws Exception
	 */
    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject();
		masterPassword = new SimpleStringProperty(s.readUTF());
		accounts = FXCollections.observableList((List<Account>)s.readObject());
		lastViewedExcels = FXCollections.observableList((List<String>)s.readObject());
		mails = FXCollections.observableList((List<Mail>)s.readObject());
		//-->Initialize not serialized objects(if not initialized they are null)
		// mails = FXCollections.observableArrayList(mail ->
		// new Observable[]{mail.destinatarioProperty(), mail.cuerpoProperty(), mail.asuntoProperty(), mail.adjuntosProperty()});
		currentAdjuntos = FXCollections.observableArrayList();
		// excelTable = new TableView<ObservableList<String>>();
		excelTable = new ExcelTable();
		//<--Initialize not serialized objects(if not initialized they are null)
    }
}
