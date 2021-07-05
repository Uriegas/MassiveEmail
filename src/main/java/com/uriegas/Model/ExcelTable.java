package com.uriegas.Model;

import java.util.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
/**
 * Wrapper of a TableView
 */
public class ExcelTable extends TableView<ObservableList<String>> {
	private ArrayList<String> headers;

	public ExcelTable(){
		super();
	}
	public ExcelTable(ArrayList<String> h){
		super();
		this.headers = h;
	}
	public ArrayList<String> getHeaders(){
		return this.headers;
	}
	public void setHeaders(ArrayList<String> h){
		this.headers = h;
	}
	/**
	 * Return rows in ArrayList without the headers
	 * @return
	 */
	public ArrayList<ObservableList<String>> getRows(){
		ArrayList<ObservableList<String>> tmp = new ArrayList<ObservableList<String>>();
		for( int i = 0; i < this.getItems().size(); i++ )
			tmp.add( this.getItems().get(i) );
		return tmp;
	}

	/**
	 * Get the email directions of each person: {@code 1930526@upv.edu.mx}
	 * @return List of emails
	 */
	public ArrayList<String> getReceivers() throws NumberFormatException {
		ObservableList<String> row = this.getItems().get(0);//Get first user row
		int column = -1;
		//-->Get column with emails directions
		for( int i = 0; i < row.size(); i++ )
			if(row.get(i).contains("@")){
				column = i; break;}
		if( column < -1 )
			throw new NumberFormatException("This table doesn't have valid emails");
		//<--Get column with emails directions

		//-->Return the row with the items
		ArrayList<String> receivers = new ArrayList<String>();//Emails
		for( ObservableList<String> aRow : this.getItems() )
				receivers.add( aRow.get(column) );
		return receivers;
		//<--Return the row with the items
	}
	/**
	 * Get the variables for each row aka. person <p>
	 * Example: {@code [ ("NAME", "Uriegas"), ("DIRECCION", "Victoria") ]}
	 * @return A list of hashmap, each hashmap links a var with a value
	 */
	public ArrayList<HashMap<String, String>> getVars(){
		ArrayList<String> headers = this.getHeaders();//Get keys (var names)
		ArrayList<HashMap<String, String>> vars = new ArrayList<HashMap<String, String>>();//Get values(instance of vars)
		for( ObservableList<String> row : this.getItems() ){//Iterate over each row in the table
			HashMap<String, String> tmp = new HashMap<String, String>();
			for( int j = 0; j < row.size(); j++ ){//Iterate over each var in the table
				tmp.put(headers.get(j), row.get(j));//Add key (var name) and value (var instance) to map
			}
			vars.add(tmp);//Add map to array
		}
		return vars;
	}
}
