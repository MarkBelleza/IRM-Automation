package ca.IRM.selenium.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;



public class ReadXLSData {
	
	public String[] getData(String excelSheetName) throws EncryptedDocumentException, IOException {
//		Get file
		File f = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\data.xlsx");
		FileInputStream fis = new FileInputStream(f);
		
//		Get the sheet we want from the excel doc
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheetName = wb.getSheet(excelSheetName);
		
//		Count the rows of the sheet
		int totalRows = sheetName.getLastRowNum();
		System.out.println(totalRows);
		
//		Count the col of the sheet
		Row rowCells = sheetName.getRow(0);
		int totalCols = rowCells.getLastCellNum();
		System.out.println(totalCols);
		
//		Create the array for that will contain the contents of data.xlsx
		DataFormatter format = new DataFormatter();
		String testData[] = new String[totalRows];
		
		for (int i = 0; i < totalRows; i++) {
			testData[i] = format.formatCellValue(sheetName.getRow(i).getCell(0));
			System.out.println(testData[i]);
		}
		
		return testData;
	}
	
	@DataProvider(name="xmls")
	public String[] getData(Method m) throws EncryptedDocumentException, IOException {
//		
		String excelSheetName = m.getName();
//		Get file
		File f = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\data.xlsx");
		FileInputStream fis = new FileInputStream(f);
		
//		Get the sheet we want from the excel doc
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheetName = wb.getSheet(excelSheetName);
		
//		Count the rows of the sheet
		int totalRows = sheetName.getLastRowNum();
		System.out.println(totalRows);
		
//		Count the col of the sheet
		Row rowCells = sheetName.getRow(0);
		int totalCols = rowCells.getLastCellNum();
		System.out.println(totalCols);
		
//		Create the array for that will contain the contents of data.xlsx
		DataFormatter format = new DataFormatter();
		String testData[] = new String[totalRows];
		
		for (int i = 0; i < totalRows; i++) {
			testData[i] = format.formatCellValue(sheetName.getRow(i).getCell(0));
			System.out.println(testData[i]);
		}
		
		return testData;
		
	}

}
