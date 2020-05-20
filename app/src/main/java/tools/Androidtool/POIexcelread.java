package tools.Androidtool;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class POIexcelread {
	XSSFWorkbook workbook;
	
	
	public void testExcel2007Write(String sheetname,String cellvalue) throws IOException{
		workbook=new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet(sheetname);
		XSSFRow row=sheet.createRow(0);
		XSSFCell cell=row.createCell(2);
		cell.setCellValue(cellvalue);
		FileOutputStream fileout=new FileOutputStream("f:\\hello2.xlsx");
		workbook.write(fileout);

	}


}
