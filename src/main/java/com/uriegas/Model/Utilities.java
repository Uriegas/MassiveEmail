package com.uriegas.Model;

import java.io.*;
import java.util.*;
import org.apache.poi.xssf.usermodel.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.scene.control.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Util functions to interact with the system
 */
public class Utilities {
    /**
     * Dummy constructor
     */
    public Utilities(){
    }
    /**
     * Reads .xlsx files.
     * Implementes external libraries due to xlsx format.
     * @param String path to read
     * @return table load in memory as an ArrayList<List<String>> object (first row is var_names)
     */
    public static ArrayList<List<String>> readExcel(String path) throws IOException{
        if(!path.endsWith(".xlsx"))//If not an excel file throw exception
            throw new FileNotFoundException("Not valid file extension in file: " + path);
        else{
			if(!path.startsWith("/"))//Get user dir if not an absolute path
				path = System.getProperty("user.dir") + '/' + path;
			//-->Setup
            FileInputStream file = new FileInputStream(new File(path));
            XSSFWorkbook worksheet = new XSSFWorkbook(file);
            XSSFSheet sheet = worksheet.getSheetAt(0);
            Iterator<Row> rowIt = sheet.rowIterator();//Iterator over rows
            ArrayList<List<String>> values = new ArrayList<List<String>>();//DB
            //<--Setup
            //-->Get values of Excel file
			while(rowIt.hasNext()){
                Row row = rowIt.next();
                if(isRowEmpty(row))
                    continue;
				Iterator<Cell> cellIt = row.cellIterator();
				values.add(new ArrayList<String>());
				while(cellIt.hasNext()){
					Cell cell = cellIt.next();
                    if( cell.getCellType() != CellType.BLANK || cell != null ){
					try{
                        if(cell.getStringCellValue() != "")
                            values.get(values.size()-1).add(cell.getStringCellValue());
					}catch(IllegalStateException e){//TODO: Parse Doubles Ex. 10 -> "10"; NOT 10 -> "10.0"
						values.get(values.size()-1).add(String.valueOf(cell.getNumericCellValue()));
					}
                    }
				}
			}
            //<--Get values of Excel file
            worksheet.close();
            file.close();
			return values;
        }
    }
    /**
     * Read the same excel file but creates a TableView to display it using javafx
     * @param row
     * @return TableView<ObservableList<String>>
     */
    public static TableView<ObservableList<String>> readExcelToList(ArrayList<List<String>> excelData){
        // ArrayList<List<String>> excelData = readExcel(path);//Excel table in array format
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();//Excel table in javafx arraylist's
        List<String> headers = excelData.get(0);//Headers of the table

        for(int i = 1; i < excelData.size(); i++)
        {
            data.add(FXCollections.observableArrayList(excelData.get(i)));
        }

        TableView<ObservableList<String>> tableView = new TableView<ObservableList<String>>();
        tableView.setItems(data);

        //Create the table columns, set the cell value factory and add the column to the tableview.
        for (int i = 0; i < excelData.get(0).size(); i++) {
            final int curCol = i;
            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    headers.get(curCol)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }
        return tableView;
    } 
    /**
     * Evaluate if a row is empty.
     * Source from: https://roytuts.com/how-to-detect-and-delete-empty-or-blank-rows-from-excel-file-using-apache-poi-in-java/
     * @param row
     * @return boolean
     */
    private static boolean isRowEmpty(Row row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();
		if (row != null) {
			for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}
		return isEmpty;
	}
    /**
     * Instantiate variables in <var_name> format in a html or txt string
     * Example: Hi <name>, how are you? --> Hi Rigoberta Menchu, how are you?
     * @param txt 
     * @param row values to replace for
     * @param vars values to search and replace
     * @return Parsed text
     */
    public static String instantiateRow(String txt, List<String> row, List<String> vars){
        //Suppose that the row and vars instances are in order
        //TODO: replaceAll whitout regex aproach, change to regex approach and benckmark
        for(int i = 0; i < vars.size(); i++){
            txt = txt.replaceAll(vars.get(i), row.get(i));
        }
        return txt;
    }
    /**
     * Iterates over @see{@link com.uriegas.Utilities#instantiateRow(String, List)}
     * @param String txt template email
     * @param table whit instantiated variables and var_names row
     * @return list of txt's instantiated
     * TODO: Replace the String approach to a Regex approach and benchmark
     */
    public static ArrayList<String> instantiateTable(String txt, ArrayList<List<String>> table){
        ArrayList<String> instantiatedtxts = new ArrayList<String>();
        List<String> var_names = table.remove(0);
        for( int i = 0; i < var_names.size(); i++ )//Add <> to vars
            var_names.set(i, '<' + var_names.get(i) + '>');
        for(List<String> row : table)//Instantiate all vars in txt
            instantiatedtxts.add(instantiateRow(txt, row, var_names));
        return instantiatedtxts;
    }
    /**
     * Search the column where the emails are located
     * @param excel table
     * @return index to the column where the emails are
     */
    public static int findMailColumn(ArrayList<List<String>> excel){
        int col = -1;
        for( int i = 0; i < excel.get(0).size(); i++ )//Search in each column
            if(excel.get(0).get(i).contains("@")){
                col = i; break;
            }
        return col;
    }
}