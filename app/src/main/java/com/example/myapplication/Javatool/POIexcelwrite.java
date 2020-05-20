package com.example.myapplication.Javatool;

import android.util.Log;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POIexcelwrite {
	/**
	 * 在安卓端实现创建一个xlsx文件
	 * 第三方jar 包在百度网盘里面 ： android 读取excel
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void creatdowneexcel() throws FileNotFoundException, IOException {
		 XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet("信息读取记录");
		XSSFRow row=sheet.createRow(0);
		XSSFCell cell=row.createCell(0);
		XSSFCell cell1=row.createCell(1);
		XSSFCell cell2=row.createCell(2);
//		XSSFCell cell3=row.createCell(3);
//		XSSFCell cell4=row.createCell(4);
//		XSSFCell cell5=row.createCell(5);
//		XSSFCell cell6=row.createCell(6);
		cell.setCellValue("测试次数");
		cell1.setCellValue("测试用例");
		cell2.setCellValue("测试结果");
		FileOutputStream fileout=new FileOutputStream("/mnt/sdcard/result.xlsx");


		
		workbook.write(fileout);
//		System.out.println("创建");
		 Log.v("TAG","创建成功");

	}

	/**
	 *主要功能是打开一个xlsx文件，然后往里面追加数据，creatdowneexcel()方法需要先使用
	 * @param testcount 测试次数
	 * @param testname  测试用例名称
	 * @param result    测试结果
	 */
	public  void writeExcel3(String testcount, String testname, String result) {
		try {
			FileInputStream fileInput = new FileInputStream("/mnt/sdcard/result.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
			XSSFSheet sh = workbook.getSheetAt(0);
			//获取最后一行的值
			int lastRowNum = sh.getLastRowNum();
			 Log.v("TAG","最后一行"+lastRowNum);
			XSSFCell createCell1 = sh.createRow(lastRowNum + 1).createCell(0);
			XSSFCell createCel2 = sh.getRow(lastRowNum + 1).createCell(1);
			XSSFCell createCel3 = sh.getRow(lastRowNum + 1).createCell(2);
			createCell1.setCellValue(testcount);
			createCel2.setCellValue(testname);
			//有失败项红色显示
			if(result.contains("失败")){
				XSSFFont font = workbook.createFont();
				font.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED);
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFont(font);
				createCel3.setCellValue(result);
				createCel3.setCellStyle(cellStyle);
			}else {
				createCel3.setCellValue(result);
			}
			FileOutputStream os = new FileOutputStream("/mnt/sdcard/result.xlsx");
			os.flush();
			workbook.write(os);
			fileInput.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	


}
