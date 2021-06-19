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
				Iterator<Cell> cellIt = rowIt.next().cellIterator();
				values.add(new ArrayList<String>());
				while(cellIt.hasNext()){
					Cell cell = cellIt.next();
					try{
						values.get(values.size()-1).add(cell.getStringCellValue());
					}catch(IllegalStateException e){
						values.get(values.size()-1).add(String.valueOf(cell.getNumericCellValue()));
					}
				}
			}
            //<--Get values of Excel file
            worksheet.close();
            file.close();
			return values;
        }
    }
}
