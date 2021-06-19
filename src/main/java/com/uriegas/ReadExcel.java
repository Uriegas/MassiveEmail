package com.uriegas;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

/**
 * Util functions to interact with the system
 */
public class ReadExcel {
    /**
     * Dummy constructor
     */
    public ReadExcel(){
    }
    /**
     * readFile method: reads .equ and .xlsx files,
     * implementes external libraries due to xlsx format
     * @param String path to read
     * @return table load in memory as an ArrayList<List<String>> object
     */
    public ArrayList<List<String>> readFile(String path) throws IOException{
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
					}catch(IllegalStateException e){
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
     * @return boolean: is empty or not
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
}
