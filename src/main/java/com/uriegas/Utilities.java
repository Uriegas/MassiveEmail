package com.uriegas;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

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
}
